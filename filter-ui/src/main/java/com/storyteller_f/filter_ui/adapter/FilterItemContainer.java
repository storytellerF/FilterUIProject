package com.storyteller_f.filter_ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.storyteller_f.filter_ui.R;

public class FilterItemContainer {
    private final View view;
    protected FrameLayout frameLayout;

    public FilterItemContainer(ViewGroup context) {
        view = LayoutInflater.from(context.getContext()).inflate(R.layout.layout_item_filter_container, context, false);
        frameLayout = view.findViewById(R.id.sort_ui_frameLayout);
    }

    public FrameLayout getFrameLayout() {
        return frameLayout;
    }

    public View getView() {
        return view;
    }

    public void add(View view) {
        frameLayout.addView(view);
    }

}
