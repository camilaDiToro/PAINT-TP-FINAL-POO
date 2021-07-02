package main.java.frontend.Renderers;
import javafx.scene.canvas.GraphicsContext;
import main.java.backend.model.RoundedFigure;

public class RoundedFigureRender<F extends RoundedFigure> implements Render<F> {

    private final F roundedFigure;

    public RoundedFigureRender(F roundedFigure) {
        this.roundedFigure = roundedFigure;
    }

    public F getFigure() {
        return roundedFigure;
    }

    public void render(GraphicsContext gc) {
        gc.fillOval(roundedFigure.getTopLeft().getX(),roundedFigure.getTopLeft().getY(), roundedFigure.getWidth(), roundedFigure.getHeight());
        gc.strokeOval(roundedFigure.getTopLeft().getX(),roundedFigure.getTopLeft().getY(), roundedFigure.getWidth(), roundedFigure.getHeight());
    }

}

