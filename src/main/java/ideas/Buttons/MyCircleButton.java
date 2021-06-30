package main.java.ideas.Buttons;

import main.java.backend.model.Circle;
import main.java.backend.model.Figure;
import main.java.backend.model.Point;

public class MyCircleButton extends MyToggleButton{

    public MyCircleButton(String name) {
        super(name);
    }

    @Override
    public Figure generateFigure(Point startPoint, Point endPoint) {
        return new Circle(startPoint,endPoint);
    }
}
