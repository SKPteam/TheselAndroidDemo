package com.skiplab.theselandroiddemo.Models;

public class UserAccountSettings {

    String profile_photo, bio, date_created, uid;
    String location, acc_number, bank_name, day_time, night_time, expertise_one, expertise_two, expertise_three;
    String selectedCategory, isStaff;
    long day_of_birth, month_of_birth, year_of_birth, posts;
    boolean everify, onlineStatus, isDisabled, anonymous;

    public UserAccountSettings() {
    }

    public UserAccountSettings(String profile_photo, String bio, String date_created, String uid, String location, String acc_number, String bank_name, String day_time, String night_time, String expertise_one, String expertise_two, String expertise_three, String selectedCategory, String isStaff, long day_of_birth, long month_of_birth, long year_of_birth, long posts, boolean everify, boolean onlineStatus, boolean isDisabled, boolean anonymous) {
        this.profile_photo = profile_photo;
        this.bio = bio;
        this.date_created = date_created;
        this.uid = uid;
        this.location = location;
        this.acc_number = acc_number;
        this.bank_name = bank_name;
        this.day_time = day_time;
        this.night_time = night_time;
        this.expertise_one = expertise_one;
        this.expertise_two = expertise_two;
        this.expertise_three = expertise_three;
        this.selectedCategory = selectedCategory;
        this.isStaff = isStaff;
        this.day_of_birth = day_of_birth;
        this.month_of_birth = month_of_birth;
        this.year_of_birth = year_of_birth;
        this.posts = posts;
        this.everify = everify;
        this.onlineStatus = onlineStatus;
        this.isDisabled = isDisabled;
        this.anonymous = anonymous;
    }

    public String getProfile_photo() {
        return profile_photo;
    }

    public void setProfile_photo(String profile_photo) {
        this.profile_photo = profile_photo;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getDate_created() {
        return date_created;
    }

    public void setDate_created(String date_created) {
        this.date_created = date_created;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAcc_number() {
        return acc_number;
    }

    public void setAcc_number(String acc_number) {
        this.acc_number = acc_number;
    }

    public String getBank_name() {
        return bank_name;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }

    public String getDay_time() {
        return day_time;
    }

    public void setDay_time(String day_time) {
        this.day_time = day_time;
    }

    public String getNight_time() {
        return night_time;
    }

    public void setNight_time(String night_time) {
        this.night_time = night_time;
    }

    public String getExpertise_one() {
        return expertise_one;
    }

    public void setExpertise_one(String expertise_one) {
        this.expertise_one = expertise_one;
    }

    public String getExpertise_two() {
        return expertise_two;
    }

    public void setExpertise_two(String expertise_two) {
        this.expertise_two = expertise_two;
    }

    public String getExpertise_three() {
        return expertise_three;
    }

    public void setExpertise_three(String expertise_three) {
        this.expertise_three = expertise_three;
    }

    public String getSelectedCategory() {
        return selectedCategory;
    }

    public void setSelectedCategory(String selectedCategory) {
        this.selectedCategory = selectedCategory;
    }

    public String getIsStaff() {
        return isStaff;
    }

    public void setIsStaff(String isStaff) {
        this.isStaff = isStaff;
    }

    public long getDay_of_birth() {
        return day_of_birth;
    }

    public void setDay_of_birth(long day_of_birth) {
        this.day_of_birth = day_of_birth;
    }

    public long getMonth_of_birth() {
        return month_of_birth;
    }

    public void setMonth_of_birth(long month_of_birth) {
        this.month_of_birth = month_of_birth;
    }

    public long getYear_of_birth() {
        return year_of_birth;
    }

    public void setYear_of_birth(long year_of_birth) {
        this.year_of_birth = year_of_birth;
    }

    public long getPosts() {
        return posts;
    }

    public void setPosts(long posts) {
        this.posts = posts;
    }

    public boolean isEverify() {
        return everify;
    }

    public void setEverify(boolean everify) {
        this.everify = everify;
    }

    public boolean isOnlineStatus() {
        return onlineStatus;
    }

    public void setOnlineStatus(boolean onlineStatus) {
        this.onlineStatus = onlineStatus;
    }

    public boolean isDisabled() {
        return isDisabled;
    }

    public void setDisabled(boolean disabled) {
        isDisabled = disabled;
    }

    public boolean isAnonymous() {
        return anonymous;
    }

    public void setAnonymous(boolean anonymous) {
        this.anonymous = anonymous;
    }
}