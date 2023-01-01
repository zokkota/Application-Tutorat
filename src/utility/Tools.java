package utility;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import fr.ulille.but.sae2_02.graphes.Arete;
import graphs.Tutoring;
import oop.Student;
import oop.StudentPriorityComparator;
import oop.Tutor;
import oop.Tutored;

/**
 * Utility class that regroups multiple static methods useful for list
 * optimisation, assignments, and retrieving information. Those method were
 * seperated from the {@code Tutoring.java} class for multiple reasons :
 * methods might be reused later in the application, and these methods do not
 * fundamentally work with the graph itself, they only help with data
 * processing.
 * 
 * @author Léopold V.
 * @author Alexandre H.
 * @see Tutoring
 * 
 */
public final class Tools {
    private Tools() {
        throw new UnsupportedOperationException("Utility class and cannot be instantiated");
    }

    /**
     * Orders a given list of student, then removes the last {@code diff} students
     * of this list, placing them in a waiting list that is returned. Order is
     * arbitrary. Order does not change after building the waiting list as it does
     * not impact the assignment.
     * 
     * @param list list to sort and build the waiting list from.
     * @param diff number of students to remove.
     * @return The waiting list of students.
     * 
     * @see Tutored#compareTo(Student)
     * @see Tutor#compareTo(Student)
     */
    public static List<Student> waitingListBuilder(List<? extends Student> list, Tutoring tutorat, int diff) {
        int difference = diff;
        List<Student> waitingList = new ArrayList<>();
        list.sort(new StudentPriorityComparator(tutorat));
        List<Student> copy = new ArrayList<>();
        copy.addAll(list);

        ListIterator<? extends Student> it = list.listIterator(list.size());
        while (it.hasPrevious() && difference > 0) {
            Student student = it.previous();
            waitingList.add(student);
            difference--;
        }
        for (Student student : waitingList) {
            list.remove(student);
        }

        return waitingList;
    }

    /**
     * Static method that returns a numerical value of a character representing the
     * motivation of a student.
     * 
     * @param motivation A, B, or C the motivation of the student.
     * @return a value to assess the motivation of a student.
     * @throws IllegalArgumentException if the motivation character is not A, B or
     *                                  C.
     */
    public static double motivationValue(char motivation) {
        if (motivation < 'A' && motivation > 'C') {
            throw new IllegalArgumentException("The motivation character is not valid (A, B or C).");
        }
        return 1 + (motivation - 'B') * 0.1;
    }

    /**
     * Tests if the toString method of 2 edges return the same text. Used as an
     * {@code equals} method to test edges equality.
     * 
     * @param edge1 first edge.
     * @param edge2 second edge.
     * @return true if text is equals, false otherwise.
     */
    public static boolean edgeTextEquals(Arete<Student> edge1, Arete<Student> edge2) {
        return edge1.toString().equals(edge2.toString());
    }

}