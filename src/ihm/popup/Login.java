package ihm.popup;

import ihm.Interface;
import ihm.utils.DisplayUtils;
import ihm.utils.WidgetUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Login extends PopUp {
    static String notLogged = "Non connecté";
    static String logged = "Connecté en tant que ";

    public static boolean loggedIn = false;
    public static String account;
    public static int sizeX = 300;
    public static int sizeY = 125;

    PasswordField mdpTf = new PasswordField();
    TextField idTf = new TextField();
    Label erreur = new Label();

    public Login(Interface parent) {
        super(parent);
        start(stage);
        DisplayUtils.setTheme(scene);
        
    }

    private class LoginChecker implements EventHandler<ActionEvent> {
        public void handle(ActionEvent e) {
            Login.loggedIn = idTf.getText().equals("root") && mdpTf.getText().equals("root");
            if (!Login.loggedIn) {
                erreur.setText("⚠ Login ou Mot de passe erroné !");
                erreur.setTextFill(Color.RED);
            } else {
                Login.account = idTf.getText();
                updateSession(parent);
                stage.close();
            }
        }
    }

    public void start(Stage stage) {
        stage.setOnCloseRequest(e -> updateSession(parent));
        VBox root = new VBox();
        stage.setTitle("Connexion");
        scene = new Scene(root, sizeX, sizeY);
        stage.setScene(scene);
        stage.setResizable(false);

        Label idLb = new Label("Identifiant");
        idTf.setPromptText("prenom.nom");
        // idTf.setFocusTraversable(false);
        HBox id = new HBox(idLb, WidgetUtils.spacer(), idTf);

        Label mdpLb = new Label("Mot de passe");

        mdpTf.setPromptText("motdepasse");
        // mdpTf.setFocusTraversable(false);
        HBox mdp = new HBox(mdpLb, WidgetUtils.spacer(), mdpTf);

        Button btn = new Button("Se connecter");
        btn.setOnAction(new LoginChecker());
        root.getChildren().addAll(erreur, id, mdp, WidgetUtils.filler(20),btn);

        root.requestFocus();
        root.setPadding(new Insets(10));
        root.setAlignment(Pos.CENTER);

        stage.show();
    }

    public static void updateSession(Interface iface) {
        if (Login.loggedIn) {
            iface.sessionBt.setText(logged + Login.account);
            iface.login.setDisable(true);
            iface.setProfilPhoto("file:res/img/root.jpg");
            iface.cbSession.setPromptText(Login.account);
        } else {
            iface.sessionBt.setText(notLogged);
            iface.login.setDisable(false);
            iface.setProfilPhoto();
            iface.cbSession.setPromptText(notLogged);
        }        
        iface.logout.setDisable(!iface.login.isDisable());
    }
}
