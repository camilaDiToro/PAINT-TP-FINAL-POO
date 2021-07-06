package main.java.frontend.App;

import main.java.backend.models.MovableDrawing;
import main.java.frontend.Renderers.Render;
import java.util.*;

public class CanvasState {

    private final List<Render<? extends MovableDrawing>> renders = new LinkedList<>();

    public void addRenderFigures(Collection<Render<? extends MovableDrawing>> toAdd){ renders.addAll(toAdd); }

    public void deleteRenderFigures(Collection<Render<? extends MovableDrawing>> toRemove) {
        renders.removeAll(toRemove);
    }

    public void moveToFront(List<Render<? extends MovableDrawing>> selectedList){
        deleteRenderFigures(selectedList);
        addRenderFigures(selectedList);
    }

    public void moveToBack(List<Render<? extends MovableDrawing>> selectedList){
        deleteRenderFigures(selectedList);
        renders.addAll(0, selectedList);
    }

    public List<Render< ? extends MovableDrawing>> renderFigures() {
       return renders;
    }
}
