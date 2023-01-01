package ihm;

import java.util.Optional;
import java.util.Random;

import graphs.Couple;
import graphs.Tutoring;
import ihm.events.AffectationGlimpseManager;
import ihm.events.Events;
import ihm.events.SelectedStudentListener;
import ihm.events.SliderListener;
import ihm.events.SortListHandler;
import ihm.events.TutoringSelectorListener;
import ihm.popup.Login;
import ihm.utils.CoupleCellFactory;
import ihm.utils.DisplayUtils;
import ihm.utils.RightClickMenu;
import ihm.utils.Shortcuts;
import ihm.utils.StudentCellFactory;
import ihm.utils.WidgetUtils;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.Slider;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import oop.Coefficient;
import oop.Resource;
import oop.Student;
import oop.Teacher;

public class Interface extends Application {
    // Glob
    public BorderPane root;
    Stage stage;
    public Scene scene;
    public IHMDepartment dpt = new IHMDepartment();

    // list filter
    public boolean filterTutored = true;
    // Scrollbar synchronize
    public ScrollBar scrollBarOne;
    public ScrollBar scrollBarTwo;
    public ScrollBar scrollBarThree;

    MenuBar menuBar;

    // Waiting for tutoring
    VBox etudiantsControls;
    VBox triControls;
    Button btAffect;
    MenuItem affecter;
    MenuItem ajouter;
    MenuItem supprimer;
    public CheckBox polyTutoring;
    Button coefResetBtn;
    Button coefShuffleBtn;
    Button tutoringAffectationGlimpseBt = new Button("👁");

    // Tutoring infos;
    public Label tutoringTeacherLb = new Label();
    public Label tutoringAffectedLb = new Label();
    public Label tutoringForcedLb = new Label();
    public Label tutoringForbiddenLb = new Label();
    public Label tutoringAwaitingLb = new Label();
    public Label tutoringTutorNbLb = new Label();
    public Label tutoringTutoredNbLb = new Label();

    // Toolbar
    public HBox affectationContainer = new HBox();
    HBox coefficientContainer = new HBox();
    VBox tutoringContainer = new VBox();

    // Login controls
    public Label sessionBt = new Label();

    public MenuItem login = new MenuItem("Se connecter");
    public MenuItem logout = new MenuItem("Se déconnecter");
    Circle sessionPhoto = new Circle(50);

    // RightClickMenu
    ContextMenu rightClickMenu;

    public ComboBox<Resource> cbMatieres = new ComboBox<>();
    public ComboBox<String> cbSession = new ComboBox<>();

    public Student selectedStudent;
    public boolean dragging;
    public boolean draggingTarget = false;

    //
    final double TOOLBAR_HEIGHT = 55;
    final String ASC = "Tri croissant";
    final String DESC = "Tri décroissant";

    int slMin = 0;
    int slMax = 5;

    // Undo redo coefs
    public Button redoCoef;
    public Button undoCoef;

    Slider slAvg = new Slider(slMin, slMax, 1);
    Slider slAbs = new Slider(slMin, slMax, 1);
    Slider slLvl = new Slider(slMin, slMax, 1);

    public ListView<Student> tutorsView;
    public ListView<Student> tutoredView;
    public ListView<Couple> couplesView;

    public boolean affectationInterdite;

    // Padding
    public final Insets PAD_MIN = new Insets(10);

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        initScene();
        initData();
        initControls();

        stage.setTitle("Tutorat du département " + this.dpt.getName());
        stage.setScene(scene);
        stage.show();
        
        initDisplays();
    }

    /**
     * Creation de la scene : BorderPane
     */
    void initScene() {
        root = new BorderPane();
        scene = new Scene(root, 850, 800);

        root.setTop(initTop());
        root.setCenter(initCenter());
        root.setBottom(initBottom());
        root.setLeft(initLeft());

        stage.setMinHeight(592);
        stage.setMinWidth(540);
    }

    // Init Border Pane
    VBox initCenter() {

        HBox tutoredBox = new HBox(new Label("Tutorés ("), tutoringTutoredNbLb, new Label(")"));
        tutoredBox.setAlignment(Pos.CENTER);
        Region spacer = new Region();
        HBox tutorBox = new HBox(new Label("Tuteurs ("), tutoringTutorNbLb, new Label(")"));
        tutorBox.setAlignment(Pos.CENTER);

        HBox titres = new HBox(
                tutoredBox,
                spacer,
                tutorBox);
        titres.setAlignment(Pos.CENTER);
        HBox listViews = initListViews();

        
        tutorBox.prefWidthProperty().bind(tutorsView.widthProperty());
        tutoredBox.prefWidthProperty().bind(tutoredView.widthProperty());
        spacer.prefWidthProperty().bind(couplesView.widthProperty());

        VBox retour = new VBox(titres, listViews);
        retour.setSpacing(10);
        VBox.setVgrow(retour, Priority.ALWAYS);
        retour.setPadding(PAD_MIN);

        listViews.prefHeightProperty().bind(Bindings.multiply(retour.heightProperty(), .85));

        return retour;

    }

    VBox initTop() {
        VBox retour = new VBox(
                initMenuBar(),
                initHeader(),
                horizontalToolbar());

        retour.setBorder(
                new Border(
                        new BorderStroke(
                                null, null, Color.DARKGRAY, null,
                                null, null, BorderStrokeStyle.SOLID, null,
                                null, null, Insets.EMPTY)));

        return retour;
    }

    HBox initBottom() {

        ToggleGroup tGroup = new ToggleGroup();
        ToggleButton candyMode = new ToggleButton("🍬");
        ToggleButton nightMode = new ToggleButton("🌙");
        ToggleButton dayMode = new ToggleButton("☀");

        candyMode.setTooltip(new Tooltip("Passer en thème bonbon"));
        nightMode.setTooltip(new Tooltip("Passer en thème sombre"));
        dayMode.setTooltip(new Tooltip("Passer en thème jour"));

        candyMode.setToggleGroup(tGroup);
        nightMode.setToggleGroup(tGroup);
        dayMode.setToggleGroup(tGroup);
        tGroup.selectToggle(dayMode);

        candyMode.setOnAction(e -> DisplayUtils.setTheme(scene, "pink"));
        nightMode.setOnAction(e -> DisplayUtils.setTheme(scene, "black"));
        dayMode.setOnAction(e -> DisplayUtils.setTheme(scene, "#ececec"));

        HBox retour = new HBox(
                sessionBt,
                WidgetUtils.spacer(),
                nightMode,
                dayMode,
                candyMode);

        retour.setOnMouseClicked(e -> Events.AuthentificationHandler(this));
        retour.setPadding(PAD_MIN);
        retour.getStyleClass().addAll(cbMatieres.getStyleClass());

        return retour;
    }

    VBox initLeft() {
        VBox retour = new VBox(
                listStudentControls(),
                WidgetUtils.filler(),
                listSortingControls());

        retour.setPadding(PAD_MIN);
        retour.prefWidthProperty().bind(Bindings.divide(stage.widthProperty(), 5));
        retour.setMaxWidth(300);

        return retour;
    }

    // Init Center
    HBox initListViews() {
        tutorsView = new ListView<>();
        tutoredView = new ListView<>();
        couplesView = new ListView<>();

        tutorsView.getSelectionModel().getSelectedItems().addListener(new SelectedStudentListener(this));
        tutoredView.getSelectionModel().getSelectedItems().addListener(new SelectedStudentListener(this));

        // right click menu
        rightClickMenu = RightClickMenu.get(this);
        tutoredView.setContextMenu(rightClickMenu);
        tutorsView.setContextMenu(rightClickMenu);

        // Cell factories
        tutoredView.setCellFactory(new StudentCellFactory(this));
        tutorsView.setCellFactory(new StudentCellFactory(this));
        couplesView.setCellFactory(new CoupleCellFactory(this));

        HBox retour = new HBox(
                tutoredView,
                couplesView,
                tutorsView);

        retour.setSpacing(10);
        // Tout le monde fait la hauteur max de vbox
        VBox.setVgrow(tutorsView, Priority.ALWAYS);
        VBox.setVgrow(tutoredView, Priority.ALWAYS);
        VBox.setVgrow(couplesView, Priority.ALWAYS);

        couplesView.prefWidthProperty().bind(Bindings.divide(retour.widthProperty(), 6));
        couplesView.getStyleClass().clear();

        retour.setAlignment(Pos.CENTER);

        return retour;
    }

    // Init top
    // Menu bar
    MenuBar initMenuBar() {
        menuBar = new MenuBar(
                initMenuFichier(),
                initMenuSelection(),
                initMenuListes(),
                initMenuAffectation(),
                initMenuEdition());

        return menuBar;
    }

    Menu initMenuFichier() {
        MenuItem exporter = new MenuItem("Exporter");
        exporter.setDisable(true);
        MenuItem importer = new MenuItem("Importer");
        importer.setDisable(true);
        MenuItem save = new MenuItem("Sauvegarder");
        save.setDisable(true);
        MenuItem quit = new MenuItem("Quitter");

        Menu retour = new Menu(
                "Fichier",
                null,
                exporter,
                importer,
                new SeparatorMenuItem(),
                login,
                logout,
                new SeparatorMenuItem(),
                save,
                quit);

        // exporter.setOnAction(e->exporter());
        // importer.setOnAction(e->importer());
        login.setOnAction(e -> Events.AuthentificationHandler(this));
        logout.setOnAction(e -> Events.AuthentificationHandler(this));
        // save.setOnAction(e->save());
        quit.setOnAction(e -> close());

        return retour;
    }

    Menu initMenuSelection() {
        ajouter = new MenuItem("Ajoute un étudiant");
        supprimer = new MenuItem("Supprimer l'étudiant");
        MenuItem forcer = new MenuItem("Forcer l'affectation");
        MenuItem interdire = new MenuItem("Interdire l'affection");

        Menu retour = new Menu(
                "Édition",
                null,
                ajouter,
                supprimer,
                new SeparatorMenuItem(),
                forcer,
                interdire);

        ajouter.setOnAction(e -> Events.AddStudentHandler(this));
        supprimer.setOnAction(e -> Events.RemoveStudentHandler(this));
        forcer.setOnAction(e -> Events.AddForcedAffectationHandler(this, false));
        interdire.setOnAction(e -> Events.AddForcedAffectationHandler(this, true));

        return retour;
    }

    Menu initMenuEdition() {
        MenuItem changeMat = new MenuItem("Changer de matière");
        changeMat.setDisable(true);
        MenuItem editProfil = new MenuItem("Editer le profil");
        editProfil.setDisable(true);
        MenuItem changeProf = new MenuItem("Changer le professeur référent");
        changeProf.setDisable(true);

        Menu retour = new Menu(
                "Edition",
                null,
                changeMat,
                new SeparatorMenuItem(),
                editProfil,
                new SeparatorMenuItem(),
                changeProf);

        // changeMat.setOnAction(e->changeMat());
        // editProfil.setOnAction(e->editprofil());
        // changeProf.setOnAction(e->changeProf());

        return retour;
    }

    Menu initMenuListes() {
        // ajouter = new MenuItem("Ajouter un étudiant");
        // supprimer = new MenuItem("Supprimer l'étudiant");
        MenuItem separator = new MenuItem("Trier par :");
        separator.setDisable(true);
        MenuItem triNom = new MenuItem("\tnom");
        MenuItem triPrenom = new MenuItem("\tprénom");
        MenuItem triNotes = new MenuItem("\tmoyennes");
        MenuItem triAbs = new MenuItem("\tabsences");
        MenuItem triMot = new MenuItem("\tmotivation");

        Menu retour = new Menu(
                "Listes",
                null,
                // ajouter,
                // supprimer,
                // new SeparatorMenuItem(),
                separator,
                triNom,
                triPrenom,
                triNotes,
                triAbs,
                triMot);

        // ajouter.setOnAction(e -> Events.AddStudentHandler(this));
        // supprimer.setOnAction(e -> Events.RemoveStudentHandler(this));
        triNom.setOnAction(new SortListHandler(this, "nom"));
        triPrenom.setOnAction(new SortListHandler(this, "pre"));
        triNotes.setOnAction(new SortListHandler(this, "avg"));
        triAbs.setOnAction(new SortListHandler(this, "abs"));
        triMot.setOnAction(new SortListHandler(this, "mot"));

        return retour;
    }

    Menu initMenuAffectation() {
        affecter = new MenuItem("Lancer l'affectation");
        MenuItem coefRst = new MenuItem("Réinitialiser les coefficients");
        MenuItem coefAvg = new MenuItem("Maximiser la moyenne");
        MenuItem coefAbs = new MenuItem("Maximiser les absences");
        MenuItem coefLvl = new MenuItem("Maximiser la motivation");
        MenuItem coefRng = new MenuItem("Coefficients aléatoires");

        Menu retour = new Menu(
                "Affectation",
                null,
                affecter,
                new SeparatorMenuItem(),
                coefRst,
                coefRng,
                new SeparatorMenuItem(),
                coefAvg,
                coefAbs,
                coefLvl);

        affecter.setOnAction(e -> Events.AffectationHandler(this));
        affecter.disableProperty().bind(Bindings.isNull(new SimpleObjectProperty<>(dpt.tutoring)));
        coefRst.setOnAction(e -> setCoefs());
        coefAvg.setOnAction(e -> setCoefs(2, .5, .5));
        coefAbs.setOnAction(e -> setCoefs(.5, 2, .5));
        coefLvl.setOnAction(e -> setCoefs(.5, .5, 2));
        coefRng.setOnAction(e -> setCoefs(666));

        return retour;
    }

    // header
    HBox initHeader() {

        ImageView logoImgView = new ImageView(new Image("file:res/img/logo.png"));
        logoImgView.setFitHeight(75);
        logoImgView.setPreserveRatio(true);
        HBox logoWrapper = new HBox(logoImgView);
        logoWrapper.setAlignment(Pos.CENTER);

        HBox.setHgrow(logoWrapper, Priority.ALWAYS);

        HBox session = new HBox(
                cbSession,
                sessionPhoto);
        
        

        session.setAlignment(Pos.CENTER_RIGHT);
        session.setSpacing(10);

        HBox matieresWRapper = new HBox(cbMatieres);
        matieresWRapper.setAlignment(Pos.CENTER_LEFT);
        cbMatieres.setPromptText("Choisir une matière");
        cbMatieres.setMaxWidth(150);
        cbMatieres.getSelectionModel().selectedItemProperty().addListener(new TutoringSelectorListener(this));
        for (Resource resource : dpt.getTutorings().keySet()) {
            cbMatieres.getItems().add(resource);
        }

        HBox retour = new HBox(
                matieresWRapper,
                // WidgetUtils.spacer(),
                logoWrapper,
                // WidgetUtils.spacer(),
                session);

        matieresWRapper.prefWidthProperty().bind(Bindings.divide(retour.widthProperty(), 3));
        session.prefWidthProperty().bind(Bindings.divide(retour.widthProperty(), 3));

        retour.setPadding(PAD_MIN);
        retour.setAlignment(Pos.CENTER);
        retour.setBorder(
                new Border(
                        new BorderStroke(
                                null, null, Color.DARKGRAY, null,
                                null, null, BorderStrokeStyle.SOLID, null,
                                null, null, Insets.EMPTY)));

        return retour;
    }

    // toolbar
    HBox horizontalToolbar() {
        HBox retour = new HBox(
                coefficientWidgets(),
                WidgetUtils.spacer(),
                affectationWidgets(),
                WidgetUtils.spacer(),
                tutoringWidgets());

        for (Node n : retour.getChildren()) {
            if (n instanceof TitledPane) {
                Pane p = ((Pane) ((TitledPane) n).getContent());
                p.setMinHeight(TOOLBAR_HEIGHT);
                p.setPadding(PAD_MIN);
            }
        }

        retour.setPadding(PAD_MIN);

        return retour;
    }

    TitledPane coefficientWidgets() {
        initSliders();

        Label slAvgLb = new Label("Moyenne");
        Label slAbsLb = new Label("Absences");
        Label slLblLb = new Label("Motivation");

        coefResetBtn = new Button("↺");
        coefResetBtn.setTooltip(new Tooltip("Réinitialiser les coefficients"));
        coefResetBtn.setOnAction(e -> setCoefs());

        coefShuffleBtn = new Button("🔀");
        coefShuffleBtn.setTooltip(new Tooltip("Randomiser les coefficients"));
        coefShuffleBtn.setOnAction(e -> setCoefs(1));

        undoCoef = new Button("Undo");
        undoCoef.setOnAction(e -> SliderListener.restoreCoefs(this, dpt.tutoring.getTeacher()));
        undoCoef.setDisable(true);

        redoCoef = new Button("Redo");
        redoCoef.setOnAction(e -> SliderListener.reEditCoefs(this, dpt.tutoring.getTeacher()));
        redoCoef.setDisable(true);

        coefficientContainer.getChildren().addAll(
                WidgetUtils.nodeSpaceNode(slAvgLb, slAvg),
                WidgetUtils.nodeSpaceNode(slAbsLb, slAbs),
                WidgetUtils.nodeSpaceNode(slLblLb, slLvl),
                WidgetUtils.nodeSpaceNode(coefResetBtn, coefShuffleBtn));
        // , undoCoef, redoCoef);

        coefficientContainer.setAlignment(Pos.CENTER);
        coefficientContainer.setSpacing(10);
        return new TitledPane("Coefficients", coefficientContainer);
    }

    void initSliders() {
        for (Slider slider : new Slider[] { slAvg, slAbs, slLvl }) {
            slider.setMaxWidth(75);
            slider.setMajorTickUnit(1);
            slider.setShowTickLabels(true);
        }
        slAvg.valueProperty().addListener(new SliderListener(this, slAvg, Coefficient.GRADES));
        slAbs.valueProperty().addListener(new SliderListener(this, slAbs, Coefficient.ABSENCES));
        slLvl.valueProperty().addListener(new SliderListener(this, slLvl, Coefficient.LEVEL));
    }

    TitledPane affectationWidgets() {
        btAffect = new Button("Affecter !");
        btAffect.setTooltip(new Tooltip("Lancer l'affectation"));
        btAffect.setOnAction(e -> Events.AffectationHandler(this));
        btAffect.setDisable(true);

        affectationContainer.setPadding(PAD_MIN);
        affectationContainer.getStyleClass().clear();
        affectationContainer.getChildren().addAll(btAffect);

        affectationContainer.setAlignment(Pos.CENTER);

        return new TitledPane("Affectation", affectationContainer);
    }

    TitledPane tutoringWidgets() {
        polyTutoring = new CheckBox("Polytutorat");
        polyTutoring.setTooltip(new Tooltip("Les tuteurs s'occupent de plusieurs tutorés."));
        polyTutoring.selectedProperty().addListener((a, o, n) -> dpt.tutoring.setPolyTutor(n));

        tutoringAffectationGlimpseBt.setTooltip(new Tooltip("Consulter les affectations"));
        tutoringAffectationGlimpseBt.setOnAction(e -> new AffectationGlimpseManager(this));

        VBox affectations = new VBox(
                WidgetUtils.nodeSpaceNode("Affectations : ", tutoringAffectationGlimpseBt),
                WidgetUtils.nodeSpaceNode("\tAffectés", tutoringAffectedLb),
                WidgetUtils.nodeSpaceNode(("\tEn attente"), tutoringAwaitingLb),
                WidgetUtils.nodeSpaceNode("\tForcées", tutoringForcedLb),
                WidgetUtils.nodeSpaceNode("\tInterdites", tutoringForbiddenLb));

        tutoringContainer.getChildren().addAll(
                new HBox(new Label("Responsable : "), tutoringTeacherLb),
                polyTutoring,
                affectations);

        tutoringContainer.setSpacing(10);
        TitledPane retour = new TitledPane("Informations sur le tutorat", tutoringContainer);
        retour.setExpanded(false);
        return retour;
    }

    // Init left
    TitledPane listStudentControls() {

        HBox add = WidgetUtils.labelButton(
            "Ajouter (N)", 
            "+", 
            e -> Events.AddStudentHandler(this), 
            "Ajouter un étudiant");

        HBox del = WidgetUtils.labelButton(
            "Supprimer (D)", 
            "‒", 
            e -> Events.RemoveStudentHandler(this),
            "Supprimer un étudiant");

        HBox union = WidgetUtils.labelButton(
            "Forcer (F)", 
            "🔗", 
            e -> Events.AddForcedAffectationHandler(this, false),
            "Forcer une affectation");

        HBox disUnion = WidgetUtils.labelButton(
            "Interdire (I)", 
            "⦸", 
            e -> Events.AddForcedAffectationHandler(this, true),
            "Interdire une affectation");

        HBox consult = WidgetUtils.labelButton(
            "Consulter (V)", 
            "👁", 
            e -> new AffectationGlimpseManager(this) , 
            "Consulter les affectations automatiques, forcées et interdites");

        
        etudiantsControls = new VBox(
            add, 
            del, 
            union, 
            disUnion,
            consult);

        etudiantsControls.setPadding(PAD_MIN);
        TitledPane retour = new TitledPane("Etudiants", etudiantsControls);
        return retour;
    }

    TitledPane listSortingControls() {

        ToggleGroup tg = new ToggleGroup();
        tg.selectedToggleProperty()
                .addListener((obs, old, newv) -> filterTutored = ((RadioButton) newv).getText().equals("Tutorés"));
        RadioButton tutorFilter = new RadioButton("Tuteurs");
        tutorFilter.setToggleGroup(tg);
        RadioButton tutoredFilter = new RadioButton("Tutorés");
        tutoredFilter.setToggleGroup(tg);
        tutoredFilter.setSelected(true);

        HBox filterGroup = new HBox(tutoredFilter, WidgetUtils.filler(), tutorFilter);

        HBox nom = WidgetUtils.labelButton(
            "Nom", 
            "▼", "▲", 
            new SortListHandler(this, "nom"), 
            ASC, DESC);

        HBox prenom = WidgetUtils.labelButton(
            "Prénom", 
            "▼", "▲", 
            new SortListHandler(this, "pre"), 
            ASC, DESC);

        HBox avg = WidgetUtils.labelButton(
            "Moyenne", 
            "▼", "▲", 
            new SortListHandler(this, "avg"), 
            ASC, DESC);

        HBox abs = WidgetUtils.labelButton(
            "Absences", 
            "▼", "▲", 
            new SortListHandler(this, "abs"), 
            ASC, DESC);

        HBox motiv = WidgetUtils.labelButton(
            "Motivation", 
            "▼", "▲", 
            new SortListHandler(this, "mot"), 
            ASC, DESC);

        triControls = new VBox(
            nom, 
            prenom, 
            avg, 
            abs, 
            motiv);
        triControls.setSpacing(5);

        VBox filter = new VBox(
            filterGroup, 
            WidgetUtils.filler(), 
            triControls);

        filter.setPadding(PAD_MIN);
        TitledPane retour = new TitledPane("Trier par", filter);        
        retour.setExpanded(false);
        return retour;
    }


    // Data initialize
    void initData() {
        Login.updateSession(this);
    }

    void initControls(){
        Shortcuts.init(this);
        disableTutoringControls(true);
    }
   
    public void disableTutoringControls(boolean bool) {
        for (Node box : etudiantsControls.getChildren()) {
            ((Button) ((HBox) box).getChildren().get(2)).setDisable(bool);
        }
        for (Node box : triControls.getChildren()) {
            for (Node bt : ((VBox) ((HBox) box).getChildren().get(2)).getChildren()) {

                ((Button) bt).setDisable(bool);
            }
        }
        for (MenuItem item : rightClickMenu.getItems()) {
            item.setDisable(bool);
        }
        btAffect.setDisable(bool);
        // affecter.setDisable(bool);
        ajouter.setDisable(bool);
        supprimer.setDisable(bool);
        polyTutoring.setDisable(bool);
        coefResetBtn.setDisable(bool);
        coefShuffleBtn.setDisable(bool);
        slAbs.setDisable(bool);
        slAvg.setDisable(bool);
        slLvl.setDisable(bool);
    }

    void initDisplays(){        
        DisplayUtils.setScrollBars(this);
        DisplayUtils.setTheme(scene);
    }


    public void close() {
        Alert alert = new Alert(AlertType.CONFIRMATION,
                "Quitter l'application?",
                ButtonType.YES, ButtonType.CANCEL);
        alert.headerTextProperty().set("");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.YES) {
            stage.close();
        }
    }

    void setCoefs(double avg, double abs, double lvl) {
        slAvg.setValue(avg);
        slAbs.setValue(abs);
        slLvl.setValue(lvl);
        if (!(avg == 1 && avg == abs && avg == lvl)) {
            SliderListener.saveCoefs(dpt.tutoring.getTeacher());
        }
    }

    void setCoefs() {
        setCoefs(1, 1, 1);
    }

    public void setCoefs(Teacher teacher) {
        setCoefs(teacher.getAverageWeighting(), teacher.getAbsenceWeighting(), teacher.getLevelWeighting());
    }

    void setCoefs(int n) {
        Random rng = new Random();
        setCoefs(rng.nextDouble(Tutoring.getMaxWeighting()), rng.nextDouble(Tutoring.getMaxWeighting()),
                rng.nextDouble(Tutoring.getMaxWeighting()));
    }

    public void setProfilPhoto(String path) {
        sessionPhoto.setStroke(Color.DARKGREY);
        sessionPhoto.setFill(new ImagePattern(new Image(path)));
    }

    public void setProfilPhoto() {
        setProfilPhoto("file:res/img/notlogged.jpg");
    }



    public static void main(String[] args) {
        Application.launch(args);
    }

}
