package com.storyteller_f.filter_ui;

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
import com.storyteller_f.filter_ui.adapter.FilterItemAdapter;
import com.storyteller_f.filter_ui.adapter.FilterItemViewHolder;
import com.storyteller_f.filter_ui.adapter.FilterViewHolderFactory;
import com.storyteller_f.filter_ui.choose.ChooseFilterDialog;
import com.storyteller_f.filter_ui.config.FilterConfig;
import com.storyteller_f.filter_ui.config.FilterConfigItem;
import com.storyteller_f.recycleview_ui_extra.DragItemHelper;
import com.storyteller_f.recycleview_ui_extra.GeneralItemDecoration;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class FilterDialog<T extends Filterable> implements ConfigEditor.Listener {
    private static final String TAG = "FilterDialog";
    private final ChooseFilterDialog<T> chooseFilterDialog;
    private final AlertDialog alertDialog;
    private final List<FilterChain<T>> filterChains;
    private final RecyclerView recyclerView;
    private final List<FilterChain<T>> history;
    private final ConfigEditor configEditor;
    private FilterItemAdapter<T> filterItemAdapter = null;
    private Listener listener;

    public FilterDialog(Context context, List<FilterChain<T>> config, FilterViewHolderFactory factory) {
        filterChains = new ArrayList<>();
        history = new ArrayList<>();
        chooseFilterDialog = new ChooseFilterDialog<>(context, config);
        chooseFilterDialog.setListener((sortChain) -> {
            if (!filterChains.contains(sortChain)) {
                filterChains.add(sortChain);
                filterItemAdapter.notifyItemInserted(filterChains.size() - 1);
            }
        });
        View inflate = LayoutInflater.from(context).inflate(R.layout.layout_dialog_filter_filter_ui, null, false);
        Button add = inflate.findViewById(R.id.filter_ui_button_add);
        add.setOnClickListener((view) -> chooseFilterDialog.show());
        configEditor = inflate.findViewById(R.id.filter_ui_config_edit);

        configEditor.setListener(this);
        recyclerView = inflate.findViewById(R.id.filter_ui_recyclerView_filter_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        filterItemAdapter = new FilterItemAdapter<>(filterChains, factory);
        recyclerView.addItemDecoration(new GeneralItemDecoration(context));
        recyclerView.setAdapter(filterItemAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new DragItemHelper(filterChains, filterItemAdapter));
        itemTouchHelper.attachToRecyclerView(recyclerView);
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setTitle("筛选").setView(inflate)
                .setPositiveButton("确定", (dialog, which) -> {
                    handle();
                    save();
                })
                .setNegativeButton("取消", ((dialog, which) -> {
                    filterChains.clear();
                    filterChains.addAll(history);
                }));
        alertDialog = builder.create();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            alertDialog.setOnShowListener(dialog -> filterItemAdapter.notifyDataSetChanged());
        }
    }

    public List<FilterChain<T>> getFilterChains() {
        return filterChains;
    }

    public void init(String name, TypeAdapterFactory factory, TypeAdapterFactory configFactory) throws FileNotFoundException {
        configEditor.init(name, factory, configFactory);
//        Config lastConfig = configEditor.configs.getLastConfig();
//        if (lastConfig instanceof FilterConfig) {
//            List<FilterConfigItem> configItems = ((FilterConfig) lastConfig).getConfigItems();
//            if (listener != null) {
//                listener.onInitHistory(configItems);
//                handle();
//                filterItemAdapter.notifyDataSetChanged();
//            }
//        }
    }

    public void show() {
        if (!alertDialog.isShowing()) {
            alertDialog.show();
            //保存当前状态
            history.clear();
            history.addAll(filterChains);
        }
    }

    public void close() {
        if (alertDialog.isShowing())
            alertDialog.dismiss();
        chooseFilterDialog.close();
    }

    private void handle() {
        for (int i = 0; i < filterChains.size(); i++) {
            if (i < filterChains.size() - 1)
                filterChains.get(i).next = filterChains.get(i + 1);
            else
                filterChains.get(i).next = null;
        }
    }

    public void filter(List<T> list) {
        for (T t : list) {
            int i;
            for (i = 0; i < filterChains.size(); i++) {
                if (!filterChains.get(i).filter(t, (FilterItemViewHolder) recyclerView.findViewHolderForAdapterPosition(i))) {
                    break;
                }
            }
            t.setHide(i != filterChains.size());
        }
    }

    public void save() {
        Log.d(TAG, "save() called");
        Config lastConfig = configEditor.configs.getLastConfig();
        if (lastConfig instanceof FilterConfig) {
            if (listener != null) {
                List<FilterConfigItem> configItems = listener.onSaveState();
                ((FilterConfig) lastConfig).reUpdate(configItems);
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

    @Override
    public Config onNew() {
        FilterConfig filterConfig = new FilterConfig();
        filterConfig.setName("未命名" + Calendar.getInstance(Locale.CHINA).getTime().getTime());
        return filterConfig;
    }

    @Override
    public void onChangeChoose(long id, int position) {
        Log.d(TAG, "onChangeChoose() called with: id = [" + id + "], position = [" + position + "]");
        if (listener != null) {
            Config configAt = configEditor.configs.getConfigAt(position);
            if (configAt instanceof FilterConfig) {
                listener.onInitHistory(((FilterConfig) configAt).getConfigItems());
                handle();
                filterItemAdapter.notifyDataSetChanged();
            }
        }
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public void add(FilterChain<T> packageConfigInFilter) {
        if (!filterChains.contains(packageConfigInFilter)) {
            filterChains.add(packageConfigInFilter);
        }
    }

    public interface Listener {
        List<FilterConfigItem> onSaveState();

        void onInitHistory(List<FilterConfigItem> configItems);
    }
}
