package com.dikai.chenghunjiclient.view;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;

/**
 * Created by cmk03 on 2018/1/8.
 * 禁止横向滑动
 */


public class CustomLinearLayoutManager extends LinearLayoutManager {
    private boolean isScrollEnabled = true;

    public CustomLinearLayoutManager(Context context) {
        super(context);
    }

    public void setScrollEnabled(boolean flag) {
        this.isScrollEnabled = flag;
    }

    //禁止横向滑动
    @Override
    public boolean canScrollHorizontally() {
        return isScrollEnabled && super.canScrollHorizontally();
    }
}