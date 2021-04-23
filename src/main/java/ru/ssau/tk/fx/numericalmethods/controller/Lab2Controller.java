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
        double x;

        if (!firstFunctionField.getText().equals("") || !secondFunctionField.getText().equals("")) {


            String firstFunction = firstFunctionField.getText();
            String[] firstStrings = firstFunction.split("\\s*,\\s*");
            String domainOfTheFirstFunction = "-20, 20";

            String rx = "\\[(.*?)]";
            Pattern ptrn = Pattern.compile(rx);

            if (firstStrings.length > 1) {

                Matcher mFirst = ptrn.matcher(firstStrings[1]);
                if (mFirst.find()) {
                    domainOfTheFirstFunction = mFirst.group(1);
                    domainOfTheFirstFunction = domainOfTheFirstFunction.replaceAll(";", ",");
                }
            }


            String secondFunction = secondFunctionField.getText();
            String[] secondStrings = secondFunction.split("\\s*,\\s*");
            String domainOfTheSecondFunction = "-20, 20 ";

            if (secondStrings.length > 1) {
                Matcher mSecond = ptrn.matcher(secondStrings[1]);
                if (mSecond.find()) {
                    domainOfTheSecondFunction = mSecond.group(1);
                    domainOfTheSecondFunction = domainOfTheSecondFunction.replaceAll(";", ",");
                }
            }


            Argument xValue = new Argument("x = 0");
            Expression firstExpression = new Expression("solve(" + firstStrings[0] + ", y," + domainOfTheFirstFunction + ")", xValue);
            Expression secondExpression = new Expression("solve(" + secondStrings[0] + ", y," + domainOfTheSecondFunction + ")", xValue);

            if (firstExpression.checkSyntax() && secondExpression.checkSyntax()) {
                for (int i = -500; i < 500; i++) {
                    x = i / 100.;
                    firstExpression.setArgumentValue("x", x);
                    secondExpression.setArgumentValue("x", x);
                    if (!Double.isNaN(firstExpression.calculate()) && !Double.isNaN(secondExpression.calculate())) {

                        series1.getData().add(
                                new XYChart.Data<Number, Number>(xValue.getArgumentValue(), firstExpression.calculate()));

                        series2.getData().add(
                                new XYChart.Data<Number, Number>(xValue.getArgumentValue(), secondExpression.calculate()));
                    }

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
        String firstFunction = f1.split("\\s*,\\s*")[0];

        String f2 = secondFunctionField.getText();
        String secondFunction = f2.split("\\s*,\\s*")[0];
        double valueX = Double.parseDouble(xField.getText());
        double valueY = Double.parseDouble(yField.getText());
        Methods methods = new Methods();


        //Newton method
        try {
            RealVector rootNewtonMethodVector = methods.calculateNewtonMethod(firstFunction, secondFunction, valueX, valueY);
            numberOfNewtonMethodIterationsLabel.setText(Integer.toString(methods.getIterationNewtonMethod()));
            rootNewtonMethodLabel.setText("(" + String.format("%.4f", rootNewtonMethodVector.getEntry(0)) + ", " + String.format("%.4f", rootNewtonMethodVector.getEntry(1)) + ")");
            discrepancyNewtonMethodLabel.setText("f_1(x,y) = " + String.format("%6.3e", methods.getDiscrepancy(firstFunction, rootNewtonMethodVector.getEntry(0), rootNewtonMethodVector.getEntry(1))) + "\n" +
                    "f_2(x,y) = " + String.format("%6.3e", methods.getDiscrepancy(secondFunction, rootNewtonMethodVector.getEntry(0), rootNewtonMethodVector.getEntry(1))));
        } catch (IllegalArgumentException e) {
            errorLabel.setText("Метод Ньютона расходится");
            numberOfNewtonMethodIterationsLabel.setText("");
            rootNewtonMethodLabel.setText("");
            discrepancyNewtonMethodLabel.setText("");
        } catch (Exception e) {
            errorLabel.setText("Ошибка: неккоректная система уравнений");
        }


        //Modified Newton Method
        try {
            RealVector rootModifiedNewtonMethodVector = methods.calculateModifiedNewtonMethod(firstFunction, secondFunction, valueX, valueY);
            numberOfModifiedNewtonMethodIterationsLabel.setText(Integer.toString(methods.getIterationModifiedNewtonMethod()));
            rootModifiedNewtonMethodLabel.setText("(" + String.format("%.4f", rootModifiedNewtonMethodVector.getEntry(0)) + ", " + String.format("%.4f", rootModifiedNewtonMethodVector.getEntry(1)) + ")");
            discrepancyModifiedNewtonMethodLabel.setText("f_1(x,y) = " + String.format("%6.3e", methods.getDiscrepancy(firstFunction, rootModifiedNewtonMethodVector.getEntry(0), rootModifiedNewtonMethodVector.getEntry(1))) + "\n" +
                    "f_2(x,y) = " + String.format("%6.3e", methods.getDiscrepancy(secondFunction, rootModifiedNewtonMethodVector.getEntry(0), rootModifiedNewtonMethodVector.getEntry(1))));
        } catch (IllegalArgumentException e) {
            errorLabel.setText("Метод Ньютона(М) расходится");
            numberOfModifiedNewtonMethodIterationsLabel.setText("");
            rootModifiedNewtonMethodLabel.setText("");
            discrepancyModifiedNewtonMethodLabel.setText("");
        } catch (Exception e) {
            errorLabel.setText("Ошибка: неккоректная система уравнений");
        }


    }


}
