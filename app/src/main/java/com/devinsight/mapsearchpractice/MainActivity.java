package com.devinsight.mapsearchpractice;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.devinsight.mapsearchpractice.R;
import com.devinsight.mapsearchpractice.dodoApi.ApiTestActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button mapButton = findViewById(R.id.mapBtn);
        Button imageButton = findViewById(R.id.imageBtn);
        Button apiButton = findViewById(R.id.apiBtn);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // SearchListFragment를 호출
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, new SearchListFragment())
                        .addToBackStack(null)  // 이전 프래그먼트로 돌아갈 수 있도록 백 스택에 추가
                        .commit();
            }
        });

        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // MapFragment를 호출
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, new MapFragment())
                        .addToBackStack(null)  // 이전 프래그먼트로 돌아갈 수 있도록 백 스택에 추가
                        .commit();
            }
        });

        apiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, ApiTestActivity.class);
                startActivity(intent);
                finish();
//                // SearchListFragment를 호출
//                getSupportFragmentManager().beginTransaction()
//                        .replace(R.id.container, new SearchListFragment())
//                        .addToBackStack(null)  // 이전 프래그먼트로 돌아갈 수 있도록 백 스택에 추가
//                        .commit();
            }
        });
    }
}