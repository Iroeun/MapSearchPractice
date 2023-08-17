package com.devinsight.mapsearchpractice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class resultActivity extends AppCompatActivity {

    TextView storeName;
    TextView address;
    ImageView anything;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_activity);

        storeName = findViewById(R.id.storename);
        address = findViewById(R.id.address);
        anything = findViewById(R.id.anything);

        Intent intent = getIntent();
        int storeImage = intent.getIntExtra("storeImage", -1);
        if (storeImage != -1) {
            Glide.with(getApplicationContext()).load(storeImage).into(anything);
        }
        String Name = intent.getStringExtra("Name");
        String storeAddress = intent.getStringExtra("storeAddress");

        storeName.setText(Name);
        address.setText(storeAddress);




    }


}