package com.devinsight.mapsearchpractice;

import java.util.List;

public class RestaurantResponse {
    public RestaurantInfoList foodKr;
    public static class RestaurantInfoList {
        public List<RestaurantItem> row;
    }
    public static class RestaurantItem {
        public String MAIN_TITLE;
    }
}
