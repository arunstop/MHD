package com.arunseto.mhd.models;

import com.google.gson.annotations.SerializedName;

public class ImgurResponse {
    @SerializedName("success")
    private String success;
    @SerializedName("status")
    private String status;
    @SerializedName("data")
    private ImgurData data;

    public ImgurResponse(String success, String status, ImgurData data) {
        this.success = success;
        this.status = status;
        this.data = data;
    }

    public String getSuccess() {
        return success;
    }

    public String getStatus() {
        return status;
    }

    public ImgurData getData() {
        return data;
    }
}

