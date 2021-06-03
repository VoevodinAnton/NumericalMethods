package ru.ssau.tk.fx.numericalmethods.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import org.apache.commons.math3.analysis.polynomials.PolynomialSplineFunction;
import org.apache.commons.math3.util.FastMath;
import ru.ssau.tk.fx.numericalmethods.model.MyFunction;

public class Lab3Controller {
    Methods methods;
    MyFunction myFunction;
    double a = 0;
    double b = 10;
    XYChart.Series series1;
    XYChart.Series series2;
    XYChart.Series series3;
    XYChart.Series series4;
    XYChart.Series series5;
    XYChart.Series series6;
    XYChart.Series series7;
    XYChart.Series series8;
    XYChart.Series series9;
    XYChart.Series series11;
    @FXML
    public LineChart lineChartOne;
    @FXML
    public NumberAxis xAxisOne;
    @FXML
    public NumberAxis yAxisOne;
    @FXML
    public LineChart lineChartTwo;
    @FXML
    public NumberAxis xAxisTwo;
    @FXML
    public NumberAxis yAxisTwo;
    @FXML
    public Button calculateButton;

    {
        methods = new Methods();
        myFunction = new MyFunction();

        series1 = new XYChart.Series();
        series2 = new XYChart.Series();
        series3 = new XYChart.Series();
        series4 = new XYChart.Series();
        series5 = new XYChart.Series();
        series6 = new XYChart.Series();
        series7 = new XYChart.Series();
        series8 = new XYChart.Series();
        series9 = new XYChart.Series();
        series11 = new XYChart.Series();

        series1.setName("Plot 1");
        series2.setName("Plot 2");
        series3.setName("Plot 3");
        series4.setName("Plot 4");
        series5.setName("Plot 5");
        series6.setName("Plot 6");
        series7.setName("Plot 7");
        series8.setName("Plot 8");
        series9.setName("Plot 9");
        series11.setName("Plot 11");


    }


    public void calculate(ActionEvent actionEvent) {

        double h = methods.defineAStep(3, 0, 10);
        System.out.println("Шаг = " + h);
        double[] xArray = methods.descretizeX(a, b, h);
        double[] yArray = methods.descretizeY(a, b, h);
        double[] xLagrange = new double[4];
        double[] yLagrange = new double[4];
        for (int i = 0; i < 4; i++){
            //TODO: сделать так, чтобы шаг вводил пользователь
            xLagrange[i] = xArray[i];
            yLagrange[i] = yArray[i];
        }



        for (double x = 0; x <  3*h; x += 0.01) {
            series1.getData().add(
                    new XYChart.Data<Number, Number>(x, myFunction.value(x)));
            series2.getData().add(
                    new XYChart.Data<Number, Number>(x, methods.interpolateWithLagrangePolynomial(xLagrange, yLagrange, x))
            );
            series3.getData().add(
                    new XYChart.Data<Number, Number>(x, methods.interpolateWithNewtonMethod(x, xLagrange, yLagrange, h)));
            series4.getData().add(
                    new XYChart.Data<Number, Number>(x, methods.interpolateLinearSpline(xArray, yArray).value(x)));
            series5.getData().add(
                    new XYChart.Data<Number, Number>(x, methods.interpolateParabolicSpline(xArray, yArray).value(x)));
            series6.getData().add(
                    new XYChart.Data<Number, Number>(x, methods.interpolateCubicSpline(xArray, yArray).value(x)));
            series7.getData().add(
                    new XYChart.Data<Number, Number>(x, FastMath.abs(methods.interpolateWithLagrangePolynomial(xLagrange, yLagrange, x) - myFunction.value(x))));
            series8.getData().add(
                    new XYChart.Data<Number, Number>(x, FastMath.abs(methods.interpolateWithNewtonMethod(x, xLagrange, yLagrange, h) - myFunction.value(x))));
            series9.getData().add(
                    new XYChart.Data<Number, Number>(x, FastMath.abs(methods.interpolateLinearSpline(xArray, yArray).value(x) - myFunction.value(x))));
            series11.getData().add(
                    new XYChart.Data<Number, Number>(x, FastMath.abs(methods.interpolateCubicSpline(xArray, yArray).value(x) - myFunction.value(x))));

        }

        lineChartOne.getData().addAll(series2, series3);
        lineChartTwo.getData().addAll(series7, series8);
    }
}
