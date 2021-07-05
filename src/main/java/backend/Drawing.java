package main.java.backend;

/**
 * Esta clase vincula los dos tipos de dibujos que podemos hacer en la aplicación, figuras
 * y líneas. La clase se guarda el primer punto donde se hizo click para comenzar a dibujar
 * y el último punto donde se soltó el mouse. Estos dos puntos permiten identificar a la figura
 * y representarla en la interfaz gráfica.
 */

public abstract class Drawing implements MovableDrawing{

    private final Point firstPoint, secondPoint;

    public Drawing(Point firstPoint, Point secondPoint){
        this.firstPoint = firstPoint;
        this.secondPoint = secondPoint;
    }

    public Point getFirstPoint() { return this.firstPoint; }

    public Point getSecondPoint() { return this.secondPoint; }

    @Override
    public Point[] getPoints() {
        return new Point[]{firstPoint,secondPoint};
    }

    /**
     *  Diremos que un dibujo está incluido en un rectángulo si su
     *  punto superior izquierdo y su punto inferior derecho están incluidos en él.
     */
    @Override
    public boolean isContained(Rectangle rectangle){
        return rectangle.pointBelongs(firstPoint) && rectangle.pointBelongs(secondPoint);
    }
}
