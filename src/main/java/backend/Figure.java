package main.java.backend;

public abstract class Figure  extends Drawing {


    public Figure(Point startPoint, Point endPoint){
       super(startPoint, endPoint);
    }

    @Override
    public boolean isDrawable(){
       return (getFirstPoint().getX() <= getSecondPoint().getX() && getFirstPoint().getY() <= getSecondPoint().getY());
    }

    public Double getWidth(){ return getFirstPoint().distanceX(getSecondPoint()); }

    public Double getHeight(){ return getFirstPoint().distanceY(getSecondPoint()); }

}
