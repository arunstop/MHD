package id.ac.stiki.doleno.mhd.models.ytmodels;

import com.google.gson.annotations.SerializedName;

public class Id {
    @SerializedName("kind")
    private String kind;
    @SerializedName("videoId")
    private String videoId;

    public Id(String kind, String videoId) {
        this.kind = kind;
        this.videoId = videoId;
    }

    public String getKind() {
        return kind;
    }

    public String getVideoId() {
        return videoId;
    }
}
