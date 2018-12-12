package com.dikai.chenghunjiclient.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by cmk03 on 2018/1/4.
 */

public class BaseFragmentAdapter extends FragmentPagerAdapter {

    private Fragment[] fragments;
    private String[] tabs;
    private OnTabIndex onTabIndex;

    public BaseFragmentAdapter(FragmentManager fm, Fragment[] fragments, String[] tabs) {
        super(fm);
        this.fragments = fragments;
        this.tabs = tabs;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments[position];
    }

    @Override
    public int getCount() {
        return fragments.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (onTabIndex !=null) {
            onTabIndex.tabIndex(position);
        }
        return tabs[position];
    }


    public void setOnTabIndex(OnTabIndex onTabIndex) {
        this.onTabIndex = onTabIndex;
    }

    public interface OnTabIndex {
        void tabIndex(int index);
    }
}
