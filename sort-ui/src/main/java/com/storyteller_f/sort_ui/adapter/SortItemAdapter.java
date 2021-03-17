package com.storyteller_f.sort_ui.adapter;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.storyteller_f.sort_ui.SortChain;

import java.util.List;

public class SortItemAdapter<T> extends RecyclerView.Adapter<SortItemViewHolder> {
    private final List<SortChain<T>> sortChains;
    private final SortViewHolderFactory sortViewHolderFactory;
    public SortItemAdapter(List<SortChain<T>> lists, SortViewHolderFactory sortViewHolderFactory) {
        this.sortChains = lists;
        this.sortViewHolderFactory = sortViewHolderFactory;
    }

    @NonNull
    @Override
    public SortItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return sortViewHolderFactory.create(parent,viewType, sortViewHolderFactory.getContainer(parent));
    }

    @Override
    public void onBindViewHolder(@NonNull SortItemViewHolder holder, int position) {
        holder.bind(sortChains.get(position));
    }

    @Override
    public int getItemViewType(int position) {
        return sortChains.get(position).getItemViewType();
    }

    @Override
    public int getItemCount() {
        return sortChains.size();
    }
}
