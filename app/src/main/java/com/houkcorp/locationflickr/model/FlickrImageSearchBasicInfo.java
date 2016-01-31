package com.houkcorp.locationflickr.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class FlickrImageSearchBasicInfo {
    @SerializedName("page") private int page;
    @SerializedName("pages") private int pages;
    @SerializedName("perpage") private int perPage;
    @SerializedName("total") private String total;

    @SerializedName("photo") private ArrayList<FlickrImageSearchPhoto> photo;

    public int getPage() {
        return page;
    }

    public int getPages() {
        return pages;
    }

    public int getPerPage() {
        return perPage;
    }

    public String getTotal() {
        return total;
    }

    public ArrayList<FlickrImageSearchPhoto> getPhoto() {
        return photo;
    }
}