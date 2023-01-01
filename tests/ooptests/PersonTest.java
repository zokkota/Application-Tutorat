package ooptests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import oop.Person;
import oop.Teacher;
import oop.Tutor;
import oop.Tutored;
import utility.Persons;

public class PersonTest {
    Person p1 = new Tutored("Antoine", 0, 'A');
    Person p2 = new Tutored("Jean Carle", 1, 'B');
    Person p3 = new Tutor("Delphine", 2, 3, 'C');
    Person p4 = new Tutor("Patricia Everaere", 3, 0, 'A');
    Person p5 = new Teacher("Corwyn Fèvre");
    Person p6 = new Teacher("Iovka");
    Person p7 = ((Tutor) p4).duplicate();

    Person[] persons = new Person[] { p1, p2, p3, p4, p5, p6, p7 };

    @Test
    void forenameTest() {
        String[] forenames = new String[] { "Antoine", "Jean", "Delphine", "Patricia", "Corwyn", "Iovka", "Patricia" };

        for (int i = 0; i < persons.length; i++) {
            assertEquals(forenames[i], persons[i].getForename());
        }
    }

    @Test
    void surnameTest() {
        String[] surnames = new String[] { null, "Carle", null, "Everaere", "Fèvre", null, "Everaere(D)" };

        for (int i = 0; i < persons.length; i++) {
            assertEquals(surnames[i], persons[i].getSurname());
        }
    }

    @Test
    void nameTest() {
        String[] names = new String[] { "Antoine", "Jean Carle", "Delphine", "Patricia Everaere", "Corwyn Fèvre",
                "Iovka", "Patricia Everaere(D)" };

        for (int i = 0; i < persons.length; i++) {
            assertEquals(names[i], persons[i].getName());
        }
    }

    @Test
    void isStudentTest() {
        assertTrue(p1.isStudent());
        assertTrue(p2.isStudent());
        assertTrue(p3.isStudent());
        assertTrue(p4.isStudent());
        assertFalse(p5.isStudent());
        assertFalse(p6.isStudent());
        assertTrue(p7.isStudent());
    }

    @Test
    void stringTest() {
        Person.setShortName(false);

        assertEquals("Tutored [Antoine, level= 1, absences= 0, notes= {}, motivation= A]", p1.toString());
        assertEquals("Tutored [Jean Carle, level= 1, absences= 1, notes= {}, motivation= B]", p2.toString());
        assertEquals("Tutor [Delphine, level= 2, absences= 3, notes= {}, motivation= C, nbOfTutored= 1]",
                p3.toString());
        assertEquals("Tutor [Patricia Everaere, level= 3, absences= 0, notes= {}, motivation= A, nbOfTutored= 2]",
                p4.toString());
        assertEquals("Teacher [Corwyn Fèvre, matières= []]", p5.toString());
        assertEquals("Teacher [Iovka, matières= []]", p6.toString());
        assertEquals(
                "TutorDuplicate [Patricia Everaere(D), level= 3, absences= 0, notes= {}, motivation= A, nbOfTutored= 1]",
                p7.toString());
    }

    @Test
    void shortToStringTest() {
        Person.setShortName(true);

        assertEquals("Antoine", p1.toString());
        assertEquals("Jean Carle", p2.toString());
        assertEquals("Delphine", p3.toString());
        assertEquals("Patricia Everaere", p4.toString());
        assertEquals("Corwyn Fèvre", p5.toString());
        assertEquals("Iovka", p6.toString());
        assertEquals("Patricia Everaere(D)", p7.toString());
    }

    @Test
    public void personsGetPersonTest() {
        Person p = Persons.getPerson("antoine", List.of(persons));
        assertEquals(p1, p);

        p = Persons.getPerson("jean carle", List.of(persons));
        assertEquals(p2, p);

        p = Persons.getPerson("Jean", List.of(persons));
        assertNull(p);

        p = Persons.getPerson("DELPHINE", List.of(persons));
        assertEquals(p3, p);

        p = Persons.getPerson("patricia Everaere", List.of(persons));
        assertEquals(p4, p);

        p = Persons.getPerson("Patricia", List.of(persons));
        assertNull(p);

        p = Persons.getPerson("CORWYN Fèvre", List.of(persons));
        assertEquals(p5, p);

        p = Persons.getPerson("iOVKA", List.of(persons));
        assertEquals(p6, p);

        p = Persons.getPerson("patricia everaere(d)", List.of(persons));
        assertEquals(p7, p);
    }
}
