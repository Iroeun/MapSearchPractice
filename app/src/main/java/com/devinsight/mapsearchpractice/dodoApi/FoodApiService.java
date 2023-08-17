package com.devinsight.mapsearchpractice.dodoApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FoodApiService {
    @GET("/6260000/FoodService/getFoodKr")
    Call<List<FoodResonseDTO>> getFoodInfo(
            @Query("serviceKey") String serviceKey,
            @Query("pageNo") int pageNo,
            @Query("numOfRows") int numOfRows
    );

}
