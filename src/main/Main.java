package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import oop.Scenario;
import oop.ScenarioNumberTwo;

public final class Main {
    private Main() {
    };

    public static void main(String[] args) {
        System.out.println("Projet S2.01 - S2.03 - Développement d'application.");
        System.out.println("Auteurs : Herssens Alexandre, Varlamoff Léopold, Franos Théo (à hauteur de 0.2%).\n");
        System.out.println("Bienvenue sur notre application.");
        System.out.println(
                "Nous avons 2 scénarios pour démontrer notre application en CLI, choisissez celui que vous préférez :");
        System.out.println("Scénario 1 : le tutorat de web du milieu du S1. | Scénario 2 : le tutorat de M. Baste en fin de S1.");
        System.out.println("Entrez 1 ou 2 pour faire votre choix");

        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            String input;
            do {
                input = br.readLine();
            } while (!"1".equals(input) && !"2".equals(input));

            System.out.print("\033[2J\033[1;1H");

            if ("1".equals(input))
                Scenario.main(args);
            else
                ScenarioNumberTwo.main(args);

        } catch (IOException e) {
            System.out.println("Input/Output exception, please try again later.");
            e.printStackTrace();

        } finally {
            System.out.println("\nMerci d'avoir pris le temps de tester notre application. A une prochaine fois.");
        }
    }
}
