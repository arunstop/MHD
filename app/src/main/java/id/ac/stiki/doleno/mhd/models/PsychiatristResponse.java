package id.ac.stiki.doleno.mhd.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PsychiatristResponse extends DefaultResponse {
    @SerializedName("data")
    private List<Psychiatrist> data;

    public PsychiatristResponse(boolean status, String message, List<Psychiatrist> data) {
        super(status, message);
        this.data = data;
    }

    public List<Psychiatrist> getData() {
        return data;
    }
}
