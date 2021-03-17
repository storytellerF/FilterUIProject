package com.storyteller_f.sort_ui.config;

import androidx.annotation.NonNull;

import com.storyteller_f.config_edit.Config;

import java.util.ArrayList;
import java.util.List;

public class SortConfig extends Config {
    public final List<SortConfigItem> configItems;
    public SortConfig() {
        super();
        configItems=new ArrayList<>();
    }
    public List<SortConfigItem> getConfigItems() {
        return configItems;
    }
    public void reUpdate(List<SortConfigItem> sortConfigItems) {
        this.configItems.clear();
        this.configItems.addAll(sortConfigItems);
    }
    @NonNull
    @Override
    public Object clone() throws CloneNotSupportedException {
        SortConfig sortConfig = new SortConfig();
        sortConfig.name=name;
        for (SortConfigItem sortConfigItem : configItems) {
            sortConfig.configItems.add((SortConfigItem) sortConfigItem.clone());
        }
        return sortConfig;
    }
}
