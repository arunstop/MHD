package id.ac.stiki.doleno.mhd.models.ytmodels;

import com.google.gson.annotations.SerializedName;

public class Thumbnails {
    @SerializedName("default")
    private Default def;
    @SerializedName("medium")
    private Medium medium;
    @SerializedName("high")
    private High high;

    public Thumbnails(Default def, Medium medium, High high) {
        this.def = def;
        this.medium = medium;
        this.high = high;
    }

    public Default getDef() {
        return def;
    }

    public Medium getMedium() {
        return medium;
    }

    public High getHigh() {
        return high;
    }
}
