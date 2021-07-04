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
        gc.fillOval(getFigure().getTopLeft().getX(), getFigure().getTopLeft().getY(), getFigure().getWidth(), getFigure().getHeight());
        gc.strokeOval(getFigure().getTopLeft().getX(), getFigure().getTopLeft().getY(), getFigure().getWidth(), getFigure().getHeight());
    }
}

