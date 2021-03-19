package ru.ssau.tk.fx.numericalmethods.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class Controller {
    @FXML
    public Button fxButton;

    @FXML
    private void click(){
        System.out.println("hello!");
        fxButton.setText("hello");
    }
}
