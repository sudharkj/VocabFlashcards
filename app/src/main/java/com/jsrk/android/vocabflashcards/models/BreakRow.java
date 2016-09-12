package com.jsrk.android.vocabflashcards.models;

/**
 * Created by Ravi on 9/2/2016.
 */
public class BreakRow {
    private long id;
    private int position;
    private String name;
    private String meaning;

    public BreakRow(long id, int position, String name, String meaning) {
        this.id = id;
        this.position = position;
        this.name = name;
        this.meaning = meaning;
    }

    public long getId() {
        return id;
    }

    public int getPosition() {
        return position;
    }

    public String getName() {
        return name;
    }

    public String getMeaning() {
        return meaning;
    }
}
