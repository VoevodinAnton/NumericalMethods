package ru.ssau.tk.fx.numericalmethods.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class Lab1Controller {


    @FXML
    public TextField rightBorderField;

    @FXML
    public TextField initialValueField;

    @FXML
    public Label numberOfIterationsHyb;

    @FXML
    public javafx.scene.chart.LineChart<String, Number> lineChart;

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
    public CategoryAxis xAxis;

    @FXML
    public NumberAxis yAxis;

    @FXML
    public Button buildButton;

    @FXML
    public TextField leftBorderField;

    @FXML
    public void calculate(ActionEvent actionEvent) {
        System.out.println("Hello world");
        rootHyb.setText("");
    }


    @FXML
    public void buildPlot(ActionEvent actionEvent) {
        rootHyb.setText("hello");

        System.out.println("hello");
        xAxis = new CategoryAxis();
        xAxis.setLabel("Month");
        yAxis = new NumberAxis();
        lineChart = new LineChart(xAxis,yAxis);
        XYChart.Series series1 = new XYChart.Series();
        series1.setName("Portfolio 1");

        series1.getData().add(new XYChart.Data("Jan", 23));
        series1.getData().add(new XYChart.Data("Feb", 14));
        series1.getData().add(new XYChart.Data("Mar", 15));
        series1.getData().add(new XYChart.Data("Apr", 24));
        series1.getData().add(new XYChart.Data("May", 34));
        series1.getData().add(new XYChart.Data("Jun", 36));
        series1.getData().add(new XYChart.Data("Jul", 22));
        series1.getData().add(new XYChart.Data("Aug", 45));
        series1.getData().add(new XYChart.Data("Sep", 43));
        series1.getData().add(new XYChart.Data("Oct", 17));
        series1.getData().add(new XYChart.Data("Nov", 29));
        series1.getData().add(new XYChart.Data("Dec", 25));
        lineChart.getData().addAll(series1);
        lineChart.setVisible(true);
    }
}
