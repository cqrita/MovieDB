package com.example.MovieDB.data;

import android.os.Parcel;
import android.os.Parcelable;

public class Review implements Parcelable {
    private String imageAuthor;
    private String textAuthor;
    private String textContent;

    public String getImageAuthor() {
        return imageAuthor;
    }

    public String getTextAuthor() {
        return textAuthor;
    }

    public String getTextContent() {
        return textContent;
    }

    public void setImageAuthor(String imageAuthor) {
        this.imageAuthor = imageAuthor;
    }

    public void setTextAuthor(String textAuthor) {
        this.textAuthor = textAuthor;
    }

    public void setTextContent(String textContent) {
        this.textContent = textContent;
    }

    private Review(Parcel in) {
        imageAuthor = in.readString();
        textAuthor= in.readString();
        textContent = in.readString();
    }

    public static final Creator<Review> CREATOR = new Creator<Review>() {
        @Override
        public Review createFromParcel(Parcel in) {
            return new Review(in);
        }

        @Override
        public Review[] newArray(int size) {
            return new Review[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(imageAuthor);
        dest.writeString(textAuthor);
        dest.writeString(textContent);
    }
}
