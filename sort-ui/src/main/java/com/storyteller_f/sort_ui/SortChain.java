package com.storyteller_f.sort_ui;

import android.graphics.Bitmap;

import java.util.Comparator;

public abstract class SortChain<T> implements Comparator<T> {
    protected String showName;
    Bitmap icon;
    SortChain<T> next;
    public SortChain(String showName, Bitmap icon) {
        this.showName = showName;
        this.icon = icon;
    }

    public String getShowName() {
        return showName;
    }

    public Bitmap getIcon() {
        return icon;
    }

    @Override
    public int compare(T o1, T o2) {
        int i = cmp(o1, o2);
        if (i == 0) return whenEqual(o1, o2);
        return i;
    }

    private int whenEqual(T o1, T o2) {
        if (next == null) return 0;
        return next.compare(o1, o2);
    }

    public abstract int cmp(T o1, T o2);

    public abstract int getItemViewType();
}
