package com.storyteller_f.filter_ui.filter.date;

import android.graphics.Bitmap;

import com.storyteller_f.filter_ui.adapter.FilterItemViewHolder;

import java.util.Date;

public abstract class SimpleDataRangeFilter<T> extends DateRangeFilter<T> {
    public Date start;
    public Date end;
    public SimpleDataRangeFilter(String showName, Bitmap icon,Date start,Date end) {
        super(showName, icon);
        this.start=start;
        this.end=end;
    }

    @Override
    public void bind(FilterItemViewHolder viewHolder) {

    }

    @Override
    public abstract Date getTime(T t, FilterItemViewHolder viewHolder);

    @Override
    public Date getEndTime(FilterItemViewHolder viewHolder) {
        return end;
    }

    @Override
    public Date getStartTime(FilterItemViewHolder viewHolder) {
        return start;
    }

    @Override
    public int getItemViewType() {
        return 1;
    }
}
