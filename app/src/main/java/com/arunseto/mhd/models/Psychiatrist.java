package com.arunseto.mhd.models;

import com.google.gson.annotations.SerializedName;

public class Psychiatrist {
    @SerializedName("name")
    private String name;
    @SerializedName("number")
    private String number;
    @SerializedName("address")
    private String address;
    @SerializedName("extra")
    private String extra;
    @SerializedName("img")
    private String img;


    public Psychiatrist(String name, String number, String address, String extra, String img) {
        this.name = name;
        this.number = number;
        this.address = address;
        this.extra = extra;
        this.img = img;
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

    public String getExtra() {
        return extra;
    }

    public String getImg() {
        return img;
    }
}
