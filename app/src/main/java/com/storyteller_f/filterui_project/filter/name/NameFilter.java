package com.storyteller_f.filterui_project.filter.name;

import android.graphics.Bitmap;
import android.view.View;

import androidx.annotation.NonNull;

import com.storyteller_f.filter_ui.filter.regexp.SimpleRegexpFilter;
import com.storyteller_f.filter_ui.filter.regexp.SimpleRegexpFilterViewHolder;
import com.storyteller_f.filterui_project.ApplicationItem;

public class NameFilter extends SimpleRegexpFilter<ApplicationItem> {
    public NameFilter( Bitmap icon,String regexp) {
        super("应用名", icon,regexp);
    }

    @Override
    public int getItemViewType() {
        return 1;
    }

    @Override
    public CharSequence getMatchString(ApplicationItem applicationItem) {
        return applicationItem.getName();
    }
    public static class NameFilterViewHolder extends SimpleRegexpFilterViewHolder {
        public NameFilterViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
