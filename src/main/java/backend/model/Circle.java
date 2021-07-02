package main.java.backend.model;

public class Circle extends RoundedFigure {

    public Circle(Point center, Point endPoint) {
        super(new Point(center.getX() - center.distance(endPoint),center.getY() - center.distance(endPoint)),
              new Point(center.getX() + center.distance(endPoint),center.getY() + center.distance(endPoint)),
              center);
    }

    @Override
    public String toString() {
        return String.format("Círculo [Centro: %s, Radio: %.2f]", getCenterPoint(), getHeight()/2);
    }

}
