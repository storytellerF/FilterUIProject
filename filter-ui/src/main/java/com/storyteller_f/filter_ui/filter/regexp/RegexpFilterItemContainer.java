package com.storyteller_f.filter_ui.filter.regexp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.storyteller_f.filter_ui.R;
import com.storyteller_f.filter_ui.adapter.FilterItemContainer;

public class RegexpFilterItemContainer extends FilterItemContainer {
    public RegexpFilterItemContainer(ViewGroup context) {
        super(context);
        View inflate = LayoutInflater.from(context.getContext()).inflate(R.layout.layout_item_regexp_container_part, getFrameLayout(), true);
        frameLayout=inflate.findViewById(R.id.filter_ui_regexp_container_framelayout);
    }
}
