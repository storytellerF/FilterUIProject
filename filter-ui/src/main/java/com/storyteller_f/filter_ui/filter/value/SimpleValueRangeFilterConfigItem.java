package com.storyteller_f.filter_ui.filter.value;

import com.storyteller_f.filter_ui.config.FilterConfigItem;

public class SimpleValueRangeFilterConfigItem extends FilterConfigItem {
    double min;
    double max;
    boolean hasMinValue;
    boolean hasMaxValue;

    public SimpleValueRangeFilterConfigItem(double min, double max, boolean hasMinValue, boolean hasMaxValue) {
        this.min = min;
        this.max = max;
        this.hasMinValue = hasMinValue;
        this.hasMaxValue = hasMaxValue;
    }

    public double getMin() {
        return min;
    }

    public double getMax() {
        return max;
    }

    public boolean isHasMinValue() {
        return hasMinValue;
    }

    public boolean isHasMaxValue() {
        return hasMaxValue;
    }
}
