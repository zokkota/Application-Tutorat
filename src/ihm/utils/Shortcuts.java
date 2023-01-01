package ihm.utils;

import ihm.Interface;
import ihm.events.AffectationGlimpseManager;
import ihm.events.Events;
import ihm.popup.StudentForm;
import oop.Tutor;

public class Shortcuts {

    public Shortcuts(Interface iface) {
        init(iface);
    }

    public static void init(Interface iface) {
        iface.scene.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case MINUS, SUBTRACT, D, DELETE -> Events.RemoveStudentHandler(iface);
                case ADD, PLUS, N -> Events.AddStudentHandler(iface);
                case F5, ENTER -> Events.AffectationHandler(iface);
                case F -> Events.AddForcedAffectationHandler(iface, false);
                case I -> Events.AddForcedAffectationHandler(iface, true);
                case ESCAPE -> iface.close();
                case R -> {if (!iface.selectedStudent.isTutored()) new StudentForm(iface, (Tutor)iface.selectedStudent);}
                case V -> new AffectationGlimpseManager(iface);
                default -> nothing();
            }
        });
    }
    private static void nothing() {
    }

}
