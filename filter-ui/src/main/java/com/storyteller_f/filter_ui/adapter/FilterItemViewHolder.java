package com.storyteller_f.filter_ui.adapter;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.storyteller_f.filter_ui.FilterChain;

public abstract class FilterItemViewHolder extends RecyclerView.ViewHolder {
    public FilterItemViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    public abstract void bind(FilterChain<?> filterChain);
}
