package com.myapp.maliciousurlchecker.models;

public class IqQualityScoreResponseModel {


    boolean unsafe;
    String final_url;
    int risk_score;

    public IqQualityScoreResponseModel(){}

    public boolean getIsUnsafe(){
        return unsafe;
    }

    public String getFinalUrl() {
        return final_url;
    }

    public int getRisk_score() {
        return risk_score;
    }
}
