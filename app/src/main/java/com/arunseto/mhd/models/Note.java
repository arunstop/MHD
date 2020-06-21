package com.arunseto.mhd.models;

import com.google.gson.annotations.SerializedName;

public class Note {

    @SerializedName("ID_CATATAN")
    private int id;
    @SerializedName("JUDUL_CATATAN")
    private String title;
    @SerializedName("ISI_CATATAN")
    private String content;
    @SerializedName("CREATED_AT")
    private String date;

    public Note(int id, String title, String content, String date) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getDate() {
        return date;
    }
}
