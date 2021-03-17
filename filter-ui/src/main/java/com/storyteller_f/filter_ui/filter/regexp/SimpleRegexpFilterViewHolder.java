package com.storyteller_f.filter_ui.filter.regexp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.storyteller_f.filter_ui.FilterChain;
import com.storyteller_f.filter_ui.R;
import com.storyteller_f.filter_ui.adapter.FilterItemViewHolder;

import java.util.Locale;

public class SimpleRegexpFilterViewHolder extends FilterItemViewHolder {
    EditText editText;
    TextView textView;

    public SimpleRegexpFilterViewHolder(@NonNull View itemView) {
        super(itemView);
        editText = itemView.findViewById(R.id.filter_input_text_regexp);
        textView = itemView.findViewById(R.id.filter_input_textView_regexp);
    }

    public static void create(Context context, FrameLayout frameLayout) {
        LayoutInflater.from(context).inflate(R.layout.layout_item_input_filter_regexp, frameLayout, true);
    }

    public String getInput() {
        return editText.getText().toString().trim();
    }

    @Override
    public void bind(FilterChain<?> filterChain) {
        if (filterChain instanceof SimpleRegexpFilter) {
            editText.setText(((SimpleRegexpFilter<?>) filterChain).regexp);
        }
        textView.setText(String.format(Locale.CHINA,"请输入%s的正则表达式",filterChain.getShowName()) );
        editText.setOnKeyListener((v, keyCode, event) -> {
            if (filterChain instanceof SimpleRegexpFilter<?>) {
                ((SimpleRegexpFilter<?>) filterChain).regexp=getInput();
            }
            return false;
        });
    }
}