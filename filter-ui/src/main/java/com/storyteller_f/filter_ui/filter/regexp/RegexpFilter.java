package com.storyteller_f.filter_ui.filter.regexp;

import android.graphics.Bitmap;

import com.storyteller_f.filter_ui.FilterChain;
import com.storyteller_f.filter_ui.adapter.FilterItemViewHolder;

import java.util.regex.Pattern;

public abstract class RegexpFilter<T> extends FilterChain<T> {
    public RegexpFilter(String showName, Bitmap icon) {
        super(showName, icon);
    }

    @Override
    public boolean filter(T t, FilterItemViewHolder viewHolder) {
        return Pattern.compile(getRegexp(viewHolder)).matcher(getMatchString(t)).find();
    }

    public abstract void bind(FilterItemViewHolder viewHolder);

    /**
     * 返回正则表达式
     * @param viewHolder viewHolder
     * @return 返回生成的正则表达式
     */
    protected abstract String getRegexp(FilterItemViewHolder viewHolder);

    /**
     * 获取被匹配的字符串
     * @param t 当前对象
     * @return 返回被匹配的字符串
     */
    public abstract CharSequence getMatchString(T t) ;

    @Override
    public abstract int getItemViewType();
}
