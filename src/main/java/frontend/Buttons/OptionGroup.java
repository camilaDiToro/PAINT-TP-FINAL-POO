package main.java.frontend.Buttons;

import javafx.collections.ListChangeListener;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import main.java.backend.model.MovableDrawing;

import java.util.HashMap;
import java.util.Map;

// Magia de StackOverflow
class PersistentButtonToggleGroup extends ToggleGroup {
    PersistentButtonToggleGroup() {
        super();
        getToggles().addListener(new ListChangeListener<Toggle>() {
            @Override
            public void onChanged(Change<? extends Toggle> c) {
                while (c.next()) {
                    for (final Toggle addedToggle : c.getAddedSubList()) {
                        ((ToggleButton) addedToggle).addEventFilter(MouseEvent.MOUSE_RELEASED,
                                new EventHandler<MouseEvent>() {
                                    @Override
                                    public void handle(MouseEvent mouseEvent) {
                                        if (addedToggle.equals(getSelectedToggle()))
                                            mouseEvent.consume();
                                    }
                                });
                    }
                }
            }
        });
    }
}

public class OptionGroup {
    private ToggleGroup group = new PersistentButtonToggleGroup();
    private Map<Toggle, Option> optionsMap = new HashMap<>();
    private VBox buttonsBox = new VBox(10);

    public OptionGroup() {
        buttonsBox.setPadding(new Insets(10));
        buttonsBox.setStyle("-fx-background-color: #414441");
        buttonsBox.setPrefWidth(120);
    }

    public void addOption(Option option, String buttonText) {
        ToggleButton button = new ToggleButton(buttonText);

        button.setToggleGroup(group);
        button.setMinWidth(90);
        button.setMinHeight(20);
        button.setStyle("-fx-font-weight: bold;");
        button.setCursor(Cursor.HAND);
        buttonsBox.getChildren().add(button);

        optionsMap.put(button, option);
    }

    public void setSelectedOption(Option option) {
        for (Map.Entry<Toggle, Option> entry : optionsMap.entrySet()) {
            if (entry.getValue().equals(option)) {
                group.selectToggle(entry.getKey());
            }
        }
    }

    public Option getSelectedOption() {
        return optionsMap.get(group.getSelectedToggle());
    }

    public VBox getBox() {
        return buttonsBox;
    }
}
