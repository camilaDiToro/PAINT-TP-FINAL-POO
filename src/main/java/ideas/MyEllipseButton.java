package main.java.ideas;

import main.java.backend.model.Ellipse;
import main.java.backend.model.Figure;
import main.java.backend.model.Point;

public class MyEllipseButton extends MyToggleButton{

    @Override
    public Figure generateFigure(Point startPoint, Point endPoint) {
        return new Ellipse(startPoint,endPoint);
    }
}
