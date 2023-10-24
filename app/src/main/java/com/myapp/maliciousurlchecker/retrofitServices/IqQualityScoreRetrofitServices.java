package com.myapp.maliciousurlchecker.retrofitServices;

import com.myapp.maliciousurlchecker.models.IqQualityScoreResponseModel;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface IqQualityScoreRetrofitServices {

    @POST("api/json/url/{apiKey}/{url}")
    Call<IqQualityScoreResponseModel> checkUrlQuality(@Path("apiKey") String apiKey,@Path("url") String url);
}
