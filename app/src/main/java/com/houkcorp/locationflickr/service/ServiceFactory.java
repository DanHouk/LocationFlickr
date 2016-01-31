package com.houkcorp.locationflickr.service;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.RxJavaCallAdapterFactory;

public class ServiceFactory {
    public static final String META_DATA_URL =
            "https://api.flickr.com/services/";
    public static final String PHOTOS_SEARCH_URL =
            "https://api.flickr.com/services/";

    public static MetaDataService getMetaDataService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(META_DATA_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(MetaDataService.class);
    }

    public static PhotoService getPhotoService() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(loggingInterceptor);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(PHOTOS_SEARCH_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();

        return retrofit.create(PhotoService.class);
    }
}