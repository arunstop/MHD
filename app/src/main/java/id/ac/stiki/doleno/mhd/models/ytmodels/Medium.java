package id.ac.stiki.doleno.mhd.models.ytmodels;

import com.google.gson.annotations.SerializedName;

public class Medium {
    @SerializedName("url")
    private String url;
    @SerializedName("width")
    private float width;
    @SerializedName("height")
    private float height;

    public Medium(String url, float width, float height) {
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
