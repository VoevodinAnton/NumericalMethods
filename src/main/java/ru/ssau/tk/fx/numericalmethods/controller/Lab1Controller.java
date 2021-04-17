package ru.ssau.tk.fx.numericalmethods.controller;

import javafx.beans.binding.DoubleExpression;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import org.apache.commons.math3.analysis.differentiation.DerivativeStructure;
import org.apache.commons.math3.analysis.function.Constant;
import ru.ssau.tk.fx.numericalmethods.model.FirstLabFunction;
import org.mariuszgromada.math.mxparser.*;


public class Lab1Controller {


    private double x;
    private FirstLabFunction f = new FirstLabFunction();
    private final double epsilon = 0.0001;
    private final double delta = 0.0001;
    private double rootHD;
    private double rootHyb;


    @FXML
    public ImageView refreshButton;

    @FXML
    public Button calculateButton;

    @FXML
    public TextField rightBorderField;

    @FXML
    public TextField initialValueField;

    @FXML
    public Label numberOfIterationsHybLabel;


    @FXML
    public Label rootHDLabel;

    @FXML
    public Label discrepancyHDLabel;

    @FXML
    public Label rootHybLabel;

    @FXML
    public Label discrepancyHybLabel;

    @FXML
    public Label numberOfIterationsHDLabel;

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
    public void refresh(){

    }

    @FXML
    public void calculate(ActionEvent actionEvent) {
        double a = Double.parseDouble(leftBorderField.getText());
        double b = Double.parseDouble(rightBorderField.getText());
        double c;

        //метод половинного деления
        int i = 0;
        while (true) {
            c = (a + b) / 2;
            if (Math.abs(b - a) < 2 * epsilon) {
                rootHD = c;
                break;
            }
            if (Math.abs(f.value(c)) < delta) {
                rootHD = c;
                break;
            }
            if (f.value(a) * f.value(c) < 0) {
                b = c;
            } else {
                a = c;
            }
            i++;
        }
        rootHDLabel.setText(Double.toString(rootHD));
        numberOfIterationsHDLabel.setText(Integer.toString(i));
        discrepancyHDLabel.setText(Double.toString(f.value(rootHD)));

        //Гибридный метод
        int params = 1;
        int order = 1;

        double xk = Double.parseDouble(initialValueField.getText());
        double xkNew;
        int j = 0;
        while (true) {
            DerivativeStructure x = new DerivativeStructure(params, order, 0, xk);
            DerivativeStructure y = f.value(x);
            xkNew = xk - y.getValue() / y.getPartialDerivative(1);

            if (Math.abs(xkNew - xk) < 2 * epsilon) {
                rootHyb = xkNew;
                break;
            }
            if (Math.abs(f.value(xkNew)) < delta) {
                rootHyb = xkNew;
                break;
            }

            while (true){
                if (Math.abs(f.value(xkNew)) < Math.abs(f.value(xk))) {
                    xk = xkNew;
                    break;
                } else {
                    xkNew = 1 / 2. * (xk + xkNew);
                }

            }
            j++;
        }
        numberOfIterationsHybLabel.setText(Integer.toString(j));
        rootHybLabel.setText(Double.toString(rootHyb));
        discrepancyHybLabel.setText(Double.toString(f.value(rootHyb)));

    }


    @FXML
    public void buildPlot(ActionEvent actionEvent) {
        XYChart.Series series1 = new XYChart.Series();
        series1.setName("Plot 1");

        for (int i = -500; i < 500; i++) {
            x = i / 100.;
            series1.getData().add(
                    new XYChart.Data<Number, Number>(x, f.value(x)));
        }
        lineChart.getData().addAll(series1);
    }
}
