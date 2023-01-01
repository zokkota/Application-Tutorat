package ihm.events;

import java.util.LinkedList;
import java.util.List;

import ihm.Interface;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Slider;
import oop.Coefficient;
import oop.Teacher;

public class SliderListener implements ChangeListener<Number> {
    static List<Double[]> coefsUndo = new LinkedList<>();
    static List<Double[]> coefsRedo = new LinkedList<>();

    Interface iface;
    Slider slider;
    Coefficient coef;

    public SliderListener(Interface iface, Slider slider, Coefficient coef) {
        this.iface = iface;
        this.slider = slider;
        this.coef = coef;
    }

    @Override
    public void changed(ObservableValue<? extends Number> obs, Number oldVal, Number newVal) {
        slider.setValue(Math.ceil((double) newVal * 2) / 2.0d);
        iface.dpt.teacher.setWeighting(coef, (double) newVal);
        updateDisabled(iface);
    }

    private static void updateDisabled(Interface iface){        
        iface.undoCoef.setDisable(coefsUndo.size() == 0);
        iface.redoCoef.setDisable(coefsRedo.size() == 0);
    }

    public static void saveCoefs(Teacher teacher){
        coefsUndo.add(0, new Double[] {
            teacher.getAverageWeighting(),
            teacher.getAbsenceWeighting(),
            teacher.getLevelWeighting()
        });
    }

    public static void restoreCoefs(Interface iface, Teacher teacher){
        Double[] coef = coefsUndo.remove(0);
        coefsRedo.add(0, coef);
        System.out.println(coefsUndo.size() +", "+coefsRedo.size());
        teacher.setWeighting(coef);
        iface.setCoefs(teacher);
        updateDisabled(iface);
    }

    public static void reEditCoefs(Interface iface, Teacher teacher){
        Double[] coef = coefsRedo.remove(0);
        coefsUndo.add(0, coef);
        System.out.println(coefsUndo.size() +", "+coefsRedo.size());
        teacher.setWeighting(coef);
        iface.setCoefs(teacher);
        updateDisabled(iface);
    }

}
