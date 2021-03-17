package com.storyteller_f.filter_ui.filter.date;

import android.graphics.Bitmap;

import com.storyteller_f.filter_ui.FilterChain;
import com.storyteller_f.filter_ui.adapter.FilterItemViewHolder;

import java.util.Date;

public abstract class DateRangeFilter<T> extends FilterChain<T> {
    private static final String TAG = "DateRangeFilter";
    public DateRangeFilter(String showName, Bitmap icon) {
        super(showName, icon);
    }

    public abstract void bind(FilterItemViewHolder viewHolder);

    @Override
    public boolean filter(T t, FilterItemViewHolder viewHolder) {
        Date time = getTime(t,viewHolder);
        Date startTime = getStartTime(viewHolder);
        Date endTime = getEndTime(viewHolder);
        return time.after(startTime) && time.before(endTime);
    }
    public abstract Date getTime(T t, FilterItemViewHolder viewHolder);
    public abstract Date getEndTime(FilterItemViewHolder viewHolder);
    public abstract Date getStartTime(FilterItemViewHolder viewHolder);
    @Override
    public abstract int getItemViewType() ;
}
