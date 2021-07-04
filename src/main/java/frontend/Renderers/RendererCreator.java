package main.java.frontend.Renderers;

import main.java.backend.MovableDrawing;
import main.java.backend.Point;

@FunctionalInterface
public interface RendererCreator<F extends MovableDrawing> {

    Render<F> create(Point pt1, Point pt2);
}
