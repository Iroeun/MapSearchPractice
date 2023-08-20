package com.devinsight.mapsearchpractice.api.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;



public class header_item_num_page {

    private Header header;

    private List<com.devinsight.mapsearchpractice.api.data.Item> item;

    private int numOfRows;

    private int pageNo;

    private int totalCount;

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public List<Item> getItem() {
        return item;
    }

    public void setItem(List<com.devinsight.mapsearchpractice.api.data.Item> Item) {
        this.item = Item;
    }

    public int getNumOfRows() {
        return numOfRows;
    }

    public void setNumOfRows(int numOfRows) {
        this.numOfRows = numOfRows;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public header_item_num_page(Header header, List<com.devinsight.mapsearchpractice.api.data.Item> Item, int numOfRows, int pageNo, int totalCount) {
        this.header = header;
        this.item = Item;
        this.numOfRows = numOfRows;
        this.pageNo = pageNo;
        this.totalCount = totalCount;
    }
}