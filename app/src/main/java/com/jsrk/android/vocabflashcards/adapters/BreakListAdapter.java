package com.jsrk.android.vocabflashcards.adapters;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jsrk.android.vocabflashcards.R;
import com.jsrk.android.vocabflashcards.models.BreakRow;
import com.jsrk.android.vocabflashcards.adapters.ListAdapter;

import java.util.List;
import java.util.Locale;

/**
 * Created by Ravi on 9/7/2016.
 */
public class BreakListAdapter extends ListAdapter<BreakRow> {

    public BreakListAdapter(Context context, LinearLayout parent, int layoutResourceId, List<BreakRow> data) {
        super(context, parent, layoutResourceId, data);
    }

    @Override
    public View getView(BreakRow row) {
        View view = inflater.inflate(layoutResourceId, null);
        if (layoutResourceId == R.layout.break_insert_item) {
            TextView idTextView = (TextView) view.findViewById(R.id.breakIdTextView);
            idTextView.setText(String.format(Locale.US, "%d", row.getId()));
            EditText nameEditText = (EditText) view.findViewById(R.id.breakNameEditText);
            nameEditText.setText(row.getName());
            EditText meaningEditText = (EditText) view.findViewById(R.id.breakMeaningEditText);
            meaningEditText.setText(row.getMeaning());
        } else {
            TextView nameTextView = (TextView) view.findViewById(R.id.viewBreakName);
            nameTextView.setText(row.getName());
            TextView meaningTextView = (TextView) view.findViewById(R.id.viewBreakMeaning);
            meaningTextView.setText(row.getMeaning());
        }

        return view;
    }

}
