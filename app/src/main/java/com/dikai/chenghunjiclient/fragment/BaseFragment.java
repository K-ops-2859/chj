package com.dikai.chenghunjiclient.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by cmk03 on 2017/6/29.
 */

public abstract class BaseFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        int viewLayoutID = getContentViewLayoutID();
        if (viewLayoutID != 0) {
            return inflater.inflate(viewLayoutID, container, false);
        } else {
            return super.onCreateView(inflater, container, savedInstanceState);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViewsAndEvents(view);
        if (savedInstanceState != null) {
            setBundle(savedInstanceState);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        DetoryViewAndEvents();
        //progressBar.setVisibility(View.GONE);
    }

    protected abstract int getContentViewLayoutID();

    protected abstract void initViewsAndEvents(View view);

    protected abstract void DetoryViewAndEvents();

    public void setBundle(Bundle savedInstanceState) {
    }

}
