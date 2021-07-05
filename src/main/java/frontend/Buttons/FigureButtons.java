package main.java.frontend.Buttons;

import javafx.scene.control.ToggleButton;
import main.java.backend.MovableDrawing;
import main.java.backend.Point;
import main.java.frontend.Renderers.Render;
import main.java.frontend.Renderers.RendererCreator;
import java.util.List;

/***
 *  Boton que permtite crear las figuras las figuras
 */

public class FigureButtons<F extends MovableDrawing> extends ToggleOptionButton {

    private final RendererCreator<F> rendererCreator;
    private final List<Render<? extends MovableDrawing>> renderList;
    private Point startPoint;


    public FigureButtons(String buttonName, List<Render<? extends MovableDrawing>> renderList, RendererCreator<F> rendererCreator ) {
        super(buttonName);
        this.renderList = renderList;
        this.rendererCreator = rendererCreator;
    }

    public Render<F> createRenderer(Point startPoint, Point endPoint) {
        return rendererCreator.create(startPoint, endPoint);
    }

    @Override
    public void mousePressed(Point eventPoint) {
        startPoint = eventPoint;
    }

    @Override
    public void mouseReleased(Point eventPoint) {
        Render<F> newRenderer = createRenderer(startPoint, eventPoint);
        if (newRenderer.getFigure().isDrawable())
          renderList.add(newRenderer);
        startPoint = null;
    }
}
