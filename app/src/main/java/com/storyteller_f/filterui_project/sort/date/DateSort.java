package com.storyteller_f.filterui_project.sort.date;

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

public class DateSort extends SortChain<ApplicationItem> {
    public DateSort(String showName, Bitmap icon) {
        super(showName, icon);
    }

    @Override
    public int cmp(ApplicationItem o1, ApplicationItem o2) {
        return o1.getInstallTime().compareTo(o2.getInstallTime());
    }

    @Override
    public int getItemViewType() {
        return 2;
    }
    public static class DateViewHolder extends SortItemViewHolder {
        TextView name;
        public DateViewHolder(@NonNull View itemView) {
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
