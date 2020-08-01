package id.ac.stiki.doleno.mhd.models;

import com.google.gson.annotations.SerializedName;

public class NewsArticleSource {
    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;

    public NewsArticleSource(String id, String name) {
        this.id = id;
        this.name = name;
    }

// Getter Methods

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
