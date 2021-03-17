package com.storyteller_f.filterui_project.filter;

import android.view.ViewGroup;

import com.storyteller_f.filter_ui.adapter.FilterItemContainer;
import com.storyteller_f.filter_ui.adapter.FilterItemViewHolder;
import com.storyteller_f.filter_ui.adapter.FilterViewHolderFactory;
import com.storyteller_f.filterui_project.filter.date.DateFilter;
import com.storyteller_f.filterui_project.filter.name.NameFilter;
import com.storyteller_f.filterui_project.filter.packageName.PackageFilter;

public class ApplicationFilterFactory extends FilterViewHolderFactory {
    @Override
    public FilterItemViewHolder create(ViewGroup parent, int viewType, FilterItemContainer container) {
        if (viewType == 0) {
            PackageFilter.PackageFilterViewHolder.create(parent.getContext(),container.getFrameLayout());
            return new PackageFilter.PackageFilterViewHolder(container.getView());
        } else if (viewType == 1) {
            NameFilter.NameFilterViewHolder.create(parent.getContext(),container.getFrameLayout());
            return new NameFilter.NameFilterViewHolder(container.getView());
        } else if (viewType == 2) {
            DateFilter.DateFilterViewHolder.create(parent.getContext(),container.getFrameLayout());
            return new DateFilter.DateFilterViewHolder(container.getView());
        }
        return null;
    }
}
