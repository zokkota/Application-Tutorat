package graphstests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.ulille.but.sae2_02.graphes.Arete;
import graphs.Tutoring;
import oop.Person;
import oop.Resource;
import oop.Student;
import oop.Teacher;
import oop.Tutor;
import oop.Tutored;
import utility.Tools;

/**
 * Tests unitaire des différents scénarios imaginés.
 * 
 * @author Léopold V.
 * @see Scenario
 */
public class GlobalTest {
    private Tutored u1,
            u2,
            u3,
            u4,
            u5,
            u6,
            u7;
    private Tutor t1,
            t2,
            t3,
            t4,
            t5;
    private Teacher teacher;
    private Tutoring assignment;
    private static final double DELTA = 0.002;
    private static final Resource RESOURCE = Resource.R101;

    @BeforeEach
    public void initialize() {
        Person.setShortName(true);

        double[] uGrades = new double[] { 9.8, 6.9, 12.7, 0.2, 17.3, 12.5, 10.5 };
        u1 = new Tutored("Claude", 0, 'A');
        u2 = new Tutored("Madeleine", 8, 'A');
        u3 = new Tutored("Sabine", 0, 'C');
        u4 = new Tutored("Hugues", 2, 'B');
        u5 = new Tutored("Lucas", 5, 'C');
        u6 = new Tutored("Alexandria", 0, 'A');
        u7 = new Tutored("Anouk", 1, 'B');

        double[] tGrades = new double[] { 9.3, 13.2, 13.2, 16.2, 11.3 };
        t1 = new Tutor("Vincent", 2, 0, 'A');
        t2 = new Tutor("Jacqueline", 2, 1, 'B');
        t3 = new Tutor("Pénélope", 2, 3, 'A');
        t4 = new Tutor("Édouard", 3, 0, 'C', 1);
        t5 = new Tutor("Olivier", 3, 2, 'B');

        teacher = new Teacher("name", Resource.R106);
        teacher.setAbsenceWeighting(1);
        teacher.setAverageWeighting(1);
        teacher.setLevelWeighting(1);

        List<Tutored> tutoredList = new ArrayList<>();
        tutoredList.addAll(List.of(u1, u2, u3, u4, u5, u6, u7));
        for (int i = 0; i < tutoredList.size(); i++) {
            tutoredList.get(i).addGrade(RESOURCE, uGrades[i]);
        }

        List<Tutor> tutorList = new ArrayList<>();
        tutorList.addAll(List.of(t1, t2, t3, t4, t5));
        for (int i = 0; i < tutorList.size(); i++) {
            tutorList.get(i).addGrade(RESOURCE, tGrades[i]);
        }

        assignment = new Tutoring(tutoredList, tutorList, RESOURCE);
        assignment.setTeacher(teacher);
    }

    @Test
    public void casDeBase() {
        // cas 1.A
        assignment.setPolyTutor(false);
        List<Arete<Student>> edges = assignment.getAssignment();
        List<Student> waitingList = assignment.getWaitingList();

        System.out.println(assignment.detailedToString());
        System.out.println(waitingList);

        assertEquals(5, edges.size());
        assertEquals(2, waitingList.size());
        assertEquals(9.242, assignment.getAffectationCost(), DELTA);
    }

    @Test
    public void casPolytutorat() {
        // cas 1.B
        assignment.setPolyTutor(true);
        List<Arete<Student>> edges = assignment.getAssignment();
        List<Student> waitingList = assignment.getWaitingList();

        assertEquals(edges.size(), 6);
        assertEquals(waitingList.size(), 1);
        assertEquals(assignment.getAffectationCost(), 11.941, DELTA);
        assertEquals(waitingList.get(0), u5);
        int olivierCount = 0;
        Pattern pattern = Pattern.compile("olivier", Pattern.CASE_INSENSITIVE);
        Matcher matcher;
        for (Arete<Student> edge : edges) {
            matcher = pattern.matcher(edge.getExtremite2().getName());
            if (matcher.find()) {
                olivierCount++;
            }
        }
        assertEquals(olivierCount, 2);
    }

    @Test
    public void casAffectationForcee() {
        // cas 2.A
        // rappel : on force l'affectation entre Claude & Jacqueline
        assignment.setPolyTutor(false);
        List<Arete<Student>> edges = assignment.getAssignment();

        Arete<Student> wantedAssignment = new Arete<>(u1, t2);
        for (Arete<Student> edge : edges) {
            assertFalse(Tools.edgeTextEquals(edge, wantedAssignment));
        }

        assignment.addForcedAssignments(u1, t2);
        edges = assignment.getAssignment();
        List<Student> waitingList = assignment.getWaitingList();
        boolean isEdgeInAssignment = false;

        for (Arete<Student> edge : edges) {
            if (Tools.edgeTextEquals(edge, wantedAssignment)) {
                isEdgeInAssignment = true;
            }
        }
        assertTrue(isEdgeInAssignment);
        assertEquals(edges.size(), 5);
        assertEquals(waitingList, List.of(u5, u2));
        assertEquals(assignment.getAffectationCost(), 7.415, DELTA);

        assignment.removeForcedAssignment(u1, t2);
        edges = assignment.getAssignment();
        for (Arete<Student> edge : edges) {
            assertFalse(Tools.edgeTextEquals(edge, wantedAssignment));
        }

    }

    @Test
    public void casIncompatibilite() {
        // cas 2.B
        // rappel : on veut empêcher une affectation entre
        assignment.setPolyTutor(false);
        List<Arete<Student>> edges = assignment.getAssignment();

        Arete<Student> unwantedAssignment = new Arete<>(u1, t4);
        boolean isEdgeInAssignment = false;
        for (Arete<Student> edge : edges) {
            if (Tools.edgeTextEquals(edge, unwantedAssignment)) {
                isEdgeInAssignment = true;
            }
        }
        assertFalse(isEdgeInAssignment);

        assignment.addForbiddenAssignments(u1, t4);
        edges = assignment.getAssignment();
        List<Student> waitingList = assignment.getWaitingList();

        for (Arete<Student> edge : edges) {
            assertFalse(Tools.edgeTextEquals(edge, unwantedAssignment));
        }
        assertEquals(edges.size(), 5);
        assertEquals(waitingList, List.of(u5, u2));
        assertEquals(assignment.getAffectationCost(), 9.242, DELTA);

        assignment.removeForbiddenAssignment(u1, t4);
        edges = assignment.getAssignment();
        isEdgeInAssignment = false;
        for (Arete<Student> edge : edges) {
            if (Tools.edgeTextEquals(edge, unwantedAssignment)) {
                isEdgeInAssignment = true;
            }
        }
        assertFalse(isEdgeInAssignment);
    }

    @Test
    public void casPonderationMoyenne() {
        // cas 3.A
        assignment.setPolyTutor(false);
        teacher.setAverageWeighting(2);
        List<Arete<Student>> edges = assignment.getAssignment();
        List<Student> waitingList = assignment.getWaitingList();

        // assertNotEquals = poids sans pondération de la moyenne
        // assertEquals(u1.getWeight(), 1.131, DELTA);
        // assertNotEquals(u2.getWeight(), 1.055, DELTA);
        // assertEquals(u4.getWeight(), 0.497, DELTA);
        // assertNotEquals(u6.getWeight(), 0.811, DELTA);
        // assertEquals(u7.getWeight(), 1.441, DELTA);

        // assertEquals(t1.getWeight(), 1.467, DELTA);
        // assertEquals(t2.getWeight(), 1.456, DELTA);
        // assertNotEquals(t3.getWeight(), 1.141, DELTA);

        System.out.println(assignment.detailedToString());

        assertEquals(5, edges.size());
        assertEquals(List.of(u5, u2), waitingList);
        assertEquals(13.155, assignment.getAffectationCost(), DELTA);
    }

    @Test
    public void casPonderationNiveau() {
        // cas 3.B
        assignment.setPolyTutor(false);
        teacher.setLevelWeighting(2);
        List<Arete<Student>> edges = assignment.getAssignment();
        List<Student> waitingList = assignment.getWaitingList();

        // assertNotEquals = poids avec pondération de la moyenne uniquement
        // assertNotEquals(t1.getWeight(), 1.467, DELTA);
        // assertEquals(t2.getWeight(), 1.637, DELTA);
        // assertNotEquals(t3.getWeight(), 1.429, DELTA);
        // assertEquals(t4.getWeight(), 1.266, DELTA);
        // assertEquals(t5.getWeight(), 1.428, DELTA);

        assertEquals(edges.size(), 5);
        assertEquals(waitingList, List.of(u5, u2));
        assertEquals(assignment.getAffectationCost(), 11.342, DELTA);
    }

    @Test
    public void casPonderationAbsences() {
        // cas 3.C
        assignment.setPolyTutor(false);
        teacher.setAbsenceWeighting(2);
        List<Arete<Student>> edges = assignment.getAssignment();
        List<Student> waitingList = assignment.getWaitingList();

        // assertNotEquals = poids sans aucune pondération
        // assertNotEquals(u2.getWeight(), 1.055, DELTA);
        // assertEquals(u3.getWeight(), 1.306, DELTA);
        // assertEquals(u4.getWeight(), 0.965, DELTA);
        // assertNotEquals(u5.getWeight(), 1.696, DELTA);
        // assertEquals(u6.getWeight(), 1.059, DELTA);

        // assertEquals(t1.getWeight(), 1.262, DELTA);
        // assertNotEquals(t3.getWeight(), 1.141, DELTA);
        // assertEquals(t4.getWeight(), 1.147, DELTA);

        assertEquals(edges.size(), 5);
        assertEquals(waitingList, List.of(u5, u2));
        assertEquals(assignment.getAffectationCost(), 12.471, DELTA);
    }

    @Test
    public void casExclusionTuteur() {
        // cas 4.A
        assignment.setPolyTutor(false);
        assignment.removeStudent(t1);
        List<Arete<Student>> edges = assignment.getAssignment();
        List<Student> waitingList = assignment.getWaitingList();

        for (Arete<Student> edge : edges) {
            assertNotEquals(edge.getExtremite2(), t1);
        }
        assertEquals(edges.size(), 4);
        assertEquals(waitingList.size(), 3);
        assertEquals(waitingList, List.of(u5, u2, u3));
        assertEquals(assignment.getAffectationCost(), 7.178, DELTA);
    }

    @Test
    public void casExclusionTutore() {
        // cas 4.B
        assignment.setPolyTutor(false);
        assignment.removeStudent(u6);
        List<Arete<Student>> edges = assignment.getAssignment();
        List<Student> waitingList = assignment.getWaitingList();

        for (Arete<Student> edge : edges) {
            assertNotEquals(u6, edge.getExtremite1());
        }
        assertEquals(5, edges.size());
        assertEquals(1, waitingList.size());
        assertEquals(List.of(u5), waitingList);
        assertEquals(9.457, assignment.getAffectationCost(), DELTA);
    }
}
