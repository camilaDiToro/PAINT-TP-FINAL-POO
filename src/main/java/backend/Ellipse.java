package main.java.backend;

public class Ellipse extends RoundedFigure{

    public Ellipse(Point topLeft, Point bottomRight) {
        super(topLeft, bottomRight, new Point(topLeft.getX() + topLeft.distanceX(bottomRight)/2, topLeft.getY() + topLeft.distanceY(bottomRight)/2));
    }

    @Override
    public String toString() {
        return String.format("Elipse [Centro: %s, Diámentro 1: %.2f, Diámentro 2: %.2f]", getCenterPoint(), getHeight(), getWidth());
    }

}
