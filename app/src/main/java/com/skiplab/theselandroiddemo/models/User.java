package com.skiplab.theselandroiddemo.models;

public class User {
    String uid, username, email, phone_number, date_created, profile_photo, bio;
    String location, acc_number, bank_name, day_time, night_time, expertise_one,  expertise_two, expertise_three, onlineStatus;
    String selectedCategory, messaging_token, isStaff;
    long day_of_birth, month_of_birth, year_of_birth, posts;
    boolean everify;

    public User() {
    }

    public User(String uid, String username, String email, String phone_number, String date_created, String profile_photo, String bio, String location, String acc_number, String bank_name, String day_time, String night_time, String expertise_one, String expertise_two, String expertise_three, String onlineStatus, String selectedCategory, String messaging_token, String isStaff, long day_of_birth, long month_of_birth, long year_of_birth, long posts, boolean everify) {
        this.uid = uid;
        this.username = username;
        this.email = email;
        this.phone_number = phone_number;
        this.date_created = date_created;
        this.profile_photo = profile_photo;
        this.bio = bio;
        this.location = location;
        this.acc_number = acc_number;
        this.bank_name = bank_name;
        this.day_time = day_time;
        this.night_time = night_time;
        this.expertise_one = expertise_one;
        this.expertise_two = expertise_two;
        this.expertise_three = expertise_three;
        this.onlineStatus = onlineStatus;
        this.selectedCategory = selectedCategory;
        this.messaging_token = messaging_token;
        this.isStaff = isStaff;
        this.day_of_birth = day_of_birth;
        this.month_of_birth = month_of_birth;
        this.year_of_birth = year_of_birth;
        this.posts = posts;
        this.everify = everify;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getDate_created() {
        return date_created;
    }

    public void setDate_created(String date_created) {
        this.date_created = date_created;
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

    public String getOnlineStatus() {
        return onlineStatus;
    }

    public void setOnlineStatus(String onlineStatus) {
        this.onlineStatus = onlineStatus;
    }

    public String getSelectedCategory() {
        return selectedCategory;
    }

    public void setSelectedCategory(String selectedCategory) {
        this.selectedCategory = selectedCategory;
    }

    public String getMessaging_token() {
        return messaging_token;
    }

    public void setMessaging_token(String messaging_token) {
        this.messaging_token = messaging_token;
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
}
