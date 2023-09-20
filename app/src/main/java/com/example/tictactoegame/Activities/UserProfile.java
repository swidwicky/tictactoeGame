package com.example.tictactoegame.Activities;

import android.os.Parcel;
import android.os.Parcelable;

public class UserProfile implements Parcelable {
    private String username;
    private int avatarResourceId; // This stores the avatar resource ID

    public UserProfile(String username, int avatarResourceId) {
        this.username = username;
        this.avatarResourceId = avatarResourceId;
    }

    public String getUsername() {
        return username;
    }

    public int getAvatarResourceId() {
        return avatarResourceId;
    }

    // Parcelable implementation
    protected UserProfile(Parcel in) {
        username = in.readString();
        avatarResourceId = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(username);
        dest.writeInt(avatarResourceId);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<UserProfile> CREATOR = new Parcelable.Creator<UserProfile>() {
        @Override
        public UserProfile createFromParcel(Parcel in) {
            return new UserProfile(in);
        }

        @Override
        public UserProfile[] newArray(int size) {
            return new UserProfile[size];
        }
    };
}
