package main.java.frontend.Renderers;

import javafx.scene.canvas.GraphicsContext;
import main.java.backend.Rectangle;
import main.java.frontend.FigureStyle;

public class RectangleRender<F extends Rectangle> extends Render<F>{

    public RectangleRender(F rectangle, FigureStyle fg){
        super(rectangle, fg);
    }

    public void render( GraphicsContext  gc ){
        gc.fillRect(getFigure().getFirstPoint().getX(), getFigure().getFirstPoint().getY(), getFigure().getWidth(), getFigure().getHeight());
        gc.strokeRect(getFigure().getFirstPoint().getX(), getFigure().getFirstPoint().getY(), getFigure().getWidth(), getFigure().getHeight());
    }

}
