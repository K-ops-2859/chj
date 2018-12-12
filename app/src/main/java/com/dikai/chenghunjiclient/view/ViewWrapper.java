package com.dikai.chenghunjiclient.view;

import android.view.View;

/**
 * Created by cmk03 on 2017/11/29.
 */

public class ViewWrapper {
    private View mTarget;

    public ViewWrapper(View target) {
        this.mTarget = target;
    }

    public int getWidth() {
        return mTarget.getLayoutParams().width;
    }

    public int getHeight() {
        return mTarget.getLayoutParams().height;
    }

    public void setWidth(int width) {
        mTarget.getLayoutParams().width = width;
        mTarget.requestLayout();
    }

    public void setHeight(int height) {
        mTarget.getLayoutParams().height = height;
        mTarget.requestLayout();
    }
}
