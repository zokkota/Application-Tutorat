package ihm.popup;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import graphs.Couple;
import ihm.Interface;
import ihm.events.Events;
import ihm.utils.DisplayUtils;
import ihm.utils.StudentCellFactory;
import ihm.utils.TutoringUtils;
import ihm.utils.WidgetUtils;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import oop.Motivation;
import oop.Student;
import oop.Tutor;
import oop.Tutored;
import utility.Couples;

public class StudentForm extends PopUp {
    List<Student> students;
    ListView<Student> searchList = new ListView<>();
    Student selectedStudent;
    Button ajouterFresh;
    Button ajouterList;
    Boolean interdite;
    TextField nom, prenom, moyenne, absences;
    Spinner<Motivation> motivation;
    Spinner<Integer> niveau;
    TabPane root;

    Tutored toReaffect;
    Tutor toRemove;
    boolean replacing = false;

    TextField searchListTf = new TextField();

    CheckBox tutoredCb = new CheckBox("Tutorés");
    CheckBox tutorCb = new CheckBox("Tuteurs");
    HBox listCb = new HBox(tutoredCb, WidgetUtils.filler(25), tutorCb);

    private class AddingChecker implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent e) {
            Button target = (Button) e.getTarget();
            if (replacing) {
                confirmReplace();
            } else if (interdite != null) {
                StudentCellFactory.draggedStudent = parent.selectedStudent;
                Events.DragNDropHandler(parent, selectedStudent, interdite);
            } else if (target == ajouterFresh) {
                if (checkInputs()) {
                    selectedStudent = newStudent();
                    confirm();
                }
            } else {
                confirm();
            }
            TutoringUtils.updateLists(parent);
            stage.close();

        }

        void confirmReplace() {
            Alert alert = new Alert(AlertType.CONFIRMATION,
                    "Vous allez remplacer " + toRemove.getName() + " par " + selectedStudent.getName()
                            + ". Êtes-vous certain(e)?",
                    ButtonType.YES, ButtonType.CANCEL);
            alert.headerTextProperty().set("");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.YES) {
                selectedStudent.addGrade(parent.dpt.tutoring.getResource(), Student.getDefaultGrade());
                parent.dpt.tutoring.addStudent(selectedStudent);
                parent.dpt.tutoring.removeStudent(toRemove);
                if (toReaffect != null) {
                    parent.dpt.tutoring.addForcedAssignments(toReaffect, (Tutor) selectedStudent);
                }
                parent.dpt.tutoring.getWaitingList().remove(toReaffect);
                parent.dpt.tutoring.getWaitingList().remove(selectedStudent);
                parent.dpt.tutoring.affectations.add(new Couple(toReaffect, (Tutor)selectedStudent));
            }
        }

        void confirm() {
            Alert alert = new Alert(AlertType.CONFIRMATION,
                    "Vous allez ajouter " + selectedStudent.getName() + ". Êtes-vous certain(e)?",
                    ButtonType.YES, ButtonType.CANCEL);
            alert.headerTextProperty().set("");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.YES) {
                selectedStudent.addGrade(parent.dpt.tutoring.getResource(), Student.getDefaultGrade());
                parent.dpt.tutoring.addStudent(selectedStudent);
            }
        }

        boolean checkInputs() {
            boolean isOk = true;
            StringBuilder errorMessage = new StringBuilder();
            Alert alert = new Alert(AlertType.ERROR);
            alert.headerTextProperty().set("Saisie incorrecte");

            if (nom.getText().isEmpty()) {
                errorMessage.append("Le nom est vide.\n");
                isOk = false;
            }

            if (prenom.getText().isEmpty()) {
                errorMessage.append("Le prénom est vide.\n");
                isOk = false;
            }

            try {
                Double.parseDouble(moyenne.getText().replace(",", "."));
            } catch (NumberFormatException e) {
                errorMessage.append("La moyenne doit être une valeur numérique.\n");
                isOk = false;
            }

            try {
                Integer.parseInt(absences.getText());
            } catch (NumberFormatException e) {
                errorMessage.append("Les absences doivent être un nombre entier.\n");
                isOk = false;
            }

            if (!isOk) {
                alert.setContentText(errorMessage.toString());
                alert.showAndWait();
            }
            return isOk;
        }

        Student newStudent() {
            Student toAdd;
    
            String name = nom.getText() + " " + prenom.getText();
            double avg = Double.parseDouble(moyenne.getText());
            int abs = Integer.parseInt(absences.getText());
            Motivation motiv = motivation.getValue();
            int level = niveau.getValue();
            
            if (level == 1) {
                toAdd = new Tutored(name, abs, motiv.getAbbr());
            } else {
                toAdd = new Tutor(name, level, abs, motiv.getAbbr());
            }
            toAdd.addGrade(parent.dpt.tutoring.getResource(), avg);
            return toAdd;
        }
    }

    private class CheckBoxListener implements ChangeListener<Boolean> {

        public void changed(ObservableValue<? extends Boolean> obs, Boolean o, Boolean n) {
            updateSearchList(searchListTf.getText());
        }
    }

 private class SearchStudentListener implements ChangeListener<String> {
        public void changed(ObservableValue<? extends String> arg0, String arg1, String newV) {
            updateSearchList(newV);
        }

    }

    private class SearchListListener implements ListChangeListener<Student> {

        public void onChanged(Change<? extends Student> c) {
            if (c.getList().size() > 0) {
                selectedStudent = c.getList().get(0);
                ajouterList.setDisable(false);
            } else {
                ajouterList.setDisable(true);
            }
        }

    }

    public StudentForm(Interface parent, boolean interdite) {
        super(parent);
        this.interdite = interdite;

        List<Couple> containedIn = Couples.containedIn(
            parent.affectationInterdite ? 
                parent.dpt.tutoring.getForbiddenCouples() : 
                parent.dpt.tutoring.getForcedCouples(),
            parent.selectedStudent);

        if (parent.selectedStudent.isTutored()) {
            students = new ArrayList<>(parent.dpt.tutoring.getTutors());
            tutoredCb.setSelected(false);

        } else {
            students = new ArrayList<>(parent.dpt.tutoring.getTutored());
            tutorCb.setSelected(false);
        }

        students.removeAll(Couples.getTutors(containedIn));
        stage.setTitle((interdite ? "Interdire" : "Forcer") + " une affectation");

        start(stage);
        root.getTabs().remove(0);

        tutoredCb.setSelected(!parent.selectedStudent.isTutored());
        tutorCb.setSelected(parent.selectedStudent.isTutored());
        tutorCb.setDisable(true);
        tutoredCb.setDisable(true);
    }

    public StudentForm(Interface parent, Tutor toRemove) {
        super(parent);
        replacing = true;
        this.toRemove = toRemove;
        students = new ArrayList<>();
        students.addAll(parent.dpt.getStudents());

        students.removeAll(parent.dpt.tutoring.getTutors());
        listCb.setDisable(true);
        List<Couple> containedIn = Couples.containedIn(parent.dpt.tutoring.affectations, toRemove);
        if (containedIn.size()>0){
            toReaffect = containedIn.get(0).getTutored();
            students.removeAll(Couples
                    .getTutors(Couples.containedIn(parent.dpt.tutoring.getForbiddenCouples(), toReaffect)));
        }
        start(stage);
        tutorCb.setDisable(true);
        tutoredCb.setDisable(true);
        tutoredCb.setSelected(false);
        stage.setTitle("Remplacer " + toRemove.getName());
        root.getTabs().remove(0);


    }

    public StudentForm(Interface parent) {
        super(parent);

        students = new ArrayList<>(parent.dpt.getStudents());
        students.removeAll(parent.dpt.tutoring.getTutored());
        students.removeAll(parent.dpt.tutoring.getTutors());
        stage.setTitle("Ajouter un étudiant");

        start(stage);
    }

    @Override
    public void start(Stage stage) {
        root = new TabPane();

        root.getTabs().addAll(fresh(), fromList());
        root.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);

        scene = new Scene(root, 400, 400);
        stage.setScene(scene);
        stage.show();
        DisplayUtils.setTheme(scene);
    }

    Tab fresh() {

        nom = new TextField();

        prenom = new TextField();

        ObservableList<Integer> intvalues = FXCollections.observableList(List.of(1, 2, 3));
        niveau = new Spinner<>(intvalues);

        moyenne = new TextField();
        moyenne.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                    String newValue) {
                if (newValue.contains(",")) {
                    newValue = newValue.replaceAll(",", ".");
                }
                if (!newValue.matches("\\d+(.\\d+)?")) {
                    moyenne.setText(newValue.replaceAll("[^\\d+(.\\d+)]", ""));
                }
            }
        });
        absences = new TextField();

        ObservableList<Motivation> values = FXCollections.observableList(List.of(Motivation.values()));
        motivation = new Spinner<>(values);

        ajouterFresh = new Button("Ajouter");
        ajouterFresh.setAlignment(Pos.CENTER);
        ajouterFresh.setPrefWidth(125);
        ajouterFresh.setPrefHeight(50);
        ajouterFresh.setOnAction(new AddingChecker());

        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        VBox inputs = new VBox(
            WidgetUtils.labelInput("Nom", nom),
            WidgetUtils.labelInput("Prénom", prenom),
            WidgetUtils.labelInput("Niveau", niveau),
            WidgetUtils.labelInput("Moyenne", moyenne),
            WidgetUtils.labelInput("Absences", absences),
            WidgetUtils.labelInput("Motivation", motivation)
        );
        inputs.setSpacing(10);
        inputs.setAlignment(Pos.CENTER);

        VBox root = new VBox(
            inputs,
            ajouterFresh);
        root.setSpacing(20);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);

        return new Tab("Nouveau", root);
    }

    Tab fromList() {
        tutoredCb.setSelected(true);
        tutorCb.setSelected(true);

        tutoredCb.selectedProperty().addListener(new CheckBoxListener());
        tutorCb.selectedProperty().addListener(new CheckBoxListener());

        searchList.getSelectionModel().getSelectedItems().addListener(new SearchListListener());
        populateList(students);
        searchListTf.setPromptText("Votre recherche");
        searchListTf.textProperty().addListener(new SearchStudentListener());

        ajouterList = new Button("Ajouter");
        ajouterList.setDisable(true);
        ajouterList.setOnAction(new AddingChecker());
        VBox.setMargin(ajouterList, new Insets(10, 0, 0, 0));
        VBox.setVgrow(searchListTf, Priority.ALWAYS);
        VBox root = new VBox(listCb, searchListTf, searchList, ajouterList);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);

        return new Tab("Depuis la liste", root);
    }

    private void populateList(List<? extends Student> students) {
        searchList.getItems().clear();
        searchList.getItems().addAll(students);
    }

    private void updateSearchList(String str) {
        List<Student> found = new ArrayList<>();
        ajouterList.setDisable(true);
        for (Student student : students) {
            if (student.getName().toUpperCase().contains(str.toUpperCase())) {
                if ((tutorCb.isSelected() && !student.isTutored())
                        || (tutoredCb.isSelected() && student.isTutored())) {
                    found.add(student);
                }
            }
        }
        populateList(found);
    }

   
}
