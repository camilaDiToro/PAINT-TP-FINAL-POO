package main.java.ideas;

import main.java.backend.model.Figure;
import main.java.backend.model.Point;
import main.java.backend.model.Rectangle;

public class MyRectangularButton extends MyToggleButton{

    @Override
    public Figure generateFigure(Point startPoint, Point endPoint) {
        return new Rectangle(startPoint,endPoint);
    }
}
