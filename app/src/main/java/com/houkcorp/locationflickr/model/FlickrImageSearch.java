package com.houkcorp.locationflickr.model;

import com.google.gson.annotations.SerializedName;

public class FlickrImageSearch {
    @SerializedName("photos") private FlickrImageSearchBasicInfo photos;

    public FlickrImageSearchBasicInfo getPhotos() {
        return photos;
    }
}