package com.storyteller_f.recycleview_ui_extra;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class GeneralItemDecoration extends RecyclerView.ItemDecoration {
    private final Drawable divider;

    public GeneralItemDecoration(Context context) {
        TypedArray typedArray = context.obtainStyledAttributes(new int[]{android.R.attr.listDivider});
        divider = typedArray.getDrawable(0);
        typedArray.recycle();
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        if (view.getLayoutParams().height == 0) {
            outRect.set(0,0,0,0);
        } else
            outRect.set(0, 0, 0, divider.getIntrinsicWidth());
    }


}
