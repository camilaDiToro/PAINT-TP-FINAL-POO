package main.java.frontend;

import main.java.backend.model.MovableDrawing;
import main.java.frontend.Renderers.Render;

import java.util.*;

public class CanvasState {

    private final List<Render<MovableDrawing>> renders = new ArrayList<>();

    public void addRenderFigure(Render<MovableDrawing> renderFigure) {
        renders.add(renderFigure);
    }

    public void deleteRenderFigures(Collection<Render<MovableDrawing>> toRemove){
        renders.removeAll(toRemove);
    }

    public Iterable<Render<MovableDrawing>> renderFigures(){
        return new ArrayList<>(renders);
    }

}
