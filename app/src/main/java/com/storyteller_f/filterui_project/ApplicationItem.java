package com.storyteller_f.filterui_project;

import com.storyteller_f.filter_ui.Filterable;

import java.util.Date;

public class ApplicationItem extends Filterable {
    private String packageName;
    private String name;
    private Date installTime;
    private long updateTime;

    public ApplicationItem(String packageName, String name, Date installTime, long updateTime) {
        this.packageName = packageName;
        this.name = name;
        this.installTime = installTime;
        this.updateTime = updateTime;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public String getPackageName() {
        return packageName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getInstallTime() {
        return installTime;
    }

    public void setInstallTime(Date installTime) {
        this.installTime = installTime;
    }
}
