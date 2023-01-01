package ihm.comparators;

import java.util.Comparator;

import oop.Student;

public class StudentAbsencesComparator implements Comparator<Student> {

    @Override
    public int compare(Student o1, Student o2) {
        return o1.getAbsences()-o2.getAbsences();
    }

}
