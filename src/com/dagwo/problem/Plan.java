package com.dagwo.problem;

public class Plan {
    public String ArriveAtStation;
    public int SiteID;
    public String ArriveAtSite;
    public String LeaveFromSite;
    public String ReturnToPlant;
    public String GoStation;
    public String BackStation;

    @Override
    public String toString(){
        return ArriveAtStation + "\t\t\t" + SiteID + "\t\t\t " + ArriveAtSite + "\t\t\t\t " + LeaveFromSite + "\t\t\t\t " + ReturnToPlant
                + "\t\t\t\t " + GoStation + "\t   -->   \t" + BackStation;
    }
}
