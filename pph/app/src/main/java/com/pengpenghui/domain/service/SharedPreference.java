package com.pengpenghui.domain.service;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by zoson on 6/26/15.
 */
public class SharedPreference {
    Context context;
    public SharedPreference(Context context) {
        this.context = context;
    }

    public void set(String key, String value){
        if (value.equals("")||value.equals(" ")||value.equals("null")){
            return;
        }
        SharedPreferences mySharedPreferences = context.getSharedPreferences("pengpenghui", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mySharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public String get(String key,String defaultString) {
        SharedPreferences sharedPreferences= context.getSharedPreferences("pengpenghui",Context.MODE_PRIVATE);
        String value =sharedPreferences.getString(key, defaultString);
        return value;
    }
}
