package main.java.frontend;

import main.java.backend.model.*;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import main.java.frontend.Buttons.ActionButton;
import main.java.frontend.Buttons.CustomButton;
import main.java.frontend.Buttons.RenderButton;
import main.java.frontend.Buttons.CustomButtonGroup;
import main.java.frontend.Renderers.LineRender;
import main.java.frontend.Renderers.RectangleRender;
import main.java.frontend.Renderers.Render;
import main.java.frontend.Renderers.RoundedFigureRender;

import java.util.ArrayList;
import java.util.List;


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
	//Rectangulo seleccionador
	private Rectangle imaginaryRect;

	// Seleccionar un dibujo
	private Render<MovableDrawing> selectedFigure;

	private List<Render<MovableDrawing>> selectedList = new ArrayList<>();

	// Grupo de botones
	private final CustomButtonGroup buttonsGroup = new CustomButtonGroup();

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

	private void updateInfoBar(boolean found, StatusPane statuspane, String foundString, String notFoundString){
		if (found) {
			statusPane.updateStatus(foundString);
		} else {
			statusPane.updateStatus(notFoundString);
		}
	}

	 */
	// Indica si estoy sobre una figura o sobre un punto random cuando muevo el mouse
	private void updateInfoBarOnMove(Point eventPoint, StatusPane statuspane, CanvasState canvasState){
		for (Render<MovableDrawing> renderFigure : canvasState.renderFigures()) {
			if (renderFigure.getFigure().pointBelongs(eventPoint)){
				statusPane.updateStatus(renderFigure.getFigure().toString());
				return;
			}
		}
		statusPane.updateStatus(eventPoint.toString());
	}

	private void updateInfoBarOnCLick(Point eventPoint, StatusPane statuspane, CanvasState canvasState){
		for (Render<MovableDrawing> renderFigure : canvasState.renderFigures()) {
			if (renderFigure.getFigure().pointBelongs(eventPoint)){
				selectedList.add(renderFigure);
				statusPane.updateStatus("Se selecciono: " + renderFigure.getFigure().toString());
				return;
			}
		}
		statusPane.updateStatus("Ninguna figura encontrada");
		selectedList.clear();
	}


	private void redrawCanvas() {
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		gc.setFill(fillColor);

		System.out.println(selectedList.size());
		for (Render<MovableDrawing> renderFigure : canvasState.renderFigures()) {
			if (selectedList.contains(renderFigure))
				gc.setStroke(Color.RED);
			else
				gc.setStroke(lineColor);
			renderFigure.render(gc);
		}
	}
	//Deberia ser una clase
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
			button.setMinHeight(20);
			button.setStyle("-fx-font-weight: bold;");
			button.setToggleGroup(buttonsGroup);
			button.setCursor(Cursor.HAND);
		}

		VBox buttonsBox = new VBox(10);

		buttonsBox.getChildren().addAll(buttonArr);
		buttonsBox.setPadding(new Insets(10));
		buttonsBox.setStyle("-fx-background-color: #414441");
		buttonsBox.setPrefWidth(120);

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

			if(selected != null && !selected.isRendered()) {
				// Estoy en seleccion multiple
				// Raro este IF
				/*
					if (!selectedList.isEmpty())
						return;
				 */
				System.out.println("si" + startPoint + endPoint);
				imaginaryRect = new Rectangle(startPoint, endPoint); // Lo esta tomando bien
				for(Render<MovableDrawing> render: canvasState.renderFigures()){
					if(render.getFigure().isContained(imaginaryRect)){
						selectedList.add(render);
						System.out.println(selectedList.size());
					}
				}
				redrawCanvas();
				return;
			}

			RenderButton<MovableDrawing> button = (RenderButton<MovableDrawing>) selected;

			// Falta preguntar que sea la linea.
			if ( endPoint.getX() < startPoint.getX() || endPoint.getY() < startPoint.getY())
				return;
			if (button != null)
				canvasState.addRenderFigure(button.createRenderer(startPoint, endPoint, fillColor ,lineColor ,1));
			startPoint = null;
			redrawCanvas();
		});

		/*

		canvas.setOnMouseMoved(event -> {
			Point eventPoint = new Point(event.getX(), event.getY());
			boolean found = false;
			StringBuilder label = new StringBuilder();

			for (Render<MovableDrawing> renderFigure : canvasState.renderFigures()) {
				if (renderFigure.getFigure().pointBelongs(eventPoint)) {
					found = true;
					label.append(renderFigure.getFigure().toString()); //recordar SACARLO DE ACA Y PONERLO EN EL METODO DE INFO
				}
			}
			updateInfoBar(found, statusPane, label.toString(), eventPoint.toString());
		});

		 */
		canvas.setOnMouseMoved(event -> {
			Point eventPoint = new Point(event.getX(), event.getY());
			updateInfoBarOnMove(eventPoint, statusPane, canvasState);
		});
		//Esta mal el primer if, lo dejamos para que corra.
		// Pressed and release
		/*
			Hago click,
				si hay elementos en el cuadrado imaginario:
					si presiono fuera del cuadrado, vacío la lista.
					si presiono dentro del cuadrado, no hago nada.

				si no hay elementos en la lista del cuadrado imaginario:
					si presiono en un lugar donde hay una figura, la agrego a la lista.
					sino, no hago nada

		 */
		/*
		canvas.setOnMouseClicked(event -> {
			if (buttonsGroup.getSelectedButton() != null && !buttonsGroup.getSelectedButton().isRendered()) {
				Point eventPoint = new Point(event.getX(), event.getY());
				boolean found = false;

				// Si tocaste afuera del rectangulo imaginario, vacio la lista.
				if(imaginaryRect != null && !imaginaryRect.pointBelongs(eventPoint)){
					selectedList.clear();
					imaginaryRect = null;
				}
				if(selectedList.isEmpty() || selectedList.size() == 1) {
					for (Render<MovableDrawing> renderFigure : canvasState.renderFigures()) {
						if (renderFigure.getFigure().pointBelongs(eventPoint)) {
							selectedList.add(renderFigure);
							found = true;
						}
					}
					updateInfoBarOnCLick(eventPoint, statusPane, canvasState);
					if (!found)
						selectedList.clear();
				}
				redrawCanvas();
			}
		});
		*/


		canvas.setOnMouseClicked(event -> {


			Point eventPoint = new Point(event.getX(), event.getY());
			if (eventPoint.distance(startPoint) > 10)
				return;
			System.out.println("CLICK");
			if (buttonsGroup.getSelectedButton() != null && !buttonsGroup.getSelectedButton().isRendered()) {

				// Si tocaste afuera del rectangulo imaginario, vacio la lista.
				if(imaginaryRect != null && !imaginaryRect.pointBelongs(eventPoint)){
					selectedList.clear();
					imaginaryRect = null;
				}

				if(selectedList.isEmpty() || selectedList.size() == 1) {
					updateInfoBarOnCLick(eventPoint, statusPane, canvasState);
				}
				redrawCanvas();
			}
		});

		// Esta mal el primer if, lo dejamos para que corra.
		canvas.setOnMouseDragged(event -> {
			if (buttonsGroup.getSelectedButton() != null && !buttonsGroup.getSelectedButton().isRendered()) {
				Point eventPoint = new Point(event.getX(), event.getY());
				if(!selectedList.isEmpty()) {
					double diffX = (eventPoint.getX() - startPoint.getX());
					double diffY = (eventPoint.getY() - startPoint.getY());
					for(Render<MovableDrawing> render : selectedList)
						render.getFigure().move(diffX, diffY);
					startPoint.setX(eventPoint.getX()) ;
					startPoint.setY(eventPoint.getY());
					redrawCanvas();
				}
			}
		});
		return canvas;
	}
}