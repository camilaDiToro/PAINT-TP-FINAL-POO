package main.java.frontend;

import javafx.scene.paint.Color;

public class FigureStyle {
    private Color bgColor;
    private Color strokeColor;
    private int strokeWidth;

    public FigureStyle(Color bgColor, Color strokeColor, int strokeWidth) {
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

    public void setStrokeWidth(int strokeWidth) {
        this.strokeWidth = strokeWidth;
    }

    public Color getBgColor() {
        return bgColor;
    }

    public Color getStrokeColor() {
        return strokeColor;
    }

    public int getStrokeWidth() {
        return strokeWidth;
    }
}
