package com.example.campustour;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
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

        ImageButton mypage_btn = (ImageButton) findViewById(R.id.mypage);
        mypage_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent intent = new Intent(getApplicationContext(), MyPageActivity.class);
                startActivity(intent);
            }
        });

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

    }

    @Override
    public void onMapReady(@NonNull final GoogleMap googleMap) {

        mMap = googleMap;

        //위치 권한없으면 부여
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] {
                    Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION
            }, 1);
        }
        mMap.setMyLocationEnabled(true);
        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnMyLocationClickListener(this);

        //초기 위치 설정
        LatLng KNU = new LatLng(35.88998331973934, 128.6114154965834);

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(KNU, 16));
    }

    @Override
    public boolean onMyLocationButtonClick() {
        return false;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
    }

    public void showMarker(View view) {

        //맵 초기화
        mMap.clear();
        
        //각 버튼별 타이틀값 할당
        String title;

        switch (view.getId()) {
            case R.id.btn:
                title = "편의점";
                break;
            case R.id.btn2:
                title = "식당";
                break;
            case R.id.btn3:
                title = "카페";
                break;
            case R.id.btn4:
                title = "프린터";
                break;
            case R.id.btn5:
                title = "ATM";
                break;
            case R.id.btn6:
                title = "자판기";
                break;
            default:
                title = "";
        }
        
        //DB에서 값 파싱 후 마커 추가
        db.collection("marker")
                .whereEqualTo("title", title)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("DB", document.getId() + " => " + document.getData());

                                final MarkerOptions marker = new MarkerOptions()
                                        .position(new LatLng(document.getGeoPoint("location").getLatitude(), document.getGeoPoint("location").getLongitude()))
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
