package com.jsrk.android.vocabflashcards;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jsrk.android.vocabflashcards.models.WordRow;

import org.w3c.dom.Text;

import java.util.List;
import java.util.Locale;

/**
 * Created by Ravi on 9/2/2016.
 */
public class WordListAdapter extends ArrayAdapter<WordRow> {
    Context context;
    int layoutResourceId;
    List<WordRow> data;

    public WordListAdapter(Context context, int layoutResourceId,
                              List<WordRow> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    private class ViewHolder {
        TextView id;
        TextView name;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(layoutResourceId, parent, false);

            holder = new ViewHolder();
            holder.id = (TextView) convertView.findViewById(R.id.wordIdTextView);
            holder.name = (TextView) convertView.findViewById(R.id.wordNameTextView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (!data.isEmpty()) {
            WordRow row = data.get(position);
            holder.id.setText(String.format(Locale.US, "%d", row.getId()));
            holder.name.setText(row.getName());
        }

        return convertView;

    }
}
