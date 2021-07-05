package main.java.backend;

import java.util.List;

/**
 * Marca el comportamiento que debe tener un dibujo para poder moverse. Definimos los dibujos a través de
 * ciertos puntos que los identifican y nos permiten representarlos adecuadamente. De esto surge que mover
 * un dibujo implica mover los puntos que lo representan. Por esta razón, esta interfaz cuenta con un método
 * getPoints, mediante el cual se espera obtener cada uno de los puntos que representan a un dibujo, y una
 * implementación default del método move que lo que hace es mover cada uno de los puntos el diferencial deseado.
 *
 */

public interface MovableDrawing extends Movable{

    /**
     *   Metodo que retorna un vector de Point[] con los puntos que identifican a cada MovableDrawing.
     */
    Point[] getPoints();

    /**
     * Metodo que exige la interfaz movable, mover un dibujo implica mover los puntos que lo identifican.
     * @param diffX indica cuanto se desea mover al dibujo de forma horizontal.
     * @param diffY indica cuanto se desea mover al dibujo en forma vertical.
     */
     default void move(double diffX, double diffY){
        Point[] points = getPoints();
        for (Point p : points) {
            p.move(diffX,diffY);
        }
    }

    /**
     * Indica si es posible graficar en el canvas el dibujo.
     */
    boolean isDrawable();

    /**
     * Devuelve true si el punto recibido por parámetro pertenece a la figura, false en caso contrario
     * @param point
     */
    boolean pointBelongs(Point point);

    /**
     * Devuelve true si el dibujo se encuentra incluido completamente en le rectangulo recibido por parametro.
     * @param rectangle
     */
    boolean isContained(Rectangle rectangle);

}
