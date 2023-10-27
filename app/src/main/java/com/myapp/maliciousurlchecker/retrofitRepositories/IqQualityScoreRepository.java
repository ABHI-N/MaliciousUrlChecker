package com.myapp.maliciousurlchecker.retrofitRepositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.myapp.maliciousurlchecker.models.IqQualityScoreResponseModel;
import com.myapp.maliciousurlchecker.networking.RetrofitClient;
import com.myapp.maliciousurlchecker.retrofitServices.IqQualityScoreRetrofitServices;
import com.myapp.maliciousurlchecker.security.SecureSharedPreferences;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IqQualityScoreRepository {

    final static String RETROFIT_BASE_URL = "https://www.ipqualityscore.com/";
    IqQualityScoreRetrofitServices iqQualityScoreRetrofitServices;

    public IqQualityScoreRepository() {
        iqQualityScoreRetrofitServices = RetrofitClient.getInstance().getClient(RETROFIT_BASE_URL).create(IqQualityScoreRetrofitServices.class);
    }

    public LiveData<IqQualityScoreResponseModel> getUrlQualityLiveData(String apiKey,String url) {
        final MutableLiveData<IqQualityScoreResponseModel> urlQualityLiveData = new MutableLiveData<>();


        iqQualityScoreRetrofitServices
                .checkUrlQuality(apiKey,url)
                .enqueue(new Callback<IqQualityScoreResponseModel>() {
                    @Override
                    public void onResponse(Call<IqQualityScoreResponseModel> call, Response<IqQualityScoreResponseModel> response) {
                        urlQualityLiveData.setValue(response.body());
                    }

                    @Override
                    public void onFailure(Call<IqQualityScoreResponseModel> call, Throwable t) {
                        urlQualityLiveData.setValue(null);
                        t.printStackTrace();
                    }


                });

        return urlQualityLiveData;
    }
}
