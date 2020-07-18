package com.arunseto.mhd.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class QuizResponse extends DefaultResponse {
    @SerializedName("data")
    private List<Quiz> data;

    public QuizResponse(boolean status, String message, List<Quiz> data) {
        super(status, message);
        this.data = data;
    }

    public List<Quiz> getData() {
        return data;
    }
}
