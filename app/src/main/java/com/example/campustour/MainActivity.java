package com.example.campustour;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class MainActivity extends FragmentActivity
        implements OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener, GoogleMap.OnMyLocationClickListener {

    private GoogleMap mMap;
    private int MY_LOCATION_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(@NonNull final GoogleMap googleMap) {

        mMap = googleMap;

        LatLng KNU = new LatLng(35.88998331973934, 128.6114154965834);

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(KNU);
        markerOptions.title("경북대학교");
        markerOptions.snippet("대구캠퍼스");
        mMap.addMarker(markerOptions);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(KNU, 16));

        //권한없으면 부여
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] {
                    Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION
            }, 1);
        }
        mMap.setMyLocationEnabled(true);
        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnMyLocationClickListener(this);
    }

    @Override
    public boolean onMyLocationButtonClick() {
        return false;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {


    }
}

//    public void onSuccess(@Nullable String data) {
//        Log.d("onSuccess", data + "얻은 값");
//
//        // DB에서 얻은 값 파싱하여
//
//        for (int i = 0; i < mapDataItems.length; i++) {
//            final MarkerOptions marker = new MarkerOptions()
//                    .position(new LatLng(mapDataItems[i].getPLACE_COORD_X(), mapDataItems[i].getPLACE_COORD_Y()))
//                    .title(mapDataItems[i].getPLACE_TITLE())
//                    .snippet(mapDataItems[i].getPLACE_ADDRESS())
//                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
//
//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    mGoogleMap.addMarker(marker);
//                }
//            });
//        }
//    }