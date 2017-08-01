package com.houkcorp.locationflickr.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
/*FIXME: Does this need to be a Parcelable?  Also remove all SerializedNames.  Remove anything unused.*/
public class FlickrPhoto implements Parcelable {
    private String id;
    private String owner;
    private String secret;
    private String server;
    private int farm;
    private String title;
    @SerializedName("ispublic") private int isPublic;
    @SerializedName("isfriend") private int isFriend;
    private int isFamily;

    protected FlickrPhoto(Parcel in) {
        id = in.readString();
        owner = in.readString();
        secret = in.readString();
        server = in.readString();
        farm = in.readInt();
        title = in.readString();
        isPublic = in.readInt();
        isFriend = in.readInt();
        isFamily = in.readInt();
    }

    public static final Creator<FlickrPhoto> CREATOR = new Creator<FlickrPhoto>() {
        @Override
        public FlickrPhoto createFromParcel(Parcel in) {
            return new FlickrPhoto(in);
        }

        @Override
        public FlickrPhoto[] newArray(int size) {
            return new FlickrPhoto[size];
        }
    };

    public String getId() {
        return id;
    }

    public String getOwner() {
        return owner;
    }

    public String getSecret() {
        return secret;
    }

    public String getServer() {
        return server;
    }

    public int getFarm() {
        return farm;
    }

    public String getTitle() {
        return title;
    }

    public boolean isPublic() {
        return isPublic == 1;
    }

    public boolean isFriend() {
        return isFriend == 1;
    }

    public boolean isFamily() {
        return isFamily == 1;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(owner);
        dest.writeString(secret);
        dest.writeString(server);
        dest.writeInt(farm);
        dest.writeString(title);
        dest.writeInt(isPublic);
        dest.writeInt(isFriend);
        dest.writeInt(isFamily);
    }
}