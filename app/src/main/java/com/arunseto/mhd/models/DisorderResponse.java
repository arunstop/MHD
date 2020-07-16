package com.arunseto.mhd.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DisorderResponse extends DefaultResponse {
    @SerializedName("data")
    private List<Disorder> data;

    public DisorderResponse(boolean status, String message, List<Disorder> data) {
        super(status, message);
        this.data = data;
    }

    public List<Disorder> getData() {
        return data;
    }
}
