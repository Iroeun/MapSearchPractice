package com.devinsight.mapsearchpractice;

import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.util.FusedLocationSource;
import com.naver.maps.map.LocationTrackingMode;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MapFragment extends Fragment implements OnMapReadyCallback {

    //지도
    private FusedLocationSource locationSource;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1000;
    private Marker currentLocationMarker = new Marker();

    //API
    private NaverMap mNaverMap;
    private Retrofit retrofit;
    private BusanRestaurantService service;

    private static final String BASE_URL = "https://apis.data.go.kr/";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_map, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Retrofit 초기화
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL) // 실제 기본 URL로 변경
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(BusanRestaurantService.class);

        locationSource = new FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE);

        com.naver.maps.map.MapView mapView = view.findViewById(R.id.mapView);
        mapView.getMapAsync(this);
    }

    @Override
    public void onMapReady(NaverMap naverMap) {

        mNaverMap = naverMap;  // 지도 인스턴스 저장

        naverMap.setLocationSource(locationSource);

        LatLng defaultPosition = new LatLng(37.5053, 126.9574);  // 기본 좌표
        naverMap.moveCamera(CameraUpdate.scrollTo(defaultPosition));

        currentLocationMarker.setPosition(defaultPosition);
        currentLocationMarker.setMap(naverMap); // 마커를 지도에 추가

        currentLocationMarker.setIconTintColor(Color.RED);

        // 카메라가 변경될 때마다 호출되는 리스너를 설정합니다.
        naverMap.addOnCameraChangeListener(new NaverMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(int reason, boolean animated) {
                LatLng center = naverMap.getCameraPosition().target;
                Toast.makeText(getActivity(), "Latitude: " + center.latitude + ", Longitude: " + center.longitude, Toast.LENGTH_SHORT).show();
                currentLocationMarker.setPosition(center);
            }
        });

        // API 호출
//        fetchRestaurantData();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        locationSource.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void fetchRestaurantData() {
        String serviceKey = "6b31aH9j991v4MVhKX0sok2prlcNQjjBgL1Xj6MakWldP8zZFa9jujToWfzPpLVeEr12yjzzwpssiF2Rst5kfw%3D%3D";  // 인증키를 넣으세요.
        int pageNo = 1;
        int numOfRows = 10;
        String resultType = "json";

        Call<RestaurantResponse> call = service.getRestaurants(serviceKey, pageNo, numOfRows, resultType);
//        Log.d("LOGAPI", call.toString());
        call.enqueue(new Callback<RestaurantResponse>() {
//            Log.d("LOGAPI", response.body().toString());
            @Override
            public void onResponse(Call<RestaurantResponse> call, Response<RestaurantResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<RestaurantResponse.RestaurantItem> restaurants = response.body().foodKr.row;
                    for (RestaurantResponse.RestaurantItem restaurant : restaurants) {
                        // MAIN_TITLE 출력
                        Log.d("API", "MAIN_TITLE: " + restaurant.MAIN_TITLE);
                    }
                } else {
                    // 오류 처리
                    Log.d("API", "Error: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<RestaurantResponse> call, Throwable t) {
                // 네트워크 또는 변환 오류 처리
                Log.e("API", "API Call Failed: " + t.getMessage());
            }
        });
    }

}
