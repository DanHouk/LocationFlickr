package com.houkcorp.locationflickr.service;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

public class ServiceFactory {
    public static final String META_DATA_URL =
            "https://api.flickr.com/services/";
    public static final String PHOTOS_SEARCH_URL =
            "https://api.flickr.com/services/";

    public static MetaDataService getMetaDataService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(META_DATA_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        return retrofit.create(MetaDataService.class);
    }

    public static PhotoService getPhotoService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(PHOTOS_SEARCH_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        return retrofit.create(PhotoService.class);
    }
}