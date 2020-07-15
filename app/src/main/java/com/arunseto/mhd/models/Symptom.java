package com.arunseto.mhd.models;

import com.google.gson.annotations.SerializedName;

public class Symptom {


    @SerializedName("id_gejala_detail")
    private int id_gejala_detail;
    @SerializedName("ID_GEJALA")
    private int id_gejala;
    @SerializedName("ID_PENYAKIT")
    private int id_penyakit;
    @SerializedName("NAMA_GEJALA")
    private String nama_gejala;
    @SerializedName("NAMA_PENYAKIT")
    private String nama_penyakit;
    @SerializedName("yes")
    private int yes;
    @SerializedName("no")
    private int no;


    public int getId_gejala_detail() {
        return id_gejala_detail;
    }

    public int getId_gejala() {
        return id_gejala;
    }

    public int getId_penyakit() {
        return id_penyakit;
    }

    public String getNama_gejala() {
        return nama_gejala;
    }

    public String getNama_penyakit() {
        return nama_penyakit;
    }

    public int getYes() {
        return yes;
    }

    public int getNo() {
        return no;
    }

    public Symptom(int id_gejala_detail, int id_gejala, int id_penyakit, String nama_gejala, String nama_penyakit, int yes, int no) {
        this.id_gejala_detail = id_gejala_detail;
        this.id_gejala = id_gejala;
        this.id_penyakit = id_penyakit;
        this.nama_gejala = nama_gejala;
        this.nama_penyakit = nama_penyakit;
        this.yes = yes;
        this.no = no;
    }
}
