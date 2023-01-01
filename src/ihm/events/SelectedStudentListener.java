package ihm.events;

import ihm.Interface;
import ihm.utils.RightClickMenu;
import javafx.collections.ListChangeListener;
import oop.Student;

public class SelectedStudentListener implements ListChangeListener<Student> {
    Interface iface;

    public SelectedStudentListener(Interface iface) {
        this.iface = iface;
    }


    // @Override
    // public void handle(ActionEvent e) {
    //     Student student = (Student)e.getTarget();
    //         iface.selectedStudent = student;
    // }


    @Override
    public void onChanged(Change<? extends Student> changed) {
        if (changed.getList().size()>0){
            iface.selectedStudent=changed.getList().get(0);
            RightClickMenu.replace.setDisable(iface.dpt.tutoring.affectations.size()==0 || iface.selectedStudent.isTutored());// || (!iface.selectedStudent.isTutored() && ((Tutor) iface.selectedStudent).isDuplicate()));
            if (iface.selectedStudent.isTutored()){
                iface.tutorsView.getSelectionModel().clearSelection();
            } else {
                iface.tutoredView.getSelectionModel().clearSelection();
            }
        }
        
    }

}