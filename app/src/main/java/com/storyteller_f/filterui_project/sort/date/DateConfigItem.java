package com.storyteller_f.filterui_project.sort.date;

import androidx.annotation.NonNull;

import com.storyteller_f.sort_ui.config.SortConfigItem;

public class DateConfigItem extends SortConfigItem {
    @NonNull
    @Override
    public Object clone() {
        return new DateConfigItem();
    }
}
