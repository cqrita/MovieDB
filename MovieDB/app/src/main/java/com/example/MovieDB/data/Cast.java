package com.example.MovieDB.data;

import android.os.Parcel;
import android.os.Parcelable;

public class Cast implements Parcelable {
    private String id;
    private String name;
    private String profile_path;
    private String order;
    private String character;
    private String biography;
    private String birthday;
    private String deathday;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfile_path() {
        return profile_path;
    }

    public void setProfile_path(String profile_path) {
        this.profile_path = profile_path;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getDeathday() {
        return deathday;
    }

    public void setDeathday(String deathday) {
        this.deathday = deathday;
    }

    protected Cast(Parcel in) {
        id = in.readString();
        name = in.readString();
        profile_path = in.readString();
        order = in.readString();
        character = in.readString();
        biography = in.readString();
        birthday = in.readString();
        deathday = in.readString();
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
        dest.writeString(name);
        dest.writeString(profile_path);
        dest.writeString(order);
        dest.writeString(character);
        dest.writeString(biography);
        dest.writeString(birthday);
        dest.writeString(deathday);
    }
}
