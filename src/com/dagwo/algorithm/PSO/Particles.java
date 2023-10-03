package com.dagwo.algorithm.PSO;

public class Particles {
    public double X[];
    public double V[];
    public double O;
    public BestResult PBEST;

    public Particles(int dim){
        X = new double[dim];
        V = new double[dim];
        PBEST = new BestResult(dim);
    }
}
