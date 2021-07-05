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

public class OptionButtonGroup extends ToggleGroup {

    public OptionButtonGroup() {
        super();
        getToggles().addListener((ListChangeListener<Toggle>) c -> {
            while (c.next()) {
                for (final Toggle addedToggle : c.getAddedSubList()) {
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
