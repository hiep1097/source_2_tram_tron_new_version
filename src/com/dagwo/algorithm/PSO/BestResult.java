package com.dagwo.algorithm.PSO;

public class BestResult {
    public double X[];
    public double O;
    static double infinity = 10E+50;
    public BestResult(int dim){
        X = new double[dim];
        O = infinity;
    }
}
