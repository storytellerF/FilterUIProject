package com.storyteller_f.sort_ui.choose;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.storyteller_f.sort_ui.R;
import com.storyteller_f.sort_ui.SortChain;

import java.util.List;

public class ChooseSortAdapter<T> extends RecyclerView.Adapter<ChooseSortAdapter.ChooseFilterViewHolder> {
    private static final String TAG = "ChooseSortAdapter";
    private final List<SortChain<T>> config;
    private Listener<SortChain<T>> listener;

    public ChooseSortAdapter(List<SortChain<T>> confing) {
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

    public void setListener(Listener<SortChain<T>> listener) {
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

        public void bind(SortChain<?> sortChain) {
            name.setText(sortChain.getShowName());
            icon.setImageBitmap(sortChain.getIcon());
        }
    }
}
