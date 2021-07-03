package main.java.frontend.Buttons;

import main.java.backend.model.MovableDrawing;
import main.java.backend.model.Point;
import javafx.scene.paint.Color;
import main.java.frontend.Renderers.Render;

import java.util.function.BiFunction;
import java.util.function.Consumer;

public class FigureOption<F extends MovableDrawing> implements Option {

    private final RendererCreator<F> rendererCreator;
    private final Consumer<Render<F>> onCreateFigure;
    private final BiFunction<Point, Point, Boolean> canBeCreated;
    private final FigureStyle figureStyle;
    private Point startPoint;

    public FigureOption(RendererCreator<F> rendererCreator, Consumer<Render<F>> onCreateFigure,
            BiFunction<Point, Point, Boolean> canBeCreated, FigureStyle figureStyle) {
        this.rendererCreator = rendererCreator;
        this.onCreateFigure = onCreateFigure;
        this.figureStyle = figureStyle;
        this.canBeCreated = canBeCreated;
    }

    public Render<F> createRenderer(Point startPoint, Point endPoint) {
        if (!canBeCreated.apply(startPoint, endPoint))
            return null;

        return rendererCreator.create(startPoint, endPoint, figureStyle.getBgColor(), figureStyle.getStrokeColor(),
                figureStyle.getStrokeWidth());
    }

    @Override
    public void mousePressed(Point eventPoint) {
        startPoint = eventPoint;
    }

    @Override
    public void mouseReleased(Point eventPoint) {

        Render<F> newRenderer = createRenderer(startPoint, eventPoint);
        if (newRenderer != null)
            onCreateFigure.accept(newRenderer);

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
        // Reservado para futuras implemetacion o actualizaciones.
    }

    @Override
    public void mouseClickAndDrag(Point pressedPoint, Point releasePoint) {

    }
}
