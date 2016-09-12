package com.jsrk.android.vocabflashcards;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jsrk.android.vocabflashcards.adapters.BreakListAdapter;
import com.jsrk.android.vocabflashcards.adapters.ItemListAdapter;
import com.jsrk.android.vocabflashcards.models.Word;
import com.jsrk.android.vocabflashcards.utils.Constants;

import java.util.Locale;

public class ViewWordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_word);

        Intent intent = getIntent();
        String wordId = intent.getStringExtra(Constants.WORD_ID_STRING);
        Word word = Constants.VOCAB_DB_HELPER.getWord(Integer.parseInt(wordId));
        renderView(word);

        Button editButton = (Button) findViewById(R.id.editButton);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Constants.ACTIVITY, InsertWordActivity.class);
                TextView wordTextView = (TextView) findViewById(R.id.viewWordId);
                String wordId = wordTextView.getText().toString();
                intent.putExtra(Constants.WORD_ID_STRING, wordId);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }

    private void renderView(Word word) {
        TextView idTextView = (TextView) findViewById(R.id.viewWordId);
        idTextView.setText(String.format(Locale.US, "%d", word.getId()));

        TextView nameTextView = (TextView) findViewById(R.id.viewName);
        nameTextView.setText(word.getWord());

        LinearLayout breakListView = (LinearLayout) findViewById(R.id.breakDownViewList);
        new BreakListAdapter(Constants.ACTIVITY, breakListView, R.layout.break_view_item,
                word.getBreaks());

        LinearLayout meaningListView = (LinearLayout) findViewById(R.id.meaningViewList);
        new ItemListAdapter(Constants.ACTIVITY, meaningListView, R.layout.item_view_item,
                word.getMeanings(), "Meaning");

        LinearLayout usageListView = (LinearLayout) findViewById(R.id.usageViewList);
        new ItemListAdapter(Constants.ACTIVITY, usageListView, R.layout.item_view_item,
                word.getUsages(), "Usage");
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(Constants.ACTIVITY, ListActivity.class));
        finish();
    }
}
