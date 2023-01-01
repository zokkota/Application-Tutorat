package ihm.placard;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

public class Principale extends Application{
    //PADDINGS
    final Insets PADDING_5 = new Insets(5);

    //Styles
    final static String FX = "-fx-";
    final String BG = FX+"background";
    final String BGC = BG+"-color";
    String styleShadowBorder = FX+"effect: dropshadow(gaussian, rgba(0, 0, 0, 0.3), 10, 0.5, 0.0, 0.0)";

    //Palette de couleurs
    final static String CLR_DARK = "#091527";
    final static String CLR_MEDIUM = "#353f54";
    final static String CLR_LIGHT = "#e1f1ff";
    final static String CLR_HIGHLIGHT = "#d9a21b";
    //
    Paint PAINT_GC = Paint.valueOf("lightgrey");

    Image profilPhoto = new Image ("file:res/img/jc2.jpeg");


    public static void main(String[] arg0){
        Application.launch(arg0);
        
    }
    /**Listes d'étudiants */
    ListView<String> tutors = new ListView<>();
    ListView<String> tutored = new ListView<>();
    /**Sliders des coefs */
    Slider slAvg = new Slider();
    Slider slAbs = new Slider();
    Slider slLvl = new Slider();

    @Override
    public void start(Stage stage) throws Exception {
        BorderPane root = new BorderPane();

        HBox header = (HBox) initHeader();
        root.setTop(header);        
        VBox main = (VBox) initMain();
        root.setCenter(main);
        HBox footer = (HBox) initFooter();
        root.setBottom(footer);

        stage.setScene(new Scene(root, 800, 450));
        stage.show();
    }

    private Node initHeader(){
        HBox header = new HBox();
        header.setAlignment(Pos.CENTER_LEFT);
        header.setPadding(PADDING_5);
        header.setStyle(BGC+":"+CLR_DARK+";");

        ComboBox<String> cbMatieres = new ComboBox<>();


        HBox profil = new HBox();
        profil.setAlignment(Pos.CENTER_LEFT);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        
        Label profilLogin = new Label("Jean Carle <3");

        ComboBox<Label> cbProfil = new ComboBox<>();
        Label profilEdit = new Label("Paramètres");
        Label profilDisconnect = new Label("Se déconnecter ");
        cbProfil.getItems().addAll(profilEdit, profilDisconnect);
        // profilLogin.setTextFill(PAINT_GC);
        // ComboBox<String> cbProfil = new ComboBox<>();
        cbProfil.paddingProperty().set(new Insets(0));
        cbProfil.setMaxSize(25,25);
        cbProfil.setMinSize(25,25);

        
        ImageView profilImgView = new ImageView(profilPhoto);
        profilImgView.setFitHeight(75);
        profilImgView.setFitWidth(75);
        profilImgView.setPreserveRatio(true);

        profil.getChildren().addAll(cbProfil, profilLogin, profilImgView);

        
        header.getChildren().addAll(cbMatieres, spacer, profil);

        return header;
    }

    private Node initMain(){
        VBox main = new VBox();
        main.setStyle(BGC+":"+CLR_LIGHT+";");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        Region spacerL = new Region();
        HBox.setHgrow(spacerL, Priority.ALWAYS);
        Region spacerR = new Region();
        HBox.setHgrow(spacerR, Priority.ALWAYS);
        spacer.setMaxWidth(100);

        ToolBar tools = initToolBar();
        // 

        HBox sliders = new HBox(slAvg, slLvl, slAbs);
        sliders.setAlignment(Pos.CENTER_LEFT);
        for (Node n : sliders.getChildren()) {
            ((Slider)n).setMaxWidth(75);

            ((Slider)n).setMin(0);
            ((Slider)n).setMax(4);
            ((Slider)n).setValue(1);
            ((Slider)n).setValue(1);
            ((Slider)n).setMajorTickUnit(1);
            ((Slider)n).setShowTickLabels(true);
            ((Slider)n).valueProperty().addListener((obs, oldval, newVal) -> ((Slider)n).setValue( Math.ceil((double)newVal*2) / 2.0d));
            // ((Slider)n).setShowTickMarks(true);
        }


        HBox actions = new HBox(spacerL, sliders, tools, spacerR);
        actions.setPadding(PADDING_5);
        actions.getStyleClass().addAll(tools.getStyleClass());
        actions.setStyle(BGC+":"+CLR_MEDIUM+";");
        tools.getStyleClass().clear();


        HBox lists = new HBox(spacerL, tutors, spacer, tutored, spacerR);
        lists.setPadding(PADDING_5);

        main.getChildren().addAll(actions, lists);
        return main;
    }



    private Node initFooter(){
        HBox footer = new HBox();
        footer.setPadding(PADDING_5);
        footer.getChildren().add(new Label("Créé et entretenu avec une passion pour Jean Carle !"));
        return footer;
    }




    private ToolBar initToolBar(){
        ToolBar tb = new ToolBar();
        HBox.setHgrow(tb, Priority.ALWAYS);
        Button btReset = new Button("↺");
        Button btShuffle = new Button("🔀");
        Button btOrder = new Button("↓");
        Button btAffect = new Button("Affecter !");
        Button btRemoveStudent = new Button("‒");
        Button btAddStudent = new Button("+");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);



        tb.getItems().addAll(btReset, btShuffle, btOrder, btAffect, spacer, btAddStudent, btRemoveStudent);
        return tb;
    }

    
}
