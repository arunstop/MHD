package id.ac.stiki.doleno.mhd.models;

import com.google.gson.annotations.SerializedName;

public class Disorder {


    @SerializedName("ID_PENYAKIT")
    private Integer ID_PENYAKIT;
    @SerializedName("NAMA_PENYAKIT")
    private String NAMA_PENYAKIT;
    @SerializedName("INFORMASI")
    private String INFORMASI;
    @SerializedName("CREATED_AT")
    private String CREATED_AT;
    private int count = 0;

    public Disorder(Integer ID_PENYAKIT, String NAMA_PENYAKIT, String INFORMASI, String CREATED_AT) {
        this.ID_PENYAKIT = ID_PENYAKIT;
        this.NAMA_PENYAKIT = NAMA_PENYAKIT;
        this.INFORMASI = INFORMASI;
        this.CREATED_AT = CREATED_AT;
    }

    public Integer getID_PENYAKIT() {
        return ID_PENYAKIT;
    }

    public String getNAMA_PENYAKIT() {
        return NAMA_PENYAKIT;
    }

    public String getINFORMASI() {
        return INFORMASI;
    }

    public String getCREATED_AT() {
        return CREATED_AT;
    }

    public int getCount() {
        return count;
    }
}
