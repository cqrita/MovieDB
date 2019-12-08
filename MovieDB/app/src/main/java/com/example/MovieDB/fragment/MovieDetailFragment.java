package com.example.MovieDB.fragment;


import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MovieDetailFragment extends Fragment
{
    Movie movie;
    ImageView poster;
    TextView title;
    TextView releaseDate;
    TextView rating;
    TextView overview;
    Button recommend;
    ArrayList<Trailer> trailerList =new ArrayList<>();
    ArrayList<Cast> castList =new ArrayList<>();
    ArrayList<Review> reviewList = new ArrayList<>();
    FloatingActionButton favorite;
    private RecyclerView castView;
    private RecyclerView trailerView;
    private RecyclerView reviewView;
    public MovieDetailFragment()
    {
        // Required empty public constructor
    }

    public static MovieDetailFragment getInstance(Movie movie)
    {
        Bundle args = new Bundle();
        MovieDetailFragment movieDetailsFragment = new MovieDetailFragment();
        args.putParcelable("movie", movie);
        movieDetailsFragment.setArguments(args);
        return movieDetailsFragment;
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
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null)
        {
            this.movie = (Movie) getArguments().get("movie");
            Log.d("detail",movie.getTitle());
            if (movie != null) {
                poster = view.findViewById(R.id.movie_poster);
                title =view.findViewById(R.id.movie_name);
                releaseDate=view.findViewById(R.id.movie_year);
                rating =view.findViewById(R.id.movie_rating);
                overview= view.findViewById(R.id.movie_description);
                recommend = view.findViewById(R.id.recommend);
                Glide.with(getContext())
                        .load("https://image.tmdb.org/t/p/w500"+movie.getPoster_path())
                        .centerCrop()
                        .crossFade()
                        .into(poster);
                title.setText(movie.getTitle());
                releaseDate.setText(movie.getRelease_date());
                rating.setText(String.valueOf(movie.getVote_average()));
                overview.setText(movie.getOverview());
                recommend.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        RecommendFragment recommendFragment =RecommendFragment.getInstance(String.valueOf(movie.getId()));
                        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.fragment, recommendFragment);
                        ft.commit();
                    }
                });
                TrailerAsyncTask trailerAsyncTask = new TrailerAsyncTask();
                trailerAsyncTask.execute(movie.getId());
                CastAsyncTask castAsyncTask = new CastAsyncTask();
                castAsyncTask.execute(movie.getId());
                ReviewAsyncTask reviewAsyncTask = new ReviewAsyncTask();
                reviewAsyncTask.execute(movie.getId());
            }
        }
    }


//    private void setupTrailersAdapter(List<Trailer> trailerList) {
//        trailerAdapter.setLayoutManager(
//                new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
//        trailerAdapter.setHasFixedSize(true);
//        trailerAdapter.setAdapter(new TrailersAdapter(getContext(),trailerList));
//        ViewCompat.setNestedScrollingEnabled(trailerAdapter, false);
//    }
//
//    private void setupCastAdapter(List<Cast> castList) {
//        castAdapter.setLayoutManager(
//                new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
//        castAdapter.setAdapter(new CastAdapter(getContext(), castList));
//        ViewCompat.setNestedScrollingEnabled(castAdapter, false);
//    }
//
//    private void setupReviewsAdapter(List<Review> reviewList) {
//        reviewAdapter.setLayoutManager(
//                new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
//        reviewAdapter.setAdapter(new ReviewsAdapter(getContext(), reviewList));
//        ViewCompat.setNestedScrollingEnabled(reviewAdapter, false);
//    }
    public class TrailerAsyncTask extends AsyncTask<Integer, Void, Trailer[]> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Trailer[] trailers) {
            super.onPostExecute(trailers);
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
                Gson gson = new GsonBuilder().create();
                JsonParser parser = new JsonParser();
                JsonElement rootObject = parser.parse(response.body().charStream())
                        .getAsJsonObject().get("results");
                Trailer[] posts = gson.fromJson(rootObject, Trailer[].class);
                return posts;
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
                Gson gson = new GsonBuilder().create();
                JsonParser parser = new JsonParser();
                JsonElement rootObject = parser.parse(response.body().charStream())
                        .getAsJsonObject().get("cast");
                Cast[] posts = gson.fromJson(rootObject, Cast[].class);
                return posts;
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
                Gson gson = new GsonBuilder().create();
                JsonParser parser = new JsonParser();
                JsonElement rootObject = parser.parse(response.body().charStream())
                        .getAsJsonObject().get("results");
                Review[] posts = gson.fromJson(rootObject, Review[].class);
                return posts;
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

