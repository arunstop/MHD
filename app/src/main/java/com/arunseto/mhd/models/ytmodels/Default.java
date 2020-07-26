package com.arunseto.mhd.models.ytmodels;

import com.google.gson.annotations.SerializedName;

public class Default {
    @SerializedName("url")
    private String url;
    @SerializedName("width")
    private float width;
    @SerializedName("height")
    private float height;

    public Default(String url, float width, float height) {
        this.url = url;
        this.width = width;
        this.height = height;
    }

    public String getUrl() {
        return url;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }
}
