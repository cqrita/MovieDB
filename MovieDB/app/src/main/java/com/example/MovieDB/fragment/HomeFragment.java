package com.example.MovieDB.fragment;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.MovieDB.R;
import com.example.MovieDB.adapter.MovieListAdapter;
import com.example.MovieDB.data.Movie;
import com.example.MovieDB.network.IMDBNetwork;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    RecyclerView recyclerView;
    private ArrayList movieList = new ArrayList<>(20);
    private IMDBNetwork.HttpCallback httpCallback = new IMDBNetwork.HttpCallback() {
        @Override
        public void onResult(String result) {
            Gson gson = new Gson();
            movieList = gson.fromJson(result, ArrayList.class);
        }
    };
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Log.d("fragment","Home Fragment loaded");
        Log.d("IMDBNetwork","1");
        IMDBNetwork httpTask = new IMDBNetwork("https://api.themoviedb.org/3/trending/all/day?api_key=ee74e4df4dd623e8eb831f2fd274328f", httpCallback);
        httpTask.execute();
        Log.d("IMDBNetwork","2");
        if(movieList!=null){
            recyclerView = getView().findViewById(R.id.home_recycler_view) ;
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            MovieListAdapter adapter = new MovieListAdapter(getActivity(),movieList) ;
            recyclerView.setAdapter(adapter) ;
        }
        return inflater.inflate(R.layout.home, container, false);
    }
}
