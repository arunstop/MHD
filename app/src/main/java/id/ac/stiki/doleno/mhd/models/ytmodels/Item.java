package id.ac.stiki.doleno.mhd.models.ytmodels;

import com.google.gson.annotations.SerializedName;

public class Item {
    @SerializedName("id")
    private Id id;
    @SerializedName("snippet")
    private Snippet snippet;
    @SerializedName("kind")
    private String kind;
    @SerializedName("etag")
    private String etag;

    public Item(Id id, Snippet snippet, String kind, String etag) {
        this.id = id;
        this.snippet = snippet;
        this.kind = kind;
        this.etag = etag;
    }

    public Id getId() {
        return id;
    }

    public Snippet getSnippet() {
        return snippet;
    }

    public String getKind() {
        return kind;
    }

    public String getEtag() {
        return etag;
    }
}
