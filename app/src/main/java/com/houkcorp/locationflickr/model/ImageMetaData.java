package com.houkcorp.locationflickr.model;

import com.google.gson.annotations.SerializedName;

public class ImageMetaData {
    @SerializedName("photo") private ImageMetaDataPhoto photo;
    @SerializedName("stat") private String stat;

    public ImageMetaDataPhoto getPhoto() {
        return photo;
    }

    public String getStat() {
        return stat;
    }
}