package id.ac.stiki.doleno.mhd.models.ytmodels;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class YoutubeResponse {
    @SerializedName("kind")
    private String kind;
    @SerializedName("etag")
    private String etag;
    @SerializedName("nextPageToken")
    private String nextPageToken;
    @SerializedName("regionCode")
    private String regionCode;
    @SerializedName("pageInfo")
    private PageInfo pageinfo;
    @SerializedName("items")
    private List<Item> items;

    public YoutubeResponse(String kind, String etag, String nextPageToken, String regionCode, PageInfo pageinfo, List<Item> items) {
        this.kind = kind;
        this.etag = etag;
        this.nextPageToken = nextPageToken;
        this.regionCode = regionCode;
        this.pageinfo = pageinfo;
        this.items = items;
    }

    public String getKind() {
        return kind;
    }

    public String getEtag() {
        return etag;
    }

    public String getNextPageToken() {
        return nextPageToken;
    }

    public String getRegionCode() {
        return regionCode;
    }

    public PageInfo getPageinfo() {
        return pageinfo;
    }

    public List<Item> getItems() {
        return items;
    }
}


