package com.storyteller_f.filter_ui.filter.value;

import android.graphics.Bitmap;

import com.storyteller_f.filter_ui.FilterChain;
import com.storyteller_f.filter_ui.adapter.FilterItemViewHolder;

public abstract class ValueRangeFilter<T> extends FilterChain<T> {
    public ValueRangeFilter(String showName, Bitmap icon) {
        super(showName, icon);
    }

    @Override
    public boolean filter(T t, FilterItemViewHolder viewHolder) {
        double value = getValue(t,viewHolder);
        if (hasMinValue(viewHolder)) {
            if (minValue(viewHolder) > value) {
                return false;
            }
        }
        if (hasMaxValue(viewHolder)) {
            return !(maxValue(viewHolder) < value);
        }
        return true;
    }
    public abstract double getValue(T t, FilterItemViewHolder viewHolder);
    public abstract boolean hasMinValue(FilterItemViewHolder viewHolder);
    public abstract boolean hasMaxValue(FilterItemViewHolder viewHolder);
    public abstract double minValue(FilterItemViewHolder viewHolder);
    public abstract double maxValue(FilterItemViewHolder viewHolder);

    @Override
    public int getItemViewType() {
        return 2;
    }
}
