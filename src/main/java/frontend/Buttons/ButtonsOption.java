package main.java.frontend.Buttons;

import main.java.backend.models.Point;

/***
 *  Interfaz que permite darle un comportamiento a un boton. Llamaremos a estos
 *  metodos cuando ocurra un determinado evento en el canvas.
 *
 */

public interface ButtonsOption {

    default void mousePressed(Point eventPoint){

    }

    default void mouseReleased(Point eventPoint){

    }

    default void mouseClicked(Point eventPoint){

    }

    default void mouseMoved(Point eventPoint){

    }

    default void mouseDragged(Point startPoint, Point eventPoint){

    }

    default void mouseClickAndDrag(Point pressedPoint, Point releasePoint){

    }

}