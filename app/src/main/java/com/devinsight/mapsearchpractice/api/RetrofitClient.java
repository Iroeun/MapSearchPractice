package com.devinsight.mapsearchpractice.api;

import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static final String BASE_URL = "http://apis.data.go.kr/";
    private static Retrofit retrofit;

    private static Retrofit getRetrofit(String baseUrl){
        if(retrofit == null){
            Retrofit.Builder builder = new Retrofit.Builder();
            builder.baseUrl(baseUrl);
            builder.client(new OkHttpClient());
            builder.addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setLenient().create()));
            retrofit = builder.build();
        }
        return  retrofit;
    }

    public static FoodApiService getFoodApiService(){
        return getRetrofit(BASE_URL).create(FoodApiService.class);
    }

}
