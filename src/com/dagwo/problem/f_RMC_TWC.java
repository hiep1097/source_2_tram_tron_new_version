package com.dagwo.problem;

public class f_RMC_TWC extends f_RMC_CWT {
    public double[] Lower;
    public double[] Upper;

    public f_RMC_TWC(){
        f_SimRMC ff = new f_SimRMC();

        Lower = new double[ff.N];
        Upper = new double[ff.N];

        for(int i = 0; i < ff.N; i++)
        {
            Lower[i] = 0;
            Upper[i] = 1;
        }

        ff = null;
        System.gc();
    }

    public double func(double x[]){
        f_SimRMC ff = new f_SimRMC();

        double TWC = ff.Execute_TWC(x);

        ff = null;
        System.gc();

        return Math.abs(TWC);
    }
}