package oop;

import java.util.Comparator;

import graphs.Tutoring;

public class StudentComparator implements Comparator<Student> {
    Tutoring tutorat;

    public StudentComparator(Tutoring tutorat) {
        this.tutorat = tutorat;
    }

    @Override
    public int compare(Student o1, Student o2) {
        return (int) (100 * (o1.getWeight(tutorat) - o2.getWeight(tutorat)));
    }

}
