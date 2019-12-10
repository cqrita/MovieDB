package com.example.MovieDB.fragment;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.MovieDB.R;
import com.example.MovieDB.adapter.MovieListAdapter;
import com.example.MovieDB.database.FavoriteDBHelper;

import java.util.ArrayList;

public class FavoriteFragment extends Fragment {
    ImageView imageView;
    private SQLiteDatabase mDb;
    private FavoriteDBHelper favoriteDbHelper;
    private RecyclerView recyclerView;



    public FavoriteFragment() {

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("fragment","Favorite Fragment loaded");

//     //DB사용하여 불러오기
//        FavoriteDBHelper dbHelper = new FavoriteDBHelper(this);
//        mDb = dbHelper.getWritableDatabase();
//
//
//








        return inflater.inflate(R.layout.favorite, container, false);

    }

}
