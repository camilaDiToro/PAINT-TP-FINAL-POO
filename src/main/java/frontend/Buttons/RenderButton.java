package main.java.frontend.Buttons;

import main.java.backend.model.MovableDrawing;
import main.java.backend.model.Point;
import javafx.scene.paint.Color;
import main.java.frontend.renderers.Render;

public class RenderButton<F extends MovableDrawing> extends CustomButton {

    private final RendererCreator<F> rendererCreator;

    public RenderButton(RendererCreator<F> rendererCreator, String buttonName) {
        super(buttonName);
        this.rendererCreator=rendererCreator;
    }

    public Render<F> createRenderer(Point startPoint, Point endPoint, Color colorBg, Color strokeColor, int strokeWidth) {
        return rendererCreator.create(startPoint, endPoint, colorBg, strokeColor, strokeWidth);
    }

    public boolean isRendered(){ return true; }

}
