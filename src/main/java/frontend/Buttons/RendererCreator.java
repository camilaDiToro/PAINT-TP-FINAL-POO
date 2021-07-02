package main.java.frontend.Buttons;

import javafx.scene.paint.Color;
import main.java.backend.model.MovableDrawing;
import main.java.backend.model.Point;
import main.java.frontend.renderers.Render;

@FunctionalInterface
public interface RendererCreator<P extends MovableDrawing> {

    Render<P> create(Point pt1, Point pt2, Color color, Color stroke, int strokeWidth);
}
