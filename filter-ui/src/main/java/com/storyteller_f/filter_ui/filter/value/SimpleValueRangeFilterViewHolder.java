package com.storyteller_f.filter_ui.filter.value;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.storyteller_f.filter_ui.FilterChain;
import com.storyteller_f.filter_ui.R;
import com.storyteller_f.filter_ui.adapter.FilterItemViewHolder;

public class SimpleValueRangeFilterViewHolder extends FilterItemViewHolder {
    TextView name;
    EditText min;
    EditText max;
    public SimpleValueRangeFilterViewHolder(@NonNull View itemView) {
        super(itemView);
        name=itemView.findViewById(R.id.value_range_name_textView5);
        min=itemView.findViewById(R.id.value_range_min_value_range_editTextNumberDecimal);
        max=itemView.findViewById(R.id.value_range_max_editTextNumberDecimal2);
    }

    @Override
    public void bind(FilterChain<?> filterChain) {
        name.setText(filterChain.getShowName());
        if (filterChain instanceof SimpleValueRangeFilter) {
            SimpleValueRangeFilter<?> filterChain1 = (SimpleValueRangeFilter<?>) filterChain;
            min.setText(String.format("%s", filterChain1.min));
            max.setText(String.format("%s", filterChain1.max));
            min.setOnKeyListener((v, keyCode, event) -> {
                String trim = min.getText().toString().trim();
                if (trim.length() == 0) {
                    filterChain1.hasMinValue = false;
                } else {
                    filterChain1.hasMinValue=true;
                    filterChain1.min=Double.parseDouble(trim);
                }
                return false;
            });
            max.setOnKeyListener(((v, keyCode, event) -> {
                String trim=max.getText().toString().trim();
                if (trim.length() == 0) {
                    filterChain1.hasMaxValue = false;
                } else {
                    filterChain1.hasMaxValue=true;
                    filterChain1.max=Double.parseDouble(trim);
                }
                return false;
            }));
        }
    }
}
