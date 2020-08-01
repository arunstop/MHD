package id.ac.stiki.doleno.mhd.models;

import com.google.gson.annotations.SerializedName;

public class Question {


    @SerializedName("ID_GEJALA_DETAIL")
    private int id_symptom_detail;
    @SerializedName("ID_GEJALA")
    private int id_symptom;
    @SerializedName("ID_PENYAKIT")
    private int id_disorder;
    @SerializedName("NAMA_GEJALA")
    private String symptom_name;
    @SerializedName("NAMA_PENYAKIT")
    private String disorder_name;
    @SerializedName("PERTANYAAN")
    private String question;
    @SerializedName("YES")
    private int yes;
    @SerializedName("NO")
    private int no;


    public Question(int id_symptom_detail, int id_symptom, int id_disorder, String symptom_name, String disorder_name, String question, int yes, int no) {
        this.id_symptom_detail = id_symptom_detail;
        this.id_symptom = id_symptom;
        this.id_disorder = id_disorder;
        this.symptom_name = symptom_name;
        this.disorder_name = disorder_name;
        this.question = question;
        this.yes = yes;
        this.no = no;
    }

    public int getId_symptom_detail() {
        return id_symptom_detail;
    }

    public int getId_symptom() {
        return id_symptom;
    }

    public int getId_disorder() {
        return id_disorder;
    }

    public String getSymptom_name() {
        return symptom_name;
    }

    public String getDisorder_name() {
        return disorder_name;
    }

    public String getQuestion() {
        return question;
    }

    public int getYes() {
        return yes;
    }

    public int getNo() {
        return no;
    }
}
