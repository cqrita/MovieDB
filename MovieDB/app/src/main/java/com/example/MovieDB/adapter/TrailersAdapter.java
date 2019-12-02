package com.example.MovieDB.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.MovieDB.R;
import com.example.MovieDB.data.Trailer;

import java.util.List;

/**
 * @author Yassin Ajdi.
 */
public class TrailersAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private VideoView videoTrailer;
    private TextView trailerName;
    private List<Trailer> trailerList;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private MyViewHolder(View view) {
            super(view);
            videoTrailer = view.findViewById(R.id.videoTrailer);
            trailerName =view.findViewById(R.id.trailerName);
        }
    }

    public TrailersAdapter(Context context, List<Trailer> trailerList) {
        this.trailerList = trailerList;
        this.context =context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.trailer, parent, false);
        return new TrailersAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Trailer trailer = trailerList.get(position);
        videoTrailer.setVideoPath("https://www.youtube.com/watch?v="+trailer.getKey());
        final MediaController mediaController =
                new MediaController(context);
        videoTrailer.setMediaController(mediaController);
        videoTrailer.start();
        videoTrailer.postDelayed(new Runnable() {
            @Override
            public void run() {
                mediaController.show(0);
                videoTrailer.pause();
            }
        }, 100);
        trailerName.setText(trailer.getTitle());
    }

    @Override
    public int getItemCount() {
        return trailerList != null ? trailerList.size() : 0;
    }

}
