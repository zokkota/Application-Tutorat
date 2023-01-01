package ihm.events;

import ihm.Interface;
import ihm.utils.TutoringUtils;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import oop.Resource;

public class TutoringSelectorListener implements ChangeListener<Resource> {
    Interface iface;
    public TutoringSelectorListener(Interface iface){
        this.iface = iface;
    }
    public void changed(ObservableValue<? extends Resource> option, Resource old, Resource newVal) {
        iface.disableTutoringControls(false);
        iface.dpt.tutoring = iface.dpt.getTutoring(newVal);
        iface.polyTutoring.setSelected(iface.dpt.tutoring.IsPolyTutor());
        TutoringUtils.updateLists(iface);
    }

}