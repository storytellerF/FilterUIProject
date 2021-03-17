package com.storyteller_f.filter_ui.filter.regexp;

import com.storyteller_f.filter_ui.config.FilterConfigItem;

public class SimpleRegexpConfigItem extends FilterConfigItem {
    public String regexp;

    public SimpleRegexpConfigItem(String regexp) {
        this.regexp = regexp;
    }
}
