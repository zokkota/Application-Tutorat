package ooptests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import oop.Person;
import oop.Resource;
import oop.Tutored;
import oop.Tutor;

public class StudentTest {
    Tutored u1, u2, u3;
    Tutor t1, t2;

    @BeforeEach
    public void initialize() {
        u1 = new Tutored("Anémone", 2, 'B');
        u2 = new Tutored("Béthanie", 3, 'A');
        u3 = new Tutored("Daniel", -1, 'Z');
        t1 = new Tutor("Christian", 2, 0, 'C');
        t2 = new Tutor("Dylan", 3, 2, 'A');
    }

    @Test
    public void addGradeTest() {
        u1.addGrade(Resource.R101, 15.2);
        u1.addGrade(Resource.R106, 6.2);
        u2.addGrade(Resource.R102, 15.3);
        u2.addGrade(Resource.R105, 12.47);
        u3.addGrade(Resource.R106, 50.0);
        u3.addGrade(Resource.R104, -2.3);
        t1.addGrade(Resource.R103, 5.68);
        t1.addGrade(Resource.R104, 16.58);
        t2.addGrade(Resource.R103, 16.58);
        t2.addGrade(Resource.R105, 12.47);

        assertTrue(u1.getGrades().containsKey(Resource.R101));
        assertTrue(u1.getGrades().containsKey(Resource.R106));
        assertEquals(15.2, u1.getGrade(Resource.R101));
        assertEquals(6.2, u1.getGrade(Resource.R106));
        assertTrue(u2.getGrades().containsKey(Resource.R102));
        assertTrue(u2.getGrades().containsKey(Resource.R105));
        assertEquals(15.3, u2.getGrade(Resource.R102));
        assertEquals(12.47, u2.getGrade(Resource.R105));
        assertTrue(u3.getGrades().containsKey(Resource.R106));
        assertTrue(u3.getGrades().containsKey(Resource.R104));

        assertTrue(t1.getGrades().containsKey(Resource.R103));
        assertTrue(t1.getGrades().containsKey(Resource.R104));
        assertEquals(5.68, t1.getGrade(Resource.R103));
        assertEquals(16.58, t1.getGrade(Resource.R104));
        assertTrue(t2.getGrades().containsKey(Resource.R103));
        assertTrue(t2.getGrades().containsKey(Resource.R105));
        assertEquals(16.58, t2.getGrade(Resource.R103));
        assertEquals(12.47, t2.getGrade(Resource.R105));

        assertFalse(u1.getGrades().containsKey(Resource.R102));
        assertFalse(u1.getGrades().containsKey(Resource.R105));
        assertFalse(u2.getGrades().containsKey(Resource.R101));
        assertFalse(u2.getGrades().containsKey(Resource.R106));
        assertFalse(u3.getGrades().containsKey(Resource.R101));
        assertFalse(u3.getGrades().containsKey(Resource.R103));

        assertFalse(t1.getGrades().containsKey(Resource.R101));
        assertFalse(t1.getGrades().containsKey(Resource.R102));
        assertFalse(t2.getGrades().containsKey(Resource.R101));
        assertFalse(t2.getGrades().containsKey(Resource.R102));
    }

    @Test
    public void addGradeTextTest() {
        u1.addGrade("R101", 15.2);
        u1.addGrade("R106", 6.2);
        u2.addGrade("R102", 15.3);
        u2.addGrade("R105", 12.47);
        u3.addGrade("R106", 50.0);
        u3.addGrade("R104", -2.3);
        t1.addGrade("R103", 5.68);
        t1.addGrade("R104", 16.58);
        t2.addGrade("R103", 16.58);
        t2.addGrade("R105", 12.47);

        assertTrue(u1.getGrades().containsKey(Resource.R101));
        assertTrue(u1.getGrades().containsKey(Resource.R106));
        assertEquals(15.2, u1.getGrade(Resource.R101));
        assertEquals(6.2, u1.getGrade(Resource.R106));
        assertTrue(u2.getGrades().containsKey(Resource.R102));
        assertTrue(u2.getGrades().containsKey(Resource.R105));
        assertEquals(15.3, u2.getGrade(Resource.R102));
        assertEquals(12.47, u2.getGrade(Resource.R105));
        assertTrue(u3.getGrades().containsKey(Resource.R106));
        assertTrue(u3.getGrades().containsKey(Resource.R104));

        assertTrue(t1.getGrades().containsKey(Resource.R103));
        assertTrue(t1.getGrades().containsKey(Resource.R104));
        assertEquals(5.68, t1.getGrade(Resource.R103));
        assertEquals(16.58, t1.getGrade(Resource.R104));
        assertTrue(t2.getGrades().containsKey(Resource.R103));
        assertTrue(t2.getGrades().containsKey(Resource.R105));
        assertEquals(16.58, t2.getGrade(Resource.R103));
        assertEquals(12.47, t2.getGrade(Resource.R105));

        assertFalse(u1.getGrades().containsKey(Resource.R102));
        assertFalse(u1.getGrades().containsKey(Resource.R105));
        assertFalse(u2.getGrades().containsKey(Resource.R101));
        assertFalse(u2.getGrades().containsKey(Resource.R106));
        assertFalse(u3.getGrades().containsKey(Resource.R101));
        assertFalse(u3.getGrades().containsKey(Resource.R103));

        assertFalse(t1.getGrades().containsKey(Resource.R101));
        assertFalse(t1.getGrades().containsKey(Resource.R102));
        assertFalse(t2.getGrades().containsKey(Resource.R101));
        assertFalse(t2.getGrades().containsKey(Resource.R102));
    }

    @Test
    public void defaultValuesTest() {
        u3.addGrade(Resource.R106, 50.0);
        u3.addGrade(Resource.R104, -2.3);

        // default absences
        assertNotEquals(-1, u3.getAbsences());
        assertEquals(0, u3.getAbsences());

        // default motivation
        assertNotEquals('Z', u3.getMotivation());
        assertEquals('B', u3.getMotivation());

        // default grades
        assertEquals(20, u3.getGrade(Resource.R106));
        assertEquals(0, u3.getGrade(Resource.R104));
    }

    @Test
    public void shortToStringTest() {
        Person.setShortName(true);
        assertEquals("Anémone", u1.toString());
        assertEquals("Béthanie", u2.toString());
        assertEquals("Daniel", u3.toString());
        assertEquals("Christian", t1.toString());
        assertEquals("Dylan", t2.toString());
    }

    @Test
    public void longToStringTest() {
        u1.addGrade(Resource.R101, 15.2);
        u3.addGrade(Resource.R104, -2.3);
        t2.addGrade(Resource.R103, 16.58);

        Person.setShortName(false);
        assertEquals(
                "Tutored [Anémone, level= 1, absences= 2, notes= {R101: Initiation au développement=15.2}, motivation= B]",
                u1.toString());
        assertEquals(
                "Tutored [Béthanie, level= 1, absences= 3, notes= {}, motivation= A]",
                u2.toString());
        assertEquals(
                "Tutored [Daniel, level= 1, absences= 0, notes= {R104: Introduction aux systèmes d'exploitation et à leur fonctionnement=0.0}, motivation= B]",
                u3.toString());
        assertEquals(
                "Tutor [Christian, level= 2, absences= 0, notes= {}, motivation= C, nbOfTutored= 1]",
                t1.toString());
        assertEquals(
                "Tutor [Dylan, level= 3, absences= 2, notes= {R103: Introduction à l'architecture des ordinateurs=16.58}, motivation= A, nbOfTutored= 2]",
                t2.toString());
    }
}
