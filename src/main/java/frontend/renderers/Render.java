package main.java.frontend.renderers;
import javafx.scene.canvas.GraphicsContext;
import main.java.backend.model.MovableDrawing;

public interface Render<F extends MovableDrawing> {
    void render(GraphicsContext  graphicsContext);
    F getFigure();
}
