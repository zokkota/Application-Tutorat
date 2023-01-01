package oop;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import graphs.Tutoring;

public class Teacher extends Person {
    private List<Resource> resources = new ArrayList<>();

    private static double defaultWeighting = 1;

    private Map<Coefficient, Double> weightings = new EnumMap<>(Coefficient.class);

    /**
     * Constructs a Teacher from their name and a collection of resource(s) they
     * teach.
     * 
     * @param name      teacher's name.
     * @param resources resources the teacher teaches.
     */
    public Teacher(String name, Collection<Resource> resources) {
        super(name, false);
        this.resources = new ArrayList<>();
        this.resources.addAll(resources);

        this.weightings.put(Coefficient.GRADES, Teacher.defaultWeighting);
        this.weightings.put(Coefficient.ABSENCES, Teacher.defaultWeighting);
        this.weightings.put(Coefficient.LEVEL, Teacher.defaultWeighting);
    }

    /**
     * Constructs a Teacher from their name only.
     * 
     * @param name teacher's name.
     */
    public Teacher(String name) {
        this(name, new ArrayList<>());
    }

    /**
     * Construct a Teacher from their name and one resource they teach.
     * 
     * @param name     teacher's name.
     * @param resource resource the teacher teaches.
     */
    public Teacher(String name, Resource resource) {
        this(name);
        resources.add(resource);
    }

    /**
     * Construct a Teacher from their name and the name of one resource they teach.
     * 
     * @param name         teacher's name.
     * @param resourceName name of a resource the teacher teaches.
     */
    public Teacher(String name, String resourceName) {
        this(name, Resource.valueOf(resourceName));
    }

    // ------------------------
    // Class methods
    // ------------------------
    /**
     * Add a resource to the list of resources a teacher can teach.
     * 
     * @param resource resource to add.
     * @return true if resource could be added to the list, false otherwise.
     */
    public boolean addResource(Resource resource) {
        return this.resources.add(resource);
    }

    @Override
    public String toString() {
        if (Person.shortName) {
            return super.toString();
        }
        return super.toString().substring(0, super.toString().length() - 1) + ", matières= " + resources.toString()
                + "]";
    }

    // ------------------------
    // Attribute getters & setters
    // ------------------------
    public Map<Coefficient, Double> getWeightings() {
        return this.weightings;
    }

    /**
     * Gets an immutable copy of the list of resources the teacher teaches.
     * 
     * @return a list of resources.
     */
    public List<Resource> getResources() {
        return List.copyOf(resources);
    }

    // Custom
    // Weighting
    public double getWeighting(Coefficient coefficient) {
        return this.weightings.get(coefficient);
    }

    public double getLevelWeighting() {
        return getWeighting(Coefficient.LEVEL);
    }

    public double getAverageWeighting() {
        return getWeighting(Coefficient.GRADES);
    }

    public double getAbsenceWeighting() {
        return getWeighting(Coefficient.ABSENCES);
    }

    public void setWeighting(Coefficient coefficient, double weighting) {
        double weight = weighting;
        if (weight < 0) {
            weight = 0;
        } else if (weight > Tutoring.getMaxWeighting()) {
            weight = Tutoring.getMaxWeighting();
        }
        this.weightings.replace(coefficient, weight);
    }

    public void setWeighting(Double[] coefs){
        if (coefs.length != 3){
            return;
        }
        setAverageWeighting(coefs[0]);
        setAbsenceWeighting(coefs[1]);
        setLevelWeighting(coefs[2]);
    }

    /**
     * Sets the weighting of the average of students for weight computing. Default
     * value is 1.
     * 
     * @param levelWeighting new weighting of students level.
     */
    public void setLevelWeighting(double weighting) {
        setWeighting(Coefficient.LEVEL, weighting);
    }

    /**
     * Sets the weighting of the level of students for weight computing. Default
     * value is 1.
     * 
     * @param averageWeighting new weighting of students average.
     */
    public void setAverageWeighting(double weighting) {
        setWeighting(Coefficient.GRADES, weighting);
    }

    /**
     * Sets the weighting of the level of students for weight computing. Default
     * value is 1.
     * 
     * @param absenceWeighting new weighting of students absence.
     */
    public void setAbsenceWeighting(double weighting) {
        setWeighting(Coefficient.ABSENCES, weighting);
    }

    // Static getters & setters
    public static double getDefaultWeighting() {
        return defaultWeighting;
    }

    public static void setDefaultWeighting(double defaultWeighting) {
        Teacher.defaultWeighting = defaultWeighting;
    }

}
