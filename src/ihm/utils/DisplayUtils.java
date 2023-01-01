package ihm.utils;

import ihm.Interface;
import javafx.scene.Scene;
import javafx.scene.control.ScrollBar;

public class DisplayUtils {
    public static String theme = "#ececec";

    public static void setTheme(Scene scene, String color) {   
        theme = color;
        scene.getRoot().setStyle("-fx-base:"+color);
    }

    public static void setTheme(Scene scene) {   
        setTheme(scene, theme);
    }

    public static void setScrollBars(Interface iface) {
        iface.scrollBarOne = (ScrollBar) iface.tutoredView.lookup(".scroll-bar:vertical");
        iface.scrollBarTwo = (ScrollBar) iface.tutorsView.lookup(".scroll-bar:vertical");
        iface.scrollBarThree = (ScrollBar) iface.couplesView.lookup(".scroll-bar:vertical");
        
        iface.scrollBarOne.setStyle("-fx-opacity: 0.5");
        iface.scrollBarTwo.setStyle("-fx-opacity: 0.5");
        iface.couplesView.lookup(".scroll-bar").setStyle("-fx-scale:0;");
    }
}
