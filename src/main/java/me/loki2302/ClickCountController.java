package me.loki2302;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class ClickCountController {
    private IntegerProperty clickCountProperty = new SimpleIntegerProperty(0);

    @FXML
    public void addOneClicked(ActionEvent e) {
        System.out.println("Button clicked!");
        setClickCount(getClickCount() + 1);
    }

    public void setClickCount(int value) {
        clickCountProperty.set(value);
    }

    public int getClickCount() {
        return clickCountProperty.get();
    }

    public IntegerProperty clickCountProperty() {
        return clickCountProperty;
    }
}
