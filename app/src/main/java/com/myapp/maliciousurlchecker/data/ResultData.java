package com.myapp.maliciousurlchecker.data;

import android.content.Context;

import com.myapp.maliciousurlchecker.R;
import com.myapp.maliciousurlchecker.models.ResultModel;

import java.util.HashMap;

public class ResultData {

    Context mContext;

    public final static String RESULT_SAFE = "Congrats, this URL is Safe!";
    public final static String RESULT_MALICIOUS = "Alert: This URL is extremely dangerous!";
    public final static String RESULT_SUSPICIOUS = "Alert: This URL is not safe!";
    public final static String RESULT_ENTER_URL_FIRST = "Alert: Please Enter URL First!";
    public final static String RESULT_URL_NOT_VALID = "Alert: Enter Valid URL!";

    HashMap<String,ResultModel> dataHashMap;

    public ResultData(Context context){
        this.mContext=context;
        setData();
    }

    public void setData(){
        dataHashMap = new HashMap<>();
        dataHashMap.put(RESULT_SAFE,new ResultModel(R.color.theme_color,RESULT_SAFE));
        dataHashMap.put(RESULT_MALICIOUS,new ResultModel(R.color.theme_red,RESULT_MALICIOUS));
        dataHashMap.put(RESULT_SUSPICIOUS,new ResultModel(R.color.theme_red,RESULT_SUSPICIOUS));
        dataHashMap.put(RESULT_ENTER_URL_FIRST,new ResultModel(R.color.theme_red,RESULT_ENTER_URL_FIRST));
        dataHashMap.put(RESULT_URL_NOT_VALID,new ResultModel(R.color.theme_red,RESULT_URL_NOT_VALID));
    }

    public ResultModel getData(String key){
        return dataHashMap.get(key);
    }


}
