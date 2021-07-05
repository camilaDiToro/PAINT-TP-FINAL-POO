package main.java.frontend;

import javafx.scene.layout.VBox;
import main.java.frontend.DrawingArea.PaintPane;

/*
    Clase de layout de todos los elementos
 */

public class MainFrame extends VBox {

    public MainFrame(CanvasState canvasState) {
        getChildren().add(new AppMenuBar()); // El menuBar es la primer linea horizontal que aparece en el programa
        StatusPane statusPane = new StatusPane();
        getChildren().add(new PaintPane(canvasState, statusPane)); // El paintPane es todo lo que nos permite dibujar en el cuadrado del medio
        getChildren().add(statusPane);  // Aca figura el toString de cada figura
    }

    // Un VBOX son cajitas horizontales apiladas una arriba de otra entonces, con cada getChildren vamos "apilando" las secciones

}
