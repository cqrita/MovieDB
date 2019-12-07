package com.example.MovieDB;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.example.MovieDB.fragment.FavoriteFragment;
import com.example.MovieDB.fragment.HomeFragment;
import com.example.MovieDB.fragment.SearchFragment;
import com.example.MovieDB.toolbar.MenuBar;
import com.example.MovieDB.toolbar.SearchBar;
import com.google.android.material.button.MaterialButton;

import java.util.List;


public class MainActivity extends AppCompatActivity {
    private List<String> lastSearches;
    private DrawerLayout drawer;
//    @BindView(R.id.Msearchbar)
//    private SearchBar searchBar;
    private MenuBar menuBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("MainACtivity", "Seungrok");
        setContentView(R.layout.activity_main);
//        searchBar= new SearchBar(this);
        menuBar =new MenuBar(this);

        Button button1 = findViewById(R.id.button1);
        Button button2 = findViewById(R.id.button2);
        Button button3 = findViewById(R.id.button3);

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
                SearchFragment search= new SearchFragment();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.add(R.id.fragment, search);
                ft.commit();
            }
        });

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //save last queries to disk

    }

}

