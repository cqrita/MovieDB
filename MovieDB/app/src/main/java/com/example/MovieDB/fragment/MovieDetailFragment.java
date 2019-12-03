package com.example.MovieDB.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
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

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MovieDetailFragment extends Fragment
{
    private MovieDetailFragment view;
    @BindView(R.id.movie_poster)
    ImageView poster;
    @BindView(R.id.movie_name)
    TextView title;
    @BindView(R.id.movie_year)
    TextView releaseDate;
    @BindView(R.id.movie_rating)
    TextView rating;
    @BindView(R.id.movie_description)
    TextView overview;
    @BindView(R.id.favorite)
    FloatingActionButton favorite;
    private Movie movie;
    private Unbinder unbinder;
    @BindView(R.id.list_cast)
    private RecyclerView castAdapter;
    @BindView(R.id.list_trailers)
    private RecyclerView trailerAdapter;
    @BindView(R.id.list_reviews)
    private RecyclerView reviewAdapter;


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
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null)
        {
            this.movie = (Movie) getArguments().get("movie");
            if (movie != null) {
                setView(this);
                showDetails(movie);
                if(movie.getCastList()!= null){
                    setupCastAdapter(movie.getCastList());
                }
                if(movie.getReviewList() != null){
                    setupReviewsAdapter(movie.getReviewList());
                }
                if(movie.getTrailerList()!= null){
                    setupTrailersAdapter(movie.getTrailerList());
                }
            }
        }
    }

    public void setView(MovieDetailFragment view) {
        this.view = view;
    }


    public void showDetails(Movie movie)
    {
        Glide.with(getContext()).load("http://image.tmdb.org/t/p/w342"+movie.getPosterPath()).dontAnimate().into(poster);
        title.setText(movie.getTitle());
        releaseDate.setText(movie.getReleaseDate());
        rating.setText(Double.toString(movie.getVoteAverage()));
        overview.setText(movie.getOverview());
    }

    private void setupTrailersAdapter(List<Trailer> trailerList) {
        trailerAdapter.setLayoutManager(
                new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        trailerAdapter.setHasFixedSize(true);
        trailerAdapter.setAdapter(new TrailersAdapter(getActivity(),trailerList));
        ViewCompat.setNestedScrollingEnabled(trailerAdapter, false);
    }

    private void setupCastAdapter(List<Cast> castList) {
        castAdapter.setLayoutManager(
                new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        castAdapter.setAdapter(new CastAdapter(getActivity(), castList));
        ViewCompat.setNestedScrollingEnabled(castAdapter, false);
    }

    private void setupReviewsAdapter(List<Review> reviewList) {
        reviewAdapter.setLayoutManager(
                new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        reviewAdapter.setAdapter(new ReviewsAdapter(getActivity(), reviewList));
        ViewCompat.setNestedScrollingEnabled(reviewAdapter, false);
    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
    }
}

