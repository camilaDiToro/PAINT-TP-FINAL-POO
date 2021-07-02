package main.java.frontend.Buttons;

import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import main.java.backend.model.MovableDrawing;


public class CustomButtonGroup extends ToggleGroup {

    @SuppressWarnings("unchecked")
    public CustomButton getSelectedButton(){
        Toggle toReturn = getSelectedToggle();
        return (CustomButton) toReturn;
    }



}
