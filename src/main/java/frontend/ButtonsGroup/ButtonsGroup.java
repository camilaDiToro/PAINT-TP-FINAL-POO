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

/* Suluciones para evitar el PersistentButtonToggleGroup:

    1)
    toggleGroup.selectedToggleProperty().addListener((obsVal, oldVal, newVal) -> {
    if (newVal == null)
        oldVal.setSelected(true);
    });
    link: https://stackoverflow.com/questions/46835087/prevent-a-toggle-group-from-not-having-a-toggle-selected-java-fx

 */

public class ButtonsGroup {

    private final ToggleGroup group = new PersistentButtonToggleGroup();

    private final Map<ToggleButton, ButtonsOption> buttonsMap = new HashMap<>();

    public void addButtonToGroup(ButtonsOption buttonsOption, String buttonText) {
        ToggleButton button = new ToggleButton(buttonText);
        button.setToggleGroup(group);
        button.setMinWidth(90);
        button.setMinHeight(20);
        button.setStyle("-fx-font-weight: bold;");
        button.setCursor(Cursor.HAND);
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
