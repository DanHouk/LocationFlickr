package com.houkcorp.locationflickr.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ImageMetaData implements Parcelable {
    private String originalSecret;
    private Date postedDate;
    private String postedDateString;
    private String realName;
    private String secret;
    private String takenDate;
    private String title;
    private String userName;

    private ArrayList<String> tags;

    public ImageMetaData() {
    }

    protected ImageMetaData(Parcel in) {
        originalSecret = in.readString();
        postedDateString = in.readString();
        realName = in.readString();
        secret = in.readString();
        takenDate = in.readString();
        title = in.readString();
        userName = in.readString();
        tags = in.createStringArrayList();
    }

    public static final Creator<ImageMetaData> CREATOR = new Creator<ImageMetaData>() {
        @Override
        public ImageMetaData createFromParcel(Parcel in) {
            return new ImageMetaData(in);
        }

        @Override
        public ImageMetaData[] newArray(int size) {
            return new ImageMetaData[size];
        }
    };

    public String getOriginalSecret() {
        return originalSecret;
    }

    public void setOriginalSecret(String originalSecret) {
        this.originalSecret = originalSecret;
    }

    public Date getPostedDate() {
        return postedDate;
    }

    public void setPostedDate(Date postedDate) {
        this.postedDate = postedDate;
    }

    public String getPostedDateString() {
        String formattedDate = "";
        if(postedDate != null) {
            formattedDate = new SimpleDateFormat("MM/dd/yyyy hh:mm:sa", Locale.US).format(postedDate);
        }

        return formattedDate;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getTakenDate() {
        return takenDate;
    }

    public void setTakenDate(String takenDate) {
        this.takenDate = takenDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

    public void addTag(String tag) {
        tags.add(tag);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(originalSecret);
        dest.writeString(postedDateString);
        dest.writeString(realName);
        dest.writeString(secret);
        dest.writeString(takenDate);
        dest.writeString(title);
        dest.writeString(userName);
        dest.writeStringList(tags);
    }
}