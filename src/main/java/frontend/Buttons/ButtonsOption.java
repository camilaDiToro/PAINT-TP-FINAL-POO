package main.java.frontend.Buttons;

import main.java.backend.Point;

/***
 *  Interfaz que permite darle un comportamiento a un boton. Llamaremos a estos
 *  metodos cuando ocurra un determinado evento en el canvas.
 *
 */

public interface ButtonsOption {

    void mousePressed(Point eventPoint);

    void mouseReleased(Point eventPoint);

    void mouseClicked(Point eventPoint);

    void mouseMoved(Point eventPoint);

    void mouseDragged(Point startPoint, Point eventPoint);

    void mouseClickAndDrag(Point pressedPoint, Point releasePoint);
}