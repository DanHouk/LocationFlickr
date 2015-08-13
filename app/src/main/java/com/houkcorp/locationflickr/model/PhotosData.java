package com.houkcorp.locationflickr.model;

import android.os.Parcel;
import android.os.Parcelable;

public class PhotosData implements Parcelable {
    private int page;
    private int pages;
    private int perPage;
    private int total;

    protected PhotosData(Parcel in) {
        page = in.readInt();
        pages = in.readInt();
        perPage = in.readInt();
        total = in.readInt();
    }

    public static final Creator<PhotosData> CREATOR = new Creator<PhotosData>() {
        @Override
        public PhotosData createFromParcel(Parcel in) {
            return new PhotosData(in);
        }

        @Override
        public PhotosData[] newArray(int size) {
            return new PhotosData[size];
        }
    };

    public void setPage(int page) {
        this.page = page;
    }

    public int getPage() {
        return page;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getPages() {
        return pages;
    }

    public void setPerPage(int perpage) {
        this.perPage = perpage;
    }

    public int getPerpage() {
        return perPage;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTotal() {
        return total;
    }

    public PhotosData() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(page);
        dest.writeInt(pages);
        dest.writeInt(perPage);
        dest.writeInt(total);
    }
}