package ru.ssau.tk.fx.numericalmethods.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import ru.ssau.tk.fx.numericalmethods.model.DerivativeUnivariateFunction;
import org.mariuszgromada.math.mxparser.*;
import ru.ssau.tk.fx.numericalmethods.model.UnivariateFunction;


public class Lab1Controller {


    @FXML
    public TextField ExpressionField;
    private double x;
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
    public void refresh() {

    }

    @FXML
    public void calculate(ActionEvent actionEvent) {
        String expression = ExpressionField.getText();
        double a = Double.parseDouble(leftBorderField.getText());
        double b = Double.parseDouble(rightBorderField.getText());
        double c;
        Function function = new Function("f", new UnivariateFunction(expression));
        Function functionD = new Function("f", new DerivativeUnivariateFunction(expression));

        //метод половинного деления
        int i = 0;
        while (true) {
            c = (a + b) / 2;
            if (Math.abs(b - a) < 2 * epsilon) {
                rootHD = c;
                break;
            }
            if (Math.abs(function.calculate(c)) < delta) {
                rootHD = c;
                break;
            }
            if (function.calculate(a) * function.calculate(c) < 0) {
                b = c;
            } else {
                a = c;
            }
            i++;
        }
        numberOfIterationsHDLabel.setText(Integer.toString(i));
        rootHDLabel.setText(String.format("%.4f", rootHD));
        discrepancyHDLabel.setText(String.format("%6.3e",function.calculate(rootHD)));

        //Гибридный метод

        double xk = Double.parseDouble(initialValueField.getText());
        double xkNew;
        int j = 0;
        while (true) {
            xkNew = xk - function.calculate(xk) / functionD.calculate(xk);

            if (Math.abs(xkNew - xk) < 2 * epsilon) {
                rootHyb = xkNew;
                break;
            }
            if (Math.abs(function.calculate(xkNew)) < delta) {
                rootHyb = xkNew;
                break;
            }

            while (true) {
                if (Math.abs(function.calculate(xkNew)) < Math.abs(function.calculate(xk))) {
                    xk = xkNew;
                    break;
                } else {
                    xkNew = 1 / 2. * (xk + xkNew);
                }
            }
            j++;
        }


        numberOfIterationsHybLabel.setText(Integer.toString(j));
        rootHybLabel.setText(String.format("%.4f", rootHyb));
        discrepancyHybLabel.setText(String.format("%6.3e", function.calculate(rootHyb)));

    }


    @FXML
    public void buildPlot(ActionEvent actionEvent) {
        XYChart.Series series1 = new XYChart.Series();
        series1.setName("Plot 1");
        String function = ExpressionField.getText();
        Argument x1 = new Argument("x = 0");
        Expression e = new Expression(function, x1);
        for (int i = -500; i < 500; i++) {
            x = i / 100.;
            e.setArgumentValue("x", x);
            series1.getData().add(
                    new XYChart.Data<Number, Number>(x1.getArgumentValue(), e.calculate()));
        }
        lineChart.getData().addAll(series1);
    }
}
