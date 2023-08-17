package com.devinsight.mapsearchpractice;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SearchAdapter.searchItemListner {

    private RecyclerView recyclerView;
    private SearchAdapter searchAdapter;
    private ArrayList<SearchMainData> storeList;

    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchlist);

        context = this;

        recyclerView = findViewById(R.id.recycler_search_page);
        storeList = new ArrayList<>();

        storeList.add(new SearchMainData(R.drawable.forest, "Little Forest", "집이최고야"));
        storeList.add(new SearchMainData(R.drawable.food, "진짜 맛있는 집", "돈까스 팝니다"));
        storeList.add(new SearchMainData(R.drawable.cat, "고양이 통신", "나만 고양이 없어"));

        searchAdapter = new SearchAdapter(this, storeList, this);
        recyclerView.setAdapter(searchAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

    }


    @Override
    public void onSearchItemClick(View view, SearchMainData searchData, int position) {

        Intent intent = new Intent(MainActivity.this, resultActivity.class);

        intent.putExtra("storeImage", searchData.getStoreImage());
        intent.putExtra("Name", searchData.getStoreName());
        intent.putExtra("storeAddress", searchData.getStoreAddress());

        startActivity(intent);

        Log.d("이동", "이동" + searchData.getStoreName());
    }

}