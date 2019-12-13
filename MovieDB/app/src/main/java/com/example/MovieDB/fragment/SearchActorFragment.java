package com.example.MovieDB.fragment;


import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.MovieDB.R;
import com.example.MovieDB.adapter.CastListAdapter;
import com.example.MovieDB.data.Cast;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;


import java.util.ArrayList;
import java.util.Arrays;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SearchActorFragment extends Fragment {
    private int page=0;
    String string;
    CastListAdapter adapter;
    private RecyclerView recyclerView;
    private ArrayList<Cast> castList = new ArrayList<>();
    private boolean stop = false;

    public SearchActorFragment(String string) {
        this.string = string;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.search, container, false);
        SearchActorFragment.MyAsyncTask mAsyncTask = new SearchActorFragment.MyAsyncTask();
        mAsyncTask.execute();
        recyclerView = view.findViewById(R.id.search_recycler_view) ;
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),4));
        adapter = new CastListAdapter(getContext(), castList);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!recyclerView.canScrollVertically(1)&& stop==false) {
                    SearchActorFragment.MyAsyncTask mAsyncTask = new SearchActorFragment.MyAsyncTask();
                    mAsyncTask.execute();
                }
            }
        });
        return view;
    }
    public class MyAsyncTask extends AsyncTask<String, Void, Cast[]> {
        ProgressDialog progressDialog = new ProgressDialog(getContext());
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("\t로딩중...");
            progressDialog.show();
        }
        @Override
        protected Cast[] doInBackground(String... strings) {
            OkHttpClient client = new OkHttpClient();
            page=page+1;
            Request request = new Request.Builder()
                    .url("https://api.themoviedb.org/3/search/person?api_key=ee74e4df4dd623e8eb831f2fd274328f&language=ko-KR&query="+string+"&page="+page+"&include_adult=false")
                    .build();
            try {
                Response response = client.newCall(request).execute();
                Gson gson = new GsonBuilder().create();
                JsonParser parser = new JsonParser();
                assert response.body() != null;
                JsonElement rootObject = parser.parse(response.body().charStream())
                        .getAsJsonObject().get("results");
                return gson.fromJson(rootObject, Cast[].class);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Cast[] result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            ArrayList<Cast> castList = new ArrayList<>();
            if(result== null){
                stop=true;
            }
            if(result!=null){
                castList.addAll(Arrays.asList(result));
            }
            Log.d("IMDBNetwork","adapter");
            adapter.addCastList(castList);
            adapter.notifyDataSetChanged();
        }
    }
}
