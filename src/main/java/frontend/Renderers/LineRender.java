package main.java.frontend.Renderers;

import javafx.scene.canvas.GraphicsContext;
import main.java.backend.model.Line;

public class LineRender implements Render<Line> {

    private final Line line;

    public LineRender(Line line) {
        this.line = line;
    }

    public void render(GraphicsContext gc) {
        gc.strokeLine(line.getFirstPoint().getX(), line.getFirstPoint().getY(), line.getSecondPoint().getX(), line.getSecondPoint().getY());
    }

    public Line getFigure() {
        return line;
    }
}