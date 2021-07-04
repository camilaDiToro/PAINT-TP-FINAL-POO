package main.java.frontend.DrawingArea;

import main.java.backend.*;
import main.java.backend.Point;
import main.java.frontend.CanvasState;
import main.java.frontend.DrawingArea.ToolPanel;
import main.java.frontend.FigureStyle;
import main.java.frontend.Renderers.*;
import main.java.frontend.ButtonsGroup.ButtonsGroup;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import main.java.frontend.StatusPane;
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
	private final ButtonsGroup buttonsGroup;

	// Lista de selecciones
	private final List<Render<? extends MovableDrawing>> selectedList = new ArrayList<>();

	// Start Point
	private Point startPoint;

	public PaintPane(CanvasState canvasState, StatusPane statusPane) {
		this.canvasState = canvasState;
		this.statusPane = statusPane;
		ToolPanel toolPanel = new ToolPanel(10, canvasState, selectedList);

		this.addEventHandler(ActionEvent.ACTION, (event -> redrawCanvas()));
		this.
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

	private void updateInfoBarOnMove(Point eventPoint) {
		for (Render<? extends MovableDrawing> renderFigure : canvasState.renderFigures()) {
			if (renderFigure.getFigure().pointBelongs(eventPoint)) {
				statusPane.updateStatus(renderFigure.getFigure().toString());
				return;
			}
		}
		statusPane.updateStatus(eventPoint.toString());
	}

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