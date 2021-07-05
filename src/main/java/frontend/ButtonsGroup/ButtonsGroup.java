package main.java.frontend.ButtonsGroup;

import javafx.collections.ListChangeListener;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import main.java.frontend.Buttons.ButtonsOption;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ButtonsGroup extends PersistentButtonToggleGroup {

    /* Asociamos a cada botó con su respectiva opcion ante las acciones ocurridas en el canvas. */
    private final Map<ToggleButton, ButtonsOption> buttonsMap = new HashMap<>();

    public void addButtonToGroup(ButtonsOption buttonsOption, String buttonText) {
        ToggleButton button = new ToggleButton(buttonText);
        button.setToggleGroup(this);
        buttonsMap.put(button, buttonsOption);
    }

    /**
     * Este metodo permite setear una opción seleccionada por default
     */
    public void setSelectedOption(ButtonsOption buttonsOption) {
        for (Map.Entry<ToggleButton, ButtonsOption> entry : buttonsMap.entrySet()) {
            if (entry.getValue().equals(buttonsOption)) {
                selectToggle(entry.getKey());
                return;
            }
        }
    }

    public ButtonsOption getSelectedOption() {
        return buttonsMap.get(getSelectedToggle());
    }


}
