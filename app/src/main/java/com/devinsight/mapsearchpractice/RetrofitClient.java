package com.devinsight.mapsearchpractice;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static final String BASE_URL = "http://apis.data.go.kr/6260000/FoodService/";
    private static Retrofit retrofit = null;

    private RetrofitClient() {}  // private constructor to prevent instantiation

    public static synchronized BusanRestaurantService getService() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(BusanRestaurantService.class);
    }
}
