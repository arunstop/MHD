package com.arunseto.mhd.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TestResultResponse extends DefaultResponse {
    @SerializedName("data")
    private List<TestResult> data;

    public TestResultResponse(boolean status, String message, List<TestResult> data) {
        super(status, message);
        this.data = data;
    }

    public List<TestResult> getData() {
        return data;
    }
}
