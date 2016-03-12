package com.houkcorp.locationflickr.model;

import com.google.gson.annotations.SerializedName;

public class FlickrImageSearchResults {
    @SerializedName("photos") private ImageBasicInfo photos;
    @SerializedName("stat") private String stat;

    public ImageBasicInfo getPhotos() {
        return photos;
    }

    public String getStat() {
        return stat;
    }
}