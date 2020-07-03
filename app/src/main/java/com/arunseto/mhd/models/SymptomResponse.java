package com.arunseto.mhd.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SymptomResponse extends DefaultResponse {
    @SerializedName("data")
    private List<Symptom> data;

    public SymptomResponse(boolean status, String message, List<Symptom> data) {
        super(status, message);
        this.data = data;
    }

    public List<Symptom> getData() {
        return data;
    }
}
