package main.java.frontend.Buttons;

import javafx.scene.control.ToggleButton;

public abstract class CustomButton extends ToggleButton {

    public CustomButton(String name){
        super(name);
    }

    public abstract boolean isRendered();

}