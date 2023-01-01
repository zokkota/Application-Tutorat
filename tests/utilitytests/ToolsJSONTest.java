package utilitytests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import utility.ToolsJSON;

public class ToolsJSONTest {
    private static final String JSON_STRING = "{\"tutored\":{\"average\":{\"minAverage\":\"6.74\",\"maxAverage\":\"15.2\"},\"absences\":{\"minAbsences\":\"0\",\"maxAbsences\":\"62\"}},\"tutor\":{\"average\":{\"minAverage\":\"12.26\",\"maxAverage\":\"19.47\"},\"absences\":{\"minAbsences\":\"5\",\"maxAbsences\":\"7\"}}}";

    // Nous avons changé l'implémentation car les fichiers ressources sont
    // inaccessibles lors de tests (du moins, pas sans utiliser un chemin absolu).
    // -> voir sysout ligne 18.

    @Test
    public void readFiltersTest() {
        System.out.println("Working Directory = " + System.getProperty("user.dir"));
        double[] filters = ToolsJSON.readFilters(JSON_STRING);
        assertEquals(filters[0], 6.74);
        assertEquals(filters[1], 15.2);
        assertEquals(filters[2], 0);
        assertEquals(filters[3], 62);
        assertEquals(filters[4], 12.26);
        assertEquals(filters[5], 19.47);
        assertEquals(filters[6], 5);
        assertEquals(filters[7], 7);
    }
}
