package com.storyteller_f.filterui_project.sort.packageName;

import androidx.annotation.NonNull;

import com.storyteller_f.sort_ui.config.SortConfigItem;

public class PackageConfigItem extends SortConfigItem {
    @NonNull
    @Override
    public Object clone() {
        return new PackageConfigItem();
    }
}
