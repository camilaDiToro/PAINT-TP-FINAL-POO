package main.java.frontend;

import main.java.backend.CanvasState;
import main.java.backend.model.Circle;
import main.java.backend.model.Figure;
import main.java.backend.model.Point;
import main.java.backend.model.Rectangle;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;



public class PaintPane extends BorderPane {

	// BackEnd
	private final CanvasState canvasState;

	// Canvas(rectangulo donde dibujo) y relacionados
	private final Canvas canvas = new Canvas(800, 600);
	private final GraphicsContext gc = canvas.getGraphicsContext2D();
	private final Color lineColor = Color.BLACK;
	private final Color fillColor = Color.YELLOW;

	// Botones Barra Izquierda
	private final ToggleButton selectionButton = new ToggleButton("Seleccionar");
	private final ToggleButton rectangleButton = new ToggleButton("Rectángulo");
	private final ToggleButton circleButton = new ToggleButton("Círculo");
	private final ToggleButton ellipseButton = new ToggleButton("Ellipse");
	private final ToggleButton squareButton = new ToggleButton("Cuadrado");

	// Dibujar una figura
	private Point startPoint;

	// Seleccionar una figura
	private Figure selectedFigure;

	// StatusBar
	private StatusPane statusPane;

	public PaintPane(CanvasState canvasState, StatusPane statusPane) {
		this.canvasState = canvasState;
		this.statusPane = statusPane;

		ToggleButton[] toolsArr = {selectionButton, rectangleButton, circleButton, ellipseButton, squareButton};
		ToggleGroup tools = new ToggleGroup();

		for (ToggleButton tool : toolsArr) {
			tool.setMinWidth(90);
			tool.setToggleGroup(tools);
			tool.setCursor(Cursor.HAND);
		}

		VBox buttonsBox = new VBox(10);

		buttonsBox.getChildren().addAll(toolsArr);
		buttonsBox.setPadding(new Insets(5));
		buttonsBox.setStyle("-fx-background-color: #999");
		buttonsBox.setPrefWidth(100);

		gc.setLineWidth(1);

		canvas.setOnMousePressed(event -> {
			startPoint = new Point(event.getX(), event.getY());
		});

		setLeft(buttonsBox);  // Cuando crea el VBOX, lo manda a izquierda
		setRight(canvas); // El canvas a la derec

		canvas.setOnMouseReleased(event -> {
			Point endPoint = new Point(event.getX(), event.getY());
			if (startPoint == null) {
				return;
			}

			// TIENE SENTIDO ??
			if (endPoint.getX() < startPoint.getX() || endPoint.getY() < startPoint.getY()) {
				return;
			}
			// ESTO DEFINITIVAMENTE NO TIENE SENTIDO
			Figure newFigure = null;

			// Muy PI.

			if (rectangleButton.isSelected()) {
				newFigure = new Rectangle(startPoint, endPoint);
			} else if (circleButton.isSelected()) {
				//
				double circleRadius = Math.abs(endPoint.getX() - startPoint.getX());
				newFigure = new Circle(startPoint, endPoint);
			} else {
				return;
			}

			canvasState.addFigure(newFigure);
			startPoint = null;
			redrawCanvas();
		});

		// SetOnMouseMoved == SetOnMouseClicked

		canvas.setOnMouseMoved(event -> {
			Point eventPoint = new Point(event.getX(), event.getY());
			boolean found = false;
			StringBuilder label = new StringBuilder();

			for (Figure figure : canvasState.figures()) {
				if (figure.pointBelongs(eventPoint)) {
					found = true;
					label.append(figure.toString());
				}
			}
			if (found) {
				statusPane.updateStatus(label.toString());
			} else {
				statusPane.updateStatus(eventPoint.toString());
			}
		});

		canvas.setOnMouseClicked(event -> {
			if (selectionButton.isSelected()) {
				Point eventPoint = new Point(event.getX(), event.getY());
				boolean found = false;
				StringBuilder label = new StringBuilder("Se seleccionó: ");

				// Mejorable este ciclo

				for (Figure figure : canvasState.figures()) {
					if (figure.pointBelongs(eventPoint)) {
						found = true;
						selectedFigure = figure;
						label.append(figure.toString());
					}
				}
				if (found) {
					statusPane.updateStatus(label.toString());
				} else {
					selectedFigure = null;
					statusPane.updateStatus("Ninguna figura encontrada");
				}
				redrawCanvas();
			}
		});

		canvas.setOnMouseDragged(event -> {
			if (selectionButton.isSelected()) {
				Point eventPoint = new Point(event.getX(), event.getY());
				double diffX = (eventPoint.getX() - startPoint.getX()) / 100;
				double diffY = (eventPoint.getY() - startPoint.getY()) / 100;
				try {
					selectedFigure.move(diffX, diffY);
				}catch(NullPointerException e){
					System.out.println("No seleccionaste una figura");  /* Esta linea de
					try catch la dejo por ahora porque cuando seleccionas afuera de una figura tira null pointer excep*/
				}
				redrawCanvas();
			}
		});

	}

	// Usa el width y el height del Rectangulo y usa los metodos fillRect (te llena el rectangulo) , storkeRect(hace el borde del rectangulo).

	void redrawCanvas() {
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight()); // ? Esta linea borra todo.
		for(Figure figure : canvasState.figures()) {
			// tiene sentido el if ? Estoy borrando todo el canvas, que selectedFigure puede haber?
			if(figure == selectedFigure) {
				gc.setStroke(Color.RED);
			} //Necesario el else?
			else {
				gc.setStroke(lineColor);
			}
			gc.setFill(fillColor); // ! deberia ir fuera del for, establece de que color se rellena en el canvas
			// figure.draw(gc);
			if(figure instanceof Rectangle) {
				Rectangle rectangle = (Rectangle) figure;
				gc.fillRect(rectangle.getTopLeft().getX(), rectangle.getTopLeft().getY(),
						Math.abs(rectangle.getTopLeft().getX() - rectangle.getBottomRight().getX()), Math.abs(rectangle.getTopLeft().getY() - rectangle.getBottomRight().getY()));
				gc.strokeRect(rectangle.getTopLeft().getX(), rectangle.getTopLeft().getY(),
						Math.abs(rectangle.getTopLeft().getX() - rectangle.getBottomRight().getX()), Math.abs(rectangle.getTopLeft().getY() - rectangle.getBottomRight().getY()));
			} else if(figure instanceof Circle) {
				Circle circle = (Circle) figure;
				double diameter = circle.getHeight();
				gc.fillOval(circle.getTopLeft().getX(), circle.getTopLeft().getY(), diameter, diameter);
				gc.strokeOval(circle.getTopLeft().getX(), circle.getTopLeft().getY(), diameter, diameter);
			}
		}
	}
}
