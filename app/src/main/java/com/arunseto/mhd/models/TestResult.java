package com.arunseto.mhd.models;

import com.google.gson.annotations.SerializedName;

public class TestResult {
    @SerializedName("id_tes")
    private String id_tes;
    @SerializedName("result")
    private String result;
    @SerializedName("last_quiz")
    private String last_quiz;
    @SerializedName("id_user")
    private String id_user;
    @SerializedName("created_at")
    private String date;
    @SerializedName("nama_penyakit")
    private String nama_penyakit;

    public TestResult(String id_tes, String result, String last_quiz, String id_user, String created_at, String nama_penyakit) {
        this.id_tes = id_tes;
        this.result = result;
        this.last_quiz = last_quiz;
        this.id_user = id_user;
        this.date = created_at;
        this.nama_penyakit = nama_penyakit;
    }

    public String getId_tes() {
        return id_tes;
    }

    public String getResult() {
        return result;
    }

    public String getLast_quiz() {
        return last_quiz;
    }

    public String getId_user() {
        return id_user;
    }

    public String getDate() {
        return date;
    }

    public String getNama_penyakit() {
        return nama_penyakit;
    }
}
