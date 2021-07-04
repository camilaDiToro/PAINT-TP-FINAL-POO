package main.java.frontend.Buttons;

import main.java.backend.MovableDrawing;
import main.java.backend.Point;
import main.java.frontend.Renderers.Render;
import main.java.frontend.Renderers.RendererCreator;
import java.util.List;

/***
 * Boton de las figuras
 *
 */

public class FigureButtons<F extends MovableDrawing> implements ButtonsOption {

    private final RendererCreator<F> rendererCreator;
    private final List<Render<? extends MovableDrawing>> renderList;
    //private final FigureStyle figureStyle;
    private Point startPoint;


    public FigureButtons(List<Render<? extends MovableDrawing>> renderList, RendererCreator<F> rendererCreator ) {
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

    @Override
    public void mouseClicked(Point eventPoint) {
    }

    @Override
    public void mouseMoved(Point eventPoint) {
    }

    @Override
    public void mouseDragged(Point startPoint, Point eventPoint) {
    }

    @Override
    public void mouseClickAndDrag(Point pressedPoint, Point releasePoint) {
    }
}
