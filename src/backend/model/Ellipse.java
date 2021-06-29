package backend.model;

public class Ellipse extends Figure{

    private final Point centerPoint;

    public Ellipse(Point topLeft, Point bottomRight) {
        super(topLeft, bottomRight);
        double centerX = topLeft.getX() + topLeft.distanceX(bottomRight)/2;
        double centerY = topLeft.getY() + topLeft.distanceY(bottomRight)/2;
        centerPoint = new Point(centerX, centerY);
    }

    @Override
    public String toString() {
        return String.format("Elipse [Centro: %s, Diámentro 1: %.2f, Diámentro 2: %.2f]", centerPoint, getHeight(), getWidth());
    }
    public Point getCenterPoint() {
        return centerPoint;
    }
    @Override
    public Point[] getPoints() {
        return new Point[]{getTopLeft(),getBottomRight(),centerPoint};
    }

    @Override
    public boolean pointBelongs(Point point) {
        return Math.pow(point.getX()-centerPoint.getX(),2)/(Math.pow(getHeight()/2,2)) + Math.pow(point.getY()-centerPoint.getY(),2)/(Math.pow(getWidth()/2,2)) <=1; //Ecuación de la elipse con centro fuera del origen
    }
}
