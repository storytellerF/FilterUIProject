package com.storyteller_f.config_edit;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapterFactory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ConfigEditor extends FrameLayout {
    private static final String TAG = "ConfigEditor";
    public Configs configs;
    private Spinner spinner;
    private PopupMenu popupMenu;
    private SpinnerAdapter spinnerAdapter;
    private File file;
    private Gson gson;
    private Listener listener;

    public ConfigEditor(Context context) {
        super(context);
        constructorInit();
    }

    public ConfigEditor(Context context, AttributeSet attrs) {
        super(context, attrs);
        constructorInit();
    }

    public ConfigEditor(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        constructorInit();
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public void constructorInit() {
        configs = new Configs();
        LayoutInflater.from(getContext()).inflate(R.layout.layout_view_config, this, true);
        spinner = findViewById(R.id.config_edit_name_list_spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (listener != null) {
                    listener.onChangeChoose(id,position);
                    configs.choose(configs.getConfigAt(position).getId());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.d(TAG, "onNothingSelected() called with: parent = [" + parent + "]");
            }
        });
        ImageButton imageButton = findViewById(R.id.config_edit_function_more_imageButton);
        popupMenu = new PopupMenu(getContext(), imageButton, Gravity.BOTTOM);
        popupMenu.inflate(R.menu.popop_menu_config_edit_function);
        popupMenu.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.config_edit_popup_menu_new) {
                if (listener != null) {
                    configs.addConfig(listener.onNew());
                    configs.choose(configs.getConfigAt(configs.size()-1).getId());
                    refresh();
                    return true;
                }
            }
            Object selectedItem = spinner.getSelectedItem();
            int index = spinner.getSelectedItemPosition();
            Log.i(TAG, "initTwo: " + selectedItem.getClass());
            if (!(selectedItem instanceof Config)) return false;
            Config selected = (Config) selectedItem;
            if (item.getItemId() == R.id.config_edit_popop_menu_clone) {
                try {
                    Config clone = (Config) selected.clone();
                    clone.setName(clone.name + " 克隆");
                    configs.choose(configs.addConfig(clone));
                    refresh();
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                }
            } else if (item.getItemId() == R.id.config_edit_popop_menu_delete) {
                configs.removeAt(index);
                configs.choose(configs.getConfigAt(0).getId());
                refresh();
            } else if (item.getItemId() == R.id.config_edit_popop_menu_rename) {
                EditText editText = new EditText(getContext());
                editText.setText(selected.name);
                AlertDialog alertDialog = new AlertDialog.Builder(getContext()).setMessage("请输入").setTitle("重命名").setView(editText)
                        .setPositiveButton("确认", null)
                        .setNegativeButton("取消", (dialog, which) -> dialog.dismiss()).show();
                alertDialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(v -> {
                    String trim = editText.getText().toString().trim();
                    if (!trim.isEmpty()) {
                        selected.name = trim;
                        ((BaseAdapter) spinnerAdapter).notifyDataSetChanged();
                        alertDialog.dismiss();
                    }
                });
            }
            return false;
        });
        imageButton.setOnClickListener(v -> popupMenu.show());
    }

    private void refresh() {
        ((BaseAdapter) spinnerAdapter).notifyDataSetChanged();
        spinner.setSelection(configs.getLast());
    }

    public void fromFile() throws FileNotFoundException {
        if (!file.exists()) return;
        FileReader json = new FileReader(file);
        configs = gson.fromJson(json, Configs.class);
        try {
            json.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void toFile() throws Exception {
        if (!file.exists()) {
            if (!file.createNewFile()) {
                throw new Exception("文件创建失败");
            }
        }
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
        bufferedWriter.write(gson.toJson(configs));
        bufferedWriter.flush();
        bufferedWriter.close();
    }

    public void init(String name, TypeAdapterFactory factory, TypeAdapterFactory configFactory) throws FileNotFoundException {
        file = new File(getContext().getFilesDir(), "config-edit-" + name + ".json");
        gson = new GsonBuilder().registerTypeAdapterFactory(factory).registerTypeAdapterFactory(configFactory).create();
        fromFile();
        spinnerAdapter = new BaseAdapter() {
            @Override
            public int getCount() {
                return configs.size();
            }

            @Override
            public Config getItem(int position) {
                return configs.getConfigAt(position);
            }

            @Override
            public long getItemId(int position) {
                return getItem(position).getId();
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view;
                if (convertView != null) {
                    view = convertView;
                } else
                    view = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_spinner_dropdown_item, parent, false);
                TextView text = view.findViewById(android.R.id.text1);
                text.setText(getItem(position).name);
                text.append(" " + getItem(position).getId());
                return view;
            }
        };
        spinner.setAdapter(spinnerAdapter);
        spinner.setSelection(configs.getLastIndex());
    }

    public interface Listener {
        Config onNew();

        void onChangeChoose(long id, int position);
    }

}
