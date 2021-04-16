module ru.ssau.tk.fx.numericalmethods {
    requires javafx.controls;
    requires javafx.fxml;
    requires commons.math3;

    opens ru.ssau.tk.fx.numericalmethods.controller to javafx.fxml;
    exports ru.ssau.tk.fx.numericalmethods.view;
}