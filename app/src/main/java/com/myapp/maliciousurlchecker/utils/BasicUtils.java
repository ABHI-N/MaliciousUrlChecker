package com.myapp.maliciousurlchecker.utils;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class BasicUtils {


    public static void removeKeyboardNonClick(Context context, View v){
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    public static int dpToPx(int dp, Context context) {
        float density = context.getResources().getDisplayMetrics().density;
        return Math.round(dp * density);
    }
}
