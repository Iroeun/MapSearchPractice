package com.devinsight.mapsearchpractice.dodoApi;

import com.devinsight.mapsearchpractice.dodoApi.data.getFoodKr;

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