package id.ac.stiki.doleno.mhd.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ArticleResponse extends DefaultResponse {
    @SerializedName("data")
    private List<Article> data;

    public ArticleResponse(boolean status, String message, List<Article> data) {
        super(status, message);
        this.data = data;
    }

    public List<Article> getData() {
        return data;
    }
}
