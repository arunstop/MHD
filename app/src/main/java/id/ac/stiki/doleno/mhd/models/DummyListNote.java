package id.ac.stiki.doleno.mhd.models;

import java.util.List;

public class DummyListNote {
    private static DummyListNote instance = null;
    // this is an singleton class
    // a class where
    // 1. It can save the object and use it globally
    // 2. Does not need a initialization
    private List<Note> dln;

    protected DummyListNote() {
        // Exists only to defeat instantiation.
    }

    public static DummyListNote getInstance() {
        if (instance == null) {
            instance = new DummyListNote();
        }
        return instance;
    }

    public List<Note> getDln() {
        return dln;
    }

    public void setDln(List<Note> dln) {
        this.dln = dln;
    }
}
