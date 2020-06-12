package com.arunseto.mhd.models;

public class Psychiatrist {
    private String name;
    private String number;
    private String address;
    private String extra;
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
