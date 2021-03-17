package com.storyteller_f.filterui_project.sort.packageName;

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

public class PackageSort extends SortChain<ApplicationItem> {
    public PackageSort(Bitmap icon) {
        super("包名", icon);
    }

    @Override
    public int cmp(ApplicationItem o1, ApplicationItem o2) {
        return o1.getPackageName().compareTo(o2.getPackageName());
    }

    @Override
    public int getItemViewType() {
        return 0;
    }

    public static class PackageViewHolder extends SortItemViewHolder {
        TextView name;
        public PackageViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.textView_package_sort);
        }

        public static void create(Context context, FrameLayout frameLayout) {
            LayoutInflater.from(context).inflate(R.layout.layout_item_package_sort, frameLayout, true);
        }

        @Override
        public void bind(SortChain<?> sortChain) {
            name.setText(sortChain.getShowName());
        }

    }
}
