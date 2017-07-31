package com.houkcorp.locationflickr.service;

import com.houkcorp.locationflickr.model.ImageMetaDataResults;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/*FIXME: Change the way these are handled.*/
public interface MetaDataService {
    @GET("rest/?method=flickr.photos.getInfo&api_key=535b54e75b084504069f7b66d8bfb7c7&format=json&nojsoncallback=1")
    Observable<ImageMetaDataResults> getMetaData(
            @Query("photo_id") String photoId);
}