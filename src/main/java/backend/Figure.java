package main.java.backend;

public abstract class Figure  extends Drawing {


    public Figure(Point startPoint, Point endPoint){
       super(startPoint, endPoint);
    }

    /**
     * Una figura es dibujable si el primer punto esta arriba y a la izquierda del segundo punto.
     */
    @Override
    public boolean isDrawable(){
       return (getFirstPoint().getX() <= getSecondPoint().getX() && getFirstPoint().getY() <= getSecondPoint().getY());
    }

    public Double getWidth(){ return getFirstPoint().distanceX(getSecondPoint()); }

    public Double getHeight(){ return getFirstPoint().distanceY(getSecondPoint()); }

}
