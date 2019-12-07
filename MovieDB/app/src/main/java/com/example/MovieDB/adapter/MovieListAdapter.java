package com.example.MovieDB.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.MovieDB.R;


/**
 * @author arun
 */
public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.MyViewHolder >{
//    private List<Movie> movies;
    private String[] textSet;
//    private Context context;
    private String[] imgSet;

    public MovieListAdapter (String[] textSet,String[] imgSet ){
        this.textSet = textSet;
        this.imgSet = imgSet;
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView moviePoster;
        public TextView movieName;

        public MyViewHolder(View view) {
            super(view);

            // 뷰 객체에 대한 참조. (hold strong reference)
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
        myViewHolder.moviePoster.setText(imgSet[i]);
        myViewHolder.movieName.setText(textSet[i]);

    }

//    @Override
//        public void onBindViewHolder(MyViewHolder myViewholder, int i) {
//
//            //    holder.itemView.setOnClickListener(holder);
//
//            myViewholder.moviePoster.setText(imgSet[i]);
//
//
//
//
//            holder. = movies.get(position);
//            holder.name.setText(holder.movie.getTitle());
//
//            RequestOptions options = new RequestOptions()
//                    .centerCrop()
//                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
//                    .priority(Priority.HIGH);
//
//            Glide.with(______)
//                    .asBitmap()
//                    .load(Api.getPosterPath(holder.movie.getPosterPath()))
//                    .apply(options)
//                    .into(new BitmapImageViewTarget(holder.poster) {
//                        @Override
//                        public void onResourceReady(Bitmap bitmap, @Nullable Transition<? super Bitmap> transition) {
//                            super.onResourceReady(bitmap, transition);
//                            Palette.from(bitmap).generate(palette -> setBackgroundColor(palette, holder));
//                        }
//                    });
//

        @Override
        public int getItemCount() {
            return textSet.length > imgSet.length ? textSet.length : imgSet.length;
        }
    }

