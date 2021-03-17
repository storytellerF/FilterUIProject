package com.storyteller_f.filterui_project;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.typeadapters.RuntimeTypeAdapterFactory;
import com.storyteller_f.config_edit.Config;
import com.storyteller_f.filter_ui.FilterChain;
import com.storyteller_f.filter_ui.FilterDialog;
import com.storyteller_f.filter_ui.config.FilterConfig;
import com.storyteller_f.filter_ui.config.FilterConfigItem;
import com.storyteller_f.filterui_project.filter.ApplicationFilterFactory;
import com.storyteller_f.filterui_project.filter.date.DateConfigItemInFilter;
import com.storyteller_f.filterui_project.filter.date.DateFilter;
import com.storyteller_f.filterui_project.filter.name.NameConfigItemInFilter;
import com.storyteller_f.filterui_project.filter.name.NameFilter;
import com.storyteller_f.filterui_project.filter.packageName.PackageConfigItemInFilter;
import com.storyteller_f.filterui_project.filter.packageName.PackageFilter;
import com.storyteller_f.filterui_project.filter.updateTime.UpdateTimeConfigItemInFilter;
import com.storyteller_f.filterui_project.filter.updateTime.UpdateTimeFilter;
import com.storyteller_f.filterui_project.sort.ApplicationSortFactory;
import com.storyteller_f.filterui_project.sort.date.DateSort;
import com.storyteller_f.filterui_project.sort.name.NameConfigItem;
import com.storyteller_f.filterui_project.sort.name.NameSort;
import com.storyteller_f.filterui_project.sort.packageName.PackageConfigItem;
import com.storyteller_f.filterui_project.sort.packageName.PackageSort;
import com.storyteller_f.sort_ui.SortChain;
import com.storyteller_f.sort_ui.SortDialog;
import com.storyteller_f.sort_ui.adapter.SortViewHolderFactory;
import com.storyteller_f.sort_ui.config.SortConfig;
import com.storyteller_f.sort_ui.config.SortConfigItem;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private SortDialog<ApplicationItem> sortDialog;
    private FilterDialog<ApplicationItem> filterDialog;
    private List<ApplicationItem> list;
    private ApplicationAdapter applicationAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list = new ArrayList<>();
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        list.add(new ApplicationItem("com.world.hello", "hello", calendar.getTime(), 1));
        calendar.set(Calendar.YEAR, 2012);
        list.add(new ApplicationItem("com.test.hello", "hello", calendar.getTime(), 2));
        calendar.set(Calendar.YEAR, 2001);
        calendar.set(Calendar.MONTH, 5);
        list.add(new ApplicationItem("com.test.hello", "dello", calendar.getTime(), 3));
        calendar.set(Calendar.HOUR, 5);
        list.add(new ApplicationItem("com.test", "world", calendar.getTime(), 15));
        calendar.set(Calendar.YEAR, 1984);
        calendar.set(Calendar.MONTH, 9);
        list.add(new ApplicationItem("com.android", "studio", calendar.getTime(), 89));
        try {
            sort();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            filter();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        RecyclerView recyclerView = findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        applicationAdapter = new ApplicationAdapter(list);
        recyclerView.setAdapter(applicationAdapter);
    }

    private void filter() throws FileNotFoundException {
        List<FilterChain<ApplicationItem>> sortList = new ArrayList<>();
        sortList.add(new PackageFilter(null, null));
        sortList.add(new NameFilter(null, null));
        sortList.add(new DateFilter("安装时间", null, null, null));
        sortList.add(new UpdateTimeFilter("更新次数", null, 0, 0, false, false));
        filterDialog = new FilterDialog<>(this, sortList, new ApplicationFilterFactory());
        Button button1 = findViewById(R.id.button4);
        button1.setOnClickListener((v -> filterDialog.show()));
        Button button = findViewById(R.id.button5);
        button.setOnClickListener(v -> {
            filterDialog.filter(list);
            applicationAdapter.notifyDataSetChanged();
        });
        RuntimeTypeAdapterFactory<FilterConfigItem> adapterFactory = RuntimeTypeAdapterFactory.of(FilterConfigItem.class, "config_edit_filter_config_item_config_key")
                .registerSubtype(DateConfigItemInFilter.class, "date")
                .registerSubtype(NameConfigItemInFilter.class, "name")
                .registerSubtype(PackageConfigItemInFilter.class, "package");
        RuntimeTypeAdapterFactory<Config> configRuntimeTypeAdapterFactory = RuntimeTypeAdapterFactory.of(Config.class, "config_edit_filter_config_key")
                .registerSubtype(FilterConfig.class, "filter-config");
        filterDialog.init("filter", adapterFactory, configRuntimeTypeAdapterFactory);
        filterDialog.setListener(new FilterDialog.Listener() {
            @Override
            public List<FilterConfigItem> onSaveState() {
                List<FilterConfigItem> list1 = new ArrayList<>();
                for (FilterChain<ApplicationItem> filterChain : filterDialog.getFilterChains()) {
                    if (filterChain instanceof PackageFilter) {
                        list1.add(new PackageConfigItemInFilter(((PackageFilter) filterChain).regexp));
                    } else if (filterChain instanceof NameFilter) {
                        list1.add(new NameConfigItemInFilter(((NameFilter) filterChain).regexp));
                    } else if (filterChain instanceof DateFilter) {
                        DateFilter filterChain1 = (DateFilter) filterChain;
                        list1.add(new DateConfigItemInFilter(filterChain1.start, filterChain1.end));
                    } else if (filterChain instanceof UpdateTimeFilter) {
                        UpdateTimeFilter updateTimeFilter = (UpdateTimeFilter) filterChain;
                        list1.add(new UpdateTimeConfigItemInFilter(updateTimeFilter.min, updateTimeFilter.max, updateTimeFilter.hasMinValue, updateTimeFilter.hasMaxValue));
                    }
                }
                return list1;
            }

            @Override
            public void onInitHistory(List<FilterConfigItem> configItems) {
                for (FilterConfigItem configItem : configItems) {
                    if (configItem instanceof PackageConfigItemInFilter) {
                        filterDialog.add(new PackageFilter(null, ((PackageConfigItemInFilter) configItem).regexp));
                    } else if (configItem instanceof NameConfigItemInFilter) {
                        filterDialog.add(new NameFilter(null, ((NameConfigItemInFilter) configItem).regexp));
                    } else if (configItem instanceof DateConfigItemInFilter) {
                        DateConfigItemInFilter configItem1 = (DateConfigItemInFilter) configItem;
                        filterDialog.add(new DateFilter("安装时间", null, configItem1.start, configItem1.end));
                    } else if (configItem instanceof UpdateTimeConfigItemInFilter) {
                        UpdateTimeConfigItemInFilter configItem1 = (UpdateTimeConfigItemInFilter) configItem;
                        filterDialog.add(new UpdateTimeFilter("更新次数", null, configItem1.getMin(), configItem1.getMax(), configItem1.isHasMinValue(), configItem1.isHasMaxValue()));
                    }
                }
            }
        });
    }

    private void sort() throws FileNotFoundException {
        List<SortChain<ApplicationItem>> sortList = new ArrayList<>();
        sortList.add(new PackageSort(null));
        sortList.add(new NameSort(null));
        SortViewHolderFactory factory = new ApplicationSortFactory();
        sortDialog = new SortDialog<>(this, sortList, factory);
        sortDialog.setListener(new SortDialog.Listener() {
            @Override
            public List<SortConfigItem> onSaveState() {
                List<SortConfigItem> list1 = new ArrayList<>();
                for (SortChain<ApplicationItem> sortChain : sortDialog.getSort()) {
                    if (sortChain instanceof PackageSort) {
                        list1.add(new PackageConfigItem());
                    } else if (sortChain instanceof NameSort) {
                        list1.add(new NameConfigItem());
                    } else if (sortChain instanceof DateSort) {
                        list1.add(new com.storyteller_f.filterui_project.sort.date.DateConfigItem());
                    }
                }
                return list1;
            }

            @Override
            public void onInitHistory(List<SortConfigItem> configItems) {
                for (SortConfigItem configItem : configItems) {
                    if (configItem instanceof PackageConfigItem) {
                        sortDialog.add(new PackageSort(null));
                    } else if (configItem instanceof NameConfigItem) {
                        sortDialog.add(new NameSort(null));
                    }
                }
            }
        });
        String config_edit_sort_key = "config_edit_sort_key";
        String config_edit_sort_config_key = "config_edit_sort_config_key";
        RuntimeTypeAdapterFactory<SortConfigItem> adapterFactory = RuntimeTypeAdapterFactory.of(SortConfigItem.class, config_edit_sort_key)
                .registerSubtype(PackageConfigItem.class, "package")
                .registerSubtype(NameConfigItem.class, "name");
        RuntimeTypeAdapterFactory<Config> configRuntimeTypeAdapterFactory = RuntimeTypeAdapterFactory.of(Config.class, config_edit_sort_config_key)
                .registerSubtype(SortConfig.class, "sort-config");
        sortDialog.init(adapterFactory, configRuntimeTypeAdapterFactory);
        Button button = findViewById(R.id.data_range_end_date_button2);
        button.setOnClickListener((view) -> sortDialog.show());
        Button print = findViewById(R.id.button3);
        print.setOnClickListener(v -> sortDialog.print());
        Button sort = findViewById(R.id.date_range_start_date_button);
        sort.setOnClickListener(v -> {
            sortDialog.sort(list);
            for (ApplicationItem applicationItem : list) {
                Log.i(TAG, "onClick: " + applicationItem.getPackageName() + " " + applicationItem.getName());
            }
            applicationAdapter.notifyDataSetChanged();
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sortDialog.close();
        filterDialog.close();
    }
}