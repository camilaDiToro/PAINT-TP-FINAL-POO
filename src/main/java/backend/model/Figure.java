package main.java.backend.model;

public abstract class Figure implements Movable {

    private final Point topLeft, bottomRight;

    public Figure(Point topLeft, Point bottomRight){
        this.topLeft=topLeft;
        this.bottomRight=bottomRight;
    }

    public Point getTopLeft() { return this.topLeft; }

    public Point getBottomRight() { return this.bottomRight; }

    // Metodo que retorna un vector de Point[] que son los que conforman la figura.
    public abstract Point[] getPoints();

    // Metodo que exige la interfaz movable.
    // Metodo que mueve los puntos de cada figura.
    // Recibe por párametro el diferencial que se debe agregar al punto en sus coordenadas x e y
    public void move(double diffX, double diffY) {
        Point[] points = getPoints();
        for (Point p:points) {
            p.move(diffX,diffY);
        }
    }

    // Metodo que retorna true si un Point pertenece a la figura, caso contrario retorna false.
    public abstract boolean pointBelongs(Point point);

    public Double getWidth(){ return topLeft.distanceX(bottomRight); }

    public Double getHeight(){ return topLeft.distanceY(bottomRight); }
}
