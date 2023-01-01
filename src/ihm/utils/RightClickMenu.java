package ihm.utils;

import ihm.Interface;
import ihm.events.Events;
import ihm.popup.StudentForm;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import oop.Tutor;

public class RightClickMenu {
    // public static MenuItem info = new MenuItem("Informations");
    public static MenuItem force = new MenuItem("Forcer une affectation");
    public static MenuItem forbid = new MenuItem("Interdire une affectation");  
    public static MenuItem remove = new MenuItem("Retirer du tutorat");
    public static MenuItem add = new MenuItem("Ajouter un étudiant");
    public static MenuItem replace = new MenuItem("Supprimer et remplacer");


    public static ContextMenu get(Interface iface) {
        ContextMenu menu = new ContextMenu(
            // info,
            // new SeparatorMenuItem(),
            force,
            forbid,            
            new SeparatorMenuItem(),
            replace,
            remove,
            add
            );

        force.setOnAction(e -> Events.AddForcedAffectationHandler(iface, false));
        forbid.setOnAction(e -> Events.AddForcedAffectationHandler(iface, true));
        remove.setOnAction(e -> Events.RemoveStudentHandler(iface));
        add.setOnAction(e -> Events.AddStudentHandler(iface));
        replace.setOnAction(e -> new StudentForm(iface, (Tutor)iface.selectedStudent));
        return menu;
    }
}
