package com.skiplab.theselandroiddemo.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Post implements Parcelable {

    String pId, pDescription, pFilePath, pFileType, pTime, uid, uName, uDp, uMood, pCategory, tags;


    public Post() {
    }

    public Post(String pId, String pDescription, String pFilePath, String pFileType, String pTime, String uid, String uName, String uDp, String uMood, String pCategory, String tags) {
        this.pId = pId;
        this.pDescription = pDescription;
        this.pFilePath = pFilePath;
        this.pFileType = pFileType;
        this.pTime = pTime;
        this.uid = uid;
        this.uName = uName;
        this.uDp = uDp;
        this.uMood = uMood;
        this.pCategory = pCategory;
        this.tags = tags;
    }

    public static final Creator<Post> CREATOR = new Creator<Post>() {
        @Override
        public Post createFromParcel(Parcel in) {
            return new Post(in);
        }

        @Override
        public Post[] newArray(int size) {
            return new Post[size];
        }
    };

    protected Post(Parcel in) {
        pId = in.readString();
        pDescription = in.readString();
        pFilePath = in.readString();
        pFileType = in.readString();
        pTime = in.readString();
        uid = in.readString();
        uName = in.readString();
        uDp = in.readString();
        uMood = in.readString();
        pCategory = in.readString();
        tags = in.readString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(pId);
        parcel.writeString(pDescription);
        parcel.writeString(pFilePath);
        parcel.writeString(pFileType);
        parcel.writeString(pTime);
        parcel.writeString(uid);
        parcel.writeString(uName);
        parcel.writeString(uDp);
        parcel.writeString(uMood);
        parcel.writeString(pCategory);
        parcel.writeString(tags);
    }


    @Override
    public int describeContents() {
        return 0;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getpDescription() {
        return pDescription;
    }

    public void setpDescription(String pDescription) {
        this.pDescription = pDescription;
    }

    public String getpFilePath() {
        return pFilePath;
    }

    public void setpFilePath(String pFilePath) {
        this.pFilePath = pFilePath;
    }

    public String getpFileType() {
        return pFileType;
    }

    public void setpFileType(String pFileType) {
        this.pFileType = pFileType;
    }

    public String getpTime() {
        return pTime;
    }

    public void setpTime(String pTime) {
        this.pTime = pTime;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public String getuDp() {
        return uDp;
    }

    public void setuDp(String uDp) {
        this.uDp = uDp;
    }

    public String getuMood() {
        return uMood;
    }

    public void setuMood(String uMood) {
        this.uMood = uMood;
    }

    public String getpCategory() {
        return pCategory;
    }

    public void setpCategory(String pCategory) {
        this.pCategory = pCategory;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public static Creator<Post> getCREATOR() {
        return CREATOR;
    }
}
