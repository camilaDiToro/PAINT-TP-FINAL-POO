package main.java.frontend;

import main.java.backend.model.*;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import main.java.frontend.Buttons.*;
import main.java.frontend.Renderers.LineRender;
import main.java.frontend.Renderers.RectangleRender;
import main.java.frontend.Renderers.Render;
import main.java.frontend.Renderers.RoundedFigureRender;

import java.util.ArrayList;
import java.util.List;

class FigureValidation {
	public static Boolean ifOrdered(Point startPoint, Point eventPoint) {
		return (eventPoint.getX() >= startPoint.getX() && eventPoint.getY() >= startPoint.getY());
	}

	public static Boolean always(Point startPoint, Point eventPoint) {
		return true;
	}
}

public class PaintPane extends BorderPane {

	// BackEnd
	private final CanvasState canvasState;

	// Canvas y relacionados
	private final Canvas canvas;
	private final GraphicsContext gc;
	private final Color lineColorDefault = Color.BLACK;
	private final Color fillColorDefault = Color.YELLOW;
	private final Color lineColorSelected = Color.RED;
	private final int strokeWidthDefault = 2;
	private final FigureStyle currentFigureStyle = new FigureStyle(fillColorDefault, lineColorDefault,
			strokeWidthDefault);

	// Punto de comienzo del dibujo
	private Point startPoint;

	private List<Render<? extends MovableDrawing>> selectedList = new ArrayList<>();

	// Grupo de botones
	private final OptionGroup optionGroup = new OptionGroup();

	// StatusBar
	private final StatusPane statusPane;

	public PaintPane(CanvasState canvasState, StatusPane statusPane) {
		this.canvasState = canvasState;
		this.statusPane = statusPane;

		VBox buttonsBox = makeToolPanel();
		canvas = makeCanvas();
		gc = canvas.getGraphicsContext2D();

		setLeft(buttonsBox);
		setRight(canvas);

		// Sacable
		// gc.setLineWidth(1);
	}

	/*
	 * 
	 * private void updateInfoBar(boolean found, StatusPane statuspane, String
	 * foundString, String notFoundString){ if (found) {
	 * statusPane.updateStatus(foundString); } else {
	 * statusPane.updateStatus(notFoundString); } }
	 * 
	 */
	// Indica si estoy sobre una figura o sobre un punto random cuando muevo el
	// mouse
	private void updateInfoBarOnMove(Point eventPoint) {
		for (Render<? extends MovableDrawing> renderFigure : canvasState.renderFigures()) {
			if (renderFigure.getFigure().pointBelongs(eventPoint)) {
				statusPane.updateStatus(renderFigure.getFigure().toString());
				return;
			}
		}
		statusPane.updateStatus(eventPoint.toString());
	}

	private void updateInfoBarOnClick(Point eventPoint) {
		for (Render<? extends MovableDrawing> renderFigure : canvasState.renderFigures()) {
			if (renderFigure.getFigure().pointBelongs(eventPoint)) {
				statusPane.updateStatus("Se selecciono: " + renderFigure.getFigure().toString());
				return;
			}
		}
		statusPane.updateStatus("Ninguna figura encontrada");
	}

	private void redrawCanvas() {
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		gc.setFill(fillColorDefault);

		// System.out.println(selectedList.size());
		for (Render<? extends MovableDrawing> renderFigure : canvasState.renderFigures()) {
			if (selectedList.contains(renderFigure))
				gc.setStroke(lineColorSelected);
			else
				gc.setStroke(currentFigureStyle.getStrokeColor());
			renderFigure.render(gc);
		}
	}

	private void onCreateFigure(Render<? extends MovableDrawing> newRender) {
		canvasState.addRenderFigure(newRender);
	}

	// Deberia ser una clase
	private VBox makeToolPanel() {

		SelectOption selectionOption = new SelectOption(selectedList, canvasState.renderFigures(),
				this::updateInfoBarOnClick);
		optionGroup.addOption(selectionOption, "Seleccionar");

		FigureOption<Square> squareOption = new FigureOption<>(
				(TopLeft, BottomRight, colorBg, strokeColor,
						strokeWidth) -> new RectangleRender<>(new Square(TopLeft, BottomRight)),
				this::onCreateFigure, FigureValidation::ifOrdered,  currentFigureStyle);
		optionGroup.addOption(squareOption, "Cuadrado");

		FigureOption<Rectangle> rectangleOption = new FigureOption<>(
				(TopLeft, BottomRight, colorBg, strokeColor,
						strokeWidth) -> new RectangleRender<>(new Rectangle(TopLeft, BottomRight)),
				this::onCreateFigure, FigureValidation::ifOrdered, currentFigureStyle);
		optionGroup.addOption(rectangleOption, "Rectangulo");

		FigureOption<Circle> circleOption = new FigureOption<>(
				(TopLeft, BottomRight, colorBg, strokeColor,
						strokeWidth) -> new RoundedFigureRender<>(new Circle(TopLeft, BottomRight)),
				this::onCreateFigure, FigureValidation::ifOrdered, currentFigureStyle);
		optionGroup.addOption(circleOption, "Círculo");

		FigureOption<Ellipse> ellipseOption = new FigureOption<>(
				(TopLeft, BottomRight, colorBg, strokeColor,
						strokeWidth) -> new RoundedFigureRender<>(new Ellipse(TopLeft, BottomRight)),
				this::onCreateFigure, FigureValidation::ifOrdered, currentFigureStyle);
		optionGroup.addOption(ellipseOption, "Elipse");

		FigureOption<Line> lineOption = new FigureOption<>(
				(TopLeft, BottomRight, colorBg, strokeColor,
						strokeWidth) -> new LineRender(new Line(TopLeft, BottomRight)),
				this::onCreateFigure, FigureValidation::always, currentFigureStyle);
		optionGroup.addOption(lineOption, "Linea");

		optionGroup.setSelectedOption(selectionOption);

		return optionGroup.getBox();
	}

	private Canvas makeCanvas() {

		Canvas canvas = new Canvas(800, 600);

		canvas.setOnMousePressed(event -> {
			startPoint = new Point(event.getX(), event.getY());

			optionGroup.getSelectedOption().mousePressed(startPoint); // guardamos el startPoint
		});

		canvas.setOnMouseReleased(event -> {
			Point eventPoint = new Point(event.getX(), event.getY());

			if (startPoint == null)
				return;
			optionGroup.getSelectedOption().mouseReleased(eventPoint);
			redrawCanvas();
		});

		canvas.setOnMouseMoved(event -> {
			Point eventPoint = new Point(event.getX(), event.getY());
			updateInfoBarOnMove(eventPoint);
		});
		// Esta mal el primer if, lo dejamos para que corra.
		// Pressed and release

		canvas.setOnMouseClicked(event -> {

			Point eventPoint = new Point(event.getX(), event.getY());
			if (eventPoint.distance(startPoint) > 10)
				optionGroup.getSelectedOption().mouseClickAndDrag(startPoint, eventPoint);
			else
				optionGroup.getSelectedOption().mouseClicked(eventPoint);
			redrawCanvas();
		});

		// Esta mal el primer if, lo dejamos para que corra.
		canvas.setOnMouseDragged(event -> {
			Point eventPoint = new Point(event.getX(), event.getY());

			optionGroup.getSelectedOption().mouseDragged(startPoint, eventPoint);
			redrawCanvas();
		});
		return canvas;
	}
}