package com.dikai.chenghunjiclient.adapter.store;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lucio on 2017/6/2.
 */

public class MainFragmentAdapter extends FragmentPagerAdapter {

    private List<Fragment> mList;
    private List<String> titleList;
    public MainFragmentAdapter(FragmentManager fm, List<Fragment> idList) {
        super(fm);
        this.mList = idList;
        titleList = new ArrayList<>();
    }

    @Override
    public Fragment getItem(int position) {
        return mList.get(position);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if(titleList != null){
            return titleList.get(position);
        }else {
            return "";
        }
    }

    public void refresh(List<Fragment> idList){
        mList = idList;
        notifyDataSetChanged();
    }

    public void setTitleList(List<String> titleList){
        this.titleList = titleList;
    }
}