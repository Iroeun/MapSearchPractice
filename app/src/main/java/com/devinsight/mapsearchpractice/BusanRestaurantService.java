package com.devinsight.mapsearchpractice;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface BusanRestaurantService {
    @GET("6260000/FoodService/getFoodKr")
    Call<RestaurantResponse> getRestaurants(
            @Query("ServiceKey") String serviceKey,
            @Query("pageNo") int pageNo,
            @Query("numOfRows") int numOfRows,
            @Query("resultType") String resultType
    );
}
