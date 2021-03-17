package com.storyteller_f.filter_ui.filter.regexp;

import android.graphics.Bitmap;

import com.storyteller_f.filter_ui.adapter.FilterItemViewHolder;

public abstract class SimpleRegexpFilter<T> extends RegexpFilter<T> {
    public String regexp;
    public SimpleRegexpFilter(String showName, Bitmap icon,String regexp) {
        super(showName, icon);
        this.regexp=regexp;
    }

    @Override
    protected String getRegexp(FilterItemViewHolder viewHolder) {
        if (viewHolder instanceof SimpleRegexpFilterViewHolder) {
            regexp = ((SimpleRegexpFilterViewHolder) viewHolder).getInput();
            return regexp;
        }
        return null;
    }

    @Override
    public void bind(FilterItemViewHolder viewHolder) {

    }

    @Override
    public abstract CharSequence getMatchString(T t);

    @Override
    public int getItemViewType() {
        return 0;
    }
}
