package id.ac.stiki.doleno.mhd.models;

import com.google.gson.annotations.SerializedName;

public class TestResult {
    @SerializedName("ID_TES")
    private int id_test;
    @SerializedName("ID_PENYAKIT")
    private int id_disorder;
    @SerializedName("NAMA_PENYAKIT")
    private String disorder_name;
    @SerializedName("TOTAL_GEJALA")
    private int symptom_total;
    @SerializedName("PERSENTASE_GEJALA")
    private double symptom_percentage;

    public TestResult(int id_test, int id_disorder, String disorder_name, int symptom_total, double symptom_percentage) {
        this.id_test = id_test;
        this.id_disorder = id_disorder;
        this.disorder_name = disorder_name;
        this.symptom_total = symptom_total;
        this.symptom_percentage = symptom_percentage;
    }

    public int getId_test() {
        return id_test;
    }

    public int getId_disorder() {
        return id_disorder;
    }

    public String getDisorder_name() {
        return disorder_name;
    }

    public int getSymptom_total() {
        return symptom_total;
    }

    public double getSymptom_percentage() {
        return symptom_percentage;
    }
}
