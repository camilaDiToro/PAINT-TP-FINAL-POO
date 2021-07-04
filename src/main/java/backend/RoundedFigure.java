package main.java.backend;

public abstract class RoundedFigure extends Figure{

    private Point centerPoint;

    public RoundedFigure(Point topLeft, Point bottomRight, Point centerPoint){
        super(topLeft,bottomRight);
        this.centerPoint = centerPoint;
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
