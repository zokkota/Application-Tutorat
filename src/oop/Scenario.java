package oop;

import java.util.ArrayList;
import java.util.List;

import graphs.Tutoring;
import utility.Persons;
import utility.ToolsCSV;

public final class Scenario {
    static List<Department> iut = new ArrayList<>();
    static Department currentDpt;
    static Tutoring currentTutoring;
    static int cas = 1;

    private Scenario() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    public static void main(String[] args) {
        System.out.println("\nL'I.U.T. de Lille veut mettre en place un système de tutorat");
        System.out.println("\t>Chaque Département dispose d'une liste d'élèves et de professeurs");
        System.out.println("\t>Chaque Département dispose d'une liste d'élèves et de professeurs");
        System.out.println("\t>Ces élèves disposent ou non de notes pour une matière");
        System.out.println("\t>Cette note est le critère d'éligibilité au tutorat pour cette matière");
        System.out.println("\t>Chaque tutorat dispose d'un professeur référent");

        bienvenueALIUT();
        monPremierTutorat();
        maPremiereAffectation();
        monPremierCouple();
        mesPremieresAnimosites();
        ilFautAiderCeuxEnDifficulte();
        mrCarleAimeLImplication();
        unEtudiantManqueDeRespect();
        finDAnnee();
    }

    static void bienvenueALIUT() {
        System.out.println();
        System.out.println(
                "Bienvenue à l'IUT, \nCette année nous ne nous intéressons qu'au département informatique !");

        Department dptInfo = new Department("Informatique");
        iut.add(dptInfo);
        System.out.println("\t>" + iut + "\n");
        currentDpt = dptInfo;

        System.out.println(
                "La liste des étudiants ne contient que 57 inscrits, \napparemment Parcoursup a encore causé des ennuis...");
        dptInfo.addStudent(ToolsCSV.importStudents());
        System.out.println("\t>" + iut + "\n");

    }

    static void monPremierTutorat() {
        System.out.println(
                "Monsieur Carle se propose d'organiser du tutorat le samedi matin\npour celles et ceux en difficulté en R102.");
        Teacher jeanCarle = new Teacher("Jean carle", Resource.R102);
        currentDpt.add(jeanCarle);
        System.out.println("\t>" + iut);
        System.out.println("\t>" + currentDpt.getCopyOfTeachers());

        Resource web = Resource.R102;
        currentDpt.newTutoring(web, jeanCarle);
        Tutoring webTutorat = currentDpt.getTutoring(web);
        currentTutoring = webTutorat;
        System.out.println("\t>" + iut);
        System.out.println("\t>" + currentDpt.getTutorings().values());

        System.out.println();
        System.out.println("Il faut maintenant inscrire les étudiants éligibles à ce tutorat,\nTuteurs comme Tutorés.");
        currentDpt.registerStudent(web);
        System.out.println("\t>" + currentDpt.getTutorings().values());

        // System.out.println();
        // System.out.println("Voici les tutorés : ");
        // System.out.println(webTutorat.getTutored());
        // System.out.println();
        // System.out.println("Et voici les tuteurs : ");
        // System.out.println(webTutorat.getTutors());
        // System.out.println();
    }

    static void maPremiereAffectation() {
        System.out.println("\n\t\t-----\n");
        System.out.println("C'est l'heure de réaliser la première affectation !");
        System.out.println();
        currentTutoring.scenarioToString(String.valueOf(cas++), "Situation de base");

    }

    static void monPremierCouple() {
        System.out.println();
        System.out.println(
                "Mettre les deux Sabine ensemble semble être une bonne\n");

        currentTutoring.addForcedAssignments("Sabine Besnard", "Sabine Leleu");
        currentTutoring.scenarioToString(String.valueOf(cas++), "Affectation forcée : Sabine & Sabine");
    }

    static void mesPremieresAnimosites() {
        System.out.println("Lucas et Martin se peuvent pas se trouver dans la même pièce...\n");
        currentTutoring.addForbiddenAssignments("Lucas Bouchet", "Martin Delmas");
        currentTutoring.scenarioToString(String.valueOf(cas++), "Affectation interdite : Lucas & Martin");
    }

    static void ilFautAiderCeuxEnDifficulte() {
        System.out.println(
                "Un nouvel étudiant, Paul Delabible aimerait participer en tant que tutoré\nIl n'a jamais été absent et est très motivé!\nEt ca se comprend, ses notes en web sont désastreuses...");
        Tutored paul = new Tutored("Paul Delabible", 0, 'A');
        paul.setGrade(currentTutoring.getResource(), 5.42);
        System.out.println("\t>" + currentTutoring);
        System.out.println("\t>" + paul);
        currentTutoring.addStudent(paul);
        System.out.println("\t>" + currentTutoring);
        // System.out.println("\t>" + currentTutoring.getTutored());
        System.out.println();
        System.out.println("Relançons l'affectation ! \n");
        currentTutoring.scenarioToString(String.valueOf(cas++), "Paul Delabible est mauvais en Web!");
    }

    static void mrCarleAimeLImplication() {
        Teacher jean = currentTutoring.getTeacher();
        System.out.println(
                "Monsieur Carle désire accorder de l'importance aux absences,\nles notes pour lui ne sont d'aucun intérêt");
        System.out.println("\t>Départ :\t\t" + jean.getWeightings());
        jean.setAbsenceWeighting(Tutoring.getMaxWeighting());
        System.out.println("\t>Update absences\t" + jean.getWeightings());
        jean.setLevelWeighting(0);
        System.out.println("\t>Update niveau\t\t" + jean.getWeightings());
        jean.setAverageWeighting(0);
        System.out.println("\t>Update moyenne\t\t" + jean.getWeightings());

        System.out.println();
        System.out.println("Réaffectons ! \n");
        currentTutoring.scenarioToString(String.valueOf(cas++), "Modification des poids d'affectation !");
    }

    static void unEtudiantManqueDeRespect() {
        System.out.println();
        System.err.println("Hortense a manqué de respect à Mr Carle,\nelle est exclue du tutorat!");
        System.out.println("\t>" + currentTutoring);
        currentTutoring.removeStudent((Tutored) Persons.getPerson("Hortense Chauveau", currentTutoring.getTutored()));
        System.out.println("\t>" + currentTutoring);
        System.out.println();
        System.out.println("Relançons une affectation !\n");
        currentTutoring.scenarioToString(String.valueOf(cas++), "Il faut respecter les enseignants !");
    }

    static void finDAnnee() {
        System.out.println();
        System.out.println(
                "C'est super, grâce au dévoument de Mr Carle,\nTous le monde a eu une super note au CTP de Web!");
        System.out.println();
        System.out.println("Fin du scénario, merci de l'avoir éxécuté!");
    }

}
