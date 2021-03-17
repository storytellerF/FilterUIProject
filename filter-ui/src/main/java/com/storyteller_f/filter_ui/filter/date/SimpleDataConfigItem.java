package com.storyteller_f.filter_ui.filter.date;

import com.storyteller_f.filter_ui.config.FilterConfigItem;

import java.util.Date;

public class SimpleDataConfigItem extends FilterConfigItem {
    public Date start;
    public Date end;

    public SimpleDataConfigItem(Date start, Date end) {
        this.start = start;
        this.end = end;
    }
}
