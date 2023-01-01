package oop;

import java.util.Comparator;

import graphs.Tutoring;
import utility.Couples;

public class StudentPriorityComparator implements Comparator<Student> {
    Tutoring tutorat;

    public StudentPriorityComparator(Tutoring tutorat){
        this.tutorat= tutorat;
    }

    @Override
    public int compare(Student o1, Student o2) {
        if (Couples.containsStudent(tutorat.getForcedCouples(), o1)) {
            return -1;
        }
        if (Couples.containsStudent(tutorat.getForcedCouples(), o2)) {
            return 1;
        }
        return (int) (100* (o1.getWeight(tutorat.getResource()) - o2.getWeight(tutorat.getResource())));
    }
    
}
