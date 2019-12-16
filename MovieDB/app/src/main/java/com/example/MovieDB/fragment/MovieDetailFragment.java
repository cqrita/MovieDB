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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.MovieDB.R;
import com.example.MovieDB.adapter.CastAdapter;
import com.example.MovieDB.adapter.ReviewsAdapter;
import com.example.MovieDB.adapter.TrailersAdapter;
import com.example.MovieDB.data.Cast;
import com.example.MovieDB.data.Movie;
import com.example.MovieDB.data.Review;
import com.example.MovieDB.data.Trailer;
import com.example.MovieDB.database.FavoriteDBHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MovieDetailFragment extends Fragment
{
    private Movie movie;
    private ImageView poster;
    private TextView title;
    private TextView releaseDate;
    private RatingBar rating;
    private TextView overview;
    private Button recommend;
    private ArrayList<Trailer> trailerList =new ArrayList<>();
    private ArrayList<Cast> castList =new ArrayList<>();
    private ArrayList<Review> reviewList = new ArrayList<>();
    private FloatingActionButton favorite_button;
    private RecyclerView castView;
    private RecyclerView trailerView;
    private RecyclerView reviewView;
    private FavoriteDBHelper favoriteDbHelper;
    private ProgressDialog progressDialog;
    private int count = 0;
//    private Movie movie;
    public MovieDetailFragment(Movie movie)
    {
        this.movie = movie;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        Log.d("test", this.movie.toString());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.moviedetails, container, false);
        trailerView = rootView.findViewById(R.id.list_trailers);
        trailerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        castView = rootView.findViewById(R.id.list_cast);
        castView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        reviewView = rootView.findViewById(R.id.list_reviews);
        reviewView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
            Log.d("detail",movie.getTitle());
            if (movie != null) {
                progressDialog = new ProgressDialog(getContext());
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.setMessage("\t로딩중...");
                progressDialog.show();
                poster = view.findViewById(R.id.movie_poster);
                title =view.findViewById(R.id.movie_name);
                releaseDate=view.findViewById(R.id.movie_year);
                rating =view.findViewById(R.id.movie_rating);
                overview= view.findViewById(R.id.movie_description);
                recommend = view.findViewById(R.id.recommend);
                Glide.with(Objects.requireNonNull(getContext()))
                        .load("https://image.tmdb.org/t/p/w500"+movie.getPoster_path())
                        .centerCrop()
                        .into(poster);
                title.setText(movie.getTitle());
                releaseDate.setText(movie.getRelease_date());
                rating.setRating((float)movie.getVote_average());// 후에 ratingbar로 수정!
                overview.setText(movie.getOverview());
                recommend.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        RecommendFragment recommendFragment =new RecommendFragment(String.valueOf(movie.getId()));
                        FragmentTransaction ft = Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.fragment, recommendFragment);
                        ft.commit();
                    }
                });
                //DB 생성
                favoriteDbHelper = new FavoriteDBHelper(getActivity());//SQLlite OpenHelpr

                //favoritebutton 클릭 이벤트 구현
                favorite_button= view.findViewById(R.id.btn_favorite);
                favorite_button.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        Log.d("favorite","favorite button clicked");
                        //DB에 저장
                        SQLiteDatabase db = favoriteDbHelper.getWritableDatabase();
                        Cursor cursor = db.rawQuery("select * from favorite where id =?",
                                new String[]{String.valueOf(movie.getId())});
                        if(cursor != null &&cursor.moveToFirst()) {
                            favoriteDbHelper.deleteFavorite(db,movie);
                            Toast.makeText(getContext(), "즐겨찾기에서 삭제되었습니다",Toast.LENGTH_SHORT).show();
                            Log.d("seungrok", movie.getTitle()+ "삭제");
                        } else {
                            assert cursor != null;
                            Log.d("seungrok",
                                    "추가전 cursor movetofirst" + cursor.moveToFirst());

                            favoriteDbHelper.addFavorite(db,movie);
                            Toast.makeText(getContext(), "즐겨찾기에 추가되었습니다",Toast.LENGTH_SHORT).show();
                            Log.d("seungrok", movie.getTitle()+"추가");

                            Cursor cursor2 = db.rawQuery("select * from favorite where id =?",
                                    new String[]{String.valueOf(movie.getId())});
                            Log.d("seungrok",
                                    "추가후 cursor movetofirst" + cursor2.moveToFirst());
                        }
                    }
                });
                Log.d("database", "++++++++++++++++++++++++");
                TrailerAsyncTask trailerAsyncTask = new TrailerAsyncTask();
                trailerAsyncTask.execute(movie.getId());
                CastAsyncTask castAsyncTask = new CastAsyncTask();
                castAsyncTask.execute(movie.getId());
                ReviewAsyncTask reviewAsyncTask = new ReviewAsyncTask();
                reviewAsyncTask.execute(movie.getId());
        }
    }

    public class TrailerAsyncTask extends AsyncTask<Integer, Void, Trailer[]> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Trailer[] trailers) {
            super.onPostExecute(trailers);
            count = count + 1;
            if (count == 3) {
                progressDialog.dismiss();
            }
            Collections.addAll(trailerList, trailers);
            TrailersAdapter adapter=new TrailersAdapter(getContext(),trailerList);
            trailerView.setAdapter(adapter);
            Log.d("trailers",String.valueOf(adapter.getItemCount()));
        }

        @Override
        protected Trailer[] doInBackground(Integer... ints) {
            String m_id = String.valueOf(ints[0]);
            Log.d("Trailer","https://api.themoviedb.org/3/movie/"+m_id+"/videos?api_key=ee74e4df4dd623e8eb831f2fd274328f");
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("https://api.themoviedb.org/3/movie/"+m_id+"/videos?api_key=ee74e4df4dd623e8eb831f2fd274328f")
                    .build();
            try {
                Response response = client.newCall(request).execute();
                Gson gson = new Gson();
                assert response.body() != null;
                JsonElement rootObject = JsonParser.parseReader(Objects.requireNonNull(response.body()).charStream())
                        .getAsJsonObject().get("results");
                return gson.fromJson(rootObject, Trailer[].class);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }
    public class CastAsyncTask extends AsyncTask<Integer, Void, Cast[]> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Cast[] casts) {
            super.onPostExecute(casts);
            count = count + 1;
            if (count == 3) {
                progressDialog.dismiss();
            }
            if(casts != null){
                castList.addAll(Arrays.asList(casts));
                Log.d("Cast",String.valueOf(castList.size()));
            }else {
                Log.d("Cast","error");
            }
            CastAdapter adapter=new CastAdapter(getContext(),castList);
            castView.setAdapter(adapter);
        }

        @Override
        protected Cast[] doInBackground(Integer... ints) {
            String m_id = String.valueOf(ints[0]);
            Log.d("Cast","https://api.themoviedb.org/3/movie/"+m_id+"/casts?api_key=ee74e4df4dd623e8eb831f2fd274328f&language=ko-KR");
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("https://api.themoviedb.org/3/movie/"+m_id+"/casts?api_key=ee74e4df4dd623e8eb831f2fd274328f&language=ko-KR")
                    .build();
            try {
                Response response = client.newCall(request).execute();
                Gson gson = new Gson();
                assert response.body() != null;
                JsonElement rootObject = JsonParser.parseReader(Objects.requireNonNull(response.body()).charStream())
                        .getAsJsonObject().get("cast");
                return gson.fromJson(rootObject, Cast[].class);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }
    public class ReviewAsyncTask extends AsyncTask<Integer, Void, Review[]> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Review[] reviews) {
            super.onPostExecute(reviews);
            count = count + 1;
            if (count == 3) {
                progressDialog.dismiss();
            }
            if(reviews != null){
                Collections.addAll(reviewList, reviews);
            }
            ReviewsAdapter adapter=new ReviewsAdapter(getContext(),reviewList);
            reviewView.setAdapter(adapter);
            Log.d("reviews",String.valueOf(adapter.getItemCount()));
        }

        @Override
        protected Review[]doInBackground(Integer... ints) {
            String m_id = String.valueOf(ints[0]);
            Log.d("Review","https://api.themoviedb.org/3/movie/"+m_id+"/reviews?api_key=ee74e4df4dd623e8eb831f2fd274328f");
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("https://api.themoviedb.org/3/movie/"+m_id+"/reviews?api_key=ee74e4df4dd623e8eb831f2fd274328f")
                    .build();
            try {
                Response response = client.newCall(request).execute();
                Gson gson = new Gson();
                assert response.body() != null;
                JsonElement rootObject = JsonParser.parseReader(response.body().charStream())
                        .getAsJsonObject().get("results");
                return gson.fromJson(rootObject, Review[].class);
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

