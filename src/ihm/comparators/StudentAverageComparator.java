package ihm.comparators;

import java.util.Comparator;

import oop.Resource;
import oop.Student;

public class StudentAverageComparator implements Comparator<Student> {
    Resource resource;

    @Override
    public int compare(Student o1, Student o2) {
        return (int) (100 * (o1.getGrade(resource)-o2.getGrade(resource)));
    }

    public StudentAverageComparator(Resource resource) {
        this.resource = resource;
    }

}
