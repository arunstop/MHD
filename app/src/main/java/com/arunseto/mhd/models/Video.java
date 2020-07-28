package com.arunseto.mhd.models;

import com.google.gson.annotations.SerializedName;

public class Video {

    @SerializedName("ID_VIDEO")
    private int id_video;
    @SerializedName("ID_USER")
    private String id_user;
    @SerializedName("TITLE")
    private String title;
    @SerializedName("CHANNEL")
    private String channel;
    @SerializedName("PUBLISHED_AT")
    private String published_at;
    @SerializedName("THUMBNAIL")
    private String thumbnail;
    @SerializedName("URL")
    private String url;
    @SerializedName("CREATED_AT")
    private String created_at;

    public Video(int id_video, String id_user, String title, String channel, String published_at, String thumbnail, String url, String created_at) {
        this.id_video = id_video;
        this.id_user = id_user;
        this.title = title;
        this.channel = channel;
        this.published_at = published_at;
        this.thumbnail = thumbnail;
        this.url = url;
        this.created_at = created_at;
    }

    public int getId_video() {
        return id_video;
    }

    public String getId_user() {
        return id_user;
    }

    public String getTitle() {
        return title;
    }

    public String getChannel() {
        return channel;
    }

    public String getPublished_at() {
        return published_at;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getUrl() {
        return url;
    }

    public String getCreated_at() {
        return created_at;
    }
}
