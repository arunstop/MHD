package com.arunseto.mhd.models;

import com.google.gson.annotations.SerializedName;

public class Psychiatrist {


    @SerializedName("ID_AHLI")
    private int id;
    @SerializedName("NAMA_AHLI")
    private String name;
    @SerializedName("NO_TELP_AHLI")
    private String number;
    @SerializedName("ADDRESS")
    private String address;
    @SerializedName("DESCRIPTION")
    private String desc;
    @SerializedName("PHOTO_URL")
    private String photoUrl;

    public Psychiatrist(int id, String name, String number, String address, String desc, String photoUrl) {
        this.id = id;
        this.name = name;
        this.number = number;
        this.address = address;
        this.desc = desc;
        this.photoUrl = photoUrl;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    public String getAddress() {
        return address;
    }

    public String getDesc() {
        return desc;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }
}
