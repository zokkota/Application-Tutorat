package oop;

public enum Resource {

    R101("Initiation au développement"),
    R102("Développement d'interfaces web"),
    R103("Introduction à l'architecture des ordinateurs"),
    R104("Introduction aux systèmes d'exploitation et à leur fonctionnement"),
    R105("Introduction aux bases de données et SQL"),
    R106("Mathématiques discrètes");

    private static boolean shortName = false;

    private String label;

    Resource(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public String getName(){
        return this.name();
        
    }

    @Override
    public String toString() {
        if (Resource.shortName) { 
            return this.getName();
        }
        return this.getName()+": "+this.getLabel();
    }

    public static void setShortName(boolean bool){
        Resource.shortName = bool;
    }
}
