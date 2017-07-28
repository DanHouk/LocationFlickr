package com.houkcorp.locationflickr.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;

/*FIXME: Is this either needed or anything else?  Remove Serialzed if not needed.  Lets look into
* view models.*/
public class PhotoMetaData {
    @SerializedName("id") private String id;
    @SerializedName("secret") private String secret;
    @SerializedName("server") private String server;
    @SerializedName("farm") private int farm;
    @SerializedName("dateuploaded") private String dateUploaded;
    @SerializedName("isfavorite") private int isFavorite;
    @SerializedName("license") private String license;
    @SerializedName("rotation") private int rotation;
    @SerializedName("originalsecret") private String originalsecret;
    @SerializedName("originalformat") private String originalformat;
    @SerializedName("views") private String views;
    @SerializedName("media") private String media;

    //TODO: set all of this up and hook it up.
    @SerializedName("owner") private DataOwner owner;
    @SerializedName("title") private DataTitle title;
    @SerializedName("description") private DataDescription description;
    @SerializedName("visibility") private DataVisibility visibility;
    @SerializedName("dates") private DataDates dates;

    @SerializedName("comments") private DataComments comments;
    @SerializedName("tags") private DataTags tags;
    @SerializedName("location") private DataLocation location;
    @SerializedName("geoperms") private DataGEOPerms geoPerms;
    @SerializedName("urls") private DataURLs urls;

    public String getId() {
        return id;
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

    public String getDateUploaded() {
        return dateUploaded;
    }

    public boolean getIsFavorite() {
        return isFavorite == 1;
    }

    public String getLicense() {
        return license;
    }

    public int getRotation() {
        return rotation;
    }

    public String getOriginalsecret() {
        return originalsecret;
    }

    public String getOriginalformat() {
        return originalformat;
    }

    public String getViews() {
        return views;
    }

    public String getMedia() {
        return media;
    }

    public DataOwner getOwner() {
        return owner;
    }

    public DataTitle getTitle() {
        return title;
    }

    public DataDescription getDescription() {
        return description;
    }

    public DataVisibility getVisibility() {
        return visibility;
    }

    public DataDates getDates() {
        return dates;
    }

    public DataComments getComments() {
        return comments;
    }

    public DataTags getTags() {
        return tags;
    }

    public DataLocation getLocation() {
        return location;
    }

    public DataGEOPerms getGeoPerms() {
        return geoPerms;
    }

    public DataURLs getUrls() {
        return urls;
    }

    public class DataOwner {
        @SerializedName("nsid") private String id;
        @SerializedName("username") private String userName;
        @SerializedName("realname") private String realName;
        @SerializedName("location") private String location;
        @SerializedName("path_alias") private String juanomatic;

        public String getId() {
            return id;
        }

        public String getUserName() {
            return userName;
        }

        public String getRealName() {
            return realName;
        }

        public String getLocation() {
            return location;
        }

        public String getJuanomatic() {
            return juanomatic;
        }
    }

    public class DataTitle {
        @SerializedName("_content") private String content;

        public String getContent() {
            return content;
        }
    }

    public class DataDescription {
        @SerializedName("_content") private String content;

        public String getContent() {
            return content;
        }
    }

    public class DataVisibility {
        @SerializedName("ispublic") private int isPublic;
        @SerializedName("isfriend") private int isFriend;
        @SerializedName("isfamily") private int isFamily;

        public boolean getIsPublic() {
            return isPublic == 1;
        }

        public boolean getIsFriend() {
            return isFriend == 1;
        }

        public boolean getIsFamily() {
            return isFamily == 1;
        }
    }

    public class DataDates {
        @SerializedName("posted") private String posted;
        @SerializedName("taken") private Date taken;

        @SerializedName("lastupdate") private String lastUpdate;

        public String getPosted() {
            return posted;
        }

        public Date getTaken() {
            return taken;
        }

        public String getLastUpdate() {
            return lastUpdate;
        }
    }

    public class DataComments {
        @SerializedName("_content") private String content;

        public String getContent() {
            return content;
        }
    }

    public class DataTags {
        @SerializedName("tag") private ArrayList<DataTag> tag;

        public ArrayList<DataTag> getTag() {
            return tag;
        }
    }

    public class DataTag {
        @SerializedName("id") private String id;
        @SerializedName("author") private String author;
        @SerializedName("authorname") private String authorName;
        @SerializedName("raw") private String raw;
        @SerializedName("_content") private String content;
        @SerializedName("machinetag") private int machineTag;

        public String getId() {
            return id;
        }

        public String getAuthor() {
            return author;
        }

        public String getAuthorName() {
            return authorName;
        }

        public String getRaw() {
            return raw;
        }

        public String getContent() {
            return content;
        }

        public boolean getMachineTag() {
            return machineTag == 1;
        }
    }

    public class DataLocation {
        @SerializedName("latitude") private String latitude;
        @SerializedName("longitude") private String longitude;
        @SerializedName("accuracy") private String accuracy;
        @SerializedName("context") private String context;

        public String getLatitude() {
            return latitude;
        }

        public String getLongitude() {
            return longitude;
        }

        public String getAccuracy() {
            return accuracy;
        }

        public String getContext() {
            return context;
        }
    }

    public class DataGEOPerms {
        @SerializedName("ispublic") private int isPublic;
        @SerializedName("iscontact") private int isContact;
        @SerializedName("isfriend") private int isFriend;
        @SerializedName("isfamily") private int isFamily;

        public int getIsPublic() {
            return isPublic;
        }

        public int getIsContact() {
            return isContact;
        }

        public int getIsFriend() {
            return isFriend;
        }

        public int getIsFamily() {
            return isFamily;
        }
    }

    public class DataURLs {
        @SerializedName("url") private ArrayList<DataURL> url;

        public ArrayList<DataURL> getUrls() {
            return url;
        }
    }

    public class DataURL {
        @SerializedName("type") private String type;
        @SerializedName("_content") private String content;


    }


}