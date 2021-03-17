package com.storyteller_f.filterui_project.sort.name;

import androidx.annotation.NonNull;

import com.storyteller_f.sort_ui.config.SortConfigItem;

public class NameConfigItem extends SortConfigItem {
    @NonNull
    @Override
    public Object clone() {
        return new NameConfigItem();
    }
}
