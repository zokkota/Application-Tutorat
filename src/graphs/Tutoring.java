package graphs;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import fr.ulille.but.sae2_02.graphes.Arete;
import fr.ulille.but.sae2_02.graphes.CalculAffectation;
import fr.ulille.but.sae2_02.graphes.GrapheNonOrienteValue;
import oop.Resource;
import oop.Student;
import oop.Teacher;
import oop.Tutor;
import oop.TutorDuplicate;
import oop.Tutored;
import utility.Couples;
import utility.Graphs;
import utility.Persons;
import utility.Tools;
import utility.ToolsJSON;
import utility.Tutors;

/**
 * Class that represents an assignment of two groups of students : one is
 * tutored, the other is tutoring. Encapsulates the {@code CalculAffectation}
 * class to add a level of abstraction and hide data processing and heaving
 * calculations.
 * 
 * @author Léopold V.
 * @author Alexandre H.
 * @see #Assignment(List, List)
 * @see #getAssignment()
 * @see fr.ulille.but.sae2_02.graphes.CalculAffectation
 */
public class Tutoring {
    private Resource resource;

    private List<Tutored> tutored;
    private List<Tutor> tutors;

    private boolean polyTutor;

    private List<Student> waitingList;

    private Set<Couple> forcedCouples = new HashSet<>();
    private Set<Couple> forbiddenCouples = new HashSet<>();

    public List<Couple> affectations = new ArrayList<>();

    private double tutoredGradesAverage;
    private double tutorGradesAverage;

    private double tutoredAbsenceAverage;
    private double tutorAbsenceAverage;

    private Teacher teacher;

    private static double maxWeighting = 5;

    private static int forcedAffectationWeight = 1000;

    private CalculAffectation<Student> affectation = null;

    public Tutoring(Resource resource) {
        this.resource = resource;

        this.tutored = new ArrayList<>();
        this.tutors = new ArrayList<>();

        this.polyTutor = true;

        this.waitingList = new ArrayList<>();

        this.tutoredGradesAverage = 0;
        this.tutorGradesAverage = 0;

        this.tutoredAbsenceAverage = 0;
        this.tutorAbsenceAverage = 0;

        this.teacher = null;
    }

    /**
     * Constructs an assignment with supplied tutored and tutors lists.
     * 
     * @param tutored List of tutored students that will be taken in charge.
     * @param tutors  List of tutor students that will take in charge tutored
     *                students.
     */
    public Tutoring(List<Tutored> tutored, List<Tutor> tutors, Resource resource) {
        this(resource);
        this.tutored = tutored;
        this.tutors = tutors;
    }

    /**
     * Constructs an assignment with supplied students list. Student will be
     * dispatched automatically.
     * 
     * @param students List of students to dispatch.
     */
    public Tutoring(Set<Student> students, Resource resource) {
        this(resource);
        addStudent(students);
    }

    /**
     * Constructs an assignment with supplied students list and teacher. Students
     * will be dispatched automatically.
     * 
     * @param students List of students to dispatch.
     * @param teacher  Teacher overseeing the tutoring.
     */
    public Tutoring(Set<Student> students, Teacher teacher, Resource resource) {
        this(students, resource);
        this.teacher = teacher;
    }

    /**
     * Constructs an assignment with supplied lists of tutored and tutors and
     * teacher.
     * 
     * @param tutored List of tutored students that will be taken in charge.
     * @param tutors  List of tutor students that will take in charge tutored
     *                students.
     * @param teacher Teacher overseeing the tutoring.
     */
    public Tutoring(List<Tutored> tutored, List<Tutor> tutors, Teacher teacher, Resource resource) {
        this(tutored, tutors, resource);
        this.teacher = teacher;
    }

    /**
     * Constructs an assignment with a teacher.
     * 
     * @param teacher Teacher overseeing the tutoring.
     */
    public Tutoring(Teacher teacher, Resource resource) {
        this(resource);
        this.teacher = teacher;
    }

    // ------------------------
    // Class methods
    // ------------------------
    private void updateAverages() {
        double[] values = Tutoring.computeAverages(tutored, resource);
        this.tutoredAbsenceAverage = values[0];
        this.tutoredGradesAverage = values[1];

        values = Tutoring.computeAverages(tutors, resource);
        this.tutorAbsenceAverage = values[0];
        this.tutorGradesAverage = values[1];
    }

    private static double[] computeAverages(List<? extends Student> students, Resource resource) {
        double abs = 0;
        double avg = 0;
        for (Student student : students) {
            abs += student.getAbsences();
            avg += student.getGrade(resource);
        }
        return new double[] { abs / students.size(), avg / students.size() };
    }

    /**
     * Adds a duo of tutored and tutor student to assign together.
     * 
     * @param tutored the tutored student.
     * @param tutor   the tutor student.
     * 
     * @throws IllegalArgumentException if this duo has already been put in the map.
     */
    public boolean addForcedAssignments(Tutored tutored, Tutor tutor) {
        return addForcedAssignments(new Couple(tutored, tutor));
    }

    public boolean addForcedAssignments(Couple couple){
        return this.forcedCouples.add(couple);
    }

    public boolean addForcedAssignments(String tutored, String tutor) {
        return addForcedAssignments((Tutored) Persons.getPerson(tutored, this.tutored),
                (Tutor) Persons.getPerson(tutor, this.tutors));
    }

    /**
     * Adds a duo of tutored and tutor students to not assign together.
     * 
     * @param tutored the tutored student.
     * @param tutor   the tutor student.
     * 
     * @throws IllegalArgumentException if this duo has already been put in the map.
     */


    public boolean addForbiddenAssignments(Tutored tutored, Tutor tutor) {
        return addForbiddenAssignments(new Couple(tutored, tutor));
    }

    public boolean addForbiddenAssignments(Tutored tutored, TutorDuplicate tutorDuplicate) {
        return addForbiddenAssignments(new Couple(tutored, tutorDuplicate.getTutor()));
    }

    public boolean addForbiddenAssignments(Couple couple){
        return this.forbiddenCouples.add(couple);
    }

    public boolean addForbiddenAssignments(String tutored, String tutor) {
        return addForbiddenAssignments((Tutored) Persons.getPerson(tutored, this.tutored),
                (Tutor) Persons.getPerson(tutor, this.tutors));
    }

    /**
     * Removes a forced assigment for a tutored student.
     * 
     * @param tutored the tutored student.
     * 
     * @throws IllegalArgumentException if the tutored student has no forced
     *                                  assignment with anyone.
     */
    public boolean removeForcedAssignment(Tutored tutored, Tutor tutor) {
        return Couples.remove(this.forcedCouples, tutored, tutor);        
    }

    /**
     * Removes a forbidden assignment for a tutored student.
     * 
     * @param tutored the tutored student.
     * 
     * @throws IllegalArgumentException if the tutored student has no forbidden
     *                                  assignment with anyone.
     */
    public boolean removeForbiddenAssignment(Tutored tutored, Tutor tutor) {
        return Couples.remove(this.forbiddenCouples, tutored, tutor);
    }

    // ------------------------
    // Collections methods
    // ------------------------
    public void removeStudent(Student student) {
        List<Couple> couples = Couples.containedIn(this.affectations, student);
        if (student.isTutored()){
            this.tutored.remove(student);
            waitingList.addAll(Couples.getTutors(couples));
        }
        else {
            this.tutors.remove(student);
            waitingList.addAll(Couples.getTutored(couples));
        }
        this.affectations.removeAll(couples);
        this.forcedCouples.removeAll(Couples.containedIn(this.forcedCouples, student));
        this.forbiddenCouples.removeAll(Couples.containedIn(this.forbiddenCouples, student));
        
    }

    /**
     * Dispatches all students in the correct list : tutored or tutors.
     * 
     * @param students a list of all students to dispatch.
     */
    public final void addStudent(Collection<? extends Student> students) {
        for (Student s : students) {
            addStudent(s);
        }
    }

    /**
     * Adds a student in the correct list.
     * 
     * @param student Student to add to the assignment.
     * @return true if student was added, false otherwise.
     */
    public final boolean addStudent(Student student) {
        if (this.tutored.contains(student) || this.tutors.contains(student)) {
            return false;
        }

        if (!student.getGrades().containsKey(this.resource)) {
            return false;
        }

        this.waitingList.add(student);
        if (student.getLevel() == 1) {
            return this.tutored.add((Tutored) student);
        } else {
            return this.tutors.add((Tutor) student);
        }
    }

    /**
     * Filters 
     * 
     * @param filters double[] as outputted by readFilters method.
     * 
     * @see ToolsJSON#readFilters()
     */
    public final void filterStudents(double[] filters) {
        List<Tutor> tutorToRemove = new ArrayList<>();
        List<Tutored> tutoredToRemove = new ArrayList<>();

        for (Tutored tutoreds : tutored) {
            double grade = tutoreds.getGrade(resource);
            int abs = tutoreds.getAbsences();

            if (grade < filters[0] || grade > filters[1] || abs < filters[2] || abs > filters[3]) {
                tutoredToRemove.add(tutoreds);
            }
        }
    
        for (Tutor tutor : tutors) {
            double grade = tutor.getGrade(resource);
            int abs = tutor.getAbsences();

            if (grade < filters[4] || grade > filters[5] || abs < filters[6] || abs > filters[7]) {
                tutorToRemove.add(tutor);
            }
        }

        for (Tutored tutoreds : tutoredToRemove) {
            this.removeStudent(tutoreds);
        }
        for (Tutor tutor : tutorToRemove) {
            this.removeStudent(tutor);
        }
    }

    // ------------------------
    // Affectation methods
    // ------------------------
    /**
     * Creates an assignment from 2 lists of students of the object.
     * Steps of the method :
     * 1. computes the weight of all students.
     * 2. duplicates the lists of students to work with them.
     * 3. arrages the lists of students
     * 4. builds a graph from both new lists.
     * 5. compute the assignment, saves the cost of it, then returns the assignment.
     * 
     * @return the resulting assignment
     * 
     * @see #listArrangeTutor std1 = new Tutor("A", 12.1, 2, 3, 'A');
     */
    private void calculAffectation() {
        this.waitingList.clear();
        updateAverages();

        List<Tutored> tutoredCopy = new ArrayList<>(this.tutored);
        List<Tutor> tutorCopy = new ArrayList<>(this.tutors);

        listArrange(tutoredCopy, tutorCopy);

        GrapheNonOrienteValue<Student> graph = Graphs.getGraph(tutoredCopy, tutorCopy, this);

        this.affectation = new CalculAffectation<>(graph, new ArrayList<>(tutoredCopy),
                new ArrayList<>(tutorCopy));
    }

    /**
     * Modifies the lists of students of the object in order to have 2 lists of
     * the same size and suitable for an assignment.
     * 
     * @see Tools#tutorsSplit(List, int)
     * @see Tools#waitingListBuilder(List, int)
     */
    private void listArrange(List<Tutored> tutored, List<Tutor> tutor) {
        int diff = tutored.size() - tutor.size();

        if (diff > 0 && polyTutor) {
            tutor = Tutors.gapClose(tutor, this, diff);
        }

        diff = tutored.size() - tutor.size();
        if (diff > 0) {
            waitingList = Tools.waitingListBuilder(tutored, this, diff);
        } else if (diff < 0) {
            waitingList = Tools.waitingListBuilder(tutor, this, -diff);
        }

        diff = tutored.size() - tutor.size();
        if (diff != 0) {
            throw new IllegalArgumentException("marche pas dommage");
        }
    }

    // ------------------------
    // Display methods
    // ------------------------
    @Override
    public String toString() {
        return "Tutorat [Matière: " + this.resource.getName() + ", Enseignant: " + this.teacher + ", Tuteurs: "
                + this.tutors.size() + ", Tutorés: " + this.tutored.size() + ", Attente: "
                + this.waitingList.size() + "]";
    }

    /**
     * Method that returns a textual representation of the assignment. Includes a
     * waiting list if there is one.
     * 
     * @param getGraph includes a textual representation of the bipartite graphs if
     *                 true.
     * @return the assignment as text.
     */
    public String detailedToString() {
        StringBuilder string = new StringBuilder();
        string.append("Arêtes:\t\t " + this.getAssignment().toString().replaceAll("Arete", "") + "\n");
        if (!waitingList.isEmpty()) {
            string.append("En attente:\t " + this.waitingList + "\n");
        }
        string.append("Coût total:\t " + (double) (((int) (1000 * this.getAffectationCost())) / 1000.00) + "\n\n");
        return string.toString();
    }

    public void scenarioToString(String id, String title) {
        System.out.println("\033[4m" + "Cas " + id + " : " + title + "\033[0m");
        System.out.println(this.detailedToString());
    }

    // ------------------------
    // Attribute getters & setters
    // ------------------------
    public Set<Couple> getForcedCouples() {
        return forcedCouples;
    }

    public Set<Couple> getForbiddenCouples() {
        return forbiddenCouples;
    }

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public List<Tutored> getTutoredCopy() {
        return List.copyOf(this.tutored);
    }

    public List<Tutored> getTutored() {
        return this.tutored;
    }

    public List<Tutor> getTutors() {
        return this.tutors;
    }


    public List<Tutor> getTutorsCopy() {
        return List.copyOf(this.tutors);
    }

    /**
     * Sets whether or not tutors should be split if needed. Default value is true.
     * 
     * @param polyTutor true if tutors should be doubled, false otherwise.
     */
    public void setPolyTutor(boolean polyTutor) {
        this.polyTutor = polyTutor;
    }

    public boolean IsPolyTutor(){
        return this.polyTutor;
    }

    public double getTutoredGradesAverage() {
        updateAverages();
        return tutoredGradesAverage;
    }

    public double getTutorGradesAverage() {
        updateAverages();
        return tutorGradesAverage;
    }

    public double getTutoredAbsenceAverage() {
        updateAverages();
        return tutoredAbsenceAverage;
    }

    public double getTutorAbsenceAverage() {
        updateAverages();
        return tutorAbsenceAverage;
    }

    // Custom
    public Tutor getTutor(String name) {
        return (Tutor) Persons.getPerson(name, tutors);
    }

    public Tutored getTutored(String name) {
        return (Tutored) Persons.getPerson(name, tutored);
    }

    /**
     * Method that gives acces to an <strong>immutable</strong> copy of a list of
     * edges of students that represent an assignment.
     * 
     * @return a copy of the assignment.
     */
    public List<Arete<Student>> getAssignment() {
        calculAffectation();
        return List.copyOf(this.affectation.getAffectation());
    }

    public void affectations(){
        this.affectations.clear();
        calculAffectation();
        Couple c;
        for (Arete<Student> arete : this.affectation.getAffectation()) {
            c = new Couple((Tutored)arete.getExtremite1(), (Tutor)arete.getExtremite2());
            affectations.add(c);
        }
    }

    /**
     * Method that returns an <strong>immutable</strong> copy of the waiting list of
     * an assignment.
     * 
     * @return a copy of the waiting list.
     */
    public List<Student> getWaitingList() {
        return this.waitingList;
    }

    /**
     * Returns the minimal cost of the assignment.
     * 
     * @return minimal cost.
     */
    public double getAffectationCost() {
        if (affectation == null) {
            calculAffectation();
        }
        return this.affectation.getCout() % 1000 + (this.affectation.getCout() % 1000 < 0 ? 1000 : 0);
    }

    // Static getters & setters
    public static double getMaxWeighting() {
        return maxWeighting;
    }

    public static void setMaxWeighting(double maxWeighting) {
        Tutoring.maxWeighting = maxWeighting;
    }

    public static int getForcedAffectationWeight() {
        return forcedAffectationWeight;
    }

    public static void setForcedAffectationWeight(int forcedAffectationWeight) {
        Tutoring.forcedAffectationWeight = forcedAffectationWeight;
    }
}
