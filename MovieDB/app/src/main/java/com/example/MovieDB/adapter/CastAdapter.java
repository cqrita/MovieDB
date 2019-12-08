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
import com.example.MovieDB.data.Cast;

import java.util.List;

public class CastAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private ImageView imageCastProfile;
    private TextView textCastName;
    private TextView order;
    private TextView character;
    private List<Cast> castList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private MyViewHolder(View view) {
            super(view);
            imageCastProfile = view.findViewById(R.id.imageCastProfile);
            textCastName =view.findViewById(R.id.textCastName);
            order=view.findViewById(R.id.order);
            character= view.findViewById(R.id.character);
        }
    }

    public CastAdapter(Context context, List<Cast> castList) {
        this.castList = castList;
        this.context =context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cast, parent, false);
        MyViewHolder holder = new MyViewHolder(itemView);
        holder.setIsRecyclable(false);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Log.d("cast1",castList.get(position).getName());
        Glide.with(context)
                .load("https://image.tmdb.org/t/p/w185"+castList.get(position).getProfile_path())
                .into(imageCastProfile);
        textCastName.setText(castList.get(position).getName());
        order.setText(castList.get(position).getOrder());
        character.setText(castList.get(position).getCharacter());
    }

    @Override
    public int getItemCount() {
        return castList.size();
    }

}

