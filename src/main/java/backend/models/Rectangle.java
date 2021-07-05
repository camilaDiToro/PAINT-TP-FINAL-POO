package main.java.backend.models;

public class Rectangle extends Figure {

    public Rectangle(Point startPoint, Point endPoint) {
        super(startPoint, endPoint);
    }

    @Override
    public String toString() {
        return String.format("Rectángulo [ %s , %s ]", getFirstPoint(), getSecondPoint());
    }


    @Override
    public boolean pointBelongs(Point point) {
        return point.getX() >= getFirstPoint().getX() && point.getX() <= getSecondPoint().getX()
                && point.getY() >= getFirstPoint().getY() && point.getY() <= getSecondPoint().getY();
    }
}
