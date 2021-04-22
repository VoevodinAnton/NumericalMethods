package ru.ssau.tk.fx.numericalmethods.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.apache.commons.math3.linear.*;
import org.mariuszgromada.math.mxparser.Argument;
import org.mariuszgromada.math.mxparser.Expression;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


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
    @FXML
    public Label numberOfModifiedNewtonMethodIterationsLabel;
    @FXML
    public Label numberOfNewtonMethodIterationsLabel;
    @FXML
    public Label rootModifiedNewtonMethodLabel;
    @FXML
    public Label discrepancyModifiedNewtonMethodLabel;
    @FXML
    public Label discrepancyNewtonMethodLabel;
    @FXML
    public Label rootNewtonMethodLabel;
    @FXML
    public Label errorLabel;


    public void buildPlot(ActionEvent actionEvent) { //TODO: сделать введение функции в поле с функцией с двумя переменными
        //clear
        lineChart.getData().clear();
        errorLabel.setText("");

        XYChart.Series series1 = new XYChart.Series();
        XYChart.Series series2 = new XYChart.Series();
        series1.setName("Plot 1");
        series2.setName("Plot 2");
        double x = 0;

        if (!firstFunctionField.getText().equals("") || !secondFunctionField.getText().equals("")) {


            String firstFunction = firstFunctionField.getText();
            String[] firstStrings = firstFunction.split("\\s*,\\s*");
            String domainOfTheFirstFunction = "-20 , 20 ";
            String rx = "\\[(.*?)\\]";
            Pattern ptrn = Pattern.compile(rx);
            Matcher m = ptrn.matcher(firstStrings[1]);
            if (m.find()){
                domainOfTheFirstFunction = m.group(1);
            }

            String secondFunction = secondFunctionField.getText();
            String[] secondStrings = secondFunction.split("\\s*,\\s*");
            Argument xValue = new Argument("x = 0");
            Expression firstExpression = new Expression("solve(" + firstFunction + ", y," + domainOfTheFirstFunction + ")", xValue);
            Expression secondExpression = new Expression("solve(" + secondFunction + ", y, -100, 100)", xValue);

            if (firstExpression.checkSyntax() && secondExpression.checkSyntax()) {
                for (int i = -50; i < 50; i++) {
                    x = i / 10.;
                    firstExpression.setArgumentValue("x", x);
                    series1.getData().add(
                            new XYChart.Data<Number, Number>(xValue.getArgumentValue(), firstExpression.calculate()));
                    secondExpression.setArgumentValue("x", x);
                    series2.getData().add(
                            new XYChart.Data<Number, Number>(xValue.getArgumentValue(), secondExpression.calculate()));
                }

                lineChart.getData().addAll(series1, series2);
            } else {
                errorLabel.setText("Ошибка: некорректная функция");
            }


        } else {
            errorLabel.setText("Введите функцию");
        }

    }

    public void calculate(ActionEvent actionEvent) {
        //clear
        errorLabel.setText("");


        String f1 = firstFunctionField.getText();
        String f2 = secondFunctionField.getText();
        double valueX = Double.parseDouble(xField.getText());
        double valueY = Double.parseDouble(yField.getText());
        Methods methods = new Methods();
        try {
            RealVector rootVector = methods.calculateNewtonMethod(f1, f2, valueX, valueY);

        } catch (Exception e){
            errorLabel.setText("Ошибка: неккоректная система уравнений");
        }

    }


}
