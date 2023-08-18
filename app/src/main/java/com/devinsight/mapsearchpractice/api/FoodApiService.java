package com.devinsight.mapsearchpractice.api;

import com.devinsight.mapsearchpractice.api.data.getFoodKr;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FoodApiService {
    @GET("/6260000/FoodService/getFoodKr")
    Call<getFoodKr> getFoodInfoList(
            @Query("serviceKey") String serviceKey,
            @Query("pageNo") int pageNo,
            @Query("numOfRows") int numOfRows,
            @Query("resultType") String resultType
    );
}