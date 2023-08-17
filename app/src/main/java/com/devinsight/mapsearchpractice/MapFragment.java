package com.devinsight.mapsearchpractice;

import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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

public class MapFragment extends Fragment implements OnMapReadyCallback {

    private FusedLocationSource locationSource;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1000;
    private Marker currentLocationMarker = new Marker();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_map, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        locationSource = new FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE);

        com.naver.maps.map.MapView mapView = view.findViewById(R.id.mapView);
        mapView.getMapAsync(this);
    }

    @Override
    public void onMapReady(NaverMap naverMap) {

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
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        locationSource.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
