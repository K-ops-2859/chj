package com.dikai.chenghunjiclient.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by cmk03 on 2018/1/4.
 */

public class BaseFragmentPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragments;
    private List<String> tabs;
    private OnTabIndex onTabIndex;

    public BaseFragmentPagerAdapter(FragmentManager fm, List<Fragment> fragments, List<String> tabs) {
        super(fm);
        this.fragments = fragments;
        this.tabs = tabs;
    }



    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (onTabIndex !=null) {
            onTabIndex.tabIndex(position);
        }
        return tabs.get(position);
    }


    public void setOnTabIndex(OnTabIndex onTabIndex) {
        this.onTabIndex = onTabIndex;
    }

    public interface OnTabIndex {
        void tabIndex(int index);
    }
}
