package com.example.MovieDB.fragment;

import android.app.ProgressDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.MovieDB.R;
import com.example.MovieDB.adapter.MovieListAdapter;
import com.example.MovieDB.data.Movie;
import com.example.MovieDB.database.FavoriteDBHelper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.Arrays;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class FavoriteFragment extends Fragment {
    private int page=0;
    private RecyclerView recyclerView;
    private ArrayList<Movie> movieList = new ArrayList<>();
    MovieListAdapter adapter;
    private boolean stop = false;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.favorite, container, false);
        Log.d("seungrok","FavoriteFragment");
        recyclerView = view.findViewById(R.id.favorite_recycler_view) ;
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),4));

        FavoriteDBHelper favoriteDbHelper=  new FavoriteDBHelper(getActivity());
        movieList= favoriteDbHelper.getAllFavorite();
        adapter = new MovieListAdapter(getContext(), movieList);

        recyclerView.setAdapter(adapter);
        return view;
    }

}
