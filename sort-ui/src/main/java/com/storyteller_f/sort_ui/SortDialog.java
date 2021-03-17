package com.storyteller_f.sort_ui;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.TypeAdapterFactory;
import com.storyteller_f.config_edit.Config;
import com.storyteller_f.config_edit.ConfigEditor;
import com.storyteller_f.recycleview_ui_extra.DragItemHelper;
import com.storyteller_f.recycleview_ui_extra.GeneralItemDecoration;
import com.storyteller_f.sort_ui.adapter.SortItemAdapter;
import com.storyteller_f.sort_ui.adapter.SortViewHolderFactory;
import com.storyteller_f.sort_ui.choose.ChooseSortDialog;
import com.storyteller_f.sort_ui.config.SortConfig;
import com.storyteller_f.sort_ui.config.SortConfigItem;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class SortDialog<T> implements ConfigEditor.Listener {
    private static final String TAG = "SortDialog";
    private final ChooseSortDialog<T> chooseSortDialog;
    private final AlertDialog alertDialog;
    private final List<SortChain<T>> sort;
    private final ConfigEditor configEditor;
    private final List<SortChain<T>> temp;
    private SortItemAdapter<T> sortItemAdapter = null;
    private Listener listener;

    public SortDialog(Context context, List<SortChain<T>> config, SortViewHolderFactory factory) {
        sort = new ArrayList<>();
        temp = new ArrayList<>();
        chooseSortDialog = new ChooseSortDialog<>(context, config);
        chooseSortDialog.setListener((sortChain) -> {
            if (!sort.contains(sortChain)) {
                sort.add(sortChain);
                sortItemAdapter.notifyItemInserted(sort.size() - 1);
            }
        });
        View inflate = LayoutInflater.from(context).inflate(R.layout.layout_dialog_sort_sort_ui, null, false);
        Button add = inflate.findViewById(R.id.sort_ui_button_add);
        add.setOnClickListener((view) -> chooseSortDialog.show());
        configEditor = inflate.findViewById(R.id.sort_ui_config_edit_configEditor);
        configEditor.setListener(this);
        RecyclerView recyclerView = inflate.findViewById(R.id.sort_ui_recyclerView_filter_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        sortItemAdapter = new SortItemAdapter<>(sort, factory);
        recyclerView.addItemDecoration(new GeneralItemDecoration(context));
        recyclerView.setAdapter(sortItemAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new DragItemHelper(sort, sortItemAdapter));
        itemTouchHelper.attachToRecyclerView(recyclerView);
        AlertDialog.Builder builder = new AlertDialog.Builder(context).setTitle("排序").setView(inflate)
                .setPositiveButton("确定", (dialog, which) -> {
                    handle();
                    save();
                })
                .setNegativeButton("取消", ((dialog, which) -> {
                    sort.clear();
                    sort.addAll(temp);
                }));
        alertDialog = builder.create();
    }

    public List<SortChain<T>> getSort() {
        return sort;
    }

    public void init(TypeAdapterFactory factory, TypeAdapterFactory configFactory) throws FileNotFoundException {
        configEditor.init("sort", factory, configFactory);

//        Config lastConfig = configEditor.configs.getLastConfig();
//        if (lastConfig instanceof SortConfig) {
//            List<SortConfigItem> configItems = ((SortConfig) lastConfig).getConfigItems();
//            if (listener != null) {
//                listener.onInitHistory(configItems);
//                handle();
//                sortItemAdapter.notifyDataSetChanged();
//            }
//        }
    }

    public void show() {
        if (!alertDialog.isShowing()) {
            alertDialog.show();
            //保存当前状态
            temp.clear();
            temp.addAll(sort);
        }
    }

    public void close() {
        alertDialog.dismiss();
        chooseSortDialog.close();
    }

    private void handle() {
        for (int i = 0; i < sort.size(); i++) {
            if (i < sort.size() - 1)
                sort.get(i).next = sort.get(i + 1);
            else
                sort.get(i).next = null;
        }
    }

    public void save() {
        Config lastConfig = configEditor.configs.getLastConfig();
        if (lastConfig instanceof SortConfig) {
            if (listener != null) {
                List<SortConfigItem> configItems = listener.onSaveState();
                ((SortConfig) lastConfig).reUpdate(configItems);
                try {
                    configEditor.toFile();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(alertDialog.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            Log.e(TAG, "save: lastConfig is " + lastConfig.getClass());
        }
    }

    public void sort(List<T> list) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            list.sort(sort.get(0));
        } else {
            Collections.sort(list, sort.get(0));
        }
    }

    public void print() {
        for (SortChain<T> tSortChain : sort) {
            Log.i(TAG, "print: " + tSortChain.showName);
        }
    }

    @Override
    public Config onNew() {
        SortConfig sortConfig = new SortConfig();
        sortConfig.setName("未命名" + Calendar.getInstance(Locale.CHINA).toString());
        return sortConfig;
    }

    @Override
    public void onChangeChoose(long id, int position) {
        Log.d(TAG, "onChangeChoose() called with: id = [" + id + "], position = [" + position + "]");
        if (listener != null) {
            sort.clear();
            Config configAt = configEditor.configs.getConfigAt(position);
            if (configAt instanceof SortConfig) {
                listener.onInitHistory(((SortConfig) configAt).getConfigItems());
                handle();
                sortItemAdapter.notifyDataSetChanged();
            }
        }
    }

    public void add(SortChain<T> packageSort) {
        if (!sort.contains(packageSort)) {
            sort.add(packageSort);
        }
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }
    public interface Listener {
        List<SortConfigItem> onSaveState();

        void onInitHistory(List<SortConfigItem> configItems);
    }

}
