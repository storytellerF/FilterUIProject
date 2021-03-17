package com.storyteller_f.filter_ui.filter.date;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.storyteller_f.filter_ui.FilterChain;
import com.storyteller_f.filter_ui.R;
import com.storyteller_f.filter_ui.adapter.FilterItemViewHolder;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class SimpleDataRangeFilterViewHolder extends FilterItemViewHolder {
    Button startDate;
    Button endDate;
    Button startTime;
    Button endTime;
    TextView name;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd", Locale.CHINA);
    SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm:ss:SSS", Locale.CHINA);

    public SimpleDataRangeFilterViewHolder(@NonNull View itemView) {
        super(itemView);
        startDate = itemView.findViewById(R.id.date_range_start_date_button);
        endDate = itemView.findViewById(R.id.data_range_end_date_button2);
        name = itemView.findViewById(R.id.data_range_show_name_textView4);
        startTime = itemView.findViewById(R.id.date_range_start_time_button);
        endTime = itemView.findViewById(R.id.data_range_end_time_button2);
    }

    public static void create(Context context, FrameLayout frameLayout) {
        LayoutInflater.from(context).inflate(R.layout.layout_item_date_range_filter, frameLayout, true);
    }

    @Override
    public void bind(FilterChain<?> filterChain) {
        name.setText(filterChain.getShowName());
        if (filterChain instanceof SimpleDataRangeFilter) {

            Date start = ((SimpleDataRangeFilter<?>) filterChain).start;
            if (start != null) {
                startDate.setText(dateFormat.format(start));
                Calendar instance = Calendar.getInstance(Locale.CHINA);
                instance.setTime(start);
                startDate.setTag(instance);
                startTime.setText(timeFormat.format(start));
                startTime.setTag(instance);
            }
            Date end = ((SimpleDataRangeFilter<?>) filterChain).end;
            if (end != null) {
                Calendar instance = Calendar.getInstance(Locale.CHINA);
                instance.setTime(end);
                endDate.setText(dateFormat.format(end));
                endDate.setTag(instance);
                endTime.setText(timeFormat.format(end));
                endTime.setTag(instance);
            }
        }
        startDate.setOnClickListener((v -> dateClick((Button) v,filterChain)));
        startTime.setOnClickListener(v -> timeClick((Button) v,filterChain));
        endDate.setOnClickListener(v -> dateClick((Button) v,filterChain));
        endTime.setOnClickListener(v -> timeClick((Button) v,filterChain));
    }

    private void timeClick(Button v, FilterChain<?> filterChain) {
        Calendar time;
        if (v.getTag() != null) {
            time = (Calendar) v.getTag();
        } else {
            time = Calendar.getInstance(Locale.CHINA);
            time.set(Calendar.SECOND,0);
            time.set(Calendar.MILLISECOND,0);
            v.setTag(time);
        }
        new TimePickerDialog(v.getContext(), (view, hourOfDay, minute) -> {
            time.set(Calendar.HOUR_OF_DAY, hourOfDay);
            time.set(Calendar.MINUTE, minute);
            v.setText(timeFormat.format(time.getTime()));
            if (filterChain instanceof SimpleDataRangeFilter) {
                ((SimpleDataRangeFilter<?>) filterChain).start=getStartTime().getTime();
                ((SimpleDataRangeFilter<?>) filterChain).end=getEndTime().getTime();
            }
        }, time.get(Calendar.HOUR_OF_DAY), time.get(Calendar.MINUTE), true).show();
    }

    private void dateClick(Button v, FilterChain<?> filterChain) {
        Calendar date;
        if (v.getTag() != null) {
            date = (Calendar) v.getTag();
        } else {
            date = Calendar.getInstance(Locale.CHINA);
            v.setTag(date);
        }
        new DatePickerDialog(v.getContext(), (view, year, month, dayOfMonth) -> {
            date.set(Calendar.YEAR, year);
            date.set(Calendar.MONTH, month);
            date.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            v.setText(dateFormat.format(date.getTime()));
            if (filterChain instanceof SimpleDataRangeFilter) {
                ((SimpleDataRangeFilter<?>) filterChain).start=getStartTime().getTime();
                ((SimpleDataRangeFilter<?>) filterChain).end=getEndTime().getTime();
            }
        }, date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DAY_OF_MONTH)).show();
    }

    public Calendar getEndTime() {
        return getCalendar(endDate, endTime);
    }

    private Calendar getCalendar(Button date, Button time) {
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        Calendar endDateCalendar;
        if (date.getTag() != null)
            endDateCalendar = (Calendar) date.getTag();
        else
            endDateCalendar = Calendar.getInstance(Locale.CHINA);
        calendar.set(Calendar.YEAR, endDateCalendar.get(Calendar.YEAR));
        calendar.set(Calendar.MONTH, endDateCalendar.get(Calendar.MONTH));
        calendar.set(Calendar.DAY_OF_MONTH, endDateCalendar.get(Calendar.DAY_OF_MONTH));
        Calendar endTimeCalendar;
        if (time.getTag() != null) {
            endTimeCalendar = (Calendar) time.getTag();
        } else {
            endTimeCalendar = Calendar.getInstance(Locale.CHINA);
        }
        calendar.set(Calendar.HOUR_OF_DAY, endTimeCalendar.get(Calendar.HOUR_OF_DAY));
        calendar.set(Calendar.MINUTE, endTimeCalendar.get(Calendar.MINUTE));
        return calendar;
    }

    public Calendar getStartTime() {
        return getCalendar(startDate, startTime);
    }
}