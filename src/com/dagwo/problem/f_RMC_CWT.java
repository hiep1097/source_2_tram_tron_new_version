package com.dagwo.problem;

import com.dagwo.algorithm.f_xj;

public class f_RMC_CWT extends f_xj {
    public double[] Lower;
    public double[] Upper;

    public f_RMC_CWT(){
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

        double CWT = ff.Execute_CWT(x);

        ff = null;
        System.gc();

        return Math.abs(CWT);
    }
}
