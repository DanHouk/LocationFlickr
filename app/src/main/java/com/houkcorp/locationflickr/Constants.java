package com.houkcorp.locationflickr;

public class Constants {
    /*FIXME: Need to update this file and possible remove it and rethink constants.*/
    //Default URL _t for thumbnail and _z for full image.
    public static final String DEFAULT_IMAGE_URL = "https://farm%d.staticflickr.com/%s/%s_%s_%s.jpg";
    public static final String FLICKR_IMAGE = "flickr_image";
    public static final String META_DATA_URL =
            "https://api.flickr.com/services/rest/?method=flickr.photos.getInfo&api_key=535b54e75b084504069f7b66d8bfb7c7&photo_id=%s";
    public static final String PHOTOS_SEARCH_URL =
            "https://api.flickr.com/services/rest/?method=flickr.photos.search&name=value&api_key=535b54e75b084504069f7b66d8bfb7c7";
    public static final int SERVER_TIME_OUT = 60000;
}