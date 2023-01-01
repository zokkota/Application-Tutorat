package ihm.utils;

import graphs.Couple;
import ihm.Interface;
import javafx.geometry.Insets;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.paint.Color;
import javafx.util.Callback;

public class CoupleCellFactory implements Callback<ListView<Couple>, ListCell<Couple>> {
    Interface iface;

    public CoupleCellFactory(Interface iface) {
        this.iface = iface;
    }

    @Override
    public ListCell<Couple> call(ListView<Couple> arg0) {
        return new CoupleCellStyler(iface);
    }

    static class CoupleCellStyler extends ListCell<Couple> {
        Interface iface;

        public CoupleCellStyler(Interface iface) {
            this.iface = iface;
            getStyleClass().clear();
            setBackground(null);
            setPrefHeight(TutoringUtils.LIST_CELL_HEIGHT);
            setPadding(Insets.EMPTY);
        }

        public void updateItem(Couple item, boolean empty) {
            super.updateItem(item, empty);

            Canvas canvas = new Canvas(iface.couplesView.getWidth() -10, TutoringUtils.LIST_CELL_HEIGHT);
            canvas.widthProperty().bind(iface.couplesView.widthProperty());
            GraphicsContext gc = canvas.getGraphicsContext2D();
            setGraphic(canvas);

            if (item != null) {
                if (iface.dpt.tutoring.getForcedCouples().contains(item)) {
                    gc.setStroke(Color.CYAN);
                } else if (iface.dpt.tutoring.getForbiddenCouples().contains(item)) {
                    gc.setStroke(Color.RED);
                } else {
                    gc.setStroke(Color.BLACK);
                }
                gc.strokeLine(5, 5+TutoringUtils.LIST_CELL_HEIGHT / 2, 100,
                        5+TutoringUtils.LIST_CELL_HEIGHT / 2);
            }
            else {
            }
        }

    }

}
