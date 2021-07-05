package main.java.frontend.Buttons;

import java.util.List;

import javafx.scene.control.ToggleButton;
import main.java.backend.MovableDrawing;
import main.java.backend.Point;
import main.java.backend.Rectangle;
import main.java.frontend.Renderers.Render;

/***
 *  Boton "Seleccionar"
 */

public class SelectButton extends ToggleOptionButton{

    private final List<Render<? extends MovableDrawing>> selectedList;
    private final List<Render<? extends MovableDrawing>> renderList;
    private Rectangle imaginaryRect;
    private Point lastPosition;

    public SelectButton(String buttonName, List<Render<? extends MovableDrawing>> selectedList, List<Render<? extends MovableDrawing>> renderList) {
        super(buttonName);
        this.renderList = renderList;
        this.selectedList = selectedList;
    }

    @Override
    public void mousePressed(Point eventPoint) {
        lastPosition = eventPoint;
    }

    @Override
    public void mouseClicked(Point eventPoint) {
        if (imaginaryRect == null || !imaginaryRect.pointBelongs(eventPoint)) {
            selectedList.clear();
            imaginaryRect = null;

            Render<? extends MovableDrawing> selectedDrawing = null;
            for (Render<? extends MovableDrawing> render : renderList) {
                if (render.getFigure().pointBelongs(eventPoint)) {
                    selectedDrawing = render;
                }
            }
            if(selectedDrawing!=null)
                selectedList.add(selectedDrawing);
        }

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
        imaginaryRect = new Rectangle(pressedPoint, releasePoint);
        for (Render<? extends MovableDrawing> render : renderList) {
            if (render.getFigure().isContained(imaginaryRect)) {
                selectedList.add(render);
            }
        }
    }

}
