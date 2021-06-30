package main.java.ideas;

import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import main.java.backend.CanvasState;
import main.java.backend.model.Circle;
import main.java.backend.model.Figure;
import main.java.backend.model.Point;
import main.java.backend.model.Rectangle;
import main.java.ideas.Buttons.*;
import main.java.frontend.StatusPane;
import main.java.ideas.ButtonsGroup.MyCustomGroup;


public class PaintPaneV2 extends BorderPane {

	// BackEnd
	private final CanvasState canvasState;

	// Canvas(rectangulo donde dibujo) y relacionados
	private final Canvas canvas = new Canvas(800, 600);
	private final GraphicsContext gc = canvas.getGraphicsContext2D();
	private final Color lineColor = Color.BLACK;
	private final Color fillColor = Color.YELLOW;

	// Dibujar una figura
	private Point startPoint;

	// Seleccionar una figura
	private Figure selectedFigure;

	// StatusBar
	private StatusPane statusPane;

	public PaintPaneV2(CanvasState canvasState, StatusPane statusPane) {
		this.canvasState = canvasState;
		this.statusPane = statusPane;

		MyCustomGroup tools = new MyCustomGroup();
		ToggleButton[] toolsArr = createAndGroupButtons(tools);

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

			ToggleButton button =  tools.getSelectedButton();

			if(button == null || button.getText().equals("Seleccionar"))
				return;

			MyToggleButton myButton = (MyToggleButton) button;
			Figure newFigure = myButton.generateFigure(startPoint, endPoint);

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
			if (tools.getSelectedButton().getText().equals("Seleccionar")) {
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
			if (tools.getSelectedButton().getText().equals("Seleccionar")) {
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

	private ToggleButton[] createAndGroupButtons(MyCustomGroup tools) {

		// El primer boton es de un tipo de dato distinto al del resto, no me gusta
		ToggleButton selectionButton = new ToggleButton("Seleccionar");
		MyToggleButton rectangleButton = new MyRectangleButton("Rectángulo");
		MyToggleButton circleButton = new MyCircleButton("Círculo");
		MyToggleButton ellipseButton = new MyEllipseButton("Ellipse");
		MyToggleButton squareButton = new MySquareButton("Cuadrado");

		ToggleButton[] toolsArr = {selectionButton, rectangleButton, circleButton, ellipseButton, squareButton};

		for (ToggleButton tool : toolsArr) {
			tool.setMinWidth(90);
			tool.setToggleGroup(tools);
			tool.setCursor(Cursor.HAND);
		}

		return toolsArr;
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
