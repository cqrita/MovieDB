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
import com.example.MovieDB.data.Review;

import java.util.List;


public class ReviewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<Review> reviewList;
    private ImageView imageAuthor;
    private TextView textAuthor;
    private TextView textContent;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private MyViewHolder(View view) {
            super(view);
            imageAuthor = view.findViewById(R.id.imageAuthor);
            textAuthor =view.findViewById(R.id.textAuthor);
            textContent =view.findViewById(R.id.textContent);
        }
    }

    public ReviewsAdapter(Context context, List<Review> reviewList) {
        this.context = context;
        this.reviewList = reviewList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.review, parent, false);
        return new ReviewsAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Review review = reviewList.get(position);
        Glide.with(context)
                .load(review.getImageAuthor())
                .dontAnimate()
                .into(imageAuthor);
        textAuthor.setText(review.getTextAuthor());
        textContent.setText(review.getTextContent());
    }

    @Override
    public int getItemCount() {
        return reviewList != null ? reviewList.size() : 0;
    }
}

