package com.jsrk.android.vocabflashcards.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Ravi on 9/5/2016.
 */
public class Constants {
    public static final String WORD_ID_STRING = "word_id";
    public static final String INVALID_NAME_MESSAGE = "Name cannot be empty";
    public static final String INVALID_BREAK_NAME_MESSAGE = "Break name cannot be empty";
    public static final int TOAST_DURATION = Toast.LENGTH_SHORT;
    public static Context ACTIVITY;
    public static VocabDbHelper VOCAB_DB_HELPER;

    private Constants() {
    }
}
