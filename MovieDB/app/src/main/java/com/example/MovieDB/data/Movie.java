package com.example.MovieDB.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.squareup.moshi.Json;

import java.util.List;

public class Movie implements Parcelable {
    private int id;
    private String title;
    private String original_title;
    private String poster_path;
    private String overview;
    private String backdrop_path;
    private String release_date;
    private int[] genre_ids;
    private double vote_average;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public int[] getGenre_ids() {
        return genre_ids;
    }

    public void setGenre_ids(int[] genre_ids) {
        this.genre_ids = genre_ids;
    }

    public double getVote_average() {
        return vote_average;
    }

    public void setVote_average(double vote_average) {
        this.vote_average = vote_average;
    }

    protected Movie(Parcel in) {
        id = in.readInt();
        title = in.readString();
        original_title = in.readString();
        poster_path = in.readString();
        overview = in.readString();
        backdrop_path = in.readString();
        release_date = in.readString();
        genre_ids = in.createIntArray();
        vote_average = in.readDouble();
    }
    public Movie(){

    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(original_title);
        dest.writeString(poster_path);
        dest.writeString(overview);
        dest.writeString(backdrop_path);
        dest.writeString(release_date);
        dest.writeIntArray(genre_ids);
        dest.writeDouble(vote_average);
    }
}
