package utility;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

import fr.ulille.but.sae2_02.donnees.DonneesPourTester;

public final class EnrichData {

    private EnrichData() {
        throw new UnsupportedOperationException("Utility class and cannot be instantiated");
    }

    static String[][] enrichData(String[][] src) {
        Random rng = new Random();
        String[][] dst = new String[src.length][12];
        for (int i = 0; i < dst.length; i++) {
            for (int j = 0; j < 4; j++) {
                dst[i][j] = src[i][j];
            }
            dst[i][0] = src[i][0];
            dst[i][1] = src[i][1];
            dst[i][2] = src[i][3];
            dst[i][3] = String.valueOf(rng.nextInt(12));
            dst[i][4] = String.valueOf((char) ('A' + rng.nextInt(3)));
            dst[i][5] = "3".equals(dst[i][3]) && rng.nextDouble() > 0.7 ? "1" : "";
            for (int j = 6; j < 12; j++) {
                dst[i][j] = (rng.nextDouble() > 0.5) ? String.valueOf(((int) (rng.nextDouble() * 20 * 100)) / 100) : "";
            }
        }
        return dst;
    }

    static void exportToCSV(String[][] data) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(new File("res/data/students.csv")))) {
            for (String[] strings : data) {
                for (int i = 0; i < strings.length; i++) {
                    bw.append(strings[i]);
                    if (i < strings.length - 1) {
                        bw.append(";");
                    }
                }
                bw.append("\r\n");
            }
        } catch (IOException e) {
            System.err.println("Error while exporting data to CSV : " + e);
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String[][] src = DonneesPourTester.studentData;
        String[][] dst = enrichData(src);
        exportToCSV(dst);
    }
}
