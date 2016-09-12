package com.jsrk.android.vocabflashcards.adapters;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jsrk.android.vocabflashcards.R;
import com.jsrk.android.vocabflashcards.models.ItemRow;

import java.util.List;
import java.util.Locale;

/**
 * Created by Ravi on 9/5/2016.
 */
public class ItemListAdapter extends ListAdapter<ItemRow> {
    private final String HINT;

    public ItemListAdapter(Context context, LinearLayout parent, int layoutResourceId,
                           List<ItemRow> data, String hint) {
        super(context, parent, layoutResourceId, data);
        this.HINT = hint;
    }

    @Override
    public View getView(ItemRow row) {
        View view = inflater.inflate(layoutResourceId, null);
        if (layoutResourceId == R.layout.item_insert_item) {
            view.setTag(row.getId());
            EditText valueEditText = (EditText) view.findViewById(R.id.itemValueEditText);
            valueEditText.setText(row.getValue());
            valueEditText.setHint(HINT);
        } else {
            TextView valueTextView = (TextView) view.findViewById(R.id.itemValue);
            valueTextView.setText(row.getValue());
        }

        return view;
    }
}
