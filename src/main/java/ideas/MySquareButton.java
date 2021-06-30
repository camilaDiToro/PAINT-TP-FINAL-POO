package main.java.ideas;

import main.java.backend.model.Figure;
import main.java.backend.model.Point;
import main.java.backend.model.Square;

public class MySquareButton extends MyToggleButton {

    @Override
    public Figure generateFigure(Point startPoint, Point endPoint) {
        return new Square(startPoint,endPoint);
    }
}
