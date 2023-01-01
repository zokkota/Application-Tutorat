package ihm.events;

import java.util.Optional;

import ihm.Interface;
import ihm.popup.StudentForm;
import ihm.popup.Login;
import ihm.utils.StudentCellFactory;
import ihm.utils.TutoringUtils;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import oop.Student;
import oop.Tutor;
import oop.Tutored;

public class Events {

    public static void RemoveStudentHandler(Interface iface) {
        if (iface.dpt.tutoring == null) {
            return;
        }
        if (iface.selectedStudent != null) {
            Alert alert = new Alert(AlertType.WARNING,
                    "Vous allez supprimer " + iface.selectedStudent.getName() + ". Êtes-vous certain(e)?",
                    ButtonType.YES, ButtonType.CANCEL);
            alert.headerTextProperty().set("");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.YES) {
                if (!iface.selectedStudent.isTutored()) {
                    iface.dpt.tutoring.removeStudent((Tutor) iface.selectedStudent);
                } else {
                    iface.dpt.tutoring.removeStudent((Tutored) iface.selectedStudent);

                }
                iface.selectedStudent = null;
                TutoringUtils.updateLists(iface);
            }
        }
    }

    public static void AddForcedAffectationHandler(Interface iface, boolean interdite){
        if (iface.dpt.tutoring == null || iface.selectedStudent == null) {
            return;
        }
        new StudentForm(iface, interdite);
    }

    public static void AddStudentHandler(Interface iface) {
        if (iface.dpt.tutoring == null) {
            return;
        }
        new StudentForm(iface);
    }

    public static void AffectationHandler(Interface iface) {
        if (iface.dpt.tutoring == null) {
            return;
        }
        iface.dpt.tutoring.affectations();
        TutoringUtils.updateLists(iface);

    }

    public static void DragNDropHandler(Interface iface, Student entered, boolean interdite) {
        if (StudentCellFactory.draggedStudent == null){
        } else if ((!entered.isTutored() && ((Tutor)entered).isDuplicate()) 
            |(entered.getClass().equals(StudentCellFactory.draggedStudent.getClass()))){
        }
        else {
            if (StudentCellFactory.draggedStudent.isTutored()) {
                ForcedAffectationHandler(iface, (Tutored) StudentCellFactory.draggedStudent, (Tutor) entered, interdite);
            } else {
                ForcedAffectationHandler(iface, (Tutored) entered, (Tutor) StudentCellFactory.draggedStudent, interdite);
            }
        }
        TutoringUtils.updateLists(iface);
        StudentCellFactory.draggedStudent = null;

    }

    public static void ForcedAffectationHandler(Interface iface, Tutored tutored, Tutor tutor, boolean interdite) {
        Alert alert = new Alert(AlertType.CONFIRMATION,
                "Vous allez " + (interdite ? "interdire" : "forcer") + " l'affectation entre " + tutored.getName()
                        + " et " + tutor.getName() + ". Êtes-vous certain(e)?",
                ButtonType.YES, ButtonType.CANCEL);
        alert.headerTextProperty().set("");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.YES) {
            if (interdite) {
                iface.dpt.tutoring.addForbiddenAssignments(tutored, tutor);
            } else {
                iface.dpt.tutoring.addForcedAssignments(tutored, tutor);
            }
        }
    }

    public static void AuthentificationHandler(Interface iface) {
        if (!Login.loggedIn) {
            new Login(iface);
        } else {
            Alert alert = new Alert(AlertType.CONFIRMATION,
                    "Vous allez vous déconnecter. Êtes-vous certain(e)?",
                    ButtonType.YES, ButtonType.CANCEL);
            alert.headerTextProperty().set("");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.YES) {
                Login.loggedIn = false;
                Login.account = null;
                Login.updateSession(iface);
            }
        }
    }

}
