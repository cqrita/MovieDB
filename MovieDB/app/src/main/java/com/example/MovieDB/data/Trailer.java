package com.example.MovieDB.data;

import android.os.Parcel;
import android.os.Parcelable;

public class Trailer implements Parcelable {
    private String id;
    private String movieId;
    private String key;
    private String site;
    private String title;

    public String getId() {
        return id;
    }

    public String getMovieId() {
        return movieId;
    }

    public String getKey() {
        return key;
    }

    public String getSite() {
        return site;
    }

    public String getTitle() {
        return title;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    private Trailer(Parcel in) {
        id = in.readString();
        movieId = in.readString();
        key = in.readString();
        site = in.readString();
        title = in.readString();
    }

    public static final Creator<Trailer> CREATOR = new Creator<Trailer>() {
        @Override
        public Trailer createFromParcel(Parcel in) {
            return new Trailer(in);
        }

        @Override
        public Trailer[] newArray(int size) {
            return new Trailer[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(movieId);
        dest.writeString(key);
        dest.writeString(site);
        dest.writeString(title);
    }
}
