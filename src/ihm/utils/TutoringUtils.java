package ihm.utils;

import graphs.Couple;
import graphs.Tutoring;
import ihm.Interface;
import javafx.scene.paint.Color;
import oop.Resource;
import oop.Student;
import oop.Tutor;

public class TutoringUtils {  
    public static final double LIST_CELL_HEIGHT = 25;


    public static void updateLists(Interface iface) {
        iface.tutorsView.getItems().clear();
        iface.tutoredView.getItems().clear();
        iface.couplesView.getItems().clear();
        if (iface.dpt.tutoring.affectations.size() ==0 ){
            iface.scrollBarOne.valueProperty().unbindBidirectional(iface.scrollBarTwo.valueProperty());
            iface.scrollBarOne.valueProperty().unbindBidirectional(iface.scrollBarThree.valueProperty());

            iface.tutorsView.getItems().addAll(iface.dpt.tutoring.getTutors()); 
            iface.tutoredView.getItems().addAll(iface.dpt.tutoring.getTutored());
        }
        else {
            iface.scrollBarOne.valueProperty().bindBidirectional(iface.scrollBarTwo.valueProperty());
            iface.scrollBarOne.valueProperty().bindBidirectional(iface.scrollBarThree.valueProperty());
            for (Couple couple : iface.dpt.tutoring.affectations){
                iface.tutoredView.getItems().add(couple.getTutored());
                iface.tutorsView.getItems().add(couple.getTutor());
                iface.couplesView.getItems().add(couple);
            }
            for (Student student : iface.dpt.tutoring.getWaitingList()){
                if (student.isTutored()){
                    iface.tutoredView.getItems().add(student);
                    iface.tutorsView.getItems().add(null);
                }
                else {
                    iface.tutorsView.getItems().add(student);
                    iface.tutoredView.getItems().add(null);
                }
            }
            

        }
        updateTutoringInfos(iface);
    }



    public static void updateTutoringInfos(Interface iface){
        if (iface.dpt.tutoring == null){
            // iface.tutoringTeacherLb.setText(null); 
            iface.tutoringAffectedLb.setText(null); 
            iface.tutoringForcedLb.setText(null); 
            iface.tutoringForbiddenLb.setText(null); 
            iface.tutoringAwaitingLb.setText(null); 
            iface.tutoringTutorNbLb.setText("?");             
            iface.tutoringTutoredNbLb.setText("?"); 

        }
        else {
            Tutoring tut = iface.dpt.tutoring;
            int ratio = (int) (100.00*( (double)tut.affectations.size() / tut.getTutored().size()));
            iface.tutoringAffectedLb.setText(ratio + "%");
            iface.tutoringAffectedLb.setTextFill( ratio == 100 ? Color.GREEN : Color.BLACK);
            int att = tut.getTutored().size() - tut.affectations.size();
            iface.tutoringForcedLb.setText(""+tut.getForcedCouples().size());
            iface.tutoringForbiddenLb.setText(""+tut.getForbiddenCouples().size());
            iface.tutoringAwaitingLb.setText(""+att); 
            iface.tutoringAwaitingLb.setTextFill(att > 0 ? Color.RED : Color.BLACK);
            iface.tutoringTutorNbLb.setText(""+tut.getTutors().size());             
            iface.tutoringTutoredNbLb.setText(""+tut.getTutored().size());
        }
        
    }
    static String lineSeparator(String str){
        StringBuilder sb = new StringBuilder();
        for (int i =0 ; i< str.length() ; i++){
            sb.append("﹘");
        }
        return sb.toString();
    }

    public static String getStudentLabel(Student item, Interface iface) {
        Resource resource=iface.dpt.tutoring.getResource();
        return 
        item.getName() + "\t(" + (item.isTutored()? "Tutoré" : ("Tuteur" + (((Tutor)item).isDuplicate() ? " dupliqué" : ""))) + ")"
        + " \n" + 
        lineSeparator(item.getName() + "\t(" + (item.isTutored()? "Tutoré" : "Tuteur") + ")")
        + " \nNotes de " + resource.getName() + " :\t" + item.getGrade(resource)  
        + " \nAbsences :\t\t" + item.getAbsences()
        + " \nAnnée :\t\t\t" + item.getLevel() 
        + " \nMotivation :\t\t" + item.getMotivation();

    }
}
