package ru.ssau.tk.fx.numericalmethods.controller;

import java.io.IOException;
import javafx.fxml.FXML;
import ru.ssau.tk.fx.numericalmethods.view.App;

public class SecondaryController {

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("/ru/ssau/tk/fx/numericalmethods/primary");
        System.out.println("2");
    }
}