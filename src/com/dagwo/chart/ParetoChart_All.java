package com.dagwo.chart;

import com.dagwo.main.Util;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

import static com.dagwo.main.Util.parseToObservableList;

public class ParetoChart_All extends Application {

    public static int show_DA_GWO;
    public static int show_GWO;
    public static int show_DA;
    public static int show_PSO;
    public static int show_ALO;
    public static int show_GA;

    public static ObservableList<Data<Number, Number>> lstParetoData_DA_GWO = FXCollections.observableArrayList();
    public static ObservableList<Data<Number, Number>> lstParetoData_GWO = FXCollections.observableArrayList();
    public static ObservableList<Data<Number, Number>> lstParetoData_DA = FXCollections.observableArrayList();
    public static ObservableList<Data<Number, Number>> lstParetoData_PSO = FXCollections.observableArrayList();
    public static ObservableList<Data<Number, Number>> lstParetoData_ALO = FXCollections.observableArrayList();
    public static ObservableList<Data<Number, Number>> lstParetoData_GA = FXCollections.observableArrayList();

    public static int max_x = 0;
    public static int min_x = 100000;

    public static int max_y = 0;
    public static int min_y = 100000;

    public static void main(String[] args) throws IOException {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("Master Thesis about DA_GWO for Construction");
        StackPane pane = new StackPane();
        readParetoDataFromFile();
        pane.getChildren().add(createChart());
        Scene scene = new Scene(pane, 600, 400);
        stage.setScene(scene);
        stage.show();
    }

    public LineChart createChart(){
        findMaxMinData();
        System.out.println("Min X: " + min_x);
        System.out.println("Max X: " + max_x);
        System.out.println("Min Y:" + min_y);
        System.out.println("Max y:" + max_y);

        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();

        xAxis.setAutoRanging(true);
        xAxis.setLowerBound(min_x - 3);
        xAxis.setUpperBound(max_x+3);
        xAxis.setTickUnit((max_x - min_x) / 10);

        yAxis.setAutoRanging(true);
        yAxis.setLowerBound(min_y - 3);
        yAxis.setUpperBound(max_y+3);
        yAxis.setTickUnit((max_y - min_y) / 10);

        xAxis.setLabel("Construction Wait Truck - CWT (minutes)");
        yAxis.setLabel("Truck Wait Construction - TWC (minutes)");



        XYChart.Series dataSeries_GWO = new XYChart.Series();
        XYChart.Series dataSeries_DA_GWO = new XYChart.Series();
        XYChart.Series dataSeries_DA = new XYChart.Series();
        XYChart.Series dataSeries_PSO = new XYChart.Series();
        XYChart.Series dataSeries_ALO = new XYChart.Series();
        XYChart.Series dataSeries_GA = new XYChart.Series();

        if (show_GWO == 1){
            dataSeries_GWO.setName("GWO");
            for (Data<Number, Number> data: lstParetoData_GWO){
                dataSeries_GWO.getData().add(new XYChart.Data<>(data.getXValue(), data.getYValue()));
            }
        }

        if (show_DA_GWO == 1){
            dataSeries_DA_GWO.setName("DA_GWO");
            for (Data<Number, Number> data: lstParetoData_DA_GWO){
                dataSeries_DA_GWO.getData().add(new XYChart.Data<>(data.getXValue(), data.getYValue()));
            }
        }

        if (show_DA == 1){
            dataSeries_DA.setName("DA");
            for (Data<Number, Number> data: lstParetoData_DA){
                dataSeries_DA.getData().add(new XYChart.Data<>(data.getXValue(), data.getYValue()));
            }
        }

        if (show_PSO == 1){
            dataSeries_PSO.setName("PSO");
            for (Data<Number, Number> data: lstParetoData_PSO){
                dataSeries_PSO.getData().add(new XYChart.Data<>(data.getXValue(), data.getYValue()));
            }
        }

        if (show_ALO == 1){
            dataSeries_ALO.setName("ALO");
            for (Data<Number, Number> data: lstParetoData_ALO){
                dataSeries_ALO.getData().add(new XYChart.Data<>(data.getXValue(), data.getYValue()));
            }
        }

        if (show_GA == 1){
            dataSeries_GA.setName("GA");
            for (Data<Number, Number> data: lstParetoData_GA){
                dataSeries_GA.getData().add(new XYChart.Data<>(data.getXValue(), data.getYValue()));
            }
        }

        LineChart chart = new LineChart(xAxis, yAxis);

        if (show_GWO == 1) chart.getData().addAll(dataSeries_GWO);
        if (show_DA_GWO == 1) chart.getData().addAll(dataSeries_DA_GWO);
        if (show_DA == 1) chart.getData().addAll(dataSeries_DA);
        if (show_PSO == 1) chart.getData().addAll(dataSeries_PSO);
        if (show_ALO == 1) chart.getData().addAll(dataSeries_ALO);
        if (show_GA == 1) chart.getData().addAll(dataSeries_GA);

        chart.setTitle("Pareto front for algorithms");
        return chart;
    }

    public static void readParetoDataFromFile() throws IOException {
        if (show_GWO == 1){
            String [] pareto_data = Util.readParetoDataFromFile("pareto_data/GWO_pareto_data.txt");
            lstParetoData_GWO = parseToObservableList(pareto_data);
        }


        if (show_DA_GWO == 1){
            String [] pareto_data = Util.readParetoDataFromFile("pareto_data/DA_GWO_pareto_data.txt");
            lstParetoData_DA_GWO = parseToObservableList(pareto_data);
        }

        if (show_DA == 1){
            String [] pareto_data = Util.readParetoDataFromFile("pareto_data/DA_pareto_data.txt");
            lstParetoData_DA = parseToObservableList(pareto_data);
        }

        if (show_PSO == 1){
            String [] pareto_data = Util.readParetoDataFromFile("pareto_data/PSO_pareto_data.txt");
            lstParetoData_PSO = parseToObservableList(pareto_data);
        }

        if (show_ALO == 1){
            String [] pareto_data = Util.readParetoDataFromFile("pareto_data/ALO_pareto_data.txt");
            lstParetoData_ALO = parseToObservableList(pareto_data);
        }

        if (show_GA == 1){
            String [] pareto_data = Util.readParetoDataFromFile("pareto_data/GA_pareto_data.txt");
            lstParetoData_GA = parseToObservableList(pareto_data);
        }
    }

    public static void findMaxMinData(){
        if (show_GWO == 1){
            for (int i = 0; i < lstParetoData_GWO.size(); i++) {
                if (max_x < (lstParetoData_GWO.get(i).getXValue().intValue()))
                    max_x = lstParetoData_GWO.get(i).getXValue().intValue();

                if (max_y < (lstParetoData_GWO.get(i).getYValue().intValue()))
                    max_y = lstParetoData_GWO.get(i).getYValue().intValue();

                if (min_x > (lstParetoData_GWO.get(i).getXValue().intValue()))
                    min_x = lstParetoData_GWO.get(i).getXValue().intValue();

                if (min_y > (lstParetoData_GWO.get(i).getYValue().intValue()))
                    min_y = lstParetoData_GWO.get(i).getYValue().intValue();
            }
        }

        if (show_DA_GWO == 1){
            for (int i = 0; i < lstParetoData_DA_GWO.size(); i++) {
                if (max_x < (lstParetoData_DA_GWO.get(i).getXValue().intValue()))
                    max_x = lstParetoData_DA_GWO.get(i).getXValue().intValue();

                if (max_y < (lstParetoData_DA_GWO.get(i).getYValue().intValue()))
                    max_y = lstParetoData_DA_GWO.get(i).getYValue().intValue();

                if (min_x > (lstParetoData_DA_GWO.get(i).getXValue().intValue()))
                    min_x = lstParetoData_DA_GWO.get(i).getXValue().intValue();

                if (min_y > (lstParetoData_DA_GWO.get(i).getYValue().intValue()))
                    min_y = lstParetoData_DA_GWO.get(i).getYValue().intValue();
            }
        }

        if (show_DA == 1){
            for (int i = 0; i < lstParetoData_DA.size(); i++) {
                if (max_x < (lstParetoData_DA.get(i).getXValue().intValue()))
                    max_x = lstParetoData_DA.get(i).getXValue().intValue();

                if (max_y < (lstParetoData_DA.get(i).getYValue().intValue()))
                    max_y = lstParetoData_DA.get(i).getYValue().intValue();

                if (min_x > (lstParetoData_DA.get(i).getXValue().intValue()))
                    min_x = lstParetoData_DA.get(i).getXValue().intValue();

                if (min_y > (lstParetoData_DA.get(i).getYValue().intValue()))
                    min_y = lstParetoData_DA.get(i).getYValue().intValue();
            }
        }

        if (show_PSO == 1){
            for (int i = 0; i < lstParetoData_PSO.size(); i++) {
                if (max_x < (lstParetoData_PSO.get(i).getXValue().intValue()))
                    max_x = lstParetoData_PSO.get(i).getXValue().intValue();

                if (max_y < (lstParetoData_PSO.get(i).getYValue().intValue()))
                    max_y = lstParetoData_PSO.get(i).getYValue().intValue();

                if (min_x > (lstParetoData_PSO.get(i).getXValue().intValue()))
                    min_x = lstParetoData_PSO.get(i).getXValue().intValue();

                if (min_y > (lstParetoData_PSO.get(i).getYValue().intValue()))
                    min_y = lstParetoData_PSO.get(i).getYValue().intValue();
            }
        }

        if (show_ALO == 1){
            for (int i = 0; i < lstParetoData_ALO.size(); i++) {
                if (max_x < (lstParetoData_ALO.get(i).getXValue().intValue()))
                    max_x = lstParetoData_ALO.get(i).getXValue().intValue();

                if (max_y < (lstParetoData_ALO.get(i).getYValue().intValue()))
                    max_y = lstParetoData_ALO.get(i).getYValue().intValue();

                if (min_x > (lstParetoData_ALO.get(i).getXValue().intValue()))
                    min_x = lstParetoData_ALO.get(i).getXValue().intValue();

                if (min_y > (lstParetoData_ALO.get(i).getYValue().intValue()))
                    min_y = lstParetoData_ALO.get(i).getYValue().intValue();
            }
        }

        if (show_GA == 1){
            for (int i = 0; i < lstParetoData_GA.size(); i++) {
                if (max_x < (lstParetoData_GA.get(i).getXValue().intValue()))
                    max_x = lstParetoData_GA.get(i).getXValue().intValue();

                if (max_y < (lstParetoData_GA.get(i).getYValue().intValue()))
                    max_y = lstParetoData_GA.get(i).getYValue().intValue();

                if (min_x > (lstParetoData_GA.get(i).getXValue().intValue()))
                    min_x = lstParetoData_GA.get(i).getXValue().intValue();

                if (min_y > (lstParetoData_GA.get(i).getYValue().intValue()))
                    min_y = lstParetoData_GA.get(i).getYValue().intValue();
            }
        }



    }

}
