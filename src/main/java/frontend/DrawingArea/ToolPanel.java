package main.java.frontend.DrawingArea;

import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.event.ActionEvent;
import main.java.backend.*;
import main.java.backend.Rectangle;
import main.java.frontend.App.CanvasState;
import main.java.frontend.Buttons.FigureButtons;
import main.java.frontend.Buttons.ToggleOptionGroup;
import main.java.frontend.Buttons.SelectButton;
import main.java.frontend.Buttons.ToggleOptionButton;
import main.java.frontend.Renderers.FigureStyle;
import main.java.frontend.Renderers.LineRender;
import main.java.frontend.Renderers.RectangleRender;
import main.java.frontend.Renderers.Render;
import main.java.frontend.Renderers.RoundedFigureRender;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.control.ColorPicker;
import java.util.List;

public class ToolPanel extends VBox{

    private final ToggleOptionGroup buttonsGroup = new ToggleOptionGroup();
    private final CanvasState canvasState;
    private final FigureStyle currentFigureStyle = new FigureStyle(FigureStyle.fillColorDefault, FigureStyle.lineColorDefault, FigureStyle.strokeWidthDefault);
    private final List<Render<? extends MovableDrawing>> selectedList;


    public ToolPanel(double spacing, CanvasState canvasState, List<Render<? extends MovableDrawing>> selectedList){

        super(spacing);
        this.canvasState = canvasState;
        this.selectedList = selectedList;

        setPadding(new Insets(10));
        setStyle("-fx-background-color: #D3D3D3");
        setPrefWidth(120);

        //--------------------------------Botones-------------------------------------------
        //Creamos los botones del Tool panel y los agregamos al ToolPanel
        createButtons();

        //--------------------------------Bordes--------------------------------------------
        Label border = new Label("Borde");
        getChildren().add(border);

        //Creamos el slider para definir el grosor de la linea y lo agregamos al ToolPanel
        createSlider();
        //Creamos el ColorPicker para definir el color de la linea y lo agregamos al ToolPanel
        createBorderColorPicker();

        //--------------------------------Relleno-------------------------------------------
        Label fill = new Label("Relleno");
        getChildren().add(fill);

        //Creamos el ColorPicker para definir el color de relleno y lo agregamos al ToolPanel
        createFillColorPicker();

    }

    private void createButtons(){
        SelectButton selectionOption = new SelectButton("Seleccionar", selectedList, canvasState.renderFigures());

        FigureButtons<Square> squareOption = new FigureButtons<>("Cuadrado", canvasState.renderFigures(),
                (TopLeft, BottomRight) -> new RectangleRender<>(new Square(TopLeft, BottomRight), currentFigureStyle));

        FigureButtons<Rectangle> rectangleOption = new FigureButtons<>("Rectangulo", canvasState.renderFigures(),
                (TopLeft, BottomRight) -> new RectangleRender<>(new Rectangle(TopLeft, BottomRight),currentFigureStyle));

        FigureButtons<Circle> circleOption = new FigureButtons<>("Círculo", canvasState.renderFigures(),
                (TopLeft, BottomRight) -> new RoundedFigureRender<>(new Circle(TopLeft, BottomRight),currentFigureStyle));

        FigureButtons<Ellipse> ellipseOption = new FigureButtons<>("Elipse", canvasState.renderFigures(),
                (TopLeft, BottomRight) -> new RoundedFigureRender<>(new Ellipse(TopLeft, BottomRight),currentFigureStyle));

        FigureButtons<Line> lineOption = new FigureButtons<>("Linea", canvasState.renderFigures(),
                (TopLeft, BottomRight) -> new LineRender(new Line(TopLeft, BottomRight), currentFigureStyle));

        ToggleOptionButton[] buttonArr = {selectionOption,squareOption,rectangleOption,circleOption,ellipseOption, lineOption};

        for(ToggleOptionButton b : buttonArr){
            b.setToggleGroup(buttonsGroup);
            getChildren().add(b);
            setButtonStyle(b);
        }

        //Este metodo permite setear una opción seleccionada por default.
        buttonsGroup.selectToggle(selectionOption);

        Button[] buttonArr2 = new Button[]{new Button("Borrar"),new Button("Al frente"),new Button("Al fondo")};

        for(Button button: buttonArr2){
            setButtonStyle(button);
            getChildren().add(button);
        }

        buttonArr2[0].setOnAction(this::deleteAction);
        buttonArr2[1].setOnAction(this::moveToFront);
        buttonArr2[2].setOnAction(this::moveToBack);

    }

    private void createSlider(){
        Slider slider = new Slider(1, 50, 5);
        slider.setShowTickMarks(true);
        slider.setShowTickLabels(true);
        slider.setMajorTickUnit(25);
        slider.minWidth(90);
        slider.minHeight(20);
        slider.setCursor(Cursor.HAND);
        slider.valueProperty().addListener((observableValue, number, t1) -> {
            double strokeWidth = slider.getValue();
            for(Render<? extends MovableDrawing> render: selectedList){
                render.setStrokeWidth(strokeWidth);
            }
            // Cuando se lanza este evento, lanzamos un aviso de ActionEvent para que lo detecte el PainPane
            ActionEvent.fireEvent(this,new ActionEvent());
            currentFigureStyle.setStrokeWidth(strokeWidth);
        });
        slider.getOnMouseClicked();
        getChildren().add(slider);
    }

    private void createBorderColorPicker(){
        ColorPicker colorPickerStroke = new ColorPicker(FigureStyle.lineColorDefault);
        colorPickerStroke.setOnAction((event) -> {
            Color color = colorPickerStroke.getValue();
            for(Render<? extends MovableDrawing> render: selectedList){
                render.setStrokeColor(color);
            }
            currentFigureStyle.setStrokeColor(color);
        });
        getChildren().add(colorPickerStroke);
    }

    private void createFillColorPicker(){
        ColorPicker colorPickerBg = new ColorPicker(FigureStyle.fillColorDefault);
        colorPickerBg.setOnAction((event) -> {
            Color color = colorPickerBg.getValue();
            for(Render<? extends MovableDrawing> render: selectedList){
                render.setBgColor(color);
            }
            currentFigureStyle.setBgColor(color);

        });
        getChildren().add(colorPickerBg);
    }


    public ToggleOptionGroup getButtonsGroup(){
        return buttonsGroup;
    }

    private void setButtonStyle(ButtonBase button){
        button.setMinWidth(90);
        button.setMinHeight(20);
        button.setStyle("-fx-font-weight: bold;");
        button.setCursor(Cursor.HAND);
    }
    
    private void deleteAction(ActionEvent actionEvent){
        canvasState.deleteRenderFigures(selectedList);
        selectedList.clear();
    }

    private void moveToFront(ActionEvent actionEvent){
        canvasState.moveToFront(selectedList);
    }
    
    private void moveToBack(ActionEvent actionEvent){
        canvasState.moveToBack(selectedList);
    }
}
