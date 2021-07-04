package main.java.backend;

import java.util.ArrayList;
import java.util.List;

public abstract class Drawing implements MovableDrawing{

    private final Point firstPoint, secondPoint;

    public Drawing(Point firstPoint, Point secondPoint){
        this.firstPoint = firstPoint;
        this.secondPoint = secondPoint;
    }

    public Point getFirstPoint() { return this.firstPoint; }

    public Point getSecondPoint() { return this.secondPoint; }

    @Override
    public List<Point> getPoints() {
        List<Point> toReturn = new ArrayList<>();
        toReturn.add(firstPoint);
        toReturn.add(secondPoint);
        return toReturn;
    }

    @Override
    public boolean isContained(Rectangle rectangle){
        return rectangle.pointBelongs(firstPoint) && rectangle.pointBelongs(secondPoint);
    }
}
