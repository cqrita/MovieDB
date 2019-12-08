package com.example.MovieDB.fragment;


import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.MovieDB.R;
import com.example.MovieDB.adapter.MovieListAdapter;
import com.example.MovieDB.data.Movie;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.Arrays;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class RecommendFragment extends Fragment {
    String string;
    private RecyclerView recyclerView;
    private ArrayList<Movie> movieList = new ArrayList<>();
    public static RecommendFragment getInstance(String string)
    {
        Bundle args = new Bundle();
        RecommendFragment recommendFragment = new RecommendFragment();
        args.putString("String",string);
        recommendFragment.setArguments(args);
        return recommendFragment;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.recommend, container, false);
        RecommendFragment.MyAsyncTask mAsyncTask = new RecommendFragment.MyAsyncTask();
        mAsyncTask.execute();
        recyclerView = view.findViewById(R.id.recommend_recycler_view) ;
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),4));
        return view;
    }
    public class MyAsyncTask extends AsyncTask<String, Void, Movie[]> {
        ProgressDialog progressDialog = new ProgressDialog(getContext());
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("\t로딩중...");
            //show dialog
            progressDialog.show();
        }
        @Override
        protected Movie[] doInBackground(String... strings) {
            OkHttpClient client = new OkHttpClient();
            string =  (String)getArguments().get("String");
            Log.d("string",string);
            Request request = new Request.Builder()
                    .url("https://api.themoviedb.org/3/movie/"+string+"/recommendations?api_key=ee74e4df4dd623e8eb831f2fd274328f&language=ko-KR")
                    .build();
            try {
                Response response = client.newCall(request).execute();
                Gson gson = new GsonBuilder().create();
                JsonParser parser = new JsonParser();
                assert response.body() != null;
                JsonElement rootObject = parser.parse(response.body().charStream())
                        .getAsJsonObject().get("results");
                return gson.fromJson(rootObject, Movie[].class);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Movie[] result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            if(result!=null){
                movieList.addAll(Arrays.asList(result));
            }
            Log.d("IMDBNetwork","adapter");
            MovieListAdapter adapter = new MovieListAdapter(getContext(), movieList);
            recyclerView.setAdapter(adapter) ;
        }
    }
}
