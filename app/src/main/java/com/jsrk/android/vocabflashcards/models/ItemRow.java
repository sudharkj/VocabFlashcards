package com.jsrk.android.vocabflashcards.models;

/**
 * Created by Ravi on 9/5/2016.
 */
public class ItemRow {
    private long id;
    private String value;

    public ItemRow(long id, String value) {
        this.id = id;
        this.value = value;
    }

    public long getId() {
        return id;
    }

    public String getValue() {
        return value;
    }

    public void trim() {
        this.value = this.value.trim();
    }
}
