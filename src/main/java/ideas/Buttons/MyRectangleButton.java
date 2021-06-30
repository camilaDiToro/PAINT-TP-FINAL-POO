package main.java.ideas.Buttons;

import main.java.backend.model.Figure;
import main.java.backend.model.Point;
import main.java.backend.model.Rectangle;

public class MyRectangleButton extends MyToggleButton {

    public MyRectangleButton(String name) {
        super(name);
    }

    @Override
    public Figure generateFigure(Point startPoint, Point endPoint) {
        return new Rectangle(startPoint,endPoint);
    }
}
