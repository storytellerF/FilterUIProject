package com.storyteller_f.filterui_project.sort;

import android.view.ViewGroup;

import com.storyteller_f.filterui_project.sort.date.DateSort;
import com.storyteller_f.filterui_project.sort.name.NameSort;
import com.storyteller_f.filterui_project.sort.packageName.PackageSort;
import com.storyteller_f.sort_ui.adapter.SortItemContainer;
import com.storyteller_f.sort_ui.adapter.SortItemViewHolder;
import com.storyteller_f.sort_ui.adapter.SortViewHolderFactory;

public class ApplicationSortFactory extends SortViewHolderFactory {
    @Override
    public SortItemViewHolder create(ViewGroup parent, int viewType, SortItemContainer container) {
        if (viewType == 0) {
            PackageSort.PackageViewHolder.create(parent.getContext(), container.getFrameLayout());
            return new PackageSort.PackageViewHolder(container.getView());
        } else if (viewType == 1) {
            NameSort.NameViewHolder.create(parent.getContext(), container.getFrameLayout());
            return new NameSort.NameViewHolder(container.getView());
        } else if (viewType == 2) {
            DateSort.DateViewHolder.create(parent.getContext(),container.getFrameLayout());
            return new DateSort.DateViewHolder(container.getView());
        }
        return null;
    }
}
