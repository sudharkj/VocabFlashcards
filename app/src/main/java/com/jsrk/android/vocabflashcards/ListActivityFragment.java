package com.jsrk.android.vocabflashcards;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.jsrk.android.vocabflashcards.models.WordRow;
import com.jsrk.android.vocabflashcards.utils.Constants;
import com.jsrk.android.vocabflashcards.utils.VocabDbHelper;

import java.util.List;
import java.util.Locale;

/**
 * A placeholder fragment containing a simple view.
 */
public class ListActivityFragment extends Fragment {

    public ListActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list, container, false);
        Constants.VOCAB_DB_HELPER = new VocabDbHelper(getActivity().getApplicationContext());
        Constants.ACTIVITY = getActivity();

        List<WordRow> words = Constants.VOCAB_DB_HELPER.getWords();
        ListView wordsView = (ListView) rootView.findViewById(R.id.wordlistView);
        WordListAdapter wordListAdapter = new WordListAdapter(Constants.ACTIVITY,
                R.layout.word_item, words);
        wordsView.setAdapter(wordListAdapter);
        wordsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                WordRow row = (WordRow) parent.getItemAtPosition(position);
                Intent intent = new Intent(Constants.ACTIVITY, ViewWordActivity.class);
                String wordId = String.format(Locale.US, "%d", row.getId());
                intent.putExtra(Constants.WORD_ID_STRING, wordId);
                startActivity(intent);
            }
        });

        return rootView;
    }
}
