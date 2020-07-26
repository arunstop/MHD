package com.arunseto.mhd.models;

import com.google.gson.annotations.SerializedName;

public class Article {

    @SerializedName("ID_ARTIKEL")
    private String id_artikel;
    @SerializedName("ID_USER")
    private String id_user;
    @SerializedName("JUDUL")
    private String judul;
    @SerializedName("ISI")
    private String isi;
    @SerializedName("IMG_URL")
    private String img_url;

    public Article(String id_artikel, String id_user, String judul, String isi, String img_url) {
        this.id_artikel = id_artikel;
        this.id_user = id_user;
        this.judul = judul;
        this.isi = isi;
        this.img_url = img_url;
    }

    public String getId_artikel() {
        return id_artikel;
    }

    public String getId_user() {
        return id_user;
    }

    public String getJudul() {
        return judul;
    }

    public String getIsi() {
        return isi;
    }

    public String getImg_url() {
        return img_url;
    }
}
