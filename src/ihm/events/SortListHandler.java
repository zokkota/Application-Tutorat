package ihm.events;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import ihm.Interface;
import ihm.comparators.AffectationComparator;
import ihm.comparators.StudentAbsencesComparator;
import ihm.comparators.StudentAverageComparator;
import ihm.comparators.StudentForenameComparator;
import ihm.comparators.StudentMotivationComparator;
import ihm.comparators.StudentSurnameComparator;
import ihm.utils.TutoringUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import oop.Student;

public class SortListHandler implements EventHandler<ActionEvent> {
    Interface iface;
    String filter ="nom";

    public SortListHandler(Interface iface){
        this(iface, "nom");
    }

    public SortListHandler(Interface iface, String filter) {
        this.iface = iface;
        this.filter = filter;
    }

    public void handle(ActionEvent e) {
        boolean reverse;
        if (e.getTarget() instanceof Button) {
            reverse = ((Button) e.getTarget()).getText().equals("▲");
        }
        else {
            reverse = false;
        } 

        if (iface.dpt.tutoring.affectations.size() == 0){
            firstSorting(reverse);
        }
        else {
            affectationSorting(reverse);
        }
        
        TutoringUtils.updateLists(iface);
        
    }

    private void affectationSorting(boolean reverse){
        Collections.sort(iface.dpt.tutoring.affectations, new AffectationComparator(iface, getComparator(iface, filter)));
        if (reverse) {
            Collections.reverse(iface.dpt.tutoring.affectations);
        }
    }

    private void firstSorting(boolean reverse){
        List<? extends Student> list = (iface.filterTutored) ? iface.dpt.tutoring.getTutored() : iface.dpt.tutoring.getTutors();
        list.sort(SortListHandler.getComparator(iface, filter));
        if (reverse){
            Collections.reverse(list);
        }
    }

    private static Comparator<Student> getComparator(Interface iface, String str){    
        return  
        switch (str) {
            case "nom" -> new StudentSurnameComparator();
            case "pre" -> new StudentForenameComparator();
            case "avg" -> new StudentAverageComparator(iface.dpt.tutoring.getResource());
            case "abs" -> new StudentAbsencesComparator();
            case "mot" -> new StudentMotivationComparator();           
            default -> null;
        };
    }
}
