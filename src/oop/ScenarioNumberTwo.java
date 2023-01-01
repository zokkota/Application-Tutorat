package oop;

import java.util.ArrayList;
import java.util.List;

import graphs.Tutoring;
import utility.Persons;
import utility.ToolsCSV;
import utility.ToolsJSON;

public class ScenarioNumberTwo {
    private static final String SEPARATOR = "\n\t\t-----\n";
    static List<Department> iut = new ArrayList<>();
    static Department department;

    static Tutoring sysTutoring, archTutoring;
    static Resource sys = Resource.R103;
    static Resource archi = Resource.R104;
    static Teacher teacher;

    static int etape = 0;

    private ScenarioNumberTwo() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    public static void main(String[] args) {
        System.out.println("\nL'I.U.T. de Lille veut mettre en place un système de tutorat");
        System.out.println("\t> Chaque Département dispose d'une liste d'élèves et de professeurs");
        System.out.println("\t> Ces élèves disposent ou non de notes pour une matière");
        System.out.println("\t> Cette note est le critère d'éligibilité au tutorat pour cette matière");
        System.out.println("\t> Chaque tutorat dispose d'un professeur référent\n");

        inscriptionDepartement();
        inscriptionTutorat();
        utilisationFiltre();
        affectationBasique();
        affectationForcee();
        nouvelEtudiant();
        importanceMoyenne();
        departEtudiant();
    }

    public static void inscriptionDepartement() {
        System.out.println(SEPARATOR);
        System.out.println("Nous sommes maintenant à la fin du premier semestre.");
        System.out.println("Certains étudiants ont besoin d'une remise à niveau pour le semestre 2.");
        System.out.println(
                "Monsieur Baste se propose de créer un tutorat pour les étudiants en système et en architecture.");

        department = new Department("Informatique");
        iut.add(department);

        teacher = new Teacher("Julien Baste", List.of(sys, archi));
        department.add(teacher);

        System.out.println("\t> IUT: " + iut);
        System.out.println("\t> Département: " + department.getCopyOfTeachers());

        System.out.println("Nous avons toujours les même étudiants inscrits à l'IUT,");
        System.out.println("aucun n'a abandonné malgré le DS de base de données qui était pourtant catastrophique.");

        department.addStudent(ToolsCSV.importStudents());
        System.out.println("\t> IUT: " + iut + "\n");
    }

    public static void inscriptionTutorat() {
        System.out.println(SEPARATOR);
        System.out.println("Cette fois-ci, c'est donc M. Baste qui s'occupe de 2 tutorats. Il est temps de les créer.");

        department.newTutoring(sys, teacher);
        department.newTutoring(archi, teacher);

        sysTutoring = department.getTutoring(sys);
        archTutoring = department.getTutoring(archi);

        System.out.println("\t> IUT: " + iut);
        System.out.println("\t> Tutorats: " + department.getTutorings().values() + "\n");

        System.out.println("Maintenant, il est temps d'inscrire les étudiants éligibles dans les deux tutorats");
        department.registerStudent(sys);
        department.registerStudent(archi);
        System.out.println("\t> Tutorats: " + department.getTutorings().values() + "\n");
    }

    public static void utilisationFiltre() {
        System.out.println(SEPARATOR);
        System.out.println(
                "Maintenant que tout le monde est inscrit, il est temps d'appliquer des filtres à nos listes.");
        System.out.println("Modifiez le fichier JSON (localisation : ./res/data/filters.json");

        double[] filters = ToolsJSON.readFilters();
        sysTutoring.filterStudents(filters);
        archTutoring.filterStudents(filters);
        System.out.println("\t> Tutorats: " + department.getTutorings().values() + "\n");

        System.out.println("En fonction des filtrers que vous avez choisi, il reste plus ou moins de tuteurs/tutorés.");
        System.out.println("Disclaimer : la suite des évènements à été créé avec certains filtres, nous vous conseillons");
        System.out.println("d'élargir les ensembles uniquement, sinon le scénario risque de planter.");
    }

    static void affectationBasique() {
        etape++;
        System.out.println(SEPARATOR);
        System.out.println("Premières affectations sans modifications:\n");
        sysTutoring.scenarioToString(String.valueOf(etape), "Situation de base - système");
        archTutoring.scenarioToString(String.valueOf(etape), "Situation de base - architecture");
    }

    static void affectationForcee() {
        etape++;
        System.out.println(SEPARATOR);
        System.out.println("M. Baste a entendu dire que Joseph s'entend bien avec Hugues en architecture,");
        System.out.println("et que Sabine apprécie la compagnie de Daniel en système. Mettonsles ensemble.\n");

        archTutoring.addForcedAssignments("Hugues Bigot", "Joseph Boyer");
        sysTutoring.addForcedAssignments("Sabine Besnard", "Daniel Lefebvre");

        System.out.println("Affectations avec couples forcés:\n");

        sysTutoring.scenarioToString(String.valueOf(etape), "Avec couple - système");
        archTutoring.scenarioToString(String.valueOf(etape), "Avec couple - architecture");
    }

    static void nouvelEtudiant() {
        etape++;
        System.out.println(SEPARATOR);
        System.out.println("Un nouvel étudiant venu de l'IUT de Jouy-En-Josas est arrivé à Lille,");
        System.out.println("il faut l'ajouter au tutorat d'architecture car il a des lacunes.\n");

        Tutored newEtu = new Tutored("Etienne Delatorah", 0, 'B');
        // je ne fais que suivre les conventions de nommage d'Alexandre
        newEtu.setGrade(Resource.R104, 5.42);
        System.out.println("\t> Tutorat architecture: " + archTutoring);
        System.out.println("\t> On inscrit un nouvel étudiant: " + newEtu);

        department.add(newEtu);
        department.registerStudent(Resource.R104, newEtu);
        System.out.println("\t> Tutorat architecture: " + archTutoring + "\n");

        System.out.println("On relance l'affectation et Etienne aura un camarade.\n");
        archTutoring.scenarioToString(String.valueOf(etape), "Etienne arrive - architecture");
    }

    static void importanceMoyenne() {
        etape++;
        System.out.println(SEPARATOR);
        System.out.println("M. Baste prête beaucoup d'importance à la moyenne,");
        System.out.println("il veut que celle-ci compte 2 fois plus pour l'affectation.\n");

        System.out.println("\t> Coefficients de base:\t\t" + teacher.getWeightings());
        teacher.setAverageWeighting(2);
        System.out.println("\t> Après modification:\t\t" + teacher.getWeightings() + "\n");

        System.out.println("On relance l'affectation et la moyenne aura un poids plus important.\n");
        sysTutoring.scenarioToString(String.valueOf(etape), "Moyenne x2 - système");
        archTutoring.scenarioToString(String.valueOf(etape), "Moyenne x2 - architecture");
    }

    static void departEtudiant() {
        etape++;
        System.out.println(SEPARATOR);
        System.out.println("Malheureusement, Marine a décidé de partir au conservatoire pour faire de la cornemuse.");
        System.out.println("Grand bien lui fasse, mais il faut faire le ménage maintenant.\n");

        System.out.println("\t> Avant:");
        System.out.println("\t> Tutorat système: " + sysTutoring);
        System.out.println("\t> Tutorat architecture: " + archTutoring);
        sysTutoring.removeStudent((Tutored) Persons.getPerson("Marine Roux", sysTutoring.getTutored()));
        archTutoring.removeStudent((Tutored) Persons.getPerson("Marine Roux", archTutoring.getTutored()));
        System.out.println("\t> Après:");
        System.out.println("\t> Tutorat système: " + sysTutoring);
        System.out.println("\t> Tutorat architecture: " + archTutoring + "\n");

        System.out.println("Il n'y a plus qu'affecter pour la dernière.\n");
        sysTutoring.scenarioToString(String.valueOf(etape), "Marine est partie - système");
        archTutoring.scenarioToString(String.valueOf(etape), "Marine est partie - architecture");
    }
}
