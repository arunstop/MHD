package id.ac.stiki.doleno.mhd.models;

import com.google.gson.annotations.SerializedName;

public class DefaultResponse {
    @SerializedName("ok")
    private boolean ok;
    @SerializedName("message")
    private String message;

    public DefaultResponse(boolean ok, String message) {
        this.ok = ok;
        this.message = message;
    }

    public boolean isOk() {
        return ok;
    }

    public String getMessage() {
        return message;
    }
}
