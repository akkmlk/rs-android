package com.latihan.rs;

import android.content.Context;
import android.content.SharedPreferences;

import java.security.PublicKey;

public class LocalStorage {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Context context;
    String data;

    private static final String PREF_NAME = "rs";

    public LocalStorage(Context context) {
        this.context = context;
        sharedPreferences =  context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor =  sharedPreferences.edit();
    }

    public String getData() {
        data = sharedPreferences.getString("data", "");
        return data;
    }

    public void setData(String data) {
        editor.putString("data", data);
        editor.apply();
        this.data = data;
    }
}
