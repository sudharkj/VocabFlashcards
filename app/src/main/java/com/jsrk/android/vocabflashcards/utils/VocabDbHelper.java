package com.jsrk.android.vocabflashcards.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.jsrk.android.vocabflashcards.models.BreakRow;
import com.jsrk.android.vocabflashcards.models.ItemRow;
import com.jsrk.android.vocabflashcards.models.Word;
import com.jsrk.android.vocabflashcards.models.WordRow;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Ravi on 9/2/2016.
 */
public class VocabDbHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Vocab.db";

    public VocabDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(VocabContract.SQL_CREATE_WORD_ENTRIES);
        db.execSQL(VocabContract.SQL_CREATE_BREAK_ENTRIES);
        db.execSQL(VocabContract.SQL_CREATE_MEANING_ENTRIES);
        db.execSQL(VocabContract.SQL_CREATE_USAGE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(VocabContract.SQL_DELETE_WORD_ENTRIES);
        db.execSQL(VocabContract.SQL_DELETE_BREAK_ENTRIES);
        db.execSQL(VocabContract.SQL_DELETE_MEANING_ENTRIES);
        db.execSQL(VocabContract.SQL_DELETE_USAGE_ENTRIES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public long insertWord(Word word) {
        SQLiteDatabase db = getWritableDatabase();
        long id = word.getId();

        if (id == 0) {
            id = addWord(db, word.getWord());
        } else {
            editWord(db, id, word.getWord());
        }
        updateBreaks(db, id, word.getBreaks());
        updateMeanings(db, id, word.getMeanings());
        updateUsages(db, id, word.getUsages());

        return id;
    }

    private long addWord(SQLiteDatabase db, String word) {
        ContentValues values = new ContentValues();
        long id;

        values.put(VocabContract.WordEntry.COLUMN_NAME_WORD_NAME, word);
        id = db.insert(VocabContract.WordEntry.TABLE_NAME, null, values);

        return id;
    }

    private void editWord(SQLiteDatabase db, long id, String word) {
        ContentValues values = new ContentValues();
        values.put(VocabContract.WordEntry.COLUMN_NAME_WORD_NAME, word);
        String whereClause = VocabContract.WordEntry._ID + " = ?";
        String[] whereArgs = {String.format(Locale.US, "%d", id)};

        db.update(
                VocabContract.WordEntry.TABLE_NAME,                     // The table to query
                values,                               // The columns to return
                whereClause,                                // The columns for the WHERE clause
                whereArgs                            // The values for the WHERE clause
        );
    }

    private void updateBreaks(SQLiteDatabase db, long id, List<BreakRow> breaks) {
        for (BreakRow row : breaks) {
            if (row.getId() == 0) {
                addBreak(db, id, row);
            } else {
                if (row.getName().isEmpty()) {
                    deleteBreak(db, row.getId());
                } else {
                    updateBreak(db, row);
                }
            }
        }
    }

    private void addBreak(SQLiteDatabase db, long wordId, BreakRow row) {
        ContentValues values = new ContentValues();
        values.put(VocabContract.BreakEntry.COLUMN_NAME_WORD, wordId);
        values.put(VocabContract.BreakEntry.COLUMN_NAME_POSITION, row.getPosition());
        values.put(VocabContract.BreakEntry.COLUMN_NAME_BREAK_NAME, row.getName());
        values.put(VocabContract.BreakEntry.COLUMN_NAME_BREAK_MEANING, row.getMeaning());
        db.insert(VocabContract.BreakEntry.TABLE_NAME, null, values);
    }

    private void updateBreak(SQLiteDatabase db, BreakRow row) {
        ContentValues values = new ContentValues();
        values.put(VocabContract.BreakEntry.COLUMN_NAME_POSITION, row.getPosition());
        values.put(VocabContract.BreakEntry.COLUMN_NAME_BREAK_NAME, row.getName());
        values.put(VocabContract.BreakEntry.COLUMN_NAME_BREAK_MEANING, row.getMeaning());
        String whereClause = VocabContract.BreakEntry._ID + " = ?";
        String[] whereArgs = {String.format(Locale.US, "%d", row.getId())};

        db.update(
                VocabContract.BreakEntry.TABLE_NAME,                     // The table to query
                values,                               // The columns to return
                whereClause,                                // The columns for the WHERE clause
                whereArgs                            // The values for the WHERE clause
        );
    }

    private void deleteBreak(SQLiteDatabase db, long id) {
        String whereClause = VocabContract.BreakEntry._ID + " = ?";
        String[] whereArgs = {String.format(Locale.US, "%d", id)};

        db.delete(
                VocabContract.BreakEntry.TABLE_NAME,                     // The table to query
                whereClause,                                // The columns for the WHERE clause
                whereArgs                            // The values for the WHERE clause
        );
    }

    private void updateMeanings(SQLiteDatabase db, long wordId, List<ItemRow> meanings) {
        for (ItemRow row : meanings) {
            if (row.getId() == 0) {
                addMeaning(db, wordId, row.getValue());
            } else {
                if (row.getValue().isEmpty()) {
                    deleteMeaning(db, row.getId());
                } else {
                    updateMeaning(db, row);
                }
            }
        }
    }

    private void addMeaning(SQLiteDatabase db, long id, String value) {
        ContentValues values = new ContentValues();
        values.put(VocabContract.MeaningEntry.COLUMN_NAME_WORD, id);
        values.put(VocabContract.MeaningEntry.COLUMN_NAME_VALUE, value);
        db.insert(VocabContract.MeaningEntry.TABLE_NAME, null, values);
    }

    private void updateMeaning(SQLiteDatabase db, ItemRow row) {
        ContentValues values = new ContentValues();
        values.put(VocabContract.MeaningEntry.COLUMN_NAME_VALUE, row.getValue());
        String whereClause = VocabContract.MeaningEntry._ID + " = ?";
        String[] whereArgs = {String.format(Locale.US, "%d", row.getId())};

        db.update(
                VocabContract.MeaningEntry.TABLE_NAME,                     // The table to query
                values,                               // The columns to return
                whereClause,                                // The columns for the WHERE clause
                whereArgs                            // The values for the WHERE clause
        );
    }

    private void deleteMeaning(SQLiteDatabase db, long id) {
        String whereClause = VocabContract.MeaningEntry._ID + " = ?";
        String[] whereArgs = {String.format(Locale.US, "%d", id)};

        db.delete(
                VocabContract.MeaningEntry.TABLE_NAME,                     // The table to query
                whereClause,                                // The columns for the WHERE clause
                whereArgs                            // The values for the WHERE clause
        );
    }

    private void updateUsages(SQLiteDatabase db, long wordId, List<ItemRow> usages) {
        for (ItemRow row : usages) {
            if (row.getId() == 0) {
                addUsage(db, wordId, row.getValue());
            } else {
                if (row.getValue().isEmpty()) {
                    deleteUsage(db, row.getId());
                } else {
                    updateUsage(db, row);
                }
            }
        }
    }

    private void addUsage(SQLiteDatabase db, long id, String value) {
        ContentValues values = new ContentValues();
        values.put(VocabContract.UsageEntry.COLUMN_NAME_WORD, id);
        values.put(VocabContract.UsageEntry.COLUMN_NAME_VALUE, value);
        db.insert(VocabContract.UsageEntry.TABLE_NAME, null, values);
    }

    private void updateUsage(SQLiteDatabase db, ItemRow row) {
        ContentValues values = new ContentValues();
        values.put(VocabContract.UsageEntry.COLUMN_NAME_VALUE, row.getValue());
        String whereClause = VocabContract.UsageEntry._ID + " = ?";
        String[] whereArgs = {String.format(Locale.US, "%d", row.getId())};

        db.update(
                VocabContract.UsageEntry.TABLE_NAME,                     // The table to query
                values,                               // The columns to return
                whereClause,                                // The columns for the WHERE clause
                whereArgs                            // The values for the WHERE clause
        );
    }

    private void deleteUsage(SQLiteDatabase db, long id) {
        String whereClause = VocabContract.UsageEntry._ID + " = ?";
        String[] whereArgs = {String.format(Locale.US, "%d", id)};

        db.delete(
                VocabContract.UsageEntry.TABLE_NAME,                     // The table to query
                whereClause,                                // The columns for the WHERE clause
                whereArgs                            // The values for the WHERE clause
        );
    }

    public List<WordRow> getWords() {
        String[] projection = {
                VocabContract.WordEntry._ID,
                VocabContract.WordEntry.COLUMN_NAME_WORD_NAME
        };
        String sortOrder =
                VocabContract.WordEntry.COLUMN_NAME_WORD_NAME + " ASC";

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(
                VocabContract.WordEntry.TABLE_NAME,                     // The table to query
                projection,                               // The columns to return
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );

        List<WordRow> words = new ArrayList<WordRow>();
        int idIndex = cursor.getColumnIndex(VocabContract.WordEntry._ID);
        int nameIndex = cursor.getColumnIndex(VocabContract.WordEntry.COLUMN_NAME_WORD_NAME);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            long id = cursor.getLong(idIndex);
            String name = cursor.getString(nameIndex);
            WordRow row = new WordRow(id, name);
            words.add(row);
            cursor.moveToNext();
        }

        return words;
    }

    public Word getWord(long id) {
        SQLiteDatabase db = getReadableDatabase();

        String wordName = getWordName(db, id);
        List<BreakRow> breaks = getBreaks(db, id);
        List<ItemRow> meanings = getMeanings(db, id);
        List<ItemRow> usages = getUsages(db, id);
        Word word = new Word(id, wordName, breaks, meanings, usages);

        return word;
    }

    private String getWordName(SQLiteDatabase db, long wordId) {
        String[] projection = {
                VocabContract.WordEntry.COLUMN_NAME_WORD_NAME
        };
        String selection = VocabContract.WordEntry._ID + " = ?";
        String[] selectionArgs = {String.format(Locale.US, "%d", wordId)};

        Cursor cursor = db.query(
                VocabContract.WordEntry.TABLE_NAME,                     // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );

        int nameIndex = cursor.getColumnIndex(VocabContract.WordEntry.COLUMN_NAME_WORD_NAME);
        cursor.moveToFirst();
        String wordName = cursor.getString(nameIndex);

        return wordName;
    }

    private List<BreakRow> getBreaks(SQLiteDatabase db, long wordId) {
        String[] projection = {
                VocabContract.BreakEntry._ID,
                VocabContract.BreakEntry.COLUMN_NAME_POSITION,
                VocabContract.BreakEntry.COLUMN_NAME_BREAK_NAME,
                VocabContract.BreakEntry.COLUMN_NAME_BREAK_MEANING
        };
        String selection = VocabContract.BreakEntry.COLUMN_NAME_WORD + " = ?";
        String[] selectionArgs = {String.format(Locale.US, "%d", wordId)};
        String sortOrder =
                VocabContract.BreakEntry.COLUMN_NAME_POSITION + " ASC";

        Cursor cursor = db.query(
                VocabContract.BreakEntry.TABLE_NAME,                     // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );

        List<BreakRow> breaks = new ArrayList<BreakRow>();
        int idIndex = cursor.getColumnIndex(VocabContract.BreakEntry._ID);
        int positionIndex = cursor.getColumnIndex(VocabContract.BreakEntry.COLUMN_NAME_POSITION);
        int nameIndex = cursor.getColumnIndex(VocabContract.BreakEntry.COLUMN_NAME_BREAK_NAME);
        int meaningIndex = cursor.getColumnIndex(VocabContract.BreakEntry.COLUMN_NAME_BREAK_MEANING);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            long id = cursor.getLong(idIndex);
            int position = (int) cursor.getLong(positionIndex);
            String name = cursor.getString(nameIndex);
            String meaning = cursor.getString(meaningIndex);
            BreakRow row = new BreakRow(id, position, name, meaning);
            breaks.add(row);
            cursor.moveToNext();
        }

        return breaks;
    }

    private List<ItemRow> getMeanings(SQLiteDatabase db, long wordId) {
        String[] projection = {
                VocabContract.MeaningEntry._ID,
                VocabContract.MeaningEntry.COLUMN_NAME_VALUE
        };
        String selection = VocabContract.MeaningEntry.COLUMN_NAME_WORD + " = ?";
        String[] selectionArgs = {String.format(Locale.US, "%d", wordId)};
        String sortOrder =
                VocabContract.MeaningEntry._ID + " ASC";

        Cursor cursor = db.query(
                VocabContract.MeaningEntry.TABLE_NAME,                     // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );

        List<ItemRow> meanings = new ArrayList<ItemRow>();
        int idIndex = cursor.getColumnIndex(VocabContract.MeaningEntry._ID);
        int valueIndex = cursor.getColumnIndex(VocabContract.MeaningEntry.COLUMN_NAME_VALUE);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            long id = cursor.getLong(idIndex);
            String value = cursor.getString(valueIndex);
            ItemRow meaning = new ItemRow(id, value);
            meanings.add(meaning);
            cursor.moveToNext();
        }

        return meanings;
    }

    private List<ItemRow> getUsages(SQLiteDatabase db, long wordId) {
        String[] projection = {
                VocabContract.UsageEntry._ID,
                VocabContract.UsageEntry.COLUMN_NAME_VALUE
        };
        String selection = VocabContract.UsageEntry.COLUMN_NAME_WORD + " = ?";
        String[] selectionArgs = {String.format(Locale.US, "%d", wordId)};
        String sortOrder =
                VocabContract.UsageEntry._ID + " ASC";

        Cursor cursor = db.query(
                VocabContract.UsageEntry.TABLE_NAME,                     // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );

        List<ItemRow> usages = new ArrayList<ItemRow>();
        int idIndex = cursor.getColumnIndex(VocabContract.UsageEntry._ID);
        int valueIndex = cursor.getColumnIndex(VocabContract.UsageEntry.COLUMN_NAME_VALUE);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            long id = cursor.getLong(idIndex);
            String value = cursor.getString(valueIndex);
            ItemRow usage = new ItemRow(id, value);
            usages.add(usage);
            cursor.moveToNext();
        }

        return usages;
    }
}
