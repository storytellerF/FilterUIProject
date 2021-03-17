package com.storyteller_f.filter_ui.choose;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.storyteller_f.filter_ui.FilterChain;
import com.storyteller_f.filter_ui.R;

import java.util.List;

public class ChooseFilterDialog<T> {
    private AlertDialog alertDialog = null;
    private ChooseFilterAdapter.Listener<FilterChain<T>> listener;

    public ChooseFilterDialog(Context context, List<FilterChain<T>> config) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.layout_dialog_choose_filter_filter_ui, null, false);
        RecyclerView recyclerView = inflate.findViewById(R.id.sort_ui_choose);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        ChooseFilterAdapter<T> chooseFilterAdapter = new ChooseFilterAdapter<>(config);
        recyclerView.setAdapter(chooseFilterAdapter);
        chooseFilterAdapter.setListener((sortChain) -> {
            if (listener != null) {
                listener.onChoose(sortChain);
            }
            alertDialog.dismiss();
        });
        AlertDialog.Builder builder = new AlertDialog.Builder(context).setTitle("选择···").setView(inflate);
        alertDialog = builder.create();
    }

    public void setListener(ChooseFilterAdapter.Listener<FilterChain<T>> listener) {
        this.listener = listener;
    }

    public void show() {
        alertDialog.show();
    }

    public void close() {
        if (alertDialog.isShowing())
            alertDialog.dismiss();
    }
}
