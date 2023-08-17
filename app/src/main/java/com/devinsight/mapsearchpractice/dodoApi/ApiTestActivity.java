package com.devinsight.mapsearchpractice.dodoApi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.devinsight.mapsearchpractice.R;
import com.devinsight.mapsearchpractice.dodoApi.data.getFoodKr;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiTestActivity extends AppCompatActivity {


    private static final String SERVICE_KEY = "uOimQvpXv4Oid51cKUxn7CBa2INY0E/uWjIAwIYePkx31iCI1Nc3RgjNdYuk2H3ZkXTw8Lp+onebmcoJSmQjcA=="; // 디코딩 키
    //    private static final String SERVICE_KEY = "uOimQvpXv4Oid51cKUxn7CBa2INY0E%2FuWjIAwIYePkx31iCI1Nc3RgjNdYuk2H3ZkXTw8Lp%2BonebmcoJSmQjcA%3D%3D"; // 인코딩키
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_api_test);

        Call<getFoodKr> call = RetrofitClientDODO.getFoodApiService().getFoodInfoList(SERVICE_KEY, 1, 10, "json");
        call.enqueue(new Callback<getFoodKr>() {
            @Override
            public void onResponse(Call<getFoodKr> call, Response<getFoodKr> response) {
                if (response.isSuccessful()) {
                    getFoodKr data = response.body();
                    // 서버에서는 응답을 보냈지만 에러 코드와 함께 온 경우 (예: 404, 500 등)
                    for(int i = 0; i < data.getGetFoodKr().getItem().size(); i ++) {
                        Log.d(" 성공 ", " 성공 " + data.getGetFoodKr().getItem().get(0).getMAIN_IMG_NORMAL());
                    }
                }
            }
            @Override
            public void onFailure(Call<getFoodKr> call, Throwable t) {
                Log.e("RetrofitFailure", "Network or conversion error: " + t.getMessage());

            }
        });


    }


}