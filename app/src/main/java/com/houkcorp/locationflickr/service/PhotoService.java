package com.houkcorp.locationflickr.service;

import com.houkcorp.locationflickr.model.FlickrImageSearchResults;

import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PhotoService {
    @GET("rest/?method=flickr.photos.search&name=value&api_key=535b54e75b084504069f7b66d8bfb7c7&format=json&nojsoncallback=1")
    Observable<FlickrImageSearchResults> getFlickrImage(
            @Query("bbox") String bbox,
            @Query("page") int pageNumber);
}