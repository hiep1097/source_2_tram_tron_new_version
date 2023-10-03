package com.dagwo.algorithm.PSO;

import com.dagwo.algorithm.f_xj;

import java.io.IOException;

public class PSO {
    double r1, r2, c1, c2, w, wMax, wMin;
    int N;
    int dim;
    int maxIter;
    double lb[];
    double ub[];
    double vMax[];
    double vMin[];
    f_xj ff;
    double X[][];
    double[][] Result;
    double[][] arrRandomBestVal;
    Swarm swarm;
    double [] worstArr;

    public PSO(f_xj iff, double[] Lower, double[] Upper, int imaxIter, int iN) {
        maxIter = imaxIter;
        ff = iff;
        lb = Lower;
        ub = Upper;
        vMax = new double[lb.length];
        vMin = new double[ub.length];
        N = iN;
        dim = ub.length;
        X = new double[N][dim];
        arrRandomBestVal = new double[maxIter][dim];
        worstArr = new double[dim];
    }

    void init() throws IOException {
        wMax = 0.9;
        wMin = 0.2;
        c1 = 2;
        c2 = 2;
        for (int i=0; i<lb.length; i++){
            vMax[i] = (ub[i]-lb[i])*0.2;
            vMin[i] = -vMax[i];
        }

        swarm = new Swarm(N, dim);
        for(int i = 0; i < N; i++) {
            swarm.particles[i] = new Particles(dim);
            for(int j = 0; j < dim; j++) {
                swarm.particles[i].X[j] = lb[j] + (ub[j] - lb[j]) * nextRand();
            }
        }
    }

    double[][] solution() throws IOException {
        init();
        int iter = 1;
        while(iter <= maxIter) {
            // Calcualte the objective value
            for(int i = 0; i < N; i++) {
                double [] currentX = new double[dim];
                for (int j=0; j<dim; j++){
                    currentX[j] = swarm.particles[i].X[j];
                }
                swarm.particles[i].O = ff.func(currentX);

                if (swarm.particles[i].O > ff.func(worstArr)){
                    for (int j = 0; j<dim; j++){
                        worstArr[j] = swarm.particles[i].X[j];
                    }
                }

                //Update the PBEST
                if (swarm.particles[i].O < swarm.particles[i].PBEST.O){
                    for (int j=0; j<dim; j++){
                        swarm.particles[i].PBEST.X[j] = swarm.particles[i].X[j];
                    }
                    swarm.particles[i].PBEST.O = swarm.particles[i].O;
                }

                //Update the GBEST
                if (swarm.particles[i].O < swarm.GBEST.O){
                    for (int j=0; j<dim; j++){
                        swarm.GBEST.X[j] = swarm.particles[i].X[j];
                    }
                    swarm.GBEST.O = swarm.particles[i].O;
                }
            }

            //Update the X and V vectors
            w = wMax - iter * ((wMax - wMin) / maxIter);

            for(int i = 0; i < N; i++) {
                for (int j=0; j<dim; j++){
                    r1 = nextRand();
                    r2 = nextRand();
                    swarm.particles[i].V[j] = w*swarm.particles[i].V[j] + c1*r1*(swarm.particles[i].PBEST.X[j] - swarm.particles[i].X[j])
                            + c2*r2*(swarm.GBEST.X[j] - swarm.particles[i].X[j]);

                    //Check velocities
                    if (swarm.particles[i].V[j] > vMax[j]){
                        swarm.particles[i].V[j] = vMax[j];
                    }
                    if (swarm.particles[i].V[j] < vMin[j]){
                        swarm.particles[i].V[j] = vMin[j];
                    }

                    swarm.particles[i].X[j] = swarm.particles[i].X[j] + swarm.particles[i].V[j];

                    //Check positions
                    if (swarm.particles[i].X[j] > ub[j]){
                        swarm.particles[i].X[j] = ub[j];
                    }
                    if (swarm.particles[i].X[j] < lb[j]){
                        swarm.particles[i].X[j] = lb[j];
                    }
                }
            }

            for (int i=0; i<dim; i++){
                arrRandomBestVal[iter-1][i] = swarm.GBEST.X[i];
            }
            System.out.println("Iteration: "+iter);
            System.out.println("Best score: "+swarm.GBEST.O);
            iter++;
        }

        double[][] out = new double[2][dim];

        for (int i = 0; i < dim; i++){
            out[1][i] = swarm.GBEST.X[i];
        }

        out[0][0] = swarm.GBEST.O;
        return out;
    }

    public void execute() throws IOException {
        Result = solution();
    }

    public void toStringNew(String sMessage) throws IOException {
        System.out.println(sMessage + Result[0][0]);

        for(int i = 0; i < dim;i++) {
            System.out.println("x["+i+"] = "+ Result[1][i]);
        }

        System.out.println("----------------------------------------");
    }

    public double[] getBestArray()
    {
        return Result[1];
    }

    public double[][] getArrayRandomResult(){
        return arrRandomBestVal;
    }

    public double[] getWorstArray() {
        return worstArr;
    }

    double nextRand(){
//        return 0.7;
//        position++;
//        return randomm[position-1];
        return Math.random();
    }
}
