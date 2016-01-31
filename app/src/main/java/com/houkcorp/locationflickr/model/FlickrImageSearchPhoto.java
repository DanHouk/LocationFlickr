package com.houkcorp.locationflickr.model;

import com.google.gson.annotations.SerializedName;

public class FlickrImageSearchPhoto {
    @SerializedName("id") private String id;
    @SerializedName("owner") private String owner;
    @SerializedName("secret") private String secret;
    @SerializedName("server") private String server;
    @SerializedName("farm") private int farm;
    @SerializedName("title") private String title;
    @SerializedName("ispublic") private int isPublic;
    @SerializedName("isfriend") private int isFriend;
    @SerializedName("isFamily") private int isFamily;

    public String getId() {
        return id;
    }

    public String getOwner() {
        return owner;
    }

    public String getSecret() {
        return secret;
    }

    public String getServer() {
        return server;
    }

    public int getFarm() {
        return farm;
    }

    public String getTitle() {
        return title;
    }

    public boolean isPublic() {
        return isPublic == 1;
    }

    public boolean isFriend() {
        return isFriend == 1;
    }

    public boolean isFamily() {
        return isFamily == 1;
    }
}