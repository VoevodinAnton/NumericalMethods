package ru.ssau.tk.fx.numericalmethods.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.mariuszgromada.math.mxparser.Argument;
import org.mariuszgromada.math.mxparser.Expression;

public class Lab2Controller {
    @FXML
    public LineChart lineChart;
    @FXML
    public NumberAxis xAxis;
    @FXML
    public NumberAxis yAxis;
    @FXML
    public TextField firstFunctionField;
    @FXML
    public TextField secondFunctionField;
    @FXML
    public TextField xField;
    @FXML
    public TextField yField;
    @FXML
    public Button buildButton;

    @FXML
    public Button calculateButton;

    public void buildPlot(ActionEvent actionEvent) {
        XYChart.Series series1 = new XYChart.Series();
        XYChart.Series series2 = new XYChart.Series();
        series1.setName("Plot 1");
        series2.setName("Plot 2");
        double x;

        if (!firstFunctionField.getText().equals("") || !secondFunctionField.getText().equals("")){
            lineChart.getData().clear();

            String firstFunction = firstFunctionField.getText();
            String secondFunction = secondFunctionField.getText();
            Argument x1 = new Argument("x = 0");
            Expression firstExpression = new Expression(firstFunction, x1);
            Expression secondExpression = new Expression(secondFunction, x1);

            if (firstExpression.checkSyntax() && secondExpression.checkSyntax()){
                for (int i = -500; i < 500; i++) {
                    x = i / 100.;
                    firstExpression.setArgumentValue("x", x);
                    series1.getData().add(
                            new XYChart.Data<Number, Number>(x1.getArgumentValue(), firstExpression.calculate()));
                    secondExpression.setArgumentValue("x", x);
                    series2.getData().add(
                            new XYChart.Data<Number, Number>(x1.getArgumentValue(), secondExpression.calculate()));
                }

                lineChart.getData().addAll(series1, series2);
            }



        }

    }

    public void calculate(ActionEvent actionEvent) {
    }
}
