package com.myapp.maliciousurlchecker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.lifecycle.Observer;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.myapp.maliciousurlchecker.data.ResultData;
import com.myapp.maliciousurlchecker.databinding.ActivityMainBinding;
import com.myapp.maliciousurlchecker.models.IqQualityScoreResponseModel;
import com.myapp.maliciousurlchecker.models.ResultModel;
import com.myapp.maliciousurlchecker.security.SecureSharedPreferences;

import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity {


    ActivityMainBinding binding;
    ResultData resultData;
    SecureSharedPreferences secureSharedPreferences;
    IqQualityScoreViewModel iqQualityScoreViewModel;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        resultData = new ResultData(this);
        secureSharedPreferences = new SecureSharedPreferences(this);
        iqQualityScoreViewModel = new IqQualityScoreViewModel();
        //set et.
        setupEt();
        //set check button listener.
        binding
                .mainActivitySubmitButton
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        checkButtonPressed();
                    }
                });
        //save api key.
        saveSecureKey();

    }

    private void saveSecureKey(){

        if(secureSharedPreferences.getSecureString(SecureSharedPreferences.API_KEY_IQ_QUALITY_SCORE)==null){
            secureSharedPreferences
                    .putSecureString(SecureSharedPreferences.API_KEY_IQ_QUALITY_SCORE,SecureSharedPreferences.DATA_API_KEY_IQ_QUALITY_SCORE);
        }

    }

    private void checkButtonPressed(){
        String url = binding.mainActivityEt.getText().toString();

        if(url.isEmpty()){
            setResultAndVisibility(ResultData.RESULT_ENTER_URL_FIRST);
        }else if(!isUrlValid(url)){
            setResultAndVisibility(ResultData.RESULT_URL_NOT_VALID);
        }else{
            //start loading.
            toggleLayoutOnLoading(true);

            iqQualityScoreViewModel
                    .getIqQualityScoreLiveData(secureSharedPreferences.getSecureString(SecureSharedPreferences.API_KEY_IQ_QUALITY_SCORE),encodeUrl(url))
                    .observe(this, new Observer<IqQualityScoreResponseModel>() {
                        @Override
                        public void onChanged(IqQualityScoreResponseModel iqQualityScoreResponseModel) {
                            //end loading.
                            toggleLayoutOnLoading(false);

                            if(iqQualityScoreResponseModel!=null){
                                if(iqQualityScoreResponseModel.getIsUnsafe()){
                                    //malicious.
                                    setResultAndVisibility(ResultData.RESULT_MALICIOUS);
                                }else{
                                    //safe.
                                    if(iqQualityScoreResponseModel.getRisk_score()>0){
                                        setResultAndVisibility(ResultData.RESULT_SUSPICIOUS);
                                    }else{
                                        setResultAndVisibility(ResultData.RESULT_SAFE);
                                    }
                                }
                                Log.v("data","unSafe = " + iqQualityScoreResponseModel.getIsUnsafe() +" , finalUrl = "+iqQualityScoreResponseModel.getFinalUrl());
                            }
                        }
                    });

        }
    }

    private String encodeUrl(String urlToEncode){
        String encodedUrl = null;
        try {
            encodedUrl = URLEncoder.encode(urlToEncode, "UTF-8").replace("+", "");
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error occur", Toast.LENGTH_SHORT).show();
        }
        return encodedUrl;
    }

    private void toggleLayoutOnLoading(boolean isLoadingStart){

        if(isLoadingStart){
            binding.mainActivitySubmitButton.setAlpha(0.5f);
            binding.mainActivitySubmitButton.setEnabled(false);
            binding.mainActivityLoadingPb.setVisibility(View.VISIBLE);
            binding.mainActivityEt.setAlpha(0.5f);
            binding.mainActivityEt.setEnabled(false);
        }else{
            binding.mainActivitySubmitButton.setAlpha(1);
            binding.mainActivitySubmitButton.setEnabled(true);
            binding.mainActivityLoadingPb.setVisibility(View.GONE);
            binding.mainActivityEt.setAlpha(1);
            binding.mainActivityEt.setEnabled(true);
        }
    }

    private void setResultAndVisibility(@Nullable String result){
        if(result==null){
            binding.mainActivityResultGroup.setVisibility(View.GONE);
            binding.mainActivityResultTv.setText("");
        }else{
            binding.mainActivityResultGroup.setVisibility(View.VISIBLE);
            binding.mainActivityResultTv.setText(result);
            //set data.
            ResultModel resultModel = resultData.getData(result);
            int resultColor = resultModel.getColor();
            String resultData = resultModel.getResult();
            binding.mainActivityResultTv.setTextColor(ResourcesCompat.getColor(getResources(),resultColor,null));
            binding.mainActivityResultTv.setText(resultData);
        }
    }

    private void setupEt(){
        binding
                .mainActivityEt
                .addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        boolean isResultVisible = binding.mainActivityResultGroup.getVisibility()==View.VISIBLE;
                        if(isResultVisible){
                            //visible , set to gone.
                            binding.mainActivityResultGroup.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                });
    }


    private boolean isUrlValid(String url){
        try{
            new URL(url);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

}