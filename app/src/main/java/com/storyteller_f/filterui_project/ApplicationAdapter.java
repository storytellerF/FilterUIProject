package com.storyteller_f.filterui_project;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class ApplicationAdapter extends RecyclerView.Adapter<ApplicationAdapter.ViewHolder> {
    private final List<ApplicationItem> applicationItems;

    public ApplicationAdapter(List<ApplicationItem> applicationItems) {
        this.applicationItems = applicationItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_application, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(applicationItems.get(position));
    }

    @Override
    public int getItemCount() {
        return applicationItems.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView packageName;
        TextView date;
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy年MM月dd日 hh时", Locale.CHINA);

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.application_name_textView);
            packageName = itemView.findViewById(R.id.application_package_textView2);
            date = itemView.findViewById(R.id.application_time_textView3);
        }

        public void bind(ApplicationItem applicationItem) {
            name.setText(applicationItem.getName());
            packageName.setText(applicationItem.getPackageName());
            date.setText(simpleDateFormat.format(applicationItem.getInstallTime()));
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) itemView.getLayoutParams();
            if (applicationItem.isHide()) {
                layoutParams.height=0;
            } else {
                layoutParams.height=RecyclerView.LayoutParams.WRAP_CONTENT;
            }
        }
    }
}
