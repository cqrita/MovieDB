package com.example.MovieDB.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.MovieDB.R;
import com.example.MovieDB.data.Movie;

import java.util.ArrayList;


/**
 * @author arun
 */
public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.MyViewHolder >{
    private ArrayList movieList;
    private Context context;

    public MovieListAdapter (Context context, ArrayList movieList){
        this.context = context;
        this.movieList = movieList;
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView moviePoster;
        public TextView movieName;

        public MyViewHolder(View view) {
            super(view);
            this.moviePoster = view.findViewById(R.id.movie_poster);
            this.movieName = view.findViewById(R.id.movie_title);
        }


//            implements View.OnClickListener {
//        @BindView(R.id.movie_poster)
//        ImageView poster;
//        @BindView(R.id.movie_name)
//        TextView name;
//
//        public Movie movie;
//
//        public ViewHolder(View root) {
//            super(root);
//            ButterKnife.bind(this, root);
//        }
//
//        @Override
//        public void onClick(View view) {
//            MovieListAdapter.this.view.onMovieClicked(movie);
//        }
//        }
//
//        public MovieListAdapter(List<Movie> movies) {
//            this.movies = movies;
//        }
        }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        View holderView= LayoutInflater.from(context).inflate(R.layout.movie_item, viewGroup,
                false);
        return new MyViewHolder(holderView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        Movie movie = (Movie) movieList.get(i);
        Glide.with(context).load("https://image.tmdb.org/t/p/"+movie.getPosterPath()).into(myViewHolder.moviePoster);
        myViewHolder.movieName.setText(movie.getTitle());
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }
}

