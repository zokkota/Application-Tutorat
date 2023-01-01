package theotests;

/**
 * Test Class
 * 
 * @author Franos T.
 * 
 */
public class Test {
	// public static void main(String[] args) {

	// test class Assignment

	// Tout les cas d'Assignation

	// Assignation tuteur / tutoré (cas ou moins de tuteur)

	// Tutor tutor1 = new Tutor("A", 12.1, 2, 3, 'A');
	// Tutor tutor2 = new Tutor("Jean", 13, 2, 3, 'C');
	// Tutor tutor3 = new Tutor("Pierre", 18, 2, 3, 'A');

	// Tutored tutored1 = new Tutored("Axel", 5, 2, 'C');
	// Tutored tutored2 = new Tutored("Tim", 15, 2, 'C');
	// Tutored tutored3 = new Tutored("Harry", 3, 2, 'C');
	// Tutored tutored4 = new Tutored("Paul", 12, 2, 'C');

	// List<Tutor> tuteur = new ArrayList<Tutor>();
	// List<Tutored> tutoré = new ArrayList<Tutored>();

	// tuteur.add(tutor1);
	// tuteur.add(tutor2);
	// tuteur.add(tutor3);
	//
	// tutoré.add(tutored1);
	// tutoré.add(tutored2);
	// tutoré.add(tutored3);
	// tutoré.add(tutored4);

	// Departement dep = new Departement("Test");
	// Assignment mat1 = new Assignment(tutoré, tuteur);
	// mat1.setPolyTutor(false);

	// dep.addTutoring(Resource.R101, mat1);

	// System.out.println(dep "\n" + "\n");

	// System.out.println(mat1.getTextAssignment());

	// System.out.println(mat1.getAssignment());

	// Cas ou le polytutorat accepté

	// Tutor tutor1 = new Tutor("A", 12.1, 2, 3, 'A');
	// Tutor tutor2 = new Tutor("Jean", 13, 2, 3, 'C');
	// Tutor tutor3 = new Tutor("Pierre", 18, 3, 0, 'A'); //obligé d'être 3ème année
	// pour pouvoir tutoré 2 étudiants
	//
	// Tutored tutored1 = new Tutored("Axel", 5, 2, 'C');
	// Tutored tutored2 = new Tutored("Tim", 15, 2, 'C');
	// Tutored tutored3 = new Tutored("Harry", 3, 2, 'C');
	// Tutored tutored4 = new Tutored("Paul", 12, 2, 'C');
	//
	// List<Tutor> tuteur = new ArrayList<Tutor>();
	// List<Tutored> tutoré = new ArrayList<Tutored>();
	////
	// tuteur.add(tutor1);
	// tuteur.add(tutor2);
	// tuteur.add(tutor3);
	//
	// tutoré.add(tutored1);
	// tutoré.add(tutored2);
	// tutoré.add(tutored3);
	// tutoré.add(tutored4);
	//
	// Departement dep = new Departement("Test");
	// Assignment mat1 = new Assignment(tutoré, tuteur);
	// mat1.togglePolyTutor();
	// mat1.setPolyTutor(true);
	// dep.addTutoring(Resource.R101, mat1);
	//
	// System.out.println(dep + "\n" + "\n");
	//
	// System.out.println(mat1.getTextAssignment());
	//
	// System.out.println(mat1.getAssignment());

	// Cas + tuteur que tutoré

	// Tutor tutor1 = new Tutor("A", 12.1, 2, 3, 'A');
	// Tutor tutor2 = new Tutor("Jean", 13, 2, 3, 'C');
	// Tutor tutor3 = new Tutor("Pierre", 18, 3, 0, 'A');
	// Tutor tutor4 = new Tutor("Michel", 14, 3, 1, 'A');
	// Tutor tutor5 = new Tutor("Benoit", 19, 2, 7, 'c');
	//
	// Tutored tutored1 = new Tutored("Axel", 5, 2, 'C');
	// Tutored tutored2 = new Tutored("Tim", 15, 2, 'C');
	// Tutored tutored3 = new Tutored("Harry", 3, 2, 'C');
	// Tutored tutored4 = new Tutored("Paul", 12, 2, 'C');
	//
	// List<Tutor> tuteur = new ArrayList<Tutor>();
	// List<Tutored> tutoré = new ArrayList<Tutored>();
	//
	// tuteur.add(tutor1);
	// tuteur.add(tutor2);
	// tuteur.add(tutor3);
	// tuteur.add(tutor4);
	// tuteur.add(tutor5);
	//
	// tutoré.add(tutored1);
	// tutoré.add(tutored2);
	// tutoré.add(tutored3);
	// tutoré.add(tutored4);
	//
	//
	// Departement dep = new Departement("Test");
	// Assignment mat1 = new Assignment(tutoré, tuteur);
	// mat1.togglePolyTutor();
	// mat1.setPolyTutor(true);
	// dep.addTutoring(Resource.R101, mat1);
	//
	// System.out.println(dep + "\n" + "\n");
	//
	// System.out.println(mat1.getTextAssignment());
	//
	// System.out.println(mat1.getAssignment());

	// Affectation nombre tuteur et tutoré égal (sans polytutor)
	// Tutor tutor1 = new Tutor("A", 12.1, 2, 3, 'A');
	// Tutor tutor2 = new Tutor("Jean", 13, 2, 3, 'C');
	// Tutor tutor3 = new Tutor("Pierre", 18, 3, 0, 'A');
	// Tutor tutor4 = new Tutor("Michel", 14, 3, 1, 'A');
	//
	// Tutored tutored1 = new Tutored("Axel", 5, 2, 'C');
	// Tutored tutored2 = new Tutored("Tim", 15, 2, 'C');
	// Tutored tutored3 = new Tutored("Harry", 3, 2, 'C');
	// Tutored tutored4 = new Tutored("Paul", 12, 2, 'C');
	//
	// List<Tutor> tuteur = new ArrayList<Tutor>();
	// List<Tutored> tutoré = new ArrayList<Tutored>();
	//
	// tuteur.add(tutor1);
	// tuteur.add(tutor2);
	// tuteur.add(tutor3);
	// tuteur.add(tutor4);
	//
	// tutoré.add(tutored1);
	// tutoré.add(tutored2);
	// tutoré.add(tutored3);
	// tutoré.add(tutored4);
	//
	//
	// Departement dep = new Departement("Test");
	// Assignment mat1 = new Assignment(tutoré, tuteur);
	// mat1.setPolyTutor(false);
	// dep.addTutoring(Resource.R101, mat1);
	//
	// System.out.println(dep + "\n" + "\n");
	//
	// System.out.println(mat1.getTextAssignment());
	//
	// System.out.println(mat1.getAssignment());

	// Affectation nombre tuteur = nombre tutoré avec polytutorat autorisé
	// Tutor tutor1 = new Tutor("A", 12.1, 2, 3, 'A');
	// Tutor tutor2 = new Tutor("Jean", 13, 2, 3, 'C');
	// Tutor tutor3 = new Tutor("Pierre", 18, 3, 0, 'A');
	// Tutor tutor4 = new Tutor("Michel", 14, 3, 1, 'A');
	//
	// Tutored tutored1 = new Tutored("Axel", 5, 2, 'C');
	// Tutored tutored2 = new Tutored("Tim", 15, 2, 'C');
	// Tutored tutored3 = new Tutored("Harry", 3, 2, 'C');
	// Tutored tutored4 = new Tutored("Paul", 12, 2, 'C');
	//
	// List<Tutor> tuteur = new ArrayList<>();
	// List<Tutored> tutore = new ArrayList<>();
	//
	// tuteur.add(tutor1);
	// tuteur.add(tutor2);
	// tuteur.add(tutor3);
	// tuteur.add(tutor4);
	//
	// tutore.add(tutored1);
	// tutore.add(tutored2);
	// tutore.add(tutored3);
	// tutore.add(tutored4);
	//
	//
	// Departement dep = new Departement("Test");
	// Assignment mat1 = new Assignment(tutore, tuteur);
	// mat1.togglePolyTutor();
	// mat1.setPolyTutor(true);
	// dep.addTutoring(Resource.R101, mat1);
	//
	// System.out.println(dep + "\n" + "\n");
	//
	// System.out.println(mat1.getTextAssignment());
	//
	// System.out.println(mat1.getAssignment());
	//
	//
	// }
}
