package com.example.MovieDB.adapter;

import android.content.Context;
import android.util.Log;
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
 */public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.RecyclerViewHolders>{

    private ArrayList<Movie> mMovieList;
    private LayoutInflater mInflate;
    private Context mContext;

    //constructor
    public MovieListAdapter(Context context, ArrayList<Movie> itemList) {
        this.mContext = context;
        this.mInflate = LayoutInflater.from(context);
        this.mMovieList = itemList;
    }

    @NonNull
    @Override
    public RecyclerViewHolders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("www","www1");
        View view = mInflate.inflate(R.layout.movie_item, parent, false);
        return new RecyclerViewHolders(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolders holder, int position) {

        //포스터만 출력하자.
        String url = "https://image.tmdb.org/t/p/w500" + mMovieList.get(position).getPosterPath();
        Glide.with(mContext)
                .load(url)
                .centerCrop()
                .crossFade()
                .error(R.drawable.ic_launcher_background)
                .into(holder.imageView);
        holder.textView.setText(mMovieList.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return this.mMovieList.size();
    }


    //뷰홀더 - 따로 클래스 파일로 만들어도 된다.
    public static class RecyclerViewHolders extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView textView;
        public RecyclerViewHolders(View itemView) {
            super(itemView);
            Log.d("www","www2");
            imageView = (ImageView) itemView.findViewById(R.id.movie_poster);
            textView = itemView.findViewById(R.id.movie_title);
        }
    }

}
