package com.devinsight.mapsearchpractice.api.data;

import com.google.gson.annotations.SerializedName;

public class StoreItem {
    @SerializedName("LAT")
    private double LAT; // 위도
    @SerializedName("LNG")
    private double LNG; // 경도
    @SerializedName("ADDR1")
    private String ADDR1; // 주소
    @SerializedName("MAIN_TITLE")
    private String MAIN_TITLE; // 가게 이름
    @SerializedName("MAIN_IMG_NORMAL")
    private String MAIN_IMG_NORMAL; // 이미지 URL

    public StoreItem(double LAT, double LNG, String ADDR1, String MAIN_TITLE, String MAIN_IMG_NORMAL) {
        this.LAT = LAT;
        this.LNG = LNG;
        this.ADDR1 = ADDR1;
        this.MAIN_TITLE = MAIN_TITLE;
        this.MAIN_IMG_NORMAL = MAIN_IMG_NORMAL;
    }

    public double getLAT() {
        return LAT;
    }

    public void setLAT(double LAT) {
        this.LAT = LAT;
    }

    public double getLNG() {
        return LNG;
    }

    public void setLNG(double LNG) {
        this.LNG = LNG;
    }

    public String getADDR1() {
        return ADDR1;
    }

    public void setADDR1(String ADDR1) {
        this.ADDR1 = ADDR1;
    }

    public String getMAIN_TITLE() {
        return MAIN_TITLE;
    }

    public void setMAIN_TITLE(String MAIN_TITLE) {
        this.MAIN_TITLE = MAIN_TITLE;
    }

    public String getMAIN_IMG_NORMAL() {
        return MAIN_IMG_NORMAL;
    }

    public void setMAIN_IMG_NORMAL(String MAIN_IMG_NORMAL) {
        this.MAIN_IMG_NORMAL = MAIN_IMG_NORMAL;
    }
}
