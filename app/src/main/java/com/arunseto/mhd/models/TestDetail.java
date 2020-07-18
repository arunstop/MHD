package com.arunseto.mhd.models;

import com.google.gson.annotations.SerializedName;

public class TestDetail {

    @SerializedName("ID_TES_DETAIL")
    private int id_test_detail;
    @SerializedName("ID_GEJALA_DETAIL")
    private int id_symptom_detail;
    @SerializedName("ID_TES")
    private int id_test;
    @SerializedName("CHOICE")
    private int choice;

    public int getId_test_detail() {
        return id_test_detail;
    }

    public int getId_symptom_detail() {
        return id_symptom_detail;
    }

    public int getId_test() {
        return id_test;
    }

    public int getChoice() {
        return choice;
    }

    public TestDetail(int id_test_detail, int id_symptom_detail, int id_test, int choice) {
        this.id_test_detail = id_test_detail;
        this.id_symptom_detail = id_symptom_detail;
        this.id_test = id_test;
        this.choice = choice;
    }
}
