package com.myapp.maliciousurlchecker.networking;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    static RetrofitClient retrofitClient;
    Retrofit retrofit;

    private RetrofitClient(){}

    public static RetrofitClient getInstance(){

        if(retrofitClient==null){
            retrofitClient = new RetrofitClient();
        }

        return retrofitClient;
    }

    public Retrofit getClient(String baseUrl) {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}