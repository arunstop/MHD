package com.arunseto.mhd.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserResponse extends DefaultResponse {
    @SerializedName("data")
    private List<User> data;

    public UserResponse(boolean status, String message, List<User> data) {
        super(status, message);
        this.data = data;
    }

    public List<User> getData() {
        return data;
    }
}
