package com.dagwo.problem;

import java.util.ArrayList;

public class PlanOfTruck {
    public String TruckID;
    public ArrayList<Plan> lstPlan = new ArrayList<Plan>();

    @Override
    public String toString(){
        String s = "Truck ID = " + TruckID + "\tNo. of delivery = " + lstPlan.size() + "\n";
        for (Plan p : lstPlan){
            s += p.toString() + "\n";
        }
        return s;
    }
}
