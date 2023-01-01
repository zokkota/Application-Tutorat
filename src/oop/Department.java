package oop;

import java.util.Collection;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import graphs.Tutoring;

public class Department {
    private String name;
    private Set<Student> students;
    private Set<Teacher> teachers;
    private Map<Resource, Tutoring> tutorings = new EnumMap<>(Resource.class);

    public Department(String name) {
        this(name, new HashSet<>(), new HashSet<>());
    }

    public Department(String name, Set<Student> students, Set<Teacher> teachers) {
        this.name = name;
        this.students = students;
        this.teachers = teachers;
    }

    // ------------------------
    // Class methods
    // ------------------------

    // Size of Department
    public int getNbOfStudents() {
        return this.students.size();
    }

    public int getNbOfTeachers() {
        return this.teachers.size();
    }

    public int getNbOfTutorings() {
        return this.tutorings.size();
    }

    // Adding Persons to the Department

    /**
     * Adds a student to the department if they aren't already listed.
     * 
     * @param student the student to add.
     * @return {@code true} if this department did not already contain the specified
     *         student.
     */
    public boolean addStudent(Student student) {
        return this.students.add(student);
    }

    /**
     * Adds a collection of student to the department if they aren't already listed.
     * 
     * @param students group of students to add.
     * @return true if this department's list of students changed as a result of the
     *         call.
     */
    public boolean addStudent(Collection<Student> students) {
        return this.students.addAll(students);
    }

    /**
     * Adds a teacher to the department if they aren't already listed.
     * 
     * @param teacher the teacher to add.
     * @return {@code true} if this department did not already contain the specified
     *         teacher.
     */
    public boolean addTeacher(Teacher teacher) {
        return this.teachers.add(teacher);
    }

    /**
     * Adds a collection of teachers to the department if they aren't already
     * listed.
     * 
     * @param teachers group of teachers to add.
     * @return true if this department's list of teachers changed as a result of the
     *         call.
     */
    public boolean addTeacher(Collection<Teacher> teachers) {
        return this.teachers.addAll(teachers);
    }

    /**
     * Adds a person to the department if they aren't already listed.
     * 
     * @param person any person to add.
     * @return {@code true} if this department did not already contain the specified
     *         person.
     */
    public boolean add(Person person) {
        if (person.isStudent()) {
            return addStudent((Student) person);
        } else {
            return addTeacher((Teacher) person);
        }
    }

    /**
     * Adds a collection of people to the department if they aren't already listed.
     * 
     * @param people group of persons to add.
     * @return true if this department's lists changed as a result of the call.
     */
    public boolean add(Collection<Person> people) {
        boolean add = true;
        for (Person person : people) {
            add &= add(person);
        }
        return add;
    }

    // Tutoring
    public void newTutoring(Resource resource) {
        tutorings.put(resource, new Tutoring(resource));
    }

    public void newTutoring(Resource resource, Teacher teacher) {
        tutorings.put(resource, new Tutoring(teacher, resource));
    }

    public Tutoring getTutoring(Resource resource) {
        return tutorings.get(resource);
    }

    public Teacher getTeacher(Resource resource) {
        return tutorings.get(resource).getTeacher();
    }

    public void setTeacher(Resource resource, Teacher teacher) {
        if (!teachers.contains(teacher)) {
            teachers.add(teacher);
        }
        tutorings.get(resource).setTeacher(teacher);
    }

    // Adding Students to a Tutoring
    public void registerStudent(Resource resource) {
        for (Student student : students) {
            if (student.grades.containsKey(resource)) {
                checkedRegisterStudent(resource, student);
            }
        }
    }

    public void registerStudent(Resource resource, Collection<Student> students) {
        for (Student student : students) {
            registerStudent(resource, student);
        }
    }

    public void registerStudent(Resource resource, Student student) {
        if (!student.grades.containsKey(resource)) {
            student.addGrade(resource, Student.getDefaultGrade());
        }
        checkedRegisterStudent(resource, student);
    }

    private void checkedRegisterStudent(Resource resource, Student student) {
        tutorings.get(resource).addStudent(student);
    }

    @Override
    public String toString() {
        return "Departement [ " + name + ", étudiants= " + getNbOfStudents() + ", enseignants= " + getNbOfTeachers()
                + ", tutorats= " + tutorings.keySet() + "]";
    }

    // ------------------------
    // Attribute getters & setters
    // ------------------------
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Teacher> getCopyOfTeachers() {
        return Set.copyOf(teachers);
    }

    public Set<Student> getStudents() {
        return students;
    }

    public Map<Resource, Tutoring> getTutorings() {
        return Map.copyOf(tutorings);
    }

}
