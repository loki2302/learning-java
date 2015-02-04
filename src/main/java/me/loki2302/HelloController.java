package me.loki2302;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class HelloController {
    @FXML private Button btn;

    @FXML void handleBtnClick(ActionEvent e) {
        System.out.println("Hello World!");
    }

}
