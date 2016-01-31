package com.houkcorp.locationflickr.model;

import com.google.gson.annotations.SerializedName;

public class FlickrImageSearch {
    @SerializedName("photos") private FlickrImageSearchBasicInfo photos;
    @SerializedName("stat") private String stat;

    public FlickrImageSearchBasicInfo getPhotos() {
        return photos;
    }

    public String getStat() {
        return stat;
    }
}