package main.java.frontend.Renderers;

import javafx.scene.canvas.GraphicsContext;
import main.java.backend.model.Rectangle;

public class RectangleRender<F extends Rectangle> implements Render<F>{

    private final F rectangle;

    public RectangleRender(F rectangle){
        this.rectangle = rectangle;
    }

    public void render( GraphicsContext  gc){
        gc.fillRect(rectangle.getTopLeft().getX(), rectangle.getTopLeft().getY(), rectangle.getWidth(), rectangle.getHeight());
        gc.strokeRect(rectangle.getTopLeft().getX(), rectangle.getTopLeft().getY(), rectangle.getWidth(), rectangle.getHeight());
    }

    public F getFigure(){ return rectangle; }
}
