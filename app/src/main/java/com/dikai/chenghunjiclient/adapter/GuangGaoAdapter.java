package com.dikai.chenghunjiclient.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


import com.dikai.chenghunjiclient.fragment.PhotoFragment;

import java.util.ArrayList;

public class GuangGaoAdapter extends FragmentPagerAdapter {
    private ArrayList<String> list;

    public GuangGaoAdapter(FragmentManager fm, ArrayList<String> list) {

        super(fm);
        this.list = list;
    }

    @Override
    public Fragment getItem(int position) {

        String imgUrl = list.get(position);
        return PhotoFragment.newInstance(imgUrl);
    }

    @Override
    public int getCount() {
        return list.size();
    }
    
}