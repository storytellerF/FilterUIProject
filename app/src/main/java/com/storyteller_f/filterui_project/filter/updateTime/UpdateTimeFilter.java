package com.storyteller_f.filterui_project.filter.updateTime;

import android.graphics.Bitmap;

import com.storyteller_f.filter_ui.adapter.FilterItemViewHolder;
import com.storyteller_f.filter_ui.filter.value.SimpleValueRangeFilter;
import com.storyteller_f.filterui_project.ApplicationItem;

public class UpdateTimeFilter extends SimpleValueRangeFilter<ApplicationItem> {
    public UpdateTimeFilter(String showName, Bitmap icon, double min, double max, boolean hasMinValue, boolean hasMaxValue) {
        super(showName, icon, min, max, hasMinValue, hasMaxValue);
    }

    @Override
    public double getValue(ApplicationItem applicationItem, FilterItemViewHolder viewHolder) {
        return applicationItem.getUpdateTime();
    }
}
