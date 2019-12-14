package com.example.MovieDB.fragment;


import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.MovieDB.R;
import com.example.MovieDB.data.Cast;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ActorDetailFragment extends Fragment {
    Cast cast;
    ImageView poster;
    TextView name;
    TextView castYear;
    TextView biography;
    int castInt;
    public ActorDetailFragment(int castInt)
    {
        this.castInt = castInt;
    }


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.castdetails, container, false);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        poster = view.findViewById(R.id.cast_poster);
        name = view.findViewById(R.id.cast_name);
        castYear = view.findViewById(R.id.cast_year);
        biography = view.findViewById(R.id.cast_biography);
        CastAsyncTask castAsyncTask = new CastAsyncTask();
        castAsyncTask.execute(castInt);
    }
    public class CastAsyncTask extends AsyncTask<Integer, Void, Cast> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Cast cast1) {
            super.onPostExecute(cast1);
            cast=cast1;
            Log.d("show error",cast1.getName());
            Log.d("show error",cast1.getBirthday());
            Log.d("show error",cast1.getProfile_path());
            Glide.with(getContext())
                    .load("https://image.tmdb.org/t/p/w500"+cast1.getProfile_path())
                    .centerCrop()
                    .crossFade()
                    .into(poster);
            name.setText(cast1.getName());
            biography.setText(cast1.getBiography());
            if(cast1.getDeathday()==null){
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                Date date = null;
                try {
                    date = formatter.parse(cast1.getBirthday());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Calendar cal = Calendar.getInstance();
                cal.setTime(date);
                Calendar today = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                cal.set(year, month+1, day);
                int age = today.get(Calendar.YEAR) - cal.get(Calendar.YEAR);
                if (today.get(Calendar.DAY_OF_YEAR) < cal.get(Calendar.DAY_OF_YEAR)){
                    age--;
                }
                castYear.setText(String.valueOf(age));
            }
        }

        @Override
        protected Cast doInBackground(Integer... ints) {
            String m_id = String.valueOf(ints[0]);
            Log.d("CastDetail", "https://api.themoviedb.org/3/person/"+m_id+"?api_key=ee74e4df4dd623e8eb831f2fd274328f");
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("https://api.themoviedb.org/3/person/"+m_id+"?api_key=ee74e4df4dd623e8eb831f2fd274328f")
                    .build();
            try {
                Response response = client.newCall(request).execute();
                Gson gson = new GsonBuilder().create();
                JsonParser parser = new JsonParser();
                JsonElement rootObject = parser.parse(response.body().charStream())
                        .getAsJsonObject();
                Cast post = gson.fromJson(rootObject, Cast.class);
                return post;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }
    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
    }
}


