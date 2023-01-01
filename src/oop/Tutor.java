package oop;

import graphs.Tutoring;
import utility.Tools;

/**
 * Class that represents a tutor student.
 * 
 * @author Léopold V.
 * @author Alexandre H.
 */
public class Tutor extends Student {
    private int nbofTutored;

    protected static int defaultLevel = 2;
    protected static int defaultNbOfTutored = 1;
    protected static int defaultNbOfTutoredThirdLevel = 2;

    /**
     * Instantiate a tutor.
     * 
     * @param name        tutor's name.
     * @param level       tutor's level, between 1 and 3.
     * @param absences    tutor's absences.
     * @param motivation  tutor's motivation, letter A, B or C.
     * @param nbofTutored number of tutored student the tutor can take in charge. If
     *                    level is not 3, field will be set to 1 in any case.
     */
    public Tutor(String name, int level, int absences, char motivation, int nbofTutored) {
        super(name, level, absences, motivation);
        setNbOfTutored(level, nbofTutored);
    }

    /**
     * Instatiate a tutor with a default number of tutor to take in charge (1 if
     * level is 2, 2 if level is 3.)
     * 
     * @param name       tutor's name.
     * @param level      tutor's level, between 1 and 3.
     * @param absences   tutor's absences.
     * @param motivation tutor's motivation, letter A, B or C.
     */
    public Tutor(String name, int level, int absences, char motivation) {
        this(name, level, absences, motivation, 0);
    }

    // ------------------------
    // Class methods
    // ------------------------
    /**
     * Returns {@code true} if the tutor is a duplicate of another (which is never
     * the case).
     * 
     * @return false.
     */
    public boolean isDuplicate() {
        return false;
    }

    /**
     * Returns a duplicate of the tutor using the TutorDuplicate class.
     * 
     * @return the duplicate of the tutor.
     * @see TutorDuplicate
     */
    public TutorDuplicate duplicate() {
        if (this.nbofTutored > 1) {
            TutorDuplicate duplicate = new TutorDuplicate(this);
            duplicate.getGrades().putAll(this.grades);
            return duplicate;
        }
        return null;
    }

    @Override
    public String toString() {
        if (Person.shortName) {
            return super.toString();
        } else {
            return super.toString().substring(0, super.toString().length() - 1) + ", nbOfTutored= " + this.nbofTutored
                    + "]";
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + nbofTutored;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        Tutor other = (Tutor) obj;
        return nbofTutored == other.nbofTutored;
    }

    // ------------------------
    // Inherited methods to define
    // ------------------------
    @Override
    public double getWeight(Resource resource, double gradesAverage, double absencesAverage, double gradesWeight,
            double absencesWeight, double levelWeight) {
        double grade = (this.grades.get(resource) == 0) ? 0.1 : this.grades.get(resource);
        return ((gradesAverage / grade) * gradesWeight
                + (3.0 / this.level) * levelWeight
                + Math.sqrt((1 + this.absences) / (1 + absencesAverage)) * absencesWeight)
                * Tools.motivationValue(this.motivation)
                / 3; // Le tout divisé par le nombre de paramètres pour rester autour de 1
    }

    @Override
    public double getWeight(Tutoring tutorat) {
        return getWeight(tutorat.getResource(), tutorat.getTutorGradesAverage(), tutorat.getTutorAbsenceAverage(),
                tutorat.getTeacher().getAverageWeighting(), tutorat.getTeacher().getAbsenceWeighting(),
                tutorat.getTeacher().getLevelWeighting());
    }

    // ------------------------
    // Attribute getters & setters
    // ------------------------
    public int getNbofTutored() {
        return nbofTutored;
    }

    public void setNbOfTutored(int nbOfTutored) {
        setNbOfTutored(this.level, nbOfTutored);
    }

    // Custom
    private void setNbOfTutored(int level, int nbofTutored) {
        if (level == 3) {
            if (nbofTutored == 1) {
                this.nbofTutored = 1;
            } else {
                this.nbofTutored = Tutor.defaultNbOfTutoredThirdLevel;
            }
        } else {
            setLevel(Tutor.defaultLevel);
            this.nbofTutored = Tutor.defaultNbOfTutored;
        }
    }

    // Static getters & setters
    public static int getDefaultLevel() {
        return defaultLevel;
    }

    public static void setDefaultLevel(int defaultLevel) {
        Tutor.defaultLevel = defaultLevel;
    }

    public static int getDefaultNbOfTutored() {
        return defaultNbOfTutored;
    }

    public static void setDefaultNbOfTutored(int defaultNbOfTutored) {
        Tutor.defaultNbOfTutored = defaultNbOfTutored;
    }

    public static int getDefaultNbOfTutoredThirdLevel() {
        return defaultNbOfTutoredThirdLevel;
    }

    public static void setDefaultNbOfTutoredThirdLevel(int defaultNbOfTutoredThirdLevel) {
        Tutor.defaultNbOfTutoredThirdLevel = defaultNbOfTutoredThirdLevel;
    }

    public boolean isTutored(){
        return false;
    }

}
