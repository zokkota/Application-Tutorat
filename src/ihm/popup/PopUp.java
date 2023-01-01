package ihm.popup;

import ihm.Interface;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public abstract class PopUp extends Application {
    Stage stage;
    public Scene scene;
    Interface parent;
    int sizeX;
    int sizeY;

    public PopUp(Interface parent) {        
        this.stage = new Stage();
        this.parent = parent;
        stage.initModality(Modality.APPLICATION_MODAL);
        
        stage.setResizable(false);
    }
    
    abstract public void start(Stage stage);

}
