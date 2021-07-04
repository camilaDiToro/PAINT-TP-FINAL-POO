package main.java.frontend.Renderers;

import javafx.scene.canvas.GraphicsContext;
import main.java.backend.MovableDrawing;
import javafx.scene.paint.Color;
import main.java.frontend.FigureStyle;

public abstract class Render<F extends MovableDrawing> {

    private final FigureStyle currentFigureStyle;
    private final F movableDrawing;

    public Render(F movableDrawing, FigureStyle fg) {
        this.movableDrawing = movableDrawing;
        this.currentFigureStyle = fg;
    }

    public abstract void render(GraphicsContext graphicsContext);

    public F getFigure(){
        return movableDrawing;
    }

    public void setStrokeColor(Color color){
        currentFigureStyle.setStrokeColor(color);
    }
    public void setBgColor(Color color){
        currentFigureStyle.setBgColor(color);
    }

    public void setStrokeWidth(double strokeWidth){
        currentFigureStyle.setStrokeWidth(strokeWidth);
    }

    public FigureStyle getStyle(){
        return currentFigureStyle;
    }

}
