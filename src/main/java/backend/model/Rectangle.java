package main.java.backend.model;

public class Rectangle extends Figure {

    public Rectangle(Point topLeft, Point bottomRight) {
        super(topLeft, bottomRight);
    }

    @Override
    public String toString() {
        return String.format("Rectángulo [ %s , %s ]", getTopLeft(), getBottomRight());
    }

    @Override
    public Point[] getPoints() {
        return new Point[] { getTopLeft(), getBottomRight() };
    }

    @Override
    public boolean pointBelongs(Point point) {
        return point.getX() >= getTopLeft().getX() && point.getX() <= getBottomRight().getX()
                && point.getY() >= getTopLeft().getY() && point.getY() <= getBottomRight().getY();
    }
}
