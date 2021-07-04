package main.java.frontend;

import javafx.scene.paint.Color;

public class FigureStyle {

    // Selection color
    public static final Color lineColorSelected = Color.RED;
    public static final Color lineColorDefault = Color.BLACK;
    public static final Color fillColorDefault = Color.YELLOW;
    public static final double strokeWidthDefault = 2;

    private Color bgColor;
    private Color strokeColor;
    private double strokeWidth;

    public FigureStyle(Color bgColor, Color strokeColor, double strokeWidth) {
        this.bgColor = bgColor;
        this.strokeColor = strokeColor;
        this.strokeWidth = strokeWidth;
    }

    public void setBgColor(Color bgColor) {
        this.bgColor = bgColor;
    }

    public void setStrokeColor(Color strokeColor) {
        this.strokeColor = strokeColor;
    }

    public void setStrokeWidth(double strokeWidth) {
        this.strokeWidth = strokeWidth;
    }

    public Color getBgColor() {
        return bgColor;
    }

    public Color getStrokeColor() {
        return strokeColor;
    }

    public double getStrokeWidth() {
        return strokeWidth;
    }
}
