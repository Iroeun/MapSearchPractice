package com.devinsight.mapsearchpractice.dodoApi;

import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClientDODO {

    private static final String BASE_URL = "http://apis.data.go.kr/";
    private static final String SERVICE_KEY = "uOimQvpXv4Oid51cKUxn7CBa2INY0E%2FuWjIAwIYePkx31iCI1Nc3RgjNdYuk2H3ZkXTw8Lp%2BonebmcoJSmQjcA%3D%3D";
    private static Retrofit retrofit;

    public static Retrofit getRetrofit(String baseUrl){
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
