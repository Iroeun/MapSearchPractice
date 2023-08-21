package com.devinsight.mapsearchpractice.search;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.devinsight.mapsearchpractice.R;

public class resultActivity extends AppCompatActivity {

    TextView storeName;
    TextView address;
    ImageView storeImg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_activity);

        storeName = findViewById(R.id.storename);
        address = findViewById(R.id.address);
        storeImg = findViewById(R.id.storeImg);

        Intent intent = getIntent();
        String storeImage = intent.getStringExtra("storeImage");

        if (storeImage != null ) {
            Glide.with(getApplicationContext()).load(storeImage).into(storeImg);
        }
        String Name = intent.getStringExtra("Name");
        String storeAddress = intent.getStringExtra("storeAddress");

        storeName.setText(Name);
        address.setText(storeAddress);

    }


}