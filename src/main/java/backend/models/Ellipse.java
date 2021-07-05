package main.java.backend.models;

public class Ellipse extends RoundedFigure{

    public Ellipse(Point startPoint, Point endPoint) {
        super(startPoint, endPoint, new Point(startPoint.getX() + startPoint.distanceX(endPoint)/2, startPoint.getY() + startPoint.distanceY(endPoint)/2));
    }

    @Override
    public String toString() {
        return String.format("Elipse [Centro: %s, Diámentro 1: %.2f, Diámentro 2: %.2f]", getCenterPoint(), getHeight(), getWidth());
    }
    
}
