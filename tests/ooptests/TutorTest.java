package ooptests;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import graphs.Tutoring;
import oop.Resource;
import oop.Teacher;
import oop.Tutor;
import oop.TutorDuplicate;
import oop.Tutored;
import utility.Tutors;

public class TutorTest {
    Tutor t1, t2, t3, t4, t5;
    TutorDuplicate d1, d2;

    @BeforeEach
    public void initialize() {
        t1 = new Tutor("Adrien", 1, 0, 'A', 1);
        t2 = new Tutor("Benjamin", 2, 1, 'B', 2);
        t3 = new Tutor("Charles", 3, 2, 'B', 1);
        t4 = new Tutor("David", 3, 6, 'A');
        t4.addGrade(Resource.R101, 10);
        t5 = new Tutor("Etienne", 3, 1, 'C');
        t5.addGrade(Resource.R101, 12);
        d1 = new TutorDuplicate(t4);
    }

    @Test
    public void duplicateTest() {
        assertNotEquals(t4.getClass(), d1.getClass());
        assertTrue(d1 instanceof Tutor);
        assertTrue(d1 instanceof TutorDuplicate);
        assertEquals(t4.getLevel(), d1.getLevel());
        assertEquals(t4.getAbsences(), d1.getAbsences());
        assertEquals(t4.getMotivation(), d1.getMotivation());
        assertNotEquals(t4.getNbofTutored(), d1.getNbofTutored());
        assertEquals(1, d1.getNbofTutored());
        assertEquals(t4.getGrades(), d1.getGrades());
        assertEquals(d1.getName(), t4.getName() + "(" + TutorDuplicate.getTutorDuplicateIdentifier() + ")");

        d2 = t3.duplicate();
        assertNull(d2);

        d2 = t2.duplicate();
        assertNull(d2);

        d2 = t5.duplicate();
        assertNotEquals(t3.getClass(), d2.getClass());
        assertTrue(d2 instanceof Tutor);
        assertTrue(d2 instanceof TutorDuplicate);
        assertEquals(t5.getLevel(), d2.getLevel());
        assertEquals(t5.getAbsences(), d2.getAbsences());
        assertEquals(t5.getMotivation(), d2.getMotivation());
        assertNotEquals(t5.getNbofTutored(), d2.getNbofTutored());
        assertEquals(1, d2.getNbofTutored());
        assertEquals(t5.getGrades(), d2.getGrades());
        assertEquals(d2.getName(), t5.getName() + "(" + TutorDuplicate.getTutorDuplicateIdentifier() + ")");
    }

    @Test
    public void isDuplicateTest() {
        assertFalse(t1.isDuplicate());
        assertFalse(t2.isDuplicate());
        assertFalse(t3.isDuplicate());
        assertTrue(d1.isDuplicate());

        d2 = t4.duplicate();
        assertTrue(d2.isDuplicate());
    }

    @Test
    public void numberOfTutoredTest() {
        assertEquals(1, t1.getNbofTutored());
        assertEquals(1, t2.getNbofTutored());
        assertEquals(1, t3.getNbofTutored());
        assertEquals(2, t4.getNbofTutored());
        assertEquals(2, t5.getNbofTutored());

        d2 = t4.duplicate();
        assertEquals(1, d1.getNbofTutored());
        assertEquals(1, d2.getNbofTutored());
    }

    @Test
    public void tutorsGapCloseTest() {
        Teacher teacher = new Teacher("Teacher", Resource.R101);
        Tutoring t = new Tutoring(teacher, Resource.R101);
        Tutored u = new Tutored("Benjamin", 2, 'B');
        u.addGrade(Resource.R101, 3);
        t.addStudent(u);
        
        List<Tutor> toChange = new ArrayList<>();
        toChange.add(t4);
        toChange.add(t5);
        t.addStudent(toChange);

        List<Tutor> newList = Tutors.gapClose(toChange, t, 1);
        assertEquals(3, newList.size());
        assertTrue(newList.contains(t4));
        assertTrue(newList.contains(t5));
        assertTrue(newList.contains(t5.duplicate()));

        toChange = new ArrayList<>();
        toChange.add(t4);
        toChange.add(t5);
        newList = Tutors.gapClose(toChange, t, 2);
        assertEquals(4, newList.size());
        assertTrue(newList.contains(t4));
        assertTrue(newList.contains(t5));
        assertTrue(newList.contains(t5.duplicate()));
        assertTrue(newList.contains(d1));
    }
}
