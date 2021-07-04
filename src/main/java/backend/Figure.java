package main.java.backend;

public abstract class Figure implements MovableDrawing {

    private final Point topLeft, bottomRight;

    public Figure(Point startPoint, Point endPoint){
        this.topLeft = startPoint;
        this.bottomRight = endPoint;
    }

    @Override
    public boolean isDrawable(){
       return (topLeft.getX() <= bottomRight.getX() && topLeft.getY() <= bottomRight.getY());
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
