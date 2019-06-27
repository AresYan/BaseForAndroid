package com.yz.base.ui.view;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class MySpaceItemDecoration extends RecyclerView.ItemDecoration{
    private int space;

    public MySpaceItemDecoration(Context context, int space) {
        this.space = (int)((float)space * context.getResources().getDisplayMetrics().density);
    }

    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.left = this.space;
        outRect.right = this.space;
        outRect.bottom = this.space;
        if (parent.getLayoutManager() instanceof GridLayoutManager) {
            int spanCount = ((GridLayoutManager)parent.getLayoutManager()).getSpanCount();
            outRect.top = parent.getChildLayoutPosition(view) < spanCount ? this.space : 0;
        } else {
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager)parent.getLayoutManager();
            outRect.top = linearLayoutManager.getOrientation() != 0 && parent.getChildLayoutPosition(view) != 0 ? 0 : this.space;
        }
    }
}
