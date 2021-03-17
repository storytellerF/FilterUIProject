package com.storyteller_f.filter_ui.config;

import androidx.annotation.NonNull;

import com.storyteller_f.config_edit.Config;

import java.util.ArrayList;
import java.util.List;

public class FilterConfig extends Config {
    private final ArrayList<FilterConfigItem> configItems;

    public FilterConfig() {
        super();
        configItems = new ArrayList<>();
    }
    public void reUpdate(List<FilterConfigItem> sortConfigItems) {
        this.configItems.clear();
        this.configItems.addAll(sortConfigItems);
    }
    public ArrayList<FilterConfigItem> getConfigItems() {
        return configItems;
    }

    @NonNull
    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
