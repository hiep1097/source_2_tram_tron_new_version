package com.dagwo.chart;

import java.io.IOException;

public class Main_Each_Algorithm {
    public static void main(String[] args) throws IOException {
        //valid algorithm name: DA_GWO, GWO, DA, PSO, ALO, GA
        ParetoChart_Each_Algorithm.algorithmName = "DA_GWO";

        ParetoChart_Each_Algorithm paretoChart_each_algorithm = new ParetoChart_Each_Algorithm();
        paretoChart_each_algorithm.main(args);
    }
}
