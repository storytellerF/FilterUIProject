package com.storyteller_f.filterui_project.sort.name;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.storyteller_f.filterui_project.ApplicationItem;
import com.storyteller_f.filterui_project.R;
import com.storyteller_f.sort_ui.SortChain;
import com.storyteller_f.sort_ui.adapter.SortItemViewHolder;

public class NameSort extends SortChain<ApplicationItem> {

    public NameSort(Bitmap icon) {
        super("应用名", icon);
    }

    @Override
    public int cmp(ApplicationItem o1, ApplicationItem o2) {
        return o1.getName().compareTo(o2.getName());
    }
    @Override
    public int getItemViewType() {
        return 1;
    }

    public static class NameViewHolder extends SortItemViewHolder {
        TextView name;
        public NameViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.textView_package_sort);
        }

        public static void create(Context parent, FrameLayout container) {
            LayoutInflater.from(parent).inflate(R.layout.layout_item_package_sort, container, true);
        }

        @Override
        public void bind(SortChain<?> sortChain) {
            name.setText(sortChain.getShowName());
        }

    }
}
