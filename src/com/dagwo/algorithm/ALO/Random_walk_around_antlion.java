package com.dagwo.algorithm.ALO;

public class Random_walk_around_antlion {
    public int dim;
    public int max_iter;
    double [] lb;
    double [] ub;
    double [] antlion;
    int current_iter;

    public Random_walk_around_antlion(int dim, int max_iter, double [] lb, double [] ub, double [] antlion, int current_iter){
        this.dim = dim;
        this.max_iter = max_iter;
//        this.lb = lb;
//        this.ub = ub;
        this.lb = new double[dim];
        this.ub = new double[dim];
        this.antlion = new double[dim];
        for (int i=0; i<dim; i++){
            this.lb[i] = lb[i];
            this.ub[i] = ub[i];
            this.antlion[i] = antlion[i];
        }
        this.current_iter = current_iter;
    }

    public double [][] RWs(){
        double [][] RWs = new double[max_iter+1][dim];
        double I = 1.0; // I is the ratio in Equations (2.10) and (2.11)
        if ((double) current_iter> (double) max_iter/10) I=1+100*((double) current_iter/max_iter);
        if ((double) current_iter> (double) max_iter/2) I=1+1000*((double) current_iter/max_iter);
        if ((double) current_iter> (double) max_iter*0.75) I=1+10000*((double) current_iter/max_iter);
        if ((double) current_iter> (double) max_iter*(0.9)) I=1+100000*((double) current_iter/max_iter);
        if ((double) current_iter> (double) max_iter*(0.95)) I=1+1000000*((double) current_iter/max_iter);
        // Dicrease boundaries to converge towards antlion
        for (int i=0; i<dim; i++){
            lb[i]=lb[i]/(I); // Equation (2.10) in the paper
            ub[i]=ub[i]/(I); // Equation (2.11) in the paper
        }

        // Move the interval of [lb ub] around the antlion [lb+anlion ub+antlion]
        if (Math.random() < 0.5){
            for (int i=0; i<dim; i++){
                lb[i]=lb[i] + antlion[i]; // Equation (2.8) in the paper
            }
        } else {
            for (int i=0; i<dim; i++){
                lb[i]=-lb[i] + antlion[i];
            }
        }

        if (Math.random() >= 0.5){
            for (int i=0; i<dim; i++){
                ub[i]=ub[i] + antlion[i]; // Equation (2.9) in the paper
            }
        } else {
            for (int i=0; i<dim; i++){
                ub[i]=-ub[i] + antlion[i];
            }
        }

        // This function creates n random walks and normalize accroding to lb and ub vectors
        for (int i=0; i<dim; i++){
            double [] f = new double[max_iter];
            for (int j=0; j<max_iter; j++) {
                f[j] = 2 * (Math.random() > 0.5? 1 : 0) - 1;
            }
            double [] cum_sum_f = cumsum(f);
            double [] X = new double[max_iter+1];
            X[0] = 0;
            for (int j=1; j<max_iter+1; j++) {
                X[j] = cum_sum_f[j-1];  // Equation (2.1) in the paper
            }

            double a = X[0];
            double b = X[0];
            for (int j=1; j<max_iter+1; j++){
                if (X[j] < a){
                    a = X[j];
                }
                if (X[j] > b){
                    b = X[j];
                }
            }
            double c = lb[i];
            double d = ub[i];
            double [] X_norm = new double[max_iter+1];
            for (int j=0; j<max_iter+1; j++){
                X_norm[j] = ((X[j]-a)*(d-c))/(b-a)+c;
                RWs[j][i] = X_norm[j];
            }
        }
        return RWs;
    }

    public static double[] cumsum(double[] in) {
        double[] out = new double[in.length];
        double total = 0;
        for (int i = 0; i < in.length; i++) {
            total += in[i];
            out[i] = total;
        }
        return out;
    }
}
