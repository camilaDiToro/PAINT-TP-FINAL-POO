package main.java.backend.model;

public abstract class Figure implements MovableDrawing {

    private final Point topLeft, bottomRight;

    public Figure(Point topLeft, Point bottomRight){
        this.topLeft=topLeft;
        this.bottomRight=bottomRight;
    }

    @Override
    public boolean isContained(Rectangle rectangle){
        return rectangle.pointBelongs(topLeft) && rectangle.pointBelongs(bottomRight);
    }

    public Point getTopLeft() { return this.topLeft; }

    public Point getBottomRight() { return this.bottomRight; }

    public Double getWidth(){ return topLeft.distanceX(bottomRight); }

    public Double getHeight(){ return topLeft.distanceY(bottomRight); }
}
