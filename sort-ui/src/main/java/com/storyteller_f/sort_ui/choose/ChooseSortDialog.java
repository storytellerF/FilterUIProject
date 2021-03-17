package com.storyteller_f.sort_ui.choose;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.storyteller_f.sort_ui.R;
import com.storyteller_f.sort_ui.SortChain;

import java.util.List;

public class ChooseSortDialog<T> {
    private AlertDialog alertDialog = null;
    private ChooseSortAdapter.Listener<SortChain<T>> listener;

    public ChooseSortDialog(Context context, List<SortChain<T>> config) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.layout_dialog_choose_sort_sort_ui, null, false);
        RecyclerView recyclerView = inflate.findViewById(R.id.sort_ui_choose);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        ChooseSortAdapter<T> chooseSortAdapter = new ChooseSortAdapter<>(config);
        recyclerView.setAdapter(chooseSortAdapter);
        chooseSortAdapter.setListener((sortChain) -> {
            if (listener != null) {
                listener.onChoose(sortChain);
            }
            alertDialog.dismiss();
        });
        AlertDialog.Builder builder = new AlertDialog.Builder(context).setTitle("选择···").setView(inflate);
        alertDialog = builder.create();
    }

    public void setListener(ChooseSortAdapter.Listener<SortChain<T>> listener) {
        this.listener = listener;
    }

    public void show() {
        alertDialog.show();
    }

    public void close() {
        alertDialog.dismiss();
    }
}
