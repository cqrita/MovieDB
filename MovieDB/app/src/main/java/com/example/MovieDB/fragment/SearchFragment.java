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
import com.example.MovieDB.data.Api;
import com.example.MovieDB.data.Movie;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SearchFragment extends Fragment {
    private int page = 1;
    private String string;
    private MovieListAdapter adapter;
    private RecyclerView recyclerView;
    private ProgressDialog progressDialog;
    private ArrayList<Movie> movieList = new ArrayList<>();
    private boolean stop = false;
    private MyAsyncTask.HttpCallback httpCallback = new MyAsyncTask.HttpCallback() {
        @Override
        public void onResult(Movie[] result) {
            progressDialog.dismiss();
            ArrayList<Movie> movieList = new ArrayList<>();
            if (result != null) {
                if (result.length == 0) {
                    stop = true;
                }
                movieList.addAll(Arrays.asList(result));
            } else {
                stop = true;
            }
            Log.d("IMDBNetwork", "adapter");
            adapter.addMovieList(movieList);
            adapter.notifyDataSetChanged();
        }
    };

    public SearchFragment(String string) {
        this.string = string;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.search, container, false);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("\t로딩중...");
        progressDialog.show();
        MyAsyncTask mAsyncTask = new MyAsyncTask(httpCallback, page, string);
        mAsyncTask.execute();
        recyclerView = view.findViewById(R.id.search_recycler_view) ;
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),4));
        adapter = new MovieListAdapter(getContext(), movieList);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                MyAsyncTask.HttpCallback httpCallback = new MyAsyncTask.HttpCallback() {
                    @Override
                    public void onResult(Movie[] result) {
                        progressDialog.dismiss();
                        ArrayList<Movie> movieList = new ArrayList<>();
                        if (result != null) {
                            if (result.length == 0) {
                                stop = true;
                            }
                            movieList.addAll(Arrays.asList(result));
                        } else {
                            stop = true;
                        }
                        Log.d("IMDBNetwork", "adapter");
                        adapter.addMovieList(movieList);
                        adapter.notifyDataSetChanged();
                    }
                };
                super.onScrollStateChanged(recyclerView, newState);
                if (!recyclerView.canScrollVertically(1) & !stop) {
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progressDialog.setMessage("\t로딩중...");
                    progressDialog.show();
                    page = page + 1;
                    MyAsyncTask mAsyncTask = new MyAsyncTask(httpCallback, page, string);
                    mAsyncTask.execute();
                }
            }
        });
        return view;
    }

    public static class MyAsyncTask extends AsyncTask<String, Void, Movie[]> {
        private HttpCallback httpCallback;
        private int page;
        private String string;

        MyAsyncTask(HttpCallback httpCallback, int page, String string) {
            this.httpCallback = httpCallback;
            this.page = page;
            this.string = string;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected Movie[] doInBackground(String... strings) {
            OkHttpClient client = new OkHttpClient();
            Api api = new Api();
            String api_key = api.getApikey2();
            Request request = new Request.Builder()
                    .url("https://api.themoviedb.org/3/search/movie?api_key=" + Api.apikey2 +
                            "&language=ko-KR" + "&query=" + string + "&page=" + page)
                    .build();
            try {
                Response response = client.newCall(request).execute();
                Gson gson = new Gson();
                assert response.body() != null;
                JsonElement rootObject = JsonParser.parseReader(Objects.requireNonNull(response.body()).charStream())
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
            if (this.httpCallback != null) {
                this.httpCallback.onResult(result);
            }
        }

        interface HttpCallback {
            void onResult(Movie[] result);
        }
    }
}
