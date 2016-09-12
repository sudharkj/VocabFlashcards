package com.jsrk.android.vocabflashcards;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.jsrk.android.vocabflashcards.adapters.BreakListAdapter;
import com.jsrk.android.vocabflashcards.adapters.ItemListAdapter;
import com.jsrk.android.vocabflashcards.models.BreakRow;
import com.jsrk.android.vocabflashcards.models.ItemRow;
import com.jsrk.android.vocabflashcards.models.Word;
import com.jsrk.android.vocabflashcards.utils.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class InsertWordActivity extends AppCompatActivity {

    private Intent previousIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_word);

        Word word = getWord();
        renderView(word);
        Button insertButton = (Button) findViewById(R.id.insertButton);
        insertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Word word = createWord();
                if (word != null) {
                    long id = Constants.VOCAB_DB_HELPER.insertWord(word);
                    Intent intent = new Intent(Constants.ACTIVITY, ViewWordActivity.class);
                    String wordId = String.format(Locale.US, "%d", id);
                    intent.putExtra(Constants.WORD_ID_STRING, wordId);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            }
        });

        if (word.getId() == 0) {
            previousIntent = new Intent(Constants.ACTIVITY, ListActivity.class);
        } else {
            previousIntent = new Intent(Constants.ACTIVITY, ViewWordActivity.class);
            String wordId = String.format(Locale.US, "%d", word.getId());
            previousIntent.putExtra(Constants.WORD_ID_STRING, wordId);
        }
    }

    private Word getWord() {
        Intent intent = getIntent();
        String wordId = intent.getStringExtra(Constants.WORD_ID_STRING);

        if (wordId == null) {
            return new Word(0, "", new ArrayList<BreakRow>(0), new ArrayList<ItemRow>(0),
                    new ArrayList<ItemRow>(0));
        } else {
            return Constants.VOCAB_DB_HELPER.getWord(Integer.parseInt(wordId));
        }
    }

    private void renderView(Word word) {
        TextView idTextView = (TextView) findViewById(R.id.wordIdTextView);
        idTextView.setText(String.format(Locale.US, "%d", word.getId()));

        EditText nameEditText = (EditText) findViewById(R.id.insertNameEditText);
        nameEditText.setText(word.getWord());

        LinearLayout breakListView = (LinearLayout) findViewById(R.id.breakDownEditList);
        final BreakListAdapter breakListAdapter = new BreakListAdapter(Constants.ACTIVITY,
                breakListView, R.layout.break_insert_item, word.getBreaks());
        Button addBreakButton = (Button) findViewById(R.id.addBreakdownButton);
        addBreakButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                breakListAdapter.add(new BreakRow(0, 0, "", ""));
            }
        });

        LinearLayout meaningListView = (LinearLayout) findViewById(R.id.meaningEditList);
        final ItemListAdapter meaningListAdapter = new ItemListAdapter(Constants.ACTIVITY,
                meaningListView, R.layout.item_insert_item, word.getMeanings(), "Meaning");
        Button addMeaningButton = (Button) findViewById(R.id.addMeaningButton);
        addMeaningButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                meaningListAdapter.add(new ItemRow(0, ""));
            }
        });

        LinearLayout usageListView = (LinearLayout) findViewById(R.id.usageEditList);
        final ItemListAdapter usageListAdapter = new ItemListAdapter(Constants.ACTIVITY,
                usageListView, R.layout.item_insert_item, word.getUsages(), "Usage");
        Button addUsageButton = (Button) findViewById(R.id.addUsageButton);
        addUsageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usageListAdapter.add(new ItemRow(0, ""));
            }
        });
    }

    private Word createWord() {
        EditText wordNameEditText = (EditText) findViewById(R.id.insertNameEditText);
        String wordName = wordNameEditText.getText().toString().trim();

        if (wordName.isEmpty()) {
            Toast.makeText(Constants.ACTIVITY, Constants.INVALID_NAME_MESSAGE,
                    Constants.TOAST_DURATION).show();
            return null;
        } else {
            View view;
            TextView idTextView;
            EditText nameEditText, meaningEditText, valueEditText;
            List<BreakRow> breaks;
            List<ItemRow> meanings, usages;
            String name, meaning, value;
            long wordId, id;
            int position, i, count;

            TextView wordIdTextView = (TextView) findViewById(R.id.wordIdTextView);
            wordId = Long.valueOf(wordIdTextView.getText().toString());

            LinearLayout breakListView = (LinearLayout) findViewById(R.id.breakDownEditList);
            breaks = new ArrayList<BreakRow>();
            position = 0;
            count = breakListView.getChildCount();
            for (i = 0; i < count; ++i) {
                view = breakListView.getChildAt(i);
                idTextView = (TextView) view.findViewById(R.id.breakIdTextView);
                id = Long.parseLong(idTextView.getText().toString());
                nameEditText = (EditText) view.findViewById(R.id.breakNameEditText);
                name = nameEditText.getText().toString().trim();
                meaningEditText = (EditText) view.findViewById(R.id.breakMeaningEditText);
                meaning = meaningEditText.getText().toString().trim();
                if (name.isEmpty() && !meaning.isEmpty()) {
                    Toast.makeText(Constants.ACTIVITY, Constants.INVALID_BREAK_NAME_MESSAGE,
                            Constants.TOAST_DURATION).show();
                    return null;
                } else {
                    if (!name.isEmpty()) {
                        ++position;
                    }
                    if (id>0 || !name.isEmpty()) {
                        breaks.add(new BreakRow(id, position, name, meaning));
                    }
                }
            }

            LinearLayout meaningListView = (LinearLayout) findViewById(R.id.meaningEditList);
            meanings = new ArrayList<ItemRow>();
            count = meaningListView.getChildCount();
            for (i = 0; i < count; ++i) {
                view = meaningListView.getChildAt(i);
                idTextView = (TextView) view.findViewById(R.id.itemIdTextView);
                id = Long.parseLong(idTextView.getText().toString());
                valueEditText = (EditText) view.findViewById(R.id.itemValueEditText);
                value = valueEditText.getText().toString().trim();
                if (id > 0 || !value.isEmpty()) {
                    meanings.add(new ItemRow(id, value));
                }
            }

            LinearLayout usageListView = (LinearLayout) findViewById(R.id.usageEditList);
            usages = new ArrayList<ItemRow>();
            count = usageListView.getChildCount();
            for (i = 0; i < count; ++i) {
                view = usageListView.getChildAt(i);
                idTextView = (TextView) view.findViewById(R.id.itemIdTextView);
                id = Long.parseLong(idTextView.getText().toString());
                valueEditText = (EditText) view.findViewById(R.id.itemValueEditText);
                value = valueEditText.getText().toString().trim();
                if (id > 0 || !value.isEmpty()) {
                    usages.add(new ItemRow(id, value));
                }
            }

            return new Word(wordId, wordName, breaks, meanings, usages);
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(previousIntent);
        finish();
    }
}
