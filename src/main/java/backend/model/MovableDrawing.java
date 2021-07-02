package main.java.backend.model;

public interface MovableDrawing extends Movable{


    // Metodo que retorna un vector de Point[] que son los que conforman la figura.
    Point[] getPoints();

    // Metodo que exige la interfaz movable.
    // Metodo que mueve los puntos de cada figura.
    // Recibe por párametro el diferencial que se debe agregar al punto en sus coordenadas x e y
    default void move(double diffX, double diffY){
        Point[] points = getPoints();
        for (Point p:points) {
            p.move(diffX,diffY);
        }
    }

    // Metodo que retorna true si un Point pertenece a la figura, caso contrario retorna false.
    public abstract boolean pointBelongs(Point point);

}
