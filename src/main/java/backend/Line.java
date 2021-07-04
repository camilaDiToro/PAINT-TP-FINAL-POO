package main.java.backend;

public class Line implements MovableDrawing{

    private final Point firstPoint, secondPoint;

    public Line(Point firstPoint, Point secondPoint){
        this.firstPoint = firstPoint;
        this.secondPoint = secondPoint;
    }

    @Override
    public boolean isDrawable() {
        return true;
    }

    public Point getFirstPoint() { return this.firstPoint; }

    public Point getSecondPoint() { return this.secondPoint; }

    @Override
    public Point[] getPoints() {
        return new Point[]{firstPoint,secondPoint};
    }

    @Override
    public boolean pointBelongs(Point point) {
        return false;
    }

    @Override
    public boolean isContained(Rectangle rectangle){
        return rectangle.pointBelongs(firstPoint) && rectangle.pointBelongs(secondPoint);
    }

    @Override
    public String toString(){
        return String.format("Linea [Extremo 1: %s, Extremo 2: %s]",getFirstPoint(), getSecondPoint());
    }

}
