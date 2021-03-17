package com.storyteller_f.filter_ui.choose;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.storyteller_f.filter_ui.FilterChain;
import com.storyteller_f.filter_ui.R;

import java.util.List;

public class ChooseFilterAdapter<T> extends RecyclerView.Adapter<ChooseFilterAdapter.ChooseFilterViewHolder> {
    private final List<FilterChain<T>> config;
    private Listener<FilterChain<T>> listener;

    public ChooseFilterAdapter(List<FilterChain<T>> confing) {
        this.config = confing;
    }

    @NonNull
    @Override
    public ChooseFilterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return ChooseFilterViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull ChooseFilterViewHolder holder, int position) {
        holder.bind(config.get(position));
        holder.itemView.setOnClickListener((view) -> {
            if (listener != null) listener.onChoose(config.get(position));
        });
    }

    public void setListener(Listener<FilterChain<T>> listener) {
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        return config.size();
    }

    public interface Listener<T> {
        void onChoose(T t);
    }

    static class ChooseFilterViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView icon;

        public ChooseFilterViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.sort_ui_textView_choose_filter_name);
            icon = itemView.findViewById(R.id.sort_ui_imageView_choose_filter_icon);
        }

        public static ChooseFilterViewHolder create(ViewGroup parent) {
            return new ChooseFilterViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_choose_filter, parent, false));
        }

        public void bind(FilterChain<?> filterChain) {
            name.setText(filterChain.showName);
            icon.setImageBitmap(filterChain.icon);
        }
    }
}
