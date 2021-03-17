package com.storyteller_f.filterui_project.filter.date;

import android.graphics.Bitmap;
import android.view.View;

import androidx.annotation.NonNull;

import com.storyteller_f.filter_ui.adapter.FilterItemViewHolder;
import com.storyteller_f.filter_ui.filter.date.SimpleDataRangeFilter;
import com.storyteller_f.filter_ui.filter.date.SimpleDataRangeFilterViewHolder;
import com.storyteller_f.filterui_project.ApplicationItem;

import java.util.Date;

public class DateFilter extends SimpleDataRangeFilter<ApplicationItem> {

    public DateFilter(String showName, Bitmap icon,Date start,Date end) {
        super(showName, icon,start,end);
    }

    @Override
    public Date getTime(ApplicationItem t, FilterItemViewHolder viewHolder) {
        return t.getInstallTime();
    }

    @Override
    public int getItemViewType() {
        return 2;
    }
    public static class DateFilterViewHolder extends SimpleDataRangeFilterViewHolder{

        public DateFilterViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
