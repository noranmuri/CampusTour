package com.example.campustour;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


public class MainActivity extends FragmentActivity
        implements OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener, GoogleMap.OnMyLocationClickListener {

    private GoogleMap mMap;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

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

//        LatLng KNU = new LatLng(35.88998331973934, 128.6114154965834);
//
//        MarkerOptions markerOptions = new MarkerOptions();
//        markerOptions.position(KNU);
//        markerOptions.title("경북대학교");
//        markerOptions.snippet("대구캠퍼스");
//        mMap.addMarker(markerOptions);
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(KNU, 16));
    }

    @Override
    public boolean onMyLocationButtonClick() {
        return false;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
    }

    public void showBtn(View view) {

        // DB에서 얻은 값 파싱하여

        db.collection("marker")
                .whereEqualTo("title", "편의점")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("DB", document.getId() + " => " + document.getData());

                                final MarkerOptions marker = new MarkerOptions()
                                        .position(new LatLng((double)document.get("latitude"), (double)document.get("longitude")))
                                        .title((String) document.get("title"));
//                                        .snippet(document.get("snippet"))

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        mMap.addMarker(marker);
                                    }
                                });
                            }
                        } else {
                            Log.d("DB", "Error getting documents: ", task.getException());
                        }
                    }
                });
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