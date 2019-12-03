package com.example.MovieDB;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.example.MovieDB.toolbar.MenuBar;
import com.example.MovieDB.toolbar.SearchBar;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

import butterknife.BindView;

public class MainActivity extends AppCompatActivity {
    private List<String> lastSearches;
    private DrawerLayout drawer;
    @BindView(R.id.Msearchbar)
    private SearchBar searchBar;
    @BindView(R.id.Mmenubar)
    private MenuBar menuBar;
    @BindView(R.id.fragment)
    private Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        searchBar= new SearchBar(this);
        menuBar =new MenuBar(this);
        menuBar.getMenu();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //save last queries to disk

    }

}

