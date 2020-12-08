package com.skiplab.theselandroiddemo.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Post implements Parcelable {

    String pId, pDescription, pImage, pTime, uid, uName, uEmail, uDp, uMood, pCategory, tags;
    private List<Like> likes;
    private List<Comment> comments;

    public Post() {
    }

    public Post(String pId, String pDescription, String pImage, String pTime, String uid, String uName, String uEmail, String uDp, String uMood, String pCategory, String tags, List<Like> likes, List<Comment> comments) {
        this.pId = pId;
        this.pDescription = pDescription;
        this.pImage = pImage;
        this.pTime = pTime;
        this.uid = uid;
        this.uName = uName;
        this.uEmail = uEmail;
        this.uDp = uDp;
        this.uMood = uMood;
        this.pCategory = pCategory;
        this.tags = tags;
        this.likes = likes;
        this.comments = comments;
    }

    protected Post(Parcel in) {
        pId = in.readString();
        pDescription = in.readString();
        pImage = in.readString();
        pTime = in.readString();
        uid = in.readString();
        uName = in.readString();
        uEmail = in.readString();
        uDp = in.readString();
        uMood = in.readString();
        pCategory = in.readString();
        tags = in.readString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(pId);
        parcel.writeString(pDescription);
        parcel.writeString(pImage);
        parcel.writeString(pTime);
        parcel.writeString(uid);
        parcel.writeString(uName);
        parcel.writeString(uEmail);
        parcel.writeString(uDp);
        parcel.writeString(uMood);
        parcel.writeString(pCategory);
        parcel.writeString(tags);
    }

    @Override
    public int describeContents() {
        return 0;
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

    public String getpImage() {
        return pImage;
    }

    public void setpImage(String pImage) {
        this.pImage = pImage;
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

    public String getuEmail() {
        return uEmail;
    }

    public void setuEmail(String uEmail) {
        this.uEmail = uEmail;
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

    public List<Like> getLikes() {
        return likes;
    }

    public void setLikes(List<Like> likes) {
        this.likes = likes;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

}
