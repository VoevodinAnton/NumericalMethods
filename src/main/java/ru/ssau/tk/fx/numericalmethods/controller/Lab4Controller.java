package ru.ssau.tk.fx.numericalmethods.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseButton;
import org.apache.commons.math3.analysis.BivariateFunction;
import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.util.FastMath;
import org.gillius.jfxutils.chart.ChartPanManager;
import org.gillius.jfxutils.chart.JFXChartUtil;
import ru.ssau.tk.fx.numericalmethods.model.DataLab4;
import ru.ssau.tk.fx.numericalmethods.model.MyFunctionLab4;

public class Lab4Controller {
    @FXML
    public TableView table;
    Methods methods;
    MyFunctionLab4 myFunction;
    double a = 0;
    double b = 1;
    UnivariateFunction exactFunction;

    XYChart.Series series1;
    XYChart.Series series2;
    XYChart.Series series3;

    @FXML
    public LineChart lineChart;
    @FXML
    public NumberAxis xAxis;
    @FXML
    public NumberAxis yAxis;
    @FXML
    public Button calculateButton;

    {
        methods = new Methods();
        myFunction = new MyFunctionLab4();
        exactFunction = (double x) -> FastMath.exp(4*x);

        series1 = new XYChart.Series();
        series2 = new XYChart.Series();
        series3 = new XYChart.Series();

        series1.setName("Exact function");
        series2.setName("Runge Cutta");
        series3.setName("Eiler");

    }

    public void calculate(ActionEvent actionEvent) {
        double h = 0.1;
        double y0 = 1;
        double x0 = 0;

        h = methods.defineStep(h, a, b, y0, x0);
        System.out.println("Шаг: " + h);

        double[] x = methods.descretizeX(a, b, h);
        double[] yRungeCutta = methods.calculateRungeCutta(x, y0, a, b, h);
        double[] yEiler = methods.calculateEiler(x, y0, a, b, h);

        for (int i = 0; i < x.length; i++){
            series1.getData().add(
                    new XYChart.Data<Number, Number>(x[i], exactFunction.value(x[i]))
            );
            series2.getData().add(
                    new XYChart.Data<Number, Number>(x[i], yRungeCutta[i])
            );
            series3.getData().add(
                    new XYChart.Data<Number, Number>(x[i], yEiler[i])
            );

        }
        ChartPanManager pannerTwo = new ChartPanManager(lineChart);
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
        JFXChartUtil.setupZooming(lineChart, mouseEvent -> {
            if (mouseEvent.getButton() != MouseButton.SECONDARY)//set your custom combination to trigger rectangle zooming
                mouseEvent.consume();
        });

        lineChart.getData().addAll(series2, series3);




        TableColumn<DataLab4, Double> columnX = new TableColumn<>("x");
        columnX.setPrefWidth(60);
        TableColumn<DataLab4, Double> columnY = new TableColumn<>("Точное решение");
        TableColumn<DataLab4, Double> columnRungeCutta = new TableColumn<>("Метод Рунге-Кутта");
        columnRungeCutta.setPrefWidth(140);
        TableColumn<DataLab4, Double> columnEiler = new TableColumn<>("Метод Эйлера");
        columnEiler.setPrefWidth(120);
        TableColumn<DataLab4, Double> columnErrorRungeCutta = new TableColumn<>("Погрешность метода Рунге-Кутта");
        columnErrorRungeCutta.setPrefWidth(180);
        TableColumn<DataLab4, Double> columnErrorEiler = new TableColumn<>("Погрешность метода Эйлера");
        columnErrorEiler.setPrefWidth(180);

        columnX.setCellValueFactory(cellData -> cellData.getValue().xProperty().asObject());
        columnY.setCellValueFactory(cellData -> cellData.getValue().yProperty().asObject());
        columnRungeCutta.setCellValueFactory(cellData -> cellData.getValue().rungeCuttaProperty().asObject());
        columnEiler.setCellValueFactory(cellData -> cellData.getValue().eilerProperty().asObject());
        columnErrorRungeCutta.setCellValueFactory(cellData -> cellData.getValue().errorRungeCuttaProperty().asObject());
        columnErrorEiler.setCellValueFactory(cellData -> cellData.getValue().errorEilerProperty().asObject());

        columnX.setCellFactory(tc -> new TableCell<DataLab4, Double>() {
            @Override
            protected void updateItem(Double power, boolean empty) {
                super.updateItem(power, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(String.format("%.4f", power.doubleValue()));
                }
            }
        });

        columnY.setCellFactory(tc -> new TableCell<>() {
            @Override
            protected void updateItem(Double power, boolean empty) {
                super.updateItem(power, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(String.format("%.5e", power.doubleValue()));
                }
            }
        });

        columnRungeCutta.setCellFactory(tc -> new TableCell<DataLab4, Double>() {
            @Override
            protected void updateItem(Double power, boolean empty) {
                super.updateItem(power, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(String.format("%.5f", power.doubleValue()));
                }
            }
        });

        columnEiler.setCellFactory(tc -> new TableCell<DataLab4, Double>() {
            @Override
            protected void updateItem(Double power, boolean empty) {
                super.updateItem(power, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(String.format("%.5f", power.doubleValue()));
                }
            }
        });

        columnErrorRungeCutta.setCellFactory(tc -> new TableCell<>() {
            @Override
            protected void updateItem(Double power, boolean empty) {
                super.updateItem(power, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(String.format("%.5e", power.doubleValue()));
                }
            }
        });

        columnErrorEiler.setCellFactory(tc -> new TableCell<DataLab4, Double>() {
            @Override
            protected void updateItem(Double power, boolean empty) {
                super.updateItem(power, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(String.format("%.5e", power.doubleValue()));
                }
            }
        });

        table.getColumns().addAll(columnX, columnY, columnRungeCutta, columnEiler, columnErrorRungeCutta, columnErrorEiler);


        ObservableList<DataLab4> listLab4 = FXCollections.observableArrayList();

        for (int i = 0; i < x.length; i++){
            listLab4.add(new DataLab4(x[i],
                    exactFunction.value(x[i]),
                    yRungeCutta[i],
                    yEiler[i],
                    FastMath.abs(exactFunction.value(x[i]) - yRungeCutta[i]),
                    FastMath.abs(exactFunction.value(x[i]) - yEiler[i])));
        }

        table.setItems(listLab4);


    }
}
