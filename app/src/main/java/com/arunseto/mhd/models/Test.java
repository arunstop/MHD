package com.arunseto.mhd.models;

import com.google.gson.annotations.SerializedName;

public class Test {
    @SerializedName("ID_TES")
    private int id_tes;
    @SerializedName("RESULT")
    private int result;
    @SerializedName("LAST_QUIZ")
    private int last_quiz;
    @SerializedName("ID_USER")
    private int id_user;
    @SerializedName("CREATED_AT")
    private String date;
    @SerializedName("NAMA_PENYAKIT")
    private String disorder_name;

    public Test(int id_tes, int result, int last_quiz, int id_user, String date, String disorder_name) {
        this.id_tes = id_tes;
        this.result = result;
        this.last_quiz = last_quiz;
        this.id_user = id_user;
        this.date = date;
        this.disorder_name = disorder_name;
    }

    public int getId_tes() {
        return id_tes;
    }

    public int getResult() {
        return result;
    }

    public int getLast_quiz() {
        return last_quiz;
    }

    public int getId_user() {
        return id_user;
    }

    public String getDate() {
        return date;
    }

    public String getDisorder_name() {
        return disorder_name;
    }
}
