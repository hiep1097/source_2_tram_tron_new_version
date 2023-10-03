package com.dagwo.algorithm.ALO;

public class RouletteWheelSelection {
    public static int choice(double [] weights){
        double [] accumulation = cumsum(weights);
        double p = Math.random() * accumulation[accumulation.length-1];
        int chosen_index = -1;
        for (int i=0; i<accumulation.length; i++){
            if (accumulation[i]>p){
                chosen_index = i;
                break;
            }
        }
        return chosen_index;
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
