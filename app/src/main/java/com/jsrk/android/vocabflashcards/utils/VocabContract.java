package com.jsrk.android.vocabflashcards.utils;

import android.provider.BaseColumns;

/**
 * Created by Ravi on 9/2/2016.
 */
public class VocabContract {
    private static final String CREATE_TABLE = "CREATE TABLE ";
    private static final String OPEN_BRACE = " (";
    private static final String CLOSE_BRACE = ")";
    private static final String TEXT_TYPE = " TEXT";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String PRIMARY_KEY = INTEGER_TYPE + " PRIMARY KEY, ";
    protected static final String SQL_CREATE_WORD_ENTRIES = CREATE_TABLE + WordEntry.TABLE_NAME +
            OPEN_BRACE + WordEntry._ID + PRIMARY_KEY + WordEntry.COLUMN_NAME_WORD_NAME +
            TEXT_TYPE + CLOSE_BRACE;
    private static final String COMMA_SEP = ", ";
    protected static final String SQL_CREATE_BREAK_ENTRIES = CREATE_TABLE + BreakEntry.TABLE_NAME +
            OPEN_BRACE + BreakEntry._ID + PRIMARY_KEY + BreakEntry.COLUMN_NAME_WORD + INTEGER_TYPE +
            COMMA_SEP + BreakEntry.COLUMN_NAME_POSITION + INTEGER_TYPE + COMMA_SEP +
            BreakEntry.COLUMN_NAME_BREAK_NAME + TEXT_TYPE + COMMA_SEP +
            BreakEntry.COLUMN_NAME_BREAK_MEANING + TEXT_TYPE + CLOSE_BRACE;
    protected static final String SQL_CREATE_MEANING_ENTRIES = CREATE_TABLE +
            MeaningEntry.TABLE_NAME + OPEN_BRACE + MeaningEntry._ID + PRIMARY_KEY +
            MeaningEntry.COLUMN_NAME_WORD + INTEGER_TYPE + COMMA_SEP +
            MeaningEntry.COLUMN_NAME_VALUE + TEXT_TYPE + CLOSE_BRACE;
    protected static final String SQL_CREATE_USAGE_ENTRIES = CREATE_TABLE + UsageEntry.TABLE_NAME +
            OPEN_BRACE + UsageEntry._ID + PRIMARY_KEY + UsageEntry.COLUMN_NAME_WORD +
            INTEGER_TYPE + COMMA_SEP + UsageEntry.COLUMN_NAME_VALUE + TEXT_TYPE + CLOSE_BRACE;
    private static final String DROP_TABLE = "DROP TABLE IF EXISTS ";
    protected static final String SQL_DELETE_WORD_ENTRIES = DROP_TABLE + WordEntry.TABLE_NAME;
    protected static final String SQL_DELETE_BREAK_ENTRIES = DROP_TABLE + BreakEntry.TABLE_NAME;
    protected static final String SQL_DELETE_MEANING_ENTRIES = DROP_TABLE + MeaningEntry.TABLE_NAME;
    protected static final String SQL_DELETE_USAGE_ENTRIES = DROP_TABLE + UsageEntry.TABLE_NAME;

    private VocabContract() {
    }

    public static class WordEntry implements BaseColumns {
        public static final String TABLE_NAME = "word";
        public static final String COLUMN_NAME_WORD_NAME = "name";
    }

    public static class BreakEntry implements BaseColumns {
        public static final String TABLE_NAME = "break";
        public static final String COLUMN_NAME_WORD = "word";
        public static final String COLUMN_NAME_POSITION = "position";
        public static final String COLUMN_NAME_BREAK_NAME = "name";
        public static final String COLUMN_NAME_BREAK_MEANING = "meaning";
    }

    public static class MeaningEntry implements BaseColumns {
        public static final String TABLE_NAME = "meaning";
        public static final String COLUMN_NAME_WORD = "word";
        public static final String COLUMN_NAME_VALUE = "value";
    }

    public static class UsageEntry implements BaseColumns {
        public static final String TABLE_NAME = "usage";
        public static final String COLUMN_NAME_WORD = "word";
        public static final String COLUMN_NAME_VALUE = "value";
    }
}
