package main.java.backend;

import java.util.List;

public abstract class RoundedFigure extends Figure{

    private Point centerPoint;

    public RoundedFigure(Point startPoint, Point endPoint, Point centerPoint){
        super(startPoint,endPoint);
        this.centerPoint = centerPoint;
    }

    public Point getCenterPoint() {
        return centerPoint;
    }

    @Override
    public Point[] getPoints() {
        return new Point[]{getFirstPoint(), getSecondPoint(), centerPoint};
    }

    @Override
    public boolean pointBelongs(Point point) {
        return Math.pow(point.getX()-centerPoint.getX(),2)/(Math.pow(getHeight()/2,2)) + Math.pow(point.getY()-centerPoint.getY(),2)/(Math.pow(getWidth()/2,2)) <=1; //Ecuación de la elipse con centro fuera del origen
    }
}
