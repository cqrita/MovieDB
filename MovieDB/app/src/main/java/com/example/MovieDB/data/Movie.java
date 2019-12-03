package com.example.MovieDB.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.squareup.moshi.Json;

import java.util.List;

public class Movie implements Parcelable {

    private List<Trailer> trailerList;
    private List<Cast> castList;
    private List<Review> reviewList;
    private String id;
    private String overview;
    @Json(name = "release_date")
    private String releaseDate;
    @Json(name = "poster_path")
    private String posterPath;
    private String title;
    @Json(name = "vote_average")
    private double voteAverage;

    public String getId() {
        return id;
    }

    public String getOverview() {
        return overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getTitle() {
        return title;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public List<Trailer> getTrailerList() {
        return trailerList;
    }

    public List<Cast> getCastList() {
        return castList;
    }

    public List<Review> getReviewList() {
        return reviewList;
    }

    public void setTrailerList(List<Trailer> trailerList) {
        this.trailerList = trailerList;
    }

    public void setCastList(List<Cast> castList) {
        this.castList = castList;
    }

    public void setReviewList(List<Review> reviewList) {
        this.reviewList = reviewList;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    private Movie(Parcel in) {
        id = in.readString();
        overview = in.readString();
        releaseDate = in.readString();
        posterPath = in.readString();
        title = in.readString();
        voteAverage = in.readDouble();
        in.readList(castList,Cast.class.getClassLoader());
        in.readList(reviewList,Review.class.getClassLoader());
        in.readList(trailerList,Trailer.class.getClassLoader());
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
        dest.writeString(id);
        dest.writeString(overview);
        dest.writeString(releaseDate);
        dest.writeString(posterPath);
        dest.writeString(title);
        dest.writeDouble(voteAverage);
        dest.writeList(castList);
        dest.writeList(reviewList);
        dest.writeList(trailerList);
    }
}
