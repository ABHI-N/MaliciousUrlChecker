package com.myapp.maliciousurlchecker.models;

public class ResultModel {


    int color;
    String result;

    public ResultModel(int color,String result){
        this.color=color;
        this.result=result;
    }

    public int getColor() {
        return color;
    }

    public String getResult() {
        return result;
    }
}
