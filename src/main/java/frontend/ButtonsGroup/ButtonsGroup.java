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

public class ButtonsGroup {

    // Por qué no herencia en vez de composicion??
    private final ToggleGroup group = new PersistentButtonToggleGroup();

    private final Map<ToggleButton, ButtonsOption> buttonsMap = new HashMap<>();

    public void addButtonToGroup(ButtonsOption buttonsOption, String buttonText) {
        ToggleButton button = new ToggleButton(buttonText);
        button.setToggleGroup(group);
        buttonsMap.put(button, buttonsOption);
    }

    public void setSelectedOption(ButtonsOption buttonsOption) {
        for (Map.Entry<ToggleButton, ButtonsOption> entry : buttonsMap.entrySet()) {
            if (entry.getValue().equals(buttonsOption)) {
                group.selectToggle(entry.getKey());
                return;
            }
        }
    }

    public ButtonsOption getSelectedOption() {
        return buttonsMap.get(group.getSelectedToggle());
    }


    public Collection<ToggleButton> getToggles(){
        return buttonsMap.keySet();
    }

}
