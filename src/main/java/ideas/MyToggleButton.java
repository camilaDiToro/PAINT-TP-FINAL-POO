package main.java.ideas;

import javafx.scene.control.ToggleButton;
import main.java.backend.model.Figure;
import main.java.backend.model.Point;

public abstract class MyToggleButton extends ToggleButton {

    /* ToggleGroup tiene un metodo que se llama getSelectedToggle() que devuelve el Toggle que fue seleccionado.
       La idea es crear nuestro propio tipo de boton y que cada boton tenga un metodo que indique que figura
       debemos instanciar para asignarselo a Figure newFigure y pasarselo a add. */

    /* tools.getSelectedToggle().generateFigure(startPoint, endPoint); */

    public abstract Figure generateFigure(Point startPoint, Point endPoint);
}
