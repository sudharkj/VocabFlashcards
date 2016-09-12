package com.jsrk.android.vocabflashcards.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import java.util.List;

/**
 * Created by Ravi on 9/5/2016.
 */
public abstract class ListAdapter<T> {

    private LinearLayout parent;
    private Context context;
    private List<T> data;

    protected LayoutInflater inflater;
    protected int layoutResourceId;

    public ListAdapter(Context context, LinearLayout parent, int layoutResourceId, List<T> data) {
        this.context = context;
        this.inflater = ((Activity) context).getLayoutInflater();
        this.parent = parent;
        this.layoutResourceId = layoutResourceId;
        this.data = data;
        populateView();
    }

    private void populateView() {
        for (T row : data) {
            parent.addView(getView(row));
        }
    }

    public abstract View getView(T row);

    public final void add(T row) {
        data.add(row);
        parent.addView(getView(row));
    }
}
