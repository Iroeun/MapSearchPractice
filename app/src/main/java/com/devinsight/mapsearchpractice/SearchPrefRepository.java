package com.devinsight.mapsearchpractice;

import android.content.SharedPreferences;

import com.google.gson.Gson;

public class SearchPrefRepository {

    public static final String PREFERENCE_NAME = "search_data+pref";
    private String DATA_KEY = "";

    private SharedPreferences sharedPreferences;
    private Gson gson;
}
