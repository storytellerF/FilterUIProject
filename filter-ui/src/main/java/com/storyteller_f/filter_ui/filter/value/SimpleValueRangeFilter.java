package com.storyteller_f.filter_ui.filter.value;

import android.graphics.Bitmap;

import com.storyteller_f.filter_ui.adapter.FilterItemViewHolder;

public class SimpleValueRangeFilter<T> extends ValueRangeFilter<T>{
    public double min;
    public double max;
    public boolean hasMinValue;
    public boolean hasMaxValue;
    public SimpleValueRangeFilter(String showName, Bitmap icon,double min,double max,boolean hasMinValue,boolean hasMaxValue) {
        super(showName, icon);
        this.max=max;
        this.min=min;
        this.hasMaxValue=hasMaxValue;
        this.hasMinValue=hasMinValue;
    }

    @Override
    public double getValue(T t,FilterItemViewHolder viewHolder) {
        return 0;
    }

    @Override
    public boolean hasMinValue(FilterItemViewHolder viewHolder) {
        return hasMinValue;
    }

    @Override
    public boolean hasMaxValue(FilterItemViewHolder viewHolder) {
        return hasMaxValue;
    }

    @Override
    public double minValue(FilterItemViewHolder viewHolder) {
        return min;
    }

    @Override
    public double maxValue(FilterItemViewHolder viewHolder) {
        return max;
    }


}
