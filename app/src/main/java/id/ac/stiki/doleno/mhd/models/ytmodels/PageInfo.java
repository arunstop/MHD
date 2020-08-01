package id.ac.stiki.doleno.mhd.models.ytmodels;

import com.google.gson.annotations.SerializedName;

public class PageInfo {
    @SerializedName("totalResults")
    private float totalResults;
    @SerializedName("resultsPerPage")
    private float resultsPerPage;

    public PageInfo(float totalResults, float resultsPerPage) {
        this.totalResults = totalResults;
        this.resultsPerPage = resultsPerPage;
    }

    public float getTotalResults() {
        return totalResults;
    }

    public float getResultsPerPage() {
        return resultsPerPage;
    }
}
