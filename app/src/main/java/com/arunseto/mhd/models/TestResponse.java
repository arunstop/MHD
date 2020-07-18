package com.arunseto.mhd.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TestResponse extends DefaultResponse {
    @SerializedName("data")
    private List<Test> data;

    public TestResponse(boolean status, String message, List<Test> data) {
        super(status, message);
        this.data = data;
    }

    public List<Test> getData() {
        return data;
    }
}
