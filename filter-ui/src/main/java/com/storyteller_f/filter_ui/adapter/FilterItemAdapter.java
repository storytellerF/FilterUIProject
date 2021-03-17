package com.storyteller_f.filter_ui.adapter;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.storyteller_f.filter_ui.FilterChain;
import com.storyteller_f.filter_ui.Filterable;

import java.util.List;

public class FilterItemAdapter<T extends Filterable> extends RecyclerView.Adapter<FilterItemViewHolder> {
    private final List<FilterChain<T>> filterChains;
    private final FilterViewHolderFactory filterViewHolderFactory;
    public FilterItemAdapter(List<FilterChain<T>> lists, FilterViewHolderFactory filterViewHolderFactory) {
        this.filterChains = lists;
        this.filterViewHolderFactory = filterViewHolderFactory;
    }

    @NonNull
    @Override
    public FilterItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return filterViewHolderFactory.create(parent,viewType, filterViewHolderFactory.getContainer(parent));
    }

    @Override
    public void onBindViewHolder(@NonNull FilterItemViewHolder holder, int position) {
        holder.bind(filterChains.get(position));
    }

    @Override
    public int getItemViewType(int position) {
        return filterChains.get(position).getItemViewType();
    }

    @Override
    public int getItemCount() {
        return filterChains.size();
    }
}
