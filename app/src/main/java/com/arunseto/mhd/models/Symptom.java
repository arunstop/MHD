package com.arunseto.mhd.models;

import com.google.gson.annotations.SerializedName;

public class Symptom {

    @SerializedName("NAMA_GEJALA")
    private String nama_gejala;
    @SerializedName("NAMA_PENYAKIT")
    private String nama_penyakit;

    public String getNama_gejala() {
        return nama_gejala;
    }

    public String getNama_penyakit() {
        return nama_penyakit;
    }

    public Symptom(String nama_gejala, String nama_penyakit) {
        this.nama_gejala = nama_gejala;
        this.nama_penyakit = nama_penyakit;
    }
}
