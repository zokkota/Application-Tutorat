package ihm.comparators;

import java.util.Comparator;

import oop.Student;

public class StudentSurnameComparator implements Comparator<Student> {

    @Override
    public int compare(Student o1, Student o2) {
        int diff = o1.getSurname().toUpperCase().compareTo(o2.getSurname().toUpperCase());
        if ( diff == 0 ) {
            return o1.getForename().toUpperCase().compareTo(o2.getForename().toUpperCase());
        }
        return diff;
    }

}
