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

import com.google.gson.TypeAdapterFactory;

import java.io.IOException;

public class ConfigEditor extends FrameLayout {
    private static final String TAG = "ConfigEditor";
    private Spinner spinner;
    private PopupMenu popupMenu;
    private SpinnerAdapter spinnerAdapter;
    private Listener listener;
    private ConfigEditorCore core;

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
        core = new ConfigEditorCore();
        LayoutInflater.from(getContext()).inflate(R.layout.layout_view_config, this, true);
        spinner = findViewById(R.id.config_edit_name_list_spinner);

        ImageButton imageButton = findViewById(R.id.config_edit_function_more_imageButton);
        popupMenu = new PopupMenu(getContext(), imageButton, Gravity.BOTTOM);
        popupMenu.inflate(R.menu.popop_menu_config_edit_function);
        imageButton.setOnClickListener(v -> popupMenu.show());
    }

    private void refresh(int config) {
        ((BaseAdapter) spinnerAdapter).notifyDataSetChanged();
        spinner.setSelection(config);
    }

    public void save() {
        core.save();
    }

    public void init(String name, TypeAdapterFactory... factory) throws IOException {
        core.setCoreListener(new ConfigEditorCore.CoreListener() {
            @Override
            public void updateList(int config) {
                refresh(config);
            }

            @Override
            public Config onNew() {
                return listener.onNew();
            }

            @Override
            public void onInit(Config configAt) {
                listener.onChangeChoose(configAt);
            }

            @Override
            public void bindEvent() {
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if (listener != null) {
                            listener.onChangeChoose(core.getConfigAt(position));
                            core.choose(core.getConfigAt(position).getId());
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        Log.d(TAG, "onNothingSelected() called with: parent = [" + parent + "]");
                    }
                });
                popupMenu.setOnMenuItemClickListener(item -> {
                    int index = spinner.getSelectedItemPosition();
                    if (item.getItemId() == R.id.config_edit_popop_menu_rename) {
                        Object selectedItem = spinner.getSelectedItem();
                        Log.i(TAG, "initTwo: " + selectedItem.getClass());
                        if (!(selectedItem instanceof Config)) return false;
                        Config selected = (Config) selectedItem;
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
                    } else {
                        String command;
                        if (item.getItemId() == R.id.config_edit_popop_menu_delete)
                            command = "delete";
                        else if (item.getItemId() == R.id.config_edit_popup_menu_new) {
                            command = "new";
                        } else if (item.getItemId() == R.id.config_edit_popop_menu_clone) {
                            command = "clone";
                        } else {
                            return false;
                        }
                        core.sendCommand(command, index);
                    }
                    return true;

                });
            }
        });
        core.init(name, getContext().getFilesDir().getAbsolutePath(), factory);
        spinnerAdapter = new BaseAdapter() {
            @Override
            public int getCount() {
                return core.size();
            }

            @Override
            public Config getItem(int position) {
                return core.getConfigAt(position);
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
//        file = new File(getContext().getFilesDir(), "config-edit-" + name + ".json");
//        gson = new GsonBuilder().registerTypeAdapterFactory(factory).registerTypeAdapterFactory(configFactory).create();
//        fromFile();
//        spinner.setSelection(configs.getLastIndex());
    }

    public Config getLastConfig() {
        return core.getLastConfig();
    }

    public interface Listener {
        Config onNew();

        void onChangeChoose(Config configAt);
    }
}
