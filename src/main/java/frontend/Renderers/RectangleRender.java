package main.java.frontend.Renderers;

import javafx.scene.canvas.GraphicsContext;
import main.java.backend.Rectangle;
import main.java.frontend.FigureStyle;

public class RectangleRender<F extends Rectangle> extends Render<F>{

    public RectangleRender(F rectangle, FigureStyle fg){
        super(rectangle, fg);
    }

    public void render( GraphicsContext  gc ){
        gc.fillRect(getFigure().getTopLeft().getX(), getFigure().getTopLeft().getY(), getFigure().getWidth(), getFigure().getHeight());
        gc.strokeRect(getFigure().getTopLeft().getX(), getFigure().getTopLeft().getY(), getFigure().getWidth(), getFigure().getHeight());
    }

}
