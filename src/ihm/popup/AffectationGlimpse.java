package ihm.popup;

import java.util.Collection;

import graphs.Couple;
import ihm.Interface;
import ihm.utils.DisplayUtils;
import ihm.utils.TutoringUtils;
import javafx.beans.binding.Bindings;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AffectationGlimpse extends PopUp {
    Tab pairsTab;
    Tab forcedTab;
    Tab forbiddenTab;

    TabPane root;

    ListView<Couple> pairsView;
    ListView<Couple> forcedView;
    ListView<Couple> forbiddenView;

    public AffectationGlimpse(Interface parent) {
        super(parent);
        start(stage);
    }

    @Override
    public void start(Stage stage) {
        pairsTab = newTab("Calculées", parent.dpt.tutoring.affectations);
        forcedTab = newTab("Forcées", parent.dpt.tutoring.getForcedCouples());
        forbiddenTab = newTab("Interdites", parent.dpt.tutoring.getForbiddenCouples());
        root = new TabPane(pairsTab, forcedTab, forbiddenTab);

        root.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);

        stage.setTitle("Visualiser les affectations");
        scene = new Scene(root, 300, 300);
        stage.setScene(scene);
        stage.show();
        DisplayUtils.setTheme(scene);

    }

    Tab newTab(String titre, Collection<Couple> list) {
        Tab retour = new Tab(titre);

        ListView<Couple> view = new ListView<>();
        updateList(view, list);

        Button removeBt = new Button("Supprimer");
        removeBt.disableProperty().bind(Bindings.size(view.getSelectionModel().getSelectedItems()).isEqualTo(0));
        removeBt.setOnAction(e -> predefinedAction(retour, view, list));

        VBox root = new VBox(view, removeBt);
        root.setAlignment(Pos.CENTER);
        root.setSpacing(10);
        root.setPadding(parent.PAD_MIN);

        if (titre.equals("Calculées")) {
            pairsView = view;
        } else if (titre.equals("Forcées")) {
            forcedView = view;
        } else if (titre.equals("Interdites")) {
            forbiddenView = view;
        }

        retour.setContent(root);

        return retour;
    }

    void updateLists() {

        updateList(pairsView, parent.dpt.tutoring.affectations);
        updateList(forcedView, parent.dpt.tutoring.getForcedCouples());
        updateList(forbiddenView, parent.dpt.tutoring.getForbiddenCouples());
    }

    void updateList(ListView<Couple> view, Collection<Couple> liste) {
        view.getItems().clear();
        view.getItems().addAll(liste);
    }

    void predefinedAction(Tab tab, ListView<Couple> view, Collection<Couple> liste) {
        Alert alert = new Alert(AlertType.WARNING, "", ButtonType.YES, ButtonType.CANCEL);
        alert.headerTextProperty().set("");

        if (tab == pairsTab) {
            alert.setContentText("Vous vous apprêtez à interdire cette  affectation. Êtes-vous certain(e)?");
            if (alert.showAndWait().get() == ButtonType.YES) {
                parent.dpt.tutoring.addForbiddenAssignments(view.getSelectionModel().getSelectedItem());
            }

        } else if (tab == forcedTab) {
            alert.setContentText("Vous vous apprêtez à ne plus forcer cette affectation. Êtes-vous certain(e)?");
            if (alert.showAndWait().get() == ButtonType.YES) {
                parent.dpt.tutoring.getForcedCouples().remove(view.getSelectionModel().getSelectedItem());
            }
        } else if (tab == forbiddenTab) {
            alert.setContentText("Vous vous apprêtez à ne plus interdire cette affectation. Êtes-vous certain(e)?");
            if (alert.showAndWait().get() == ButtonType.YES) {
                parent.dpt.tutoring.getForbiddenCouples().remove(view.getSelectionModel().getSelectedItem());
            }

        }
        updateLists();
        TutoringUtils.updateLists(parent);

    }

}
