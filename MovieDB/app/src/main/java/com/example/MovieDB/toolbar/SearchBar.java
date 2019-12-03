package com.example.MovieDB.toolbar;

import android.content.Context;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.Spinner;

import com.example.MovieDB.R;

import butterknife.BindView;

public class SearchBar extends RelativeLayout {
    @BindView(R.id.searchView)
    private SearchView searchView;
    @BindView(R.id.spinner)
    private Spinner spinner;
    public SearchBar(Context context) {
        super(context);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }
}
