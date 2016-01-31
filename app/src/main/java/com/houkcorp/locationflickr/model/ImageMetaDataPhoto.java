package com.houkcorp.locationflickr.model;

import com.google.gson.annotations.SerializedName;

public class ImageMetaDataPhoto {
    @SerializedName("id") private String id;
    @SerializedName("secret") private String secret;
    @SerializedName("server") private String server;
    @SerializedName("farm") private int farm;
    @SerializedName("dateuploaded") private String dateUploaded;
    @SerializedName("isfavorite") private boolean isFavorite;
    @SerializedName("license") private String license;
    @SerializedName("rotation") private int rotation;
    @SerializedName("originalsecret") private String originalsecret;
    @SerializedName("originalformat") private String originalformat;
    @SerializedName("views") private String views;
    @SerializedName("media") private String media;

    //TODO: set all of this up and hook it up.
    /*@SerializedName("owner") private ImageMetaDataOwner owner;
    @SerializedName("title") private ImageMetaDataTitle title;
    @SerializedName("visibility") private ImageMetaDataVisibility visibility;
    @SerializedName("dates") private ImageMetaDataDates dates;
    @SerializedName("description") private ImageMetaDataDescription description;

    @SerializedName("usage") private ImageMetaDataUsage usage;
    @SerializedName("comments") private ImageMetaDataComments comments;
    @SerializedName("people") private ImageMetaDataPeople people;
    @SerializedName("tags") private ArrayList<ImageMetaDataTags> tags;
    @SerializedName("location") private ImageMetaDataLocation location;
    @SerializedName("geoperms") private ImageMetaDataGEOPerms geoPerms;
    @SerializedName("urls") private ImageMetaDataURLs urls;*/

}