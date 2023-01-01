package ihm.utils;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class WidgetUtils {
    public static Region spacer(){
        return spacer(Double.MAX_VALUE);
    }
    
    public static Region spacer(double maxSize){
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        spacer.setMaxWidth(maxSize);
        return spacer;
    }
    
    public static Region filler(double size){
        Region filler = new Region();
        filler.setMinSize(size, size);
        return filler;
    }
    
    public static Region filler(){
        return filler(10);
    }

    public static HBox labelButton(String label, String button, String button2, EventHandler<ActionEvent> handler, String tooltip, String tooltip2){
        HBox box = new HBox();        
        box.setAlignment(Pos.CENTER);

        Label lb = new Label(label);        
        VBox btns = new VBox(toolButton(button2, handler, tooltip2, 2), toolButton(button, handler, tooltip, 2));


        box.getChildren().addAll(lb, WidgetUtils.spacer(), btns);
        return box;
    }

    public static HBox labelButton(String label, String button, EventHandler<ActionEvent> handler, String tooltip){
        HBox box = new HBox();        
        box.setAlignment(Pos.CENTER);

        Label lb = new Label(label);        

        box.getChildren().addAll(lb, WidgetUtils.spacer(), toolButton(button, handler, tooltip));
        return box;
    }

    static Button toolButton(String button, EventHandler<ActionEvent> handler,String tooltip){
        return toolButton( button,  handler, tooltip, 1);
    }

    static Button toolButton(String button, EventHandler<ActionEvent> handler,String tooltip, int n){
        Button bt = new Button(button);
        bt.setOnAction(handler);
        bt.setTooltip(new Tooltip(tooltip));
        bt.getTooltip().setStyle("-fx-font-size : 15;");
        bt.setStyle("-fx-font-size : " + (int) (1.5*15/n) + ";");
        bt.setPadding(Insets.EMPTY);
        bt.setPrefSize((int) (25/n), (int) (15/n));
        return bt;
    }

    public static String getRgb(Color color){
        return "rgba("+color.getRed()+","+color.getGreen()+","+color.getBlue()+","+color.getOpacity()+")";
    }



    public static HBox nodeSpaceNode(Node n1, Node n2){
        return new HBox(n1, spacer(), n2);
    }

    public static HBox nodeSpaceNode(String string, Node n2){
        return new HBox(new Label(string), spacer(), n2);
    }

    public static HBox labelInput(String label, Node node){
        HBox retour = new HBox(
            new Label(label+" : "),
            spacer(),
            node
        );
        if (node instanceof TextField) {
            ((TextField)node).setPromptText(label);
        } 
        retour.setMaxWidth(300);
        retour.setAlignment(Pos.CENTER);
        return retour;
        
    }

}
