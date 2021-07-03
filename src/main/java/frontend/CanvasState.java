package main.java.frontend;

import main.java.backend.model.MovableDrawing;
import main.java.frontend.Renderers.Render;

import java.util.*;

public class CanvasState {

    private final List<Render<? extends MovableDrawing>> renders = new ArrayList<>();

    public void addRenderFigure(Render<? extends MovableDrawing> renderFigure) {
        renders.add(0, renderFigure);
    }

    public void deleteRenderFigures(Collection<Render<? extends MovableDrawing>> toRemove) {
        renders.removeAll(toRemove);
    }

    public List<Render< ? extends MovableDrawing>> renderFigures() {
        return renders;
    }

}
