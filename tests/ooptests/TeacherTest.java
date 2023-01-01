package ooptests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import graphs.Tutoring;
import oop.Coefficient;
import oop.Resource;
import oop.Teacher;

public class TeacherTest {
    Teacher t1 = new Teacher("Corwyn Fèvre");
    Teacher t2 = new Teacher("Philippe Mathieu", Resource.R105);
    Teacher t3 = new Teacher("Julien Baste", "R103");
    Teacher t4 = new Teacher("Antoine Nongaillard", Arrays.asList(Resource.R101, Resource.R105));

    Teacher[] teachers = new Teacher[] { t1, t2, t3, t4 };

    @Test
    void getResourceTest() {
        assertTrue(t1.getResources().isEmpty());
        assertEquals(Resource.R105, t2.getResources().get(0));
        assertEquals(Resource.R103, t3.getResources().get(0));
        assertEquals(2, t4.getResources().size());
        assertEquals(Resource.R105, t4.getResources().get(1));

    }

    @Test
    void addResourceTest() {
        assertTrue(t1.addResource(Resource.R105));
        assertFalse(t1.getResources().isEmpty());
        assertEquals(Resource.R105, t1.getResources().get(0));

        assertTrue(t4.addResource(Resource.R104));
        assertEquals(3, t4.getResources().size());
    }

    @Test
    void getWeightingtest() {
        Map<Coefficient, Double> defaultCoefs = new EnumMap<>(Coefficient.class);
        defaultCoefs.put(Coefficient.GRADES, Teacher.getDefaultWeighting());
        defaultCoefs.put(Coefficient.ABSENCES, Teacher.getDefaultWeighting());
        defaultCoefs.put(Coefficient.LEVEL, Teacher.getDefaultWeighting());

        for (Teacher t : teachers) {
            assertEquals(defaultCoefs.get(Coefficient.GRADES), t.getAverageWeighting());
            assertEquals(defaultCoefs.get(Coefficient.ABSENCES), t.getAbsenceWeighting());
            assertEquals(defaultCoefs.get(Coefficient.LEVEL), t.getLevelWeighting());
        }
    }

    @Test
    void setWeightingTest() {
        t1.setLevelWeighting(2);
        assertEquals(2, t1.getWeighting(Coefficient.LEVEL));

        t2.setWeighting(Coefficient.ABSENCES, -1);
        assertEquals(0, t2.getAbsenceWeighting());

        t3.setAverageWeighting(8);
        assertEquals(Tutoring.getMaxWeighting(), t3.getAverageWeighting());
    }

}
