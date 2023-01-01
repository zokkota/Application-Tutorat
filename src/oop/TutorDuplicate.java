package oop;

public class TutorDuplicate extends Tutor {
    private static double weightModifier = 1.5;
    private static char tutorDuplicateIdentifier = 'D';
    private Tutor tutor;

    private TutorDuplicate(String name, int level, int absences, char motivation) {
        super(name + "(" + TutorDuplicate.tutorDuplicateIdentifier + ")", level, absences, motivation, 1);
    }

    public TutorDuplicate(Tutor tutor) {
        this(tutor.getName(), tutor.getLevel(), tutor.getAbsences(), tutor.getMotivation());
        this.grades.putAll(tutor.getGrades());
        this.tutor = tutor;
    }

    public Tutor getTutor(){
        return this.tutor;
    }

    @Override
    public double getWeight(Resource resource, double gradesAverage, double absencesAverage, double gradesWeight,
            double absencesWeight, double levelWeight) {
        return super.getWeight(resource, gradesAverage, absencesAverage, gradesWeight, absencesWeight, levelWeight)
                * TutorDuplicate.weightModifier;
    }

    @Override
    public boolean isDuplicate() {
        return true;
    }

    // ------------------------
    // Attribute getters & setters
    // ------------------------

    // Static getters & setters
    public static double getWeightModifier() {
        return weightModifier;
    }

    public static void setWeightModifier(double weightModifier) {
        TutorDuplicate.weightModifier = weightModifier;
    }

    public static char getTutorDuplicateIdentifier() {
        return tutorDuplicateIdentifier;
    }

    public static void setTutorDuplicateIdentifier(char tutorDuplicateIdentifier) {
        TutorDuplicate.tutorDuplicateIdentifier = tutorDuplicateIdentifier;
    }

    public String getRealName() {
        return getName().substring(0, getName().length() - 2);
    }
}
