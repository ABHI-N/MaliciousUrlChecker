package com.myapp.maliciousurlchecker.security;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;

public class SecureSharedPreferences {

    public static final String FILE_NAME = "secret_shared_prefs";
    public static final String API_KEY_IQ_QUALITY_SCORE = "API_KEY_IQ_QUALITY_SCORE";
    public static final String DATA_API_KEY_IQ_QUALITY_SCORE = "wdfHY3rWujS5JhvKJbQuG3jO9cu5Mmg7";
    SharedPreferences sharedPreferences;

    public SecureSharedPreferences(Context context) {
        try{
            String masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC);
            sharedPreferences = EncryptedSharedPreferences.create(
                    FILE_NAME,
                    masterKeyAlias,
                    context,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            );
        }catch (Exception e){
            e.printStackTrace();
            sharedPreferences = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
            Toast.makeText(context, "Error Occur", Toast.LENGTH_SHORT).show();
        }
    }

    public void putSecureString(String key,String data) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key,data);
        editor.apply();
    }

    public String getSecureString(String key) {
        return sharedPreferences.getString(key, null);
    }
}
