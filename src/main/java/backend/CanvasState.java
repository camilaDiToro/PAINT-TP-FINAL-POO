package main.java.backend;

import main.java.backend.model.Figure;

import java.util.ArrayList;
import java.util.List;

public class CanvasState {

    private final List<Figure> list = new ArrayList<>();

    // en un futuro tiene que tener alguna forma de orden, para determinar profundidad.

    public void addFigure(Figure figure) {
        list.add(figure);
    }

    public Iterable<Figure> figures() {
        return new ArrayList<>(list);
    }

}
