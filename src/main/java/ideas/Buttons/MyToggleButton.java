package main.java.ideas.Buttons;

import javafx.scene.control.ToggleButton;
import main.java.backend.model.Figure;
import main.java.backend.model.Point;

public abstract class MyToggleButton extends ToggleButton {

    /* ToggleGroup tiene un metodo que se llama getSelectedToggle() que devuelve el Toggle que fue seleccionado.
       La idea es crear nuestro propio tipo de boton y que cada boton tenga un metodo que indique que figura
       debemos instanciar para asignarselo a Figure newFigure y pasarselo a add. */

    /* tools.getSelectedToggle().generateFigure(startPoint, endPoint); */


    /* Problemas:
       1) Tenemos que crear nuestro propio grupo me parece porque ese método no nos sirve. Cree MyCustomGroup que la extiende
       2) No me gustó como resolvi lo del botón seleccionar. Es de una clase distinta del resto de los botones,
          eso se debe a que no tendria sentido crear una clase que se llame MySelectionButton que extienda de MyToggleButton
          e implemente el metodo generateFigure.
       3) Creo que es de pesimo estilo tener que castear a MyToggleButton para hacer el add, pero por ahora, despues de haberme
          peleado un rato, no se me ocurrio otra cosa.
     */
    public MyToggleButton(String name){
        super(name);
    }

    public abstract Figure generateFigure(Point startPoint, Point endPoint);
}
