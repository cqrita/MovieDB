package com.example.MovieDB;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import com.example.MovieDB.database.FavoriteDBHelper;
import com.example.MovieDB.fragment.FavoriteFragment;
import com.example.MovieDB.fragment.HomeFragment;
import com.example.MovieDB.fragment.SearchActorFragment;
import com.example.MovieDB.fragment.SearchFragment;

import java.util.List;


public class MainActivity extends AppCompatActivity {
    private List<String> lastSearches;
    private DrawerLayout drawer;
    private Spinner spinner;
    private boolean search=true;
    long first_time;
    long second_time;
    private FavoriteDBHelper favoriteDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("MainACtivity", "Seungrok");
        setContentView(R.layout.activity_main);
        Button button1 = findViewById(R.id.button1);
        Button button2 = findViewById(R.id.button2);
        Button button3 = findViewById(R.id.button3);
        SearchView searchView = findViewById(R.id.searchView);
        Spinner spinner = findViewById(R.id.spinner);
        //시작 프레그먼트 지정
        HomeFragment homeFragment = new HomeFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment, homeFragment).commitAllowingStateLoss();

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HomeFragment home =new HomeFragment();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.fragment, home);
                ft.commit();

            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FavoriteFragment favorite= new FavoriteFragment();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.fragment, favorite);
                ft.commit();
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SearchFragment search= SearchFragment.getInstance("Nothing");
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.fragment, search);
                ft.commit();
            }
        });
        String[] category = new String[2];
        category[0] = "영화";
        category[1] = "배우";
        ArrayAdapter arrayAdapter = new ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,category);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==0){
                    search=true;
                }else{
                    search=false;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(search){
                    SearchFragment search= SearchFragment.getInstance(query);
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.fragment, search);
                    ft.commit();
                }else{
                    SearchActorFragment search= SearchActorFragment.getInstance(query);
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.fragment, search);
                    ft.commit();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //save last queries to disk

    }

    @Override
    public void onBackPressed() {
        second_time = System.currentTimeMillis();
        if(second_time - first_time < 2000){
            super.onBackPressed();
            finishAffinity();
        }else{
            HomeFragment homeFragment = new HomeFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment, homeFragment).commitAllowingStateLoss();
        }
        first_time = System.currentTimeMillis();
    }
}

