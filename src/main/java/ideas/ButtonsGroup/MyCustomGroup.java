package main.java.ideas.ButtonsGroup;

import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;


public class MyCustomGroup extends ToggleGroup {

    // Hay un problema, no puedo overradear getSelectedToggle de ToggleGroup porque es un método final
    public ToggleButton getSelectedButton(){
        Toggle toReturn = getSelectedToggle();
        return (ToggleButton) toReturn;
    }


}
