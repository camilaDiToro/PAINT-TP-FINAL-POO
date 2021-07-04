package main.java.backend;

public class Line extends Drawing{

    public Line(Point firstPoint, Point secondPoint) {
        super(firstPoint, secondPoint);
    }

    @Override
    public boolean isDrawable() {
        return true;
    }

    @Override
    public boolean pointBelongs(Point point) {
        return false;
    }

    @Override
    public String toString(){
        return String.format("Linea [Extremo 1: %s, Extremo 2: %s]",getFirstPoint(), getSecondPoint());
    }

}
