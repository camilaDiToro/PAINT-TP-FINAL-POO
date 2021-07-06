package main.java.frontend.App;

import javafx.scene.layout.VBox;
import main.java.frontend.DrawingArea.PaintPane;

public class MainFrame extends VBox {

    public MainFrame(CanvasState canvasState) {
        getChildren().add(new AppMenuBar());
        StatusPane statusPane = new StatusPane();
        getChildren().add(new PaintPane(canvasState, statusPane));
        getChildren().add(statusPane);

    }

}
