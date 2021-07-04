package main.java.frontend.Renderers;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import main.java.backend.RoundedFigure;
import main.java.frontend.FigureStyle;

public class RoundedFigureRender<F extends RoundedFigure> extends Render<F> {

    public RoundedFigureRender(F roundedFigure, FigureStyle fg) {
        super(roundedFigure, fg);
    }

    public void render(GraphicsContext gc) {
        gc.fillOval(getFigure().getFirstPoint().getX(), getFigure().getFirstPoint().getY(), getFigure().getWidth(), getFigure().getHeight());
        gc.strokeOval(getFigure().getFirstPoint().getX(), getFigure().getFirstPoint().getY(), getFigure().getWidth(), getFigure().getHeight());
    }
}

