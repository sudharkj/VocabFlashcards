package com.jsrk.android.vocabflashcards.models;

/**
 * Created by Ravi on 9/2/2016.
 */
public class WordRow {
    private long id;
    private String name;

    public WordRow(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
