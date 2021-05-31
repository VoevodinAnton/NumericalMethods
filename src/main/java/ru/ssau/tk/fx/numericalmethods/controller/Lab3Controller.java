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

    public void calculate(ActionEvent actionEvent) {
        Methods methods = new Methods();


        XYChart.Series series1 = new XYChart.Series();
        XYChart.Series series2 = new XYChart.Series();
        XYChart.Series series3 = new XYChart.Series();
        XYChart.Series series4 = new XYChart.Series();
        XYChart.Series series5 = new XYChart.Series();
        XYChart.Series series6 = new XYChart.Series();
        XYChart.Series series7 = new XYChart.Series();
        XYChart.Series series8 = new XYChart.Series();
        XYChart.Series series9 = new XYChart.Series();
        XYChart.Series series11 = new XYChart.Series();

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


        MyFunction myFunction = new MyFunction();
        double[] xArray = {0., 1.14146399 , 2.28292797, 3.42439196};
        double[] yArray = {myFunction.value(xArray[0]), myFunction.value(xArray[1]), myFunction.value(xArray[2]), myFunction.value(xArray[3])};
        double x;


        for (int i = 0; i <  342.439196; i++) {
            x = i / 100.;
            series1.getData().add(
                    new XYChart.Data<Number, Number>(x, myFunction.value(x)));
            series2.getData().add(
                    new XYChart.Data<Number, Number>(x, methods.interpolateWithLagrangePolynomial(xArray, yArray, x))
            );
            series3.getData().add(
                    new XYChart.Data<Number, Number>(x, methods.interpolateWithNewtonMethod(x, xArray, yArray, 3)));
            series4.getData().add(
                    new XYChart.Data<Number, Number>(x, methods.interpolateLinearSpline(xArray, yArray).value(x)));
            series5.getData().add(
                    new XYChart.Data<Number, Number>(x, methods.interpolateParabolicSpline(xArray, yArray).value(x)));
            series6.getData().add(
                    new XYChart.Data<Number, Number>(x, methods.interpolateCubicSpline(xArray, yArray).value(x)));
            series7.getData().add(
                    new XYChart.Data<Number, Number>(x, FastMath.abs(methods.interpolateWithLagrangePolynomial(xArray, yArray, x) - myFunction.value(x))));
            series8.getData().add(
                    new XYChart.Data<Number, Number>(x, FastMath.abs(methods.interpolateWithNewtonMethod(x, xArray, yArray, 3) - myFunction.value(x))));
            series9.getData().add(
                    new XYChart.Data<Number, Number>(x, FastMath.abs(methods.interpolateLinearSpline(xArray, yArray).value(x) - myFunction.value(x))));
            series11.getData().add(
                    new XYChart.Data<Number, Number>(x, FastMath.abs(methods.interpolateCubicSpline(xArray, yArray).value(x) - myFunction.value(x))));

        }
        lineChartOne.getData().addAll( series4);
        lineChartTwo.getData().addAll(series9);

    }
}
