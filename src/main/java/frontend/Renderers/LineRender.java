package main.java.frontend.Renderers;

import javafx.scene.canvas.GraphicsContext;
import main.java.backend.Line;

public class LineRender extends Render<Line> {

    public LineRender(Line line, FigureStyle fg) {
        super(line, fg);
    }

    public void render(GraphicsContext gc) {
        gc.strokeLine(getFigure().getFirstPoint().getX(), getFigure().getFirstPoint().getY(), getFigure().getSecondPoint().getX(), getFigure().getSecondPoint().getY());
    }


}