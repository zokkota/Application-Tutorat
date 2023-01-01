package graphstests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import graphs.Couple;
import oop.Person;
import oop.Tutor;
import oop.Tutored;
import utility.Couples;

public class CoupleTest {
    Couple c1, c2, c3;
    Tutored u1, u2, u3;
    Tutor t1, t2, t3;

    @BeforeEach
    public void initialize() {
        u1 = new Tutored("Anémone", 2, 'B');
        u2 = new Tutored("Béthanie", 3, 'A');
        u3 = new Tutored("Christian", 6, 'C');

        t1 = new Tutor("Adrien", 3, 2, 'A');
        t2 = new Tutor("Bill", 2, 7, 'A');
        t3 = new Tutor("Céline", 3, 0, 'C');

        c1 = new Couple(u1, t2);
        c2 = new Couple(u3, t1);
        c3 = new Couple(u2, t1);
    }

    @Test
    public void containsTest() {
        assertTrue(c1.contains(u1));
        assertTrue(c1.contains(t2));
        assertTrue(c2.contains(u3));
        assertTrue(c2.contains(t1));
        assertTrue(c3.contains(u2));
        assertTrue(c3.contains(t1));

        assertFalse(c1.contains(u2));
        assertFalse(c2.contains(u2));
        assertFalse(c1.contains(u3));
        assertFalse(c1.contains(t1));
        assertFalse(c2.contains(t3));
        assertFalse(c3.contains(t2));
    }

    @Test
    public void stringTest() {
        Person.setShortName(true);
        assertEquals("(Anémone, Bill)", c1.toString());
        assertEquals("(Christian, Adrien)", c2.toString());
        assertEquals("(Béthanie, Adrien)", c3.toString());
    }

    @Test
    public void equalsTest() {
        assertTrue(c1.equals(u1, t2));
        assertTrue(c2.equals(u3, t1));
        assertTrue(c3.equals(u2, t1));

        assertFalse(c1.equals(u1, t3));
        assertFalse(c1.equals(u3, t2));
        assertFalse(c2.equals(u3, t2));
        assertFalse(c2.equals(u2, t1));
        assertFalse(c3.equals(u2, t2));
        assertFalse(c3.equals(u3, t1));
    }

    @Test
    public void couplesContainsCoupleTest() {
        Set<Couple> couples = Set.of(c1, c2, c3);
        assertTrue(Couples.containsCouple(couples, u1, t2));
        assertTrue(Couples.containsCouple(couples, u3, t1));
        assertTrue(Couples.containsCouple(couples, u2, t1));

        assertFalse(Couples.containsCouple(couples, u1, t1));
        assertFalse(Couples.containsCouple(couples, u1, t3));
        assertFalse(Couples.containsCouple(couples, u2, t2));
        assertFalse(Couples.containsCouple(couples, u2, t3));
        assertFalse(Couples.containsCouple(couples, u3, t2));
        assertFalse(Couples.containsCouple(couples, u3, t3));
    }

    @Test
    public void couplesContainsStudentTest() {
        Set<Couple> couples = Set.of(c1, c2, c3);
        Tutored u4 = new Tutored("Diane", 2, 'A');
        Tutor t4 = new Tutor("Diane", 2, 2, 'A');

        assertTrue(Couples.containsStudent(couples, u1));
        assertTrue(Couples.containsStudent(couples, u2));
        assertTrue(Couples.containsStudent(couples, u3));

        assertTrue(Couples.containsStudent(couples, t1));
        assertTrue(Couples.containsStudent(couples, t2));
        
        assertFalse(Couples.containsStudent(couples, t3));
        assertFalse(Couples.containsStudent(couples, u4));
        assertFalse(Couples.containsStudent(couples, t4));
    }

    @Test
    public void couplesRemove1Test() {
        Set<Couple> couples = new HashSet<>();
        couples.add(c1);
        couples.add(c2);
        couples.add(c3);

        Couples.remove(couples, u1, t2);
        assertEquals(Set.of(c2, c3), couples);
        assertFalse(Couples.containsCouple(couples, u1, t2));
        assertFalse(Couples.containsStudent(couples, t2));
    }

    @Test
    public void couplesRemove2Test() {
        Set<Couple> couples = new HashSet<>();
        couples.add(c1);
        couples.add(c2);
        couples.add(c3);

        Couples.remove(couples, c1);
        assertEquals(Set.of(c2, c3), couples);
        assertFalse(Couples.containsCouple(couples, u1, t2));
        assertFalse(Couples.containsStudent(couples, t2));

        Couples.remove(couples, c2);
        assertEquals(Set.of(c3), couples);
        assertFalse(Couples.containsCouple(couples, u3, t1));
        assertTrue(Couples.containsStudent(couples, t1));
    }

    @Test
    public void couplesGetTest() {
        Set<Couple> couples = new HashSet<>();
        couples.add(c1);
        couples.add(c2);
        couples.add(c3);

        assertEquals(c1, Couples.get(couples, u1, t2));
        assertEquals(c2, Couples.get(couples, u3, t1));
        assertEquals(c3, Couples.get(couples, u2, t1));
    }
}
