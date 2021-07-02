package main.java.frontend;

import main.java.backend.model.MovableDrawing;
import main.java.frontend.renderers.Render;

import java.util.ArrayList;
import java.util.Stack;

public class CanvasState {


    private final Stack<Render<MovableDrawing>> renders = new Stack<>();

    public void addRenderFigure(Render<MovableDrawing> renderFigure) {
        renders.push(renderFigure);
    }

    public Iterable<Render<MovableDrawing>> renderFigures() {
        return new ArrayList<>(renders);
    }
    // en un futuro tiene que tener alguna forma de orden, para determinar profundidad.

    /*
        private final List<Render<MovableDrawing>> list = new ArrayList<>();

    public void addRenderFigure(Render<MovableDrawing> renderFigure) {
        list.add(renderFigure);
    }
    public Iterable<Render<MovableDrawing>> renderFigures() {
        return new ArrayList<>(list);
    }
*/

}
