package com.arunseto.mhd.models;

import com.google.gson.annotations.SerializedName;

public class ImgurData {
    @SerializedName("link")
    private String link;

    public ImgurData(String link) {
        this.link = link;
    }

    public String getLink() {
        return link;
    }
}
