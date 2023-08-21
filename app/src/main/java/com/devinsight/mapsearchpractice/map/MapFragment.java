//그냥 현재 위치 m로 표시
//기존 코드
package com.devinsight.mapsearchpractice.map;

import static java.lang.Double.parseDouble;

import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.devinsight.mapsearchpractice.R;
import com.devinsight.mapsearchpractice.api.RetrofitClient;
import com.devinsight.mapsearchpractice.api.data.getFoodKr;
import com.devinsight.mapsearchpractice.api.data.header_item_num_page;
import com.devinsight.mapsearchpractice.api.data.item;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.geometry.LatLngBounds;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.util.FusedLocationSource;
import com.naver.maps.map.LocationTrackingMode;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MapFragment extends Fragment implements OnMapReadyCallback,MapStoreCardAdapter.mapCardItemListner  {

    //지도
    private FusedLocationSource locationSource;
    private Location mCurrentLocation;

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1000;
    private Marker currentLocationMarker = new Marker();

    //API
    private NaverMap mNaverMap;
    private Retrofit retrofit;

    //카드뷰 리사이클러뷰
    private RecyclerView recyclerView;
    private MapStoreCardAdapter cardAdapter;
    private ArrayList<MapStoreCardData> cardLit;

    private static final String SERVICE_KEY = "nrOGw0QURUK73GF9IZ1qwK2L/48kRarW7q/UHFbPSs1XJamzUDdYNTwLz81QgUlPLhJJDSUDzGGMPP1frVfl3w==";

    private ArrayList<Marker> allMarkers = new ArrayList<>();

    private boolean canCallAPI = true;
    private static final long API_CALL_DELAY = 5000; // 예: 5초
    private Handler handler = new Handler();




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        recyclerView = view.findViewById(R.id.rc_card);
        cardLit = new ArrayList<>();

//        cardLit.add(new MapStoreCardData(R.drawable.ic_launcher_background,7942, 9413, 33,4, 300,"가게 이름","주소",true));
//        cardLit.add(new MapStoreCardData(R.drawable.ic_launcher_background,7942, 9413, 33,4, 300,"가게 이름","주소",true));
//        cardLit.add(new MapStoreCardData(R.drawable.ic_launcher_background,7942, 9413, 33,4, 300,"가게 이름","주소",true));
//        cardLit.add(new MapStoreCardData(R.drawable.ic_launcher_background,7942, 9413, 33,4, 300,"가게 이름","주소",true));

        cardAdapter = new MapStoreCardAdapter(getContext(), cardLit, this::onCardClick);
        recyclerView.setAdapter(cardAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false);
        SnapHelper snapHelper = new PagerSnapHelper();
        recyclerView.setLayoutManager(linearLayoutManager);
        snapHelper.attachToRecyclerView(recyclerView);

        return view;

    }
    @Override
    public void onCardClick(MapStoreCardData item, int position) {
//        cardAdapter.notifyItem
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        locationSource = new FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE);

        com.naver.maps.map.MapView mapView = view.findViewById(R.id.mapView);
        mapView.getMapAsync(this);
    }


    @Override
    public void onMapReady(NaverMap naverMap) { //지도가 준비됐을 때 호출되는 콜백 메소드

        mNaverMap = naverMap;  // 지도 인스턴스 저장

        naverMap.setLocationSource(locationSource);

//        LatLng defaultPosition = new LatLng(37.498095, 127.027610);  // 기본 위치는 강남역
        LatLng defaultPosition = new LatLng(35.115095, 129.042694); //이지만 지금은 부산역으로 해놓음
        naverMap.moveCamera(CameraUpdate.scrollTo(defaultPosition));

        currentLocationMarker.setPosition(defaultPosition);
        currentLocationMarker.setMap(naverMap); // 마커를 지도에 추가
        currentLocationMarker.setIconTintColor(Color.RED);

        // 카메라가 변경될 때마다 호출되는 리스너를 설정합니다.
        naverMap.addOnCameraChangeListener(new NaverMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(int reason, boolean animated) {

                //TODO : 이 부분은 확대하면 씨앗모양핀, 축소하면 숫자만 떠오게 할 때 써야할 듯
//                float currentZoom = (float) naverMap.getCameraPosition().zoom;
//                // 예를 들어 zoom 수준이 15 이상일 때만 마커를 보이게 합니다.
//                if (currentZoom >= 15) {
//                    currentLocationMarker.setMap(naverMap);
//                } else {
//                    currentLocationMarker.setMap(null); // 마커를 지도에서 제거합니다.
//                }


                LatLng center = naverMap.getCameraPosition().target;
//                Toast.makeText(getActivity(), "Latitude: " + center.latitude + ", Longitude: " + center.longitude, Toast.LENGTH_SHORT).show();
                currentLocationMarker.setPosition(center);

                // 경계 밖의 마커를 제거하는 로직
                LatLngBounds currentBounds = mNaverMap.getContentBounds();
                ArrayList<Marker> markersToRemove = new ArrayList<>();
                for (Marker marker : allMarkers) {
                    if (!currentBounds.contains(marker.getPosition())) {
                        marker.setMap(null);  // 지도에서 마커 제거
                        markersToRemove.add(marker);  // 리스트에서 제거할 마커 추가
                    }
                }

                if (canCallAPI) {
                    fetchFoodData(currentBounds);
                    canCallAPI = false;
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            canCallAPI = true;
                        }
                    }, API_CALL_DELAY);
                }
//                allMarkers.removeAll(markersToRemove);  // 마커 리스트에서 제거
//                fetchFoodData(currentBounds); // 바뀐 currentBounds를 API 호출 시에 전달
            }

        });

        naverMap.addOnLocationChangeListener(new NaverMap.OnLocationChangeListener() {
            @Override
            public void onLocationChange(Location location) {
                mCurrentLocation = location; // 위치 업데이트

                // 현재 위치를 로그로 출력
                Log.d("LOG_LOCATION", "Latitude: " + location.getLatitude() + ", Longitude: " + location.getLongitude());
            }
        });

//        naverMap.setLocationTrackingMode(LocationTrackingMode.Follow);
        naverMap.setLocationTrackingMode(LocationTrackingMode.NoFollow);


    }

    //위치 권한 요청 결과를 처리하는 메소드
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        locationSource.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // 추가된 부분
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (mCurrentLocation != null) {
                Log.d("LOG_LOCATION_PERMISSION", "Latitude: " + mCurrentLocation.getLatitude() + ", Longitude: " + mCurrentLocation.getLongitude());
            }
        }
    }


    // ... 기존의 MapFragment 코드 ...

    //주어진 경계 내의 음식점 데이터를 가져오는 메소드
    private void fetchFoodData(LatLngBounds currentBounds) {
        Call<getFoodKr> call = RetrofitClient.getFoodApiService().getFoodInfoList(SERVICE_KEY, 1, 10, "json");
        Log.d("LOGcall", call.toString());
        call.enqueue(new Callback<getFoodKr>() {
            @Override
            public void onResponse(Call<getFoodKr> call, Response<getFoodKr> response) {
                Log.d("LOG", "onResponse");
                if (response.isSuccessful()) {
                    header_item_num_page item_list = response.body().getGetFoodKr();
                    List<item> items = item_list.getItem();

                    // 기존 마커들을 지도에서 제거하고 리스트에서도 제거
                    for (Marker marker : allMarkers) {
                        marker.setMap(null);
                    }
                    allMarkers.clear();

                    // 리사이클러뷰 항목을 모두 제거
                    cardLit.clear();

                    for(item singleItem : items) {
                        double lat = singleItem.getLAT();
                        double lng = singleItem.getLNG();
                        String store_name = singleItem.getMAIN_TITLE();
                        String address = singleItem.getADDR1();
                        String thumb_nail_url = singleItem.getMAIN_IMG_THUMB(); //가게 썸네일 가져와야 함

                        LatLng position = new LatLng(lat, lng);

                        if (currentBounds.contains(position)) {
                            Marker marker = new Marker();
                            marker.setPosition(position);
                            marker.setMap(mNaverMap);
                            allMarkers.add(marker);

                            if (mCurrentLocation != null) {
                                Location markerLocation = new Location("");
                                markerLocation.setLatitude(lat);
                                markerLocation.setLongitude(lng);

                                float distanceToMarker = mCurrentLocation.distanceTo(markerLocation); // 두 위치 사이의 거리를 미터 단위로 얻습니다.
                                Log.d("DISTANCE_LOG", "Distance to marker: " + distanceToMarker + " meters");
                                cardLit.add(new MapStoreCardData(thumb_nail_url, lat, lng, 33, 4, (int) distanceToMarker, store_name, address, true));
//                                cardLit.add(new MapStoreCardData(thumb_nail_url, lat, lng, 33, 4, 300, store_name, address, true));
                            }
                            // 리사이클러뷰 데이터 목록 업데이트
//                            Log.d("LOGimage", imageUrl);
//                            Log.d("LOGAPI", String.valueOf(position));

//                            Log.d("성공", " 위도: " + lat + " 경도: " + lng);
                        }

                    }

                    cardAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<getFoodKr> call, Throwable t) {
                Log.e("LOGRetrofitFailure", "Network or conversion error: " + t.getMessage());
            }
        });
    }




}