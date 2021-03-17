package com.storyteller_f.config_edit;

import java.util.ArrayList;

public class Configs {
    private final ArrayList<Config> configs;
    private int last;
    private int max;

    public Configs() {
        configs = new ArrayList<>();
    }

    /**
     * 上一次的索引位置
     *
     * @return 如果找不到返回-1
     */
    public int getLastIndex() {
        return getIndex(last);
    }

    public int getLast() {
        return this.last;
    }

    /**
     * 会自动设置id
     *
     * @param config 对象
     * @return 返回生成的id
     */
    public int addConfig(Config config) {
        config.setId(max);
        max++;
        configs.add(config);
        return config.getId();
    }

    /**
     * 获取正在选中的项
     *
     * @return 正在选中的项
     */
    public Config getLastConfig() {
        return this.getConfig(this.getLast());
    }

    public Config getConfigAt(int index) {
        return this.configs.get(index);
    }

    public int getIndex(int id) {
        int index = 0;
        for (int i = 0; i < configs.size(); i++) {
            Config config = configs.get(i);
            if (config.getId() == id) {
                return index;
            }

            ++index;
        }
        return -1;
    }

    public Config getConfig(int id) {
        for (Config config : configs) {
            if (config.getId() == id) {
                return config;
            }
        }
        return null;
    }

    public void choose(int id) {
        this.last = id;
    }

    public void removeAt(int selectedIndex) {
        configs.remove(selectedIndex);
    }

    public int size() {
        return configs.size();
    }

}
