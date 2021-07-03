package main.java.frontend.Buttons;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import main.java.backend.model.MovableDrawing;
import main.java.backend.model.Point;
import main.java.backend.model.Rectangle;
import main.java.frontend.Renderers.Render;

public class SelectOption implements Option {

    private List<Render<? extends MovableDrawing>> selectedList;
    private Rectangle imaginaryRect;
    private List<Render<? extends MovableDrawing>> renderList;
    private Consumer<Point> updateInfoBarOnClick;
    private Point lastPosition;

    public SelectOption(List<Render<? extends MovableDrawing>> selectedList,
            List<Render<? extends MovableDrawing>> renderList, Consumer<Point> updateInfoBarOnClick) {
        this.renderList = renderList;
        this.updateInfoBarOnClick = updateInfoBarOnClick;
        this.selectedList = selectedList;
    }

    @Override
    public void mousePressed(Point eventPoint) {
        lastPosition = eventPoint;
    }

    @Override
    public void mouseReleased(Point eventPoint) {

    }

    @Override
    public void mouseClicked(Point eventPoint) {
        // Si tocaste afuera del rectangulo imaginario, vacio la lista.
        if (imaginaryRect == null || !imaginaryRect.pointBelongs(eventPoint)) {
            selectedList.clear();
            imaginaryRect = null;

            for (Render<? extends MovableDrawing> render : renderList) {
                if (render.getFigure().pointBelongs(eventPoint)) {
                    selectedList.add(render);
                    break;
                }
            }
        }

        if (selectedList.isEmpty() || selectedList.size() == 1) {
            updateInfoBarOnClick.accept(eventPoint);
        }
    }

    @Override
    public void mouseMoved(Point eventPoint) {

    }

    @Override
    public void mouseDragged(Point startPoint, Point eventPoint) {
        if (!selectedList.isEmpty()) {
            double diffX = (eventPoint.getX() - lastPosition.getX());
            double diffY = (eventPoint.getY() - lastPosition.getY());
            for (Render<? extends MovableDrawing> render : selectedList)
                render.getFigure().move(diffX, diffY);
            lastPosition = eventPoint;
        }
    }

    @Override
    public void mouseClickAndDrag(Point pressedPoint, Point releasePoint) {
        imaginaryRect = new Rectangle(pressedPoint, releasePoint); // Lo esta tomando bien
        for (Render<? extends MovableDrawing> render : renderList) {
            if (render.getFigure().isContained(imaginaryRect)) {
                selectedList.add(render);
            }
        }
    }
}
