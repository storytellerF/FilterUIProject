package com.storyteller_f.sort_ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.storyteller_f.sort_ui.R;

public class SortItemContainer {
    private final View view;
    private final FrameLayout frameLayout;

    public SortItemContainer(ViewGroup context) {
        view = LayoutInflater.from(context.getContext()).inflate(R.layout.layout_item_sort_container, context, false);
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
