package com.houkcorp.locationflickr.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
/*FIXME: Is this file needed?*/
public class ImageBasicInfo {
    @SerializedName("page") private int page;
    @SerializedName("pages") private int pages;
    @SerializedName("perpage") private int perPage;
    @SerializedName("total") private String total;

    @SerializedName("photo") private ArrayList<FlickrPhoto> photo;

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

    public ArrayList<FlickrPhoto> getPhoto() {
        return photo;
    }
}