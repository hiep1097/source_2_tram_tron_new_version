package com.dagwo.chart;

import java.io.IOException;

public class Main_All {
    public static void main(String[] args) throws IOException {
        //Flag to show algorithm
        ParetoChart_All.show_DA_GWO = 0;
        ParetoChart_All.show_GWO = 1;
        ParetoChart_All.show_DA = 0;
        ParetoChart_All.show_PSO = 1;
        ParetoChart_All.show_ALO = 1;
        ParetoChart_All.show_GA = 0;

        ParetoChart_All paretoChart_all = new ParetoChart_All();
        paretoChart_all.main(args);
    }
}
