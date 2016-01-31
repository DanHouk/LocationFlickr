package com.houkcorp.locationflickr.service;

import com.houkcorp.locationflickr.model.ImageMetaData;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MetaDataService {
    @GET("rest/?method=flickr.photos.getInfo&api_key=535b54e75b084504069f7b66d8bfb7c7&format=json")
    Call<ImageMetaData> getMetaData(
            @Query("photo_id") String photoId);
}