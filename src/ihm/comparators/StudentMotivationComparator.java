package ihm.comparators;

import java.util.Comparator;

import oop.Student;
import utility.Tools;

public class StudentMotivationComparator implements Comparator<Student> {

    @Override
    public int compare(Student o1, Student o2) {
        return (int) (100* (Tools.motivationValue(o1.getMotivation())-Tools.motivationValue(o2.getMotivation())));
    }

}
