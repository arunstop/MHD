package com.arunseto.mhd.models;

import java.util.List;
public class DummyListNote {
    private List<Note> dln;
    private static DummyListNote instance = null;
    protected DummyListNote() {
        // Exists only to defeat instantiation.
    }

    public static DummyListNote getInstance() {
        if(instance == null) {
            instance = new DummyListNote();
        }
        return instance;
    }

    public void setDln(List<Note> dln) {
        this.dln = dln;
    }

    public List<Note> getDln() {
        return dln;
    }
}
