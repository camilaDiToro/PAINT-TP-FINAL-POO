package main.java.ideas.Buttons;

import main.java.backend.model.Ellipse;
import main.java.backend.model.Figure;
import main.java.backend.model.Point;

public class MyEllipseButton extends MyToggleButton{


    public MyEllipseButton(String name) {
        super(name);
    }

    @Override
    public Figure generateFigure(Point startPoint, Point endPoint) {
        return new Ellipse(startPoint,endPoint);
    }
}
