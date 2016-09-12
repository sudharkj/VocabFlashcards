package com.jsrk.android.vocabflashcards.models;

import java.util.List;

/**
 * Created by Ravi on 9/2/2016.
 */
public class Word {
    private long id;
    private String word;
    private List<BreakRow> breaks;
    private List<ItemRow> meanings;
    private List<ItemRow> usages;

    public Word(long id, String word, List<BreakRow> breaks, List<ItemRow> meanings, List<ItemRow> usages) {
        this.id = id;
        this.word = word;
        this.breaks = breaks;
        this.meanings = meanings;
        this.usages = usages;
    }

    public long getId() {
        return id;
    }

    public String getWord() {
        return word;
    }

    public List<BreakRow> getBreaks() {
        return breaks;
    }

    public List<ItemRow> getMeanings() {
        return meanings;
    }

    public List<ItemRow> getUsages() {
        return usages;
    }
}
