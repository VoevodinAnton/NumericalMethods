package ru.ssau.tk.fx.numericalmethods.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import ru.ssau.tk.fx.numericalmethods.model.FirstLabFunction;



public class Lab1Controller {
    private double x;
    private FirstLabFunction y = new FirstLabFunction();
    private final double epsilon = 0.0001;
    private final double delta = 0.0001;
    private double root;


    @FXML
    public TextField rightBorderField;

    @FXML
    public TextField initialValueField;

    @FXML
    public Label numberOfIterationsHyb;


    @FXML
    public Label rootHD;

    @FXML
    public Label discrepancyHD;

    @FXML
    public Label rootHyb;

    @FXML
    public Label discrepancyHyb;

    @FXML
    public Label numberOfIterationsHD;

    @FXML
    public NumberAxis xAxis = new NumberAxis();

    @FXML
    public NumberAxis yAxis = new NumberAxis();

    @FXML
    public javafx.scene.chart.LineChart<Number, Number> lineChart = new LineChart(xAxis, yAxis);

    @FXML
    public Button buildButton;

    @FXML
    public TextField leftBorderField;

    @FXML
    public void calculate(ActionEvent actionEvent) {
        Double a = Double.parseDouble(leftBorderField.getText());
        Double b = Double.parseDouble(rightBorderField.getText());
        double c;

        int i = 0;
        while (true) {
            c = (a + b) / 2;
            if (Math.abs(b - a) < 2 * epsilon) {
                root = c;
                break;
            }
            if (Math.abs(y.apply(c)) < delta) {
                root = c;
                break;
            }
            if (y.apply(a) * y.apply(c) < 0){
                b = c;
            } else {
                a = c;
            }
            i++;
        }

        rootHD.setText(Double.toString(root));
        numberOfIterationsHD.setText(Integer.toString(i));
        discrepancyHD.setText(Double.toString(y.apply(root)));

    }


    @FXML
    public void buildPlot(ActionEvent actionEvent) {
        rootHyb.setText("hello");
        XYChart.Series series1 = new XYChart.Series();
        series1.setName("Plot 1");

        for (int i = -500; i < 500; i++) {
            x = i / 100.;
            series1.getData().add(
                    new XYChart.Data<Number, Number>(x, y.apply(x)));
        }
        lineChart.getData().addAll(series1);
        lineChart.setVisible(true);
    }
}
