package com.storyteller_f.sort_ui.adapter;

import android.view.ViewGroup;

public abstract class SortViewHolderFactory {
    public SortItemContainer getContainer(ViewGroup parent){
        return new SortItemContainer(parent);
    }
    public abstract SortItemViewHolder create(ViewGroup parent, int viewType, SortItemContainer container);
}
