package id.ac.stiki.doleno.mhd.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VideoResponse extends DefaultResponse {
    @SerializedName("data")
    private List<Video> data;

    public VideoResponse(boolean status, String message, List<Video> data) {
        super(status, message);
        this.data = data;
    }

    public List<Video> getData() {
        return data;
    }
}
