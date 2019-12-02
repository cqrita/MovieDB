package com.example.MovieDB.data;

import android.os.Parcel;
import android.os.Parcelable;

public class Cast implements Parcelable {
    private String id;
    private String actorName;
    private String profileImagePath;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setActorName(String actorName) {
        this.actorName = actorName;
    }

    public void setProfileImagePath(String profileImagePath) {
        this.profileImagePath = profileImagePath;
    }

    public String getActorName() {
        return actorName;
    }

    public String getProfileImagePath() {
        return profileImagePath;
    }

    protected Cast(Parcel in) {
        id = in.readString();
        actorName = in.readString();
        profileImagePath = in.readString();
    }

    public static final Creator<Cast> CREATOR = new Creator<Cast>() {
        @Override
        public Cast createFromParcel(Parcel in) {
            return new Cast(in);
        }

        @Override
        public Cast[] newArray(int size) {
            return new Cast[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(actorName);
        dest.writeString(profileImagePath);
    }
}
