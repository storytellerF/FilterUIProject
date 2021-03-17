package com.storyteller_f.sort_ui.adapter;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.storyteller_f.sort_ui.SortChain;

public abstract class SortItemViewHolder extends RecyclerView.ViewHolder {
    public SortItemViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    public abstract void bind(SortChain<?> sortChain);
}
