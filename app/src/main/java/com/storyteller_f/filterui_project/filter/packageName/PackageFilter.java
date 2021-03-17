package com.storyteller_f.filterui_project.filter.packageName;

import android.graphics.Bitmap;
import android.view.View;

import androidx.annotation.NonNull;

import com.storyteller_f.filter_ui.filter.regexp.SimpleRegexpFilter;
import com.storyteller_f.filter_ui.filter.regexp.SimpleRegexpFilterViewHolder;
import com.storyteller_f.filterui_project.ApplicationItem;

public class PackageFilter extends SimpleRegexpFilter<ApplicationItem> {
    public PackageFilter(Bitmap icon,String regexp) {
        super("包 名", icon,regexp);
    }

    @Override
    public CharSequence getMatchString(ApplicationItem t) {
        return t.getPackageName();
    }

    @Override
    public int getItemViewType() {
        return 0;
    }

    public static class PackageFilterViewHolder extends SimpleRegexpFilterViewHolder{
        public PackageFilterViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
