package com.arunseto.mhd.models;

import com.google.gson.annotations.SerializedName;

public class Note {

    @SerializedName("id_catatan")
    private int id_catatan;
    @SerializedName("title")
    private String title;
    @SerializedName("content")
    private String content;
    @SerializedName("date")
    private String date;

    public Note(int id_catatan, String title, String content, String date) {
        this.id_catatan = id_catatan;
        this.title = title;
        this.content = content;
        this.date = date;
    }

    public int getId_catatan() {
        return id_catatan;
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
