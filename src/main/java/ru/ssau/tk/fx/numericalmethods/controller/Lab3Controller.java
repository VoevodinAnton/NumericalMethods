package ru.ssau.tk.fx.numericalmethods.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.util.StringConverter;
import org.apache.commons.math3.util.FastMath;
import org.gillius.jfxutils.chart.JFXChartUtil;
import ru.ssau.tk.fx.numericalmethods.model.MyFunctionLab3;
import org.gillius.jfxutils.chart.ChartPanManager;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;

public class Lab3Controller {
    Methods methods;
    MyFunctionLab3 myFunctionLab3;
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
    XYChart.Series series10;
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
        myFunctionLab3 = new MyFunctionLab3();

        series1 = new XYChart.Series();
        series2 = new XYChart.Series();
        series3 = new XYChart.Series();
        series4 = new XYChart.Series();
        series5 = new XYChart.Series();
        series6 = new XYChart.Series();
        series7 = new XYChart.Series();
        series8 = new XYChart.Series();
        series9 = new XYChart.Series();
        series10 = new XYChart.Series();
        series11 = new XYChart.Series();

        series1.setName("Function");
        series2.setName("Lagrange");
        series3.setName("Newton");
        series4.setName("Linear spline");
        series5.setName("Parabolic spline");
        series6.setName("Cubic Spline");
        series7.setName("Error Lagrange");
        series8.setName("Error Newton");
        series9.setName("Error Linear Spline");
        series10.setName("Error Parabolic Spline");
        series11.setName("Error Cubic Spline");


    }


    public void calculate(ActionEvent actionEvent) {

        double h = methods.defineAStep(3, 0, 10);
        System.out.println("Шаг = " + h);
        double[] xArray = methods.descretizeX(a, b, h);
        double[] yArray = methods.descretizeY(a, b, h);
        double[] xLagrange = new double[4];
        double[] yLagrange = new double[4];
        for (int i = 0; i < 4; i++){

            xLagrange[i] = xArray[i+1];
            yLagrange[i] = yArray[i+1];
        }



        for (double x = h; x <  4*h; x += 0.01) {
            series1.getData().add(
                    new XYChart.Data<Number, Number>(x, myFunctionLab3.value(x)));
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
                    new XYChart.Data<Number, Number>(x, FastMath.abs(methods.interpolateWithLagrangePolynomial(xLagrange, yLagrange, x) - myFunctionLab3.value(x))));
            series8.getData().add(
                    new XYChart.Data<Number, Number>(x, FastMath.abs(methods.interpolateWithNewtonMethod(x, xLagrange, yLagrange, h) - myFunctionLab3.value(x))));
            series9.getData().add(
                    new XYChart.Data<Number, Number>(x, FastMath.abs(methods.interpolateLinearSpline(xArray, yArray).value(x) - myFunctionLab3.value(x))));
            series10.getData().add(
                    new XYChart.Data<Number, Number>(x, FastMath.abs(methods.interpolateParabolicSpline(xArray, yArray).value(x) - myFunctionLab3.value(x))));
            series11.getData().add(
                    new XYChart.Data<Number, Number>(x, FastMath.abs(methods.interpolateCubicSpline(xArray, yArray).value(x) - myFunctionLab3.value(x))));

        }

        /*
        NumberFormat format = new DecimalFormat("#.#E0");
        yAxisOne.setTickLabelFormatter(new StringConverter<Number>() {

            @Override
            public String toString(Number number) {
                return format.format(number.doubleValue());
            }

            @Override
            public Number fromString(String string) {
                try {
                    return format.parse(string);
                } catch (ParseException e) {
                    e.printStackTrace();
                    return 0 ;
                }
            }

        });

         */



        lineChartOne.getData().addAll(series1, series2,series3, series4, series5, series6);
        ChartPanManager pannerOne = new ChartPanManager(lineChartOne);
        //while presssing the left mouse button, you can drag to navigate
        pannerOne.setMouseFilter(mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.PRIMARY) {//set your custom combination to trigger navigation
                // let it through
            } else {
                mouseEvent.consume();
            }
        });
        pannerOne.start();

        //holding the right mouse button will draw a rectangle to zoom to desired location
        JFXChartUtil.setupZooming(lineChartOne, mouseEvent -> {
            if (mouseEvent.getButton() != MouseButton.SECONDARY)//set your custom combination to trigger rectangle zooming
                mouseEvent.consume();
        });

        lineChartTwo.getData().addAll(series7, series8, series9, series10, series11);

        ChartPanManager pannerTwo = new ChartPanManager(lineChartTwo);
        //while presssing the left mouse button, you can drag to navigate
        pannerTwo.setMouseFilter(mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.PRIMARY) {//set your custom combination to trigger navigation
                // let it through
            } else {
                mouseEvent.consume();
            }
        });
        pannerTwo.start();

        //holding the right mouse button will draw a rectangle to zoom to desired location
        JFXChartUtil.setupZooming(lineChartTwo, mouseEvent -> {
            if (mouseEvent.getButton() != MouseButton.SECONDARY)//set your custom combination to trigger rectangle zooming
                mouseEvent.consume();
        });

        NumberFormat format = new DecimalFormat("0.######E0");
        yAxisTwo.setTickLabelFormatter(new StringConverter<>() {

            @Override
            public String toString(Number number) {
                return format.format(number.doubleValue());
            }

            @Override
            public Number fromString(String string) {
                try {
                    return format.parse(string);
                } catch (ParseException e) {
                    e.printStackTrace();
                    return 0;
                }
            }

        });
    }




}
