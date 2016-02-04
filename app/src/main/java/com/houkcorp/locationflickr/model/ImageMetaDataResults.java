package com.houkcorp.locationflickr.model;

import com.google.gson.annotations.SerializedName;

public class ImageMetaDataResults {
    @SerializedName("photo") private PhotoMetaData photo;
    @SerializedName("stat") private String stat;

    public PhotoMetaData getPhoto() {
        return photo;
    }

    public String getStat() {
        return stat;
    }
}