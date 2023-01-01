package utility;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import graphs.Couple;
import oop.Student;
import oop.Tutor;
import oop.Tutored;

public final class Couples {

    private Couples() {
        throw new UnsupportedOperationException("Cannot instantiate utility class.");
    }

    public static boolean containsCouple(Collection<Couple> sets, Tutored tutored, Tutor tutor) {
        return Couples.get(sets, tutored, tutor) != null;
    }

    public static boolean containsStudent(Collection<Couple> couples, Student student) {
        return containedIn(couples, student).size()>0;
    }

    public static boolean remove(Collection<Couple> couples, Tutored tutored, Tutor tutor) {
        return couples.remove(Couples.get(couples, tutored, tutor));
    }

    public static boolean remove(Collection<Couple> couples, Couple couple) {
        return couples.remove(couple);
    }

    public static Couple get(Collection<Couple> couples, Tutored tutored, Tutor tutor) {
        for (Couple couple : couples) {
            if (couple.equals(tutored, tutor)) {
                return couple;
            }
        }
        return null;
    }

    public static List<Couple> containedIn(Collection<Couple> couples, Student student){
        List<Couple> retour = new ArrayList<>();
        for (Couple couple : couples) {
            if (couple.contains(student)) {
                retour.add(couple);
            }
        }
        return retour;
    }

    public static Set<Student> getTutors(Collection<Couple> couples){
        Set<Student> retour = new HashSet<>();
        for (Couple couple : couples) {
            retour.add(couple.getTutor());
        }
        return retour;
    }
    public static Set<Student> getTutored(Collection<Couple> couples){
        Set<Student> retour = new HashSet<>();
        for (Couple couple : couples) {
            retour.add(couple.getTutored());
        }
        return retour;
    }
}
