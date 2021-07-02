package main.java.frontend;

import main.java.backend.model.*;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import main.java.frontend.Buttons.ActionButton;
import main.java.frontend.Buttons.CustomButton;
import main.java.frontend.Buttons.RenderButton;
import main.java.frontend.Buttons.CustomButtonGroup;
import main.java.frontend.renderers.LineRender;
import main.java.frontend.renderers.RectangleRender;
import main.java.frontend.renderers.Render;
import main.java.frontend.renderers.RoundedFigureRender;


public class PaintPane extends BorderPane {

	// BackEnd
	private final CanvasState canvasState;

	// Canvas y relacionados
	private final Canvas canvas;
	private final GraphicsContext gc;
	private final Color lineColor = Color.BLACK;
	private final Color fillColor = Color.YELLOW;

	// Punto de comienzo del dibujo
	private Point startPoint;

	// Seleccionar un dibujo
	private Render<MovableDrawing> selectedFigure;

	// Grupo de botones
	private final CustomButtonGroup buttonsGroup = new CustomButtonGroup();

	// StatusBar
	private final StatusPane statusPane;

	public PaintPane(CanvasState canvasState, StatusPane statusPane) {
		this.canvasState = canvasState;
		this.statusPane = statusPane;
		canvas = makeCanvas();
		VBox buttonsBox = makeToolPanel();
		gc = canvas.getGraphicsContext2D();
		setLeft(buttonsBox);
		setRight(canvas);
		gc.setLineWidth(1);
	}

	private void updateInfoBar(boolean found, StatusPane statuspane, String foundString, String notFoundString){
		if (found) {
			statusPane.updateStatus(foundString);
		} else {
			statusPane.updateStatus(notFoundString);
		}
	}

	private void redrawCanvas() {
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

		for (Render<MovableDrawing> renderFigure : canvasState.renderFigures()) {
			gc.setFill(fillColor);
			gc.setStroke(lineColor);
			if (renderFigure.getFigure() == selectedFigure) // OJO con el ==
				gc.setStroke(Color.RED);
			renderFigure.render(gc);
		}
	}

	private VBox makeToolPanel() {

		RenderButton<Square> squareButton = new RenderButton<>((TopLeft, BottomRight, colorBg, strokeColor, strokeWidth) -> new RectangleRender<>(new Square(TopLeft, BottomRight)),"Cuadrado");
		RenderButton<Rectangle> rectangleButton = new RenderButton<>((TopLeft, BottomRight, colorBg, strokeColor, strokeWidth) -> new RectangleRender<>(new Rectangle(TopLeft, BottomRight)),"Rectangulo");
		RenderButton<Circle> circleButton = new RenderButton<>((TopLeft, BottomRight, colorBg, strokeColor, strokeWidth) -> new RoundedFigureRender<>(new Circle(TopLeft, BottomRight)),"Circulo");
		RenderButton<Ellipse> ellipseButton = new RenderButton<>((TopLeft, BottomRight, colorBg, strokeColor, strokeWidth) -> new RoundedFigureRender<>(new Ellipse(TopLeft, BottomRight)),"Elipse");
		RenderButton<Line> lineButton = new RenderButton<>((TopLeft, BottomRight, colorBg, strokeColor, strokeWidth) -> new LineRender(new Line(TopLeft, BottomRight)),"Linea");
		ActionButton selectionButton = new ActionButton("Seleccionar");
		CustomButton[] buttonArr = {selectionButton, rectangleButton, circleButton, ellipseButton, squareButton, lineButton};

		for (CustomButton button : buttonArr) {
			button.setMinWidth(90);
			button.setToggleGroup(buttonsGroup);
			button.setCursor(Cursor.HAND);
		}

		VBox buttonsBox = new VBox(10);

		buttonsBox.getChildren().addAll(buttonArr);
		buttonsBox.setPadding(new Insets(5));
		buttonsBox.setStyle("-fx-background-color: #999");
		buttonsBox.setPrefWidth(100);

		return buttonsBox;
	}

	private Canvas makeCanvas(){

		Canvas canvas = new Canvas(800, 600);

		canvas.setOnMousePressed(event -> {
			startPoint = new Point(event.getX(), event.getY());
		});

		canvas.setOnMouseReleased(event -> {
			Point endPoint = new Point(event.getX(), event.getY());

			if (startPoint == null)
				return;

			CustomButton selected = buttonsGroup.getSelectedButton();

			if(!selected.isRendered())
				return;

			RenderButton<MovableDrawing> button = (RenderButton<MovableDrawing>) selected;

			// Falta preguntar que sea la linea.
			if (endPoint.getX() < startPoint.getX() || endPoint.getY() < startPoint.getY())
				return;

			canvasState.addRenderFigure(button.createRenderer(startPoint, endPoint, fillColor ,lineColor ,1));
			startPoint = null;
			redrawCanvas();
		});

		canvas.setOnMouseMoved(event -> {
			Point eventPoint = new Point(event.getX(), event.getY());
			boolean found = false;
			StringBuilder label = new StringBuilder();

			for (Render<MovableDrawing> renderFigure : canvasState.renderFigures()) {
				if (renderFigure.getFigure().pointBelongs(eventPoint)) {
					found = true;
					label.append(renderFigure.getFigure().toString());
				}
			}
			updateInfoBar(found, statusPane, label.toString(), eventPoint.toString());
		});

		//Esta mal el primer if, lo dejamos para que corra.
		canvas.setOnMouseClicked(event -> {
			if (buttonsGroup.getSelectedButton().getText().equals("Seleccionar")) {
				Point eventPoint = new Point(event.getX(), event.getY());
				boolean found = false;

				for( Render<MovableDrawing> renderFigure: canvasState.renderFigures()){
					if(renderFigure.getFigure().pointBelongs(eventPoint)){
						selectedFigure = renderFigure;
						found = true;
					}
				}
				updateInfoBar(found,statusPane,"Se selecciono: " + (selectedFigure==null? "":selectedFigure.getFigure().toString()), "Ninguna figura encontrada");
				if(!found)
					selectedFigure = null;
				redrawCanvas();
			}
		});

		// Esta mal el primer if, lo dejamos para que corra.
		canvas.setOnMouseDragged(event -> {
			if (buttonsGroup.getSelectedButton().getText().equals("Seleccionar")) {
				Point eventPoint = new Point(event.getX(), event.getY());
				double diffX = (eventPoint.getX() - startPoint.getX()) / 100;
				double diffY = (eventPoint.getY() - startPoint.getY()) / 100;
				if(selectedFigure != null)
					selectedFigure.getFigure().move(diffX, diffY);
				redrawCanvas();
			}
		});

		return canvas;
	}
}
