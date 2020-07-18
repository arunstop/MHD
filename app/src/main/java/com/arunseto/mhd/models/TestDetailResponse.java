package com.arunseto.mhd.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TestDetailResponse extends DefaultResponse {
    @SerializedName("data")
    private List<TestDetail> data;

    public TestDetailResponse(boolean status, String message, List<TestDetail> data) {
        super(status, message);
        this.data = data;
    }

    public List<TestDetail> getData() {
        return data;
    }
}
