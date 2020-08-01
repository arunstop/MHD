package id.ac.stiki.doleno.mhd.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class QuestionResponse extends DefaultResponse {
    @SerializedName("data")
    private List<Question> data;

    public QuestionResponse(boolean status, String message, List<Question> data) {
        super(status, message);
        this.data = data;
    }

    public List<Question> getData() {
        return data;
    }
}
