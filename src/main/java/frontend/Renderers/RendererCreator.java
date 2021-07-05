package main.java.frontend.Renderers;

import main.java.backend.models.MovableDrawing;
import main.java.backend.models.Point;

@FunctionalInterface
public interface RendererCreator<F extends MovableDrawing> {

    Render<F> create(Point pt1, Point pt2);
}
