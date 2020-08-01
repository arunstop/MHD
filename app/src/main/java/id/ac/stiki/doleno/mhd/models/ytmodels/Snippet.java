package id.ac.stiki.doleno.mhd.models.ytmodels;

import com.google.gson.annotations.SerializedName;

public class Snippet {
    @SerializedName("publishedAt")
    private String publishedAt;
    @SerializedName("channelId")
    private String channelId;
    @SerializedName("title")
    private String title;
    @SerializedName("description")
    private String description;
    @SerializedName("thumbnails")
    private Thumbnails thumbnails;
    @SerializedName("channelTitle")
    private String channelTitle;
    @SerializedName("liveBroadcastContent")
    private String liveBroadcastContent;
    @SerializedName("publishTime")
    private String publishTime;

    public Snippet(String publishedAt, String channelId, String title, String description, Thumbnails thumbnails, String channelTitle, String liveBroadcastContent, String publishTime) {
        this.publishedAt = publishedAt;
        this.channelId = channelId;
        this.title = title;
        this.description = description;
        this.thumbnails = thumbnails;
        this.channelTitle = channelTitle;
        this.liveBroadcastContent = liveBroadcastContent;
        this.publishTime = publishTime;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public String getChannelId() {
        return channelId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Thumbnails getThumbnails() {
        return thumbnails;
    }

    public String getChannelTitle() {
        return channelTitle;
    }

    public String getLiveBroadcastContent() {
        return liveBroadcastContent;
    }

    public String getPublishTime() {
        return publishTime;
    }
}
