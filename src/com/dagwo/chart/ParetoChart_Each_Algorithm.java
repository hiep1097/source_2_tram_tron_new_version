package com.dagwo.chart;

import com.dagwo.main.Util;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.stage.Stage;

import java.io.IOException;

public class ParetoChart_Each_Algorithm extends Application {

    public static int flagShowDetail = 1; // 0: show only Pareto chart; 1: Show all value (Data + Pareto)

    public static String algorithmName;

    public static ObservableList<Data<Number, Number>> lstData = FXCollections.observableArrayList();
    public static ObservableList<Data<Number, Number>> lstParetoData = FXCollections.observableArrayList();

    public int max_x = 0;
    public int min_x = 100000;

    public int max_y = 0;
    public int min_y = 100000;

    @Override
    public void start(Stage stage) throws IOException {
        readParetoDataFromFile();
        if(flagShowDetail == 1) {
            for (int i = 0; i < lstData.size(); i++) {
                if (max_x < (lstData.get(i).getXValue().intValue() + 1))
                    max_x = lstData.get(i).getXValue().intValue() + 1;

                if (max_y < (lstData.get(i).getYValue().intValue() + 1))
                    max_y = lstData.get(i).getYValue().intValue() + 1;

                if (min_x > (lstData.get(i).getXValue().intValue() + 1))
                    min_x = lstData.get(i).getXValue().intValue() + 1;

                if (min_y > (lstData.get(i).getYValue().intValue() + 1))
                    min_y = lstData.get(i).getYValue().intValue() + 1;

            }
        } else {
            for (int i = 0; i < lstParetoData.size(); i++) {
                if (max_x < (lstParetoData.get(i).getXValue().intValue() + 1))
                    max_x = lstParetoData.get(i).getXValue().intValue() + 1;

                if (max_y < (lstParetoData.get(i).getYValue().intValue() + 1))
                    max_y = lstParetoData.get(i).getYValue().intValue() + 1;

                if (min_x > (lstParetoData.get(i).getXValue().intValue() + 1))
                    min_x = lstParetoData.get(i).getXValue().intValue() + 1;

                if (min_y > (lstParetoData.get(i).getYValue().intValue() + 1))
                    min_y = lstParetoData.get(i).getYValue().intValue() + 1;
            }
        }

        stage.setTitle("PhD Thesis about DA_GWO for Construction");
        final NumberAxis xAxis = new NumberAxis(min_x - 3, max_x, (max_x - min_x) / 10);
        final NumberAxis yAxis = new NumberAxis(min_y - 5, max_y, (max_y - min_y) / 10);
        final ScatterChart<Number, Number> sc = new ScatterChart<>(xAxis,
                yAxis);
        xAxis.setLabel("Construction Wait Truck - CWT (minutes)");
        yAxis.setLabel("Truck Wait Construction - TWC (minutes)");
        sc.setTitle("Pareto front for "+ algorithmName+ " algorithm");

        Series<Number, Number> series = new Series<>();
        series.setName("Value");
        series.setData(lstData);

        Series<Number, Number> series_pareto = new Series<>();
        series_pareto.setName("Pareto value");
        series_pareto.setData(lstParetoData);

        Series<Number, Number> s1 = new Series<>();
        Series<Number, Number> s2 = new Series<>();
        Series<Number, Number> s3 = new Series<>();

        if(flagShowDetail == 1) {
            sc.getData().addAll(s1, s2, series, s3, series_pareto);
        } else {
            sc.getData().addAll(s1, s2, s3, series_pareto);
        }
        Scene scene = new Scene(sc, 500, 400);
        stage.setScene(scene);
        stage.show();
    }

    public void main(String[] args) {
        launch(args);
    }

    public static void readParetoDataFromFile() throws IOException {
        String [] data = Util.readParetoDataFromFile("pareto_data/" + algorithmName + "_data.txt");
        String [] pareto_data = Util.readParetoDataFromFile("pareto_data/" + algorithmName + "_pareto_data.txt");
        lstData = Util.parseToObservableList(data);
        lstParetoData = Util.parseToObservableList(pareto_data);
    }
}
