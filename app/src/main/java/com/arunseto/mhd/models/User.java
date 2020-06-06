package com.arunseto.mhd.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Arunstop on 17-Jun-19.
 */

public class User {

    @SerializedName("email")
    private String email;
    @SerializedName("password")
    private String password;
    @SerializedName("fname")
    private String fname;
    @SerializedName("lname")
    private String lname;
    @SerializedName("photoUrl")
    private String photoUrl;


    public User(String email, String password, String fname, String lname, String photoUrl) {
        this.email = email;
        this.password = password;
        this.fname = fname;
        this.lname = lname;
        this.photoUrl = photoUrl;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getFname() {
        return fname;
    }

    public String getLname() {
        return lname;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }
}
