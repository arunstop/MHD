package com.arunseto.mhd.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NoteResponse extends DefaultResponse {
    @SerializedName("data")
    private List<Note> data;

    public NoteResponse(boolean status, String message, List<Note> data) {
        super(status, message);
        this.data = data;
    }

    public List<Note> getData() {
        return data;
    }
}
