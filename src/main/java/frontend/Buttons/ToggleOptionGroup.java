package main.java.frontend.Buttons;

import javafx.collections.ListChangeListener;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;

/**
 *  Modela un grupo de ToggleButtons. En el constructor se garantiza
 *  que siempre haya una opcion seleccionada.
 *
 */

public class ToggleOptionGroup extends ToggleGroup {

    public ToggleOptionGroup() {
        super();

        //se garantiza que siempre haya una opcion seleccionada.
        getToggles().addListener((ListChangeListener<Toggle>) button -> {
            while (button.next()) {
                for (final Toggle addedToggle : button.getAddedSubList()) {
                    ((ToggleButton) addedToggle).addEventFilter(MouseEvent.MOUSE_RELEASED,
                            mouseEvent -> {
                                if (addedToggle.equals(getSelectedToggle()))
                                    mouseEvent.consume();
                            });
                }
            }
        });
    }

    public ToggleOptionButton getSelectedOption() {
        return (ToggleOptionButton) getSelectedToggle();
    }

}
