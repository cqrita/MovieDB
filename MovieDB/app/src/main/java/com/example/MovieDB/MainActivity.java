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




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("onCreate","1");
        super.onCreate(savedInstanceState);
        Log.d("version check","a");
        setContentView(R.layout.activity_main);
        Log.d("version check","b");

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            fragmentManager = getSupportFragmentManager();
            mapFragment=new MapFragment();
            transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.fragment, mapFragment);
        } else {
            if (checkPermission()) {
                Log.d("onCreate","4");
                fragmentManager = getSupportFragmentManager();
                mapFragment=new MapFragment();
                Log.d("permission","a");
                transaction = fragmentManager.beginTransaction();
                transaction.add(R.id.fragment, mapFragment);
                transaction.commit();
                Log.d("permission","b");
            } else {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 12345);  //request하기
            }
        }


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
                Log.d("onCreate","5");
                fragmentManager = getSupportFragmentManager();
                mapFragment=new MapFragment();
                transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.fragment, mapFragment);

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


    @Override
    protected void onPause() {
        super.onPause();
        /*
        if(locationManager != null)
            //위치수신종료
            locationManager.removeUpdates(IListener);

         */
    }

    @SuppressLint("MissingPermission")
    @Override
    protected void onResume() {
        super.onResume(); //꼭필요
        /*
        //위치수신시작
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 10, IListener); //위치수신시간
         */

    }
}