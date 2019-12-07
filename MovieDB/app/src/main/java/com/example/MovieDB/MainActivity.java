package com.example.MovieDB;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.MovieDB.fragment.MapFragment;
import com.google.android.material.navigation.NavigationView;
import com.mancj.materialsearchbar.MaterialSearchBar;

import java.text.SimpleDateFormat;
import java.util.List;

public class MainActivity extends AppCompatActivity  {
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;
    private MapFragment mapFragment;
    LocationManager locationManager;
    Location recentlocation;
    double latitude;
    double longitude;

    //지속적으로 위치 공급자로부터 위치정보를 받아온다.
    LocationListener IListener = new LocationListener() { //계속 듣고 있다가 반응한다.

        @Override
        public void onLocationChanged(Location location) { //위치의 변화가 있을 때.
            latitude = location.getLatitude();
            longitude = location.getLongitude();
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {
        }
        @Override
        public void onProviderEnabled(String s) {
        }
        @Override
        public void onProviderDisabled(String s) {
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            getLocationManager();
        } else {
            if (checkPermission()) {
                getLocationManager(); //2
                recentlocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                longitude = recentlocation.getLongitude();
                latitude = recentlocation.getLatitude();
            } else {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 12345);  //request하기
            }
        }
        fragmentManager = getSupportFragmentManager();
        mapFragment=new MapFragment();
        transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment, mapFragment);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //save last queries to disk
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) { //결과받기
        if (requestCode == 12345) {
            if (checkPermission()) {
                getLocationManager(); //3
                recentlocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                longitude = recentlocation.getLongitude();
                latitude = recentlocation.getLatitude();
            } else {
                Toast.makeText(this, "위치권한 필요", Toast.LENGTH_LONG).show();
                finish(); //MainActivity.java종료(앱종료)
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private boolean checkPermission() {
        if (PackageManager.PERMISSION_GRANTED != checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)) {
            return false;
        } else {
            return true;
        }
    }

    private void getLocationManager() { //LOCATION_SERVICE 시스템 서비스를 이용할 수 있다.
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(locationManager != null)
            //위치수신종료
            locationManager.removeUpdates(IListener);
    }

    @SuppressLint("MissingPermission")
    @Override
    protected void onResume() {
        super.onResume(); //꼭필요
        //위치수신시작
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 10, IListener); //위치수신시간

    }
}