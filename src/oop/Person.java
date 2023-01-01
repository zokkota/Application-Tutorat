package oop;

public abstract class Person {
    protected String forename;
    protected String surname;
    /**
     * A Person is either a Student or a Teacher
     */
    private boolean isAStudent;

    protected static boolean shortName = true;

    /**
     * Constructs a Person from a forename and a surname.
     * 
     * @param forename  forename of the person.
     * @param surname   surname of the person.
     * @param isStudent defines whether the person is a student or not.
     */
    protected Person(String forename, String surname, boolean isStudent) {
        this(forename + " " + surname, isStudent);
    }

    /**
     * Constructs a Person from a full name (forename and surname separated by a
     * space).
     * 
     * @param name       full name in the form of : "forename surname".
     * @param isAStudent defines whether the person is a student or not.
     */
    protected Person(String name, boolean isAStudent) {

        String[] names = name.split(" ");

        if (names.length == 1) {
            this.forename = name;
            this.surname = null;
        } else {
            this.forename = names[0];
            this.surname = names[1];
        }
        this.isAStudent = isAStudent;
    }

    // ------------------------
    // Class methods
    // ------------------------
    public boolean isStudent() {
        return this.isAStudent;
    }

    @Override
    public String toString() {
        if (Person.shortName) {
            return this.getName();
        }
        return this.getClass().getSimpleName() + " [" + this.getName() + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((forename == null) ? 0 : forename.hashCode());
        result = prime * result + ((surname == null) ? 0 : surname.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Person other = (Person) obj;
        if (forename == null) {
            if (other.forename != null)
                return false;
        } else if (!forename.equals(other.forename))
            return false;
        if (surname == null) {
            if (other.surname != null)
                return false;
        } else if (!surname.equals(other.surname))
            return false;
        return true;
    }

    // ------------------------
    // Attribute getters & setters
    // ------------------------
    public String getForename() {
        return forename;
    }

    public void setForename(String forename) {
        this.forename = forename;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    // Custom getters
    public String getName() {
        if (this.surname == null) {
            return forename;
        }
        return forename + " " + surname;
    }

    // Static setters
    public static void setShortName(boolean bool) {
        Person.shortName = bool;
    }

}
