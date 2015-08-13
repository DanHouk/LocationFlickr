package com.houkcorp.locationflickr.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class FlickrImageHolder implements Parcelable {
    private PhotosData photosData;
    private ArrayList<FlickrImage> photos;

    public FlickrImageHolder() {
        photos = new ArrayList<>();
    }

    protected FlickrImageHolder(Parcel in) {
        photosData = in.readParcelable(PhotosData.class.getClassLoader());
        photos = in.createTypedArrayList(FlickrImage.CREATOR);
    }

    public static final Creator<FlickrImageHolder> CREATOR = new Creator<FlickrImageHolder>() {
        @Override
        public FlickrImageHolder createFromParcel(Parcel in) {
            return new FlickrImageHolder(in);
        }

        @Override
        public FlickrImageHolder[] newArray(int size) {
            return new FlickrImageHolder[size];
        }
    };

    public PhotosData getPhotosData() {
        return photosData;
    }

    public void setPhotosData(PhotosData photosData) {
        this.photosData = photosData;
    }

    public ArrayList<FlickrImage> getPhotos() {
        return photos;
    }

    public void setPhotos(ArrayList<FlickrImage> photos) {
        this.photos = photos;
    }

    public void addPhoto(FlickrImage flickrImage) {
        photos.add(flickrImage);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(photosData, flags);
        dest.writeTypedList(photos);
    }
}