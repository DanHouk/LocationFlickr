package com.houkcorp.locationflickr.model;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

public class FlickrImage implements Parcelable {
    private Bitmap bitmap;
    private int farm;
    private String id;
    private String owner;
    private String secret;
    private int server;


    public FlickrImage() {
    }

    protected FlickrImage(Parcel in) {
        bitmap = in.readParcelable(Bitmap.class.getClassLoader());
        farm = in.readInt();
        id = in.readString();
        owner = in.readString();
        secret = in.readString();
        server = in.readInt();
    }

    public static final Creator<FlickrImage> CREATOR = new Creator<FlickrImage>() {
        @Override
        public FlickrImage createFromParcel(Parcel in) {
            return new FlickrImage(in);
        }

        @Override
        public FlickrImage[] newArray(int size) {
            return new FlickrImage[size];
        }
    };

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public int getFarm() {
        return farm;
    }

    public void setFarm(int farm) {
        this.farm = farm;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public int getServer() {
        return server;
    }

    public void setServer(int server) {
        this.server = server;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(bitmap, flags);
        dest.writeInt(farm);
        dest.writeString(id);
        dest.writeString(owner);
        dest.writeString(secret);
        dest.writeInt(server);
    }
}