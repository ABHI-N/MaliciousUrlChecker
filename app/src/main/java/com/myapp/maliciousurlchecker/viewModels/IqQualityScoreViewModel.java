package com.myapp.maliciousurlchecker.viewModels;

import androidx.lifecycle.LiveData;

import com.myapp.maliciousurlchecker.models.IqQualityScoreResponseModel;
import com.myapp.maliciousurlchecker.retrofitRepositories.IqQualityScoreRepository;

public class IqQualityScoreViewModel {
     IqQualityScoreRepository iqQualityScoreRepository;
     LiveData<IqQualityScoreResponseModel> iqQualityScoreViewModelLiveData;

    public IqQualityScoreViewModel() {
        iqQualityScoreRepository = new IqQualityScoreRepository();
    }

    public LiveData<IqQualityScoreResponseModel> getIqQualityScoreLiveData(String apiKey,String url) {
        iqQualityScoreViewModelLiveData = iqQualityScoreRepository.getUrlQualityLiveData(apiKey, url);
        return iqQualityScoreViewModelLiveData;
    }
}
