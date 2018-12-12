package com.dikai.chenghunjiclient.adapter;

import android.view.View;

/**
 * Created by cmk03 on 2017/11/17.
 */

public interface OnAdapterViewClickListener<T> {

    void onAdapterClick(View view, int position, T t);
}
