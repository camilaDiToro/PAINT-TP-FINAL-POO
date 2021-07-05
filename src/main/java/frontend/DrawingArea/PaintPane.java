package main.java.frontend.DrawingArea;

import main.java.backend.*;
import main.java.backend.Point;
import main.java.frontend.App.CanvasState;
import main.java.frontend.App.StatusPane;
import main.java.frontend.Buttons.ToggleOptionGroup;
import main.java.frontend.Renderers.FigureStyle;
import main.java.frontend.Renderers.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

public class PaintPane extends BorderPane {

	// BackEnd
	private final CanvasState canvasState;

	// StatusBar
	private final StatusPane statusPane;

	// Graphic context
	private final GraphicsContext gc;

	// Grupo de botones
	private final ToggleOptionGroup buttonsGroup;

	// Lista de selecciones
	private final List<Render<? extends MovableDrawing>> selectedList = new ArrayList<>();

	// Start Point
	private Point startPoint;

	public PaintPane(CanvasState canvasState, StatusPane statusPane) {
		this.canvasState = canvasState;
		this.statusPane = statusPane;

		// Creamos el toolPanel de todos los botones
		ToolPanel toolPanel = new ToolPanel(10, canvasState, selectedList);

		// Escuchamos todos los eventos lanzados por el toolPanel
		this.addEventHandler(ActionEvent.ACTION, (event -> redrawCanvas()));

		buttonsGroup = toolPanel.getButtonsGroup();

		Canvas canvas = makeCanvas();
		gc = canvas.getGraphicsContext2D();

		setLeft(toolPanel);
		setRight(canvas);
	}

	private Canvas makeCanvas() {

		Canvas canvas = new Canvas(800, 600);

		canvas.setOnMousePressed(event -> {
			startPoint = new Point(event.getX(), event.getY());
			buttonsGroup.getSelectedOption().mousePressed(startPoint);
		});

		canvas.setOnMouseReleased(event -> {
			Point eventPoint = new Point(event.getX(), event.getY());
			if (startPoint == null)
				return;
			buttonsGroup.getSelectedOption().mouseReleased(eventPoint);
			redrawCanvas();
		});

		canvas.setOnMouseMoved(event -> {
			Point eventPoint = new Point(event.getX(), event.getY());
			updateInfoBarOnMove(eventPoint);
		});

		// Como este evento salta siempre que se hace un click(pressed y release) necesitamos saber si en el medio hubo un drag, por eso necesitamos el if.
		canvas.setOnMouseClicked(event -> {
			Point eventPoint = new Point(event.getX(), event.getY());

			if (eventPoint.distance(startPoint) > 1) // Ver de cambiarlo si es posible.
				buttonsGroup.getSelectedOption().mouseClickAndDrag(startPoint, eventPoint);
			else
				buttonsGroup.getSelectedOption().mouseClicked(eventPoint);
			updateInfoBarOnClick();
			redrawCanvas();
		});

		canvas.setOnMouseDragged(event -> {

			Point eventPoint = new Point(event.getX(), event.getY());
			buttonsGroup.getSelectedOption().mouseDragged(startPoint, eventPoint);
			redrawCanvas();
		});
		return canvas;
	}

	/**
	 *   Actualizamos la barra inferior al mover el mouse por fuera o por dentro de una figura.
	 */

	private void updateInfoBarOnMove(Point eventPoint) {
		for (Render<? extends MovableDrawing> renderFigure : canvasState.renderFigures()) {
			if (renderFigure.getFigure().pointBelongs(eventPoint)) {
				statusPane.updateStatus(renderFigure.getFigure().toString());
				return;
			}
		}
		statusPane.updateStatus(eventPoint.toString());
	}

	/**
	 *   Actualizamos la barra inferior al seleccionar una, varias figuras o ninguna.
	 */

	private void updateInfoBarOnClick() {
		if (!selectedList.isEmpty()) {
			StringBuilder label = new StringBuilder("Se selecciono: ");
			for (Render<? extends MovableDrawing> selectedDrawing : selectedList) {
				label.append(selectedDrawing.getFigure().toString()).append(", ");
			}
			label.setLength(label.length() - 2);
			statusPane.updateStatus(label.toString());
		} else {
			statusPane.updateStatus("Ninguna figura encontrada");
		}
	}

	private void redrawCanvas() {
		gc.clearRect(0, 0, this.getWidth(), this.getHeight());
		for (Render<? extends MovableDrawing> renderFigure : canvasState.renderFigures()) {
			gc.setFill(renderFigure.getStyle().getBgColor());
			gc.setLineWidth(renderFigure.getStyle().getStrokeWidth());
			if (selectedList.contains(renderFigure)){
				gc.setStroke(FigureStyle.lineColorSelected);
			}
			else{
				gc.setStroke(renderFigure.getStyle().getStrokeColor());
			}
			renderFigure.render(gc);
		}
	}
}