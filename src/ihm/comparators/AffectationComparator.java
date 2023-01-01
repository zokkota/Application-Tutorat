package ihm.comparators;

import java.util.Comparator;

import graphs.Couple;
import ihm.Interface;
import oop.Student;

public class AffectationComparator implements Comparator<Couple>{
    Comparator<Student> comparator;
    Interface iface;

    public AffectationComparator(Interface iface, Comparator<Student> comparator){
        this.comparator = comparator;
        this.iface = iface;
    }

    public int compare(Couple o1, Couple o2) {
        if (iface.filterTutored) {
            return comparator.compare(o1.getTutored(), o2.getTutored());
        }
        return comparator.compare(o1.getTutor(), o2.getTutor());
    }

    
}
