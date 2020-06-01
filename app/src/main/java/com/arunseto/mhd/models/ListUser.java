package com.arunseto.mhd.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Arunstop on 17-Jun-19.
 */

public class ListUser {

    @SerializedName("email")
    private String email;
    @SerializedName("passowrd")
    private String password;

    public ListUser(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
