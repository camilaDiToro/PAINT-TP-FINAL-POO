package main.java.frontend.Buttons;

import main.java.backend.model.Point;

public interface Option {

    void mousePressed(Point eventPoint);

    void mouseReleased(Point eventPoint);

    void mouseClicked(Point eventPoint);

    void mouseMoved(Point eventPoint);

    void mouseDragged(Point startPoint, Point eventPoint);

    void mouseClickAndDrag(Point pressedPoint, Point releasePoint);
}