package com.dagwo.problem;

public class ConstructionType {
    public String constructionName;
    public int TPT;

    @Override
    public String toString(){
        return constructionName + " - " + TPT;
    }
}
