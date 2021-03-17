package com.storyteller_f.filter_ui.adapter;

import android.view.ViewGroup;

public abstract class FilterViewHolderFactory {
    public FilterItemContainer getContainer(ViewGroup parent){
        return new FilterItemContainer(parent);
    }
    public abstract FilterItemViewHolder create(ViewGroup parent, int viewType, FilterItemContainer container);
}
