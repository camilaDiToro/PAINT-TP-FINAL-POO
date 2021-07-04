package main.java.backend;

public class Point implements Movable{

    private double x, y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }


    public void setX(double x) {this.x = x;}
    public void setY(double y) {this.y = y;}

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double distanceX(Point point){
        return Math.abs(x - point.getX());
    }

    public double distanceY(Point point){
        return Math.abs(y - point.getY());
    }

    public double distance(Point point){
        return Math.sqrt(Math.pow(distanceX(point),2) + Math.pow(distanceY(point),2));
    }

    // Metodo que agrega un diferencial recibido por parametro en las coordenadas x e y
    public void move(double diffX, double diffY) {
        y+=diffY; x+=diffX;
    }

    @Override
    public String toString() {
        return String.format("{%.2f , %.2f}", x, y);
    }

}
