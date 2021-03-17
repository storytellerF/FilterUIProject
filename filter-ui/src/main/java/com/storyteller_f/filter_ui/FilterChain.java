package com.storyteller_f.filter_ui;

import android.graphics.Bitmap;

import com.storyteller_f.filter_ui.adapter.FilterItemViewHolder;

public abstract class FilterChain<T>  {
    public String showName;
    public Bitmap icon;
    FilterChain<T> next;
    public FilterChain(String showName, Bitmap icon) {
        this.showName = showName;
        this.icon = icon;
    }

    public String getShowName() {
        return showName;
    }

    public Bitmap getIcon() {
        return icon;
    }

    /**
     *
     * @param t
     * @param viewHolder
     * @return 如果通过筛选，返回true
     */
    public abstract boolean filter(T t, FilterItemViewHolder viewHolder);

    public abstract int getItemViewType();
}
