package oop;

import graphs.Tutoring;
import utility.Tools;

/**
 * Class that represents a tutored student.
 * 
 * @author Léopold V.
 * @author Alexandre H.
 */
public class Tutored extends Student {

    /**
     * Instantiate a tutored student.
     * 
     * @param name       tutored student's name.
     * @param absences   tutored student's absences.
     * @param motivation tutored student's motivation, letter A, B or C.
     * 
     */
    public Tutored(String name, int absences, char motivation) {
        super(name, 1, absences, motivation);
    }

    @Override
    public double getWeight(Resource resource, double gradesAverage, double absencesAverage, double gradesWeight,
            double absencesWeight, double levelWeight) {
        return ((this.grades.get(resource) / gradesAverage) * gradesWeight
                + Math.sqrt((1 + this.absences) / (1 + absencesAverage)) * absencesWeight)
                * Tools.motivationValue(this.motivation)
                / 2; // Le tout divisé par le nombre de paramètres pour rester autour de 1
    }

    @Override
    public double getWeight(Tutoring tutorat) {
        return getWeight(tutorat.getResource(), tutorat.getTutoredGradesAverage(), tutorat.getTutoredAbsenceAverage(),
                tutorat.getTeacher().getAverageWeighting(), tutorat.getTeacher().getAbsenceWeighting(),
                tutorat.getTeacher().getLevelWeighting());
    }

    public boolean isTutored(){
        return true;
    }
}
