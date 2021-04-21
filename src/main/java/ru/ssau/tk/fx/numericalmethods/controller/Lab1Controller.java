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
import org.mariuszgromada.math.mxparser.*;
import ru.ssau.tk.fx.numericalmethods.model.UnivariateFunction;


public class Lab1Controller {
    private double x;

    @FXML
    public TextField ExpressionField;

    @FXML
    public Label errorLabel;


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
        Methods methods = new Methods();
        String expression = ExpressionField.getText();
        Function function = new Function("f", new UnivariateFunction(expression));

        //метод половинного деления
        if (!leftBorderField.getText().equals("") && !rightBorderField.getText().equals("")) {
            double a = Double.parseDouble(leftBorderField.getText());
            double b = Double.parseDouble(rightBorderField.getText());

            double rootHD = methods.calculateHalfDivision(expression, a, b);

            numberOfIterationsHDLabel.setText(Integer.toString(methods.getIterationHalfDivision()));
            rootHDLabel.setText(String.format("%.4f", rootHD));
            discrepancyHDLabel.setText(String.format("%6.3e", function.calculate(rootHD)));
        }


        //Гибридный метод

        if (!initialValueField.getText().equals("")) {
            double xk = Double.parseDouble(initialValueField.getText());
            try {
                double rootHyb = methods.calculateHybridMethod(expression, xk);
                numberOfIterationsHybLabel.setText(Integer.toString(methods.getIterationHybridMethod()));
                rootHybLabel.setText(String.format("%.4f", rootHyb));
                discrepancyHybLabel.setText(String.format("%6.3e", function.calculate(rootHyb)));
                errorLabel.setText("");
            } catch (Exception e) {
                errorLabel.setText("Ошибка: деление на 0");
                numberOfIterationsHybLabel.setText("ошибка");
                rootHybLabel.setText("ошибка");
                discrepancyHybLabel.setText("ошибка");
            }

        }
    }


    @FXML
    public void buildPlot(ActionEvent actionEvent) {
        //clear
        lineChart.getData().clear();
        errorLabel.setText("");

        XYChart.Series series1 = new XYChart.Series();
        series1.setName("Plot 1");
        if (!ExpressionField.getText().equals("")) {
            String function = ExpressionField.getText();
            Argument x1 = new Argument("x = 0");
            Expression expression = new Expression(function, x1);
            if (expression.checkSyntax()) {
                for (int i = -500; i < 500; i++) {
                    x = i / 100.;
                    expression.setArgumentValue("x", x);
                    series1.getData().add(
                            new XYChart.Data<Number, Number>(x1.getArgumentValue(), expression.calculate()));
                }

                lineChart.getData().addAll(series1);
            } else {
                errorLabel.setText("Ошибка: некорректная функция");
            }
        }

    }
}
