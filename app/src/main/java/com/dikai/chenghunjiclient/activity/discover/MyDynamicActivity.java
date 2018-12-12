package com.dikai.chenghunjiclient.activity.discover;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.adapter.store.MainFragmentAdapter;
import com.dikai.chenghunjiclient.fragment.discover.VideoFragment;
import com.dikai.chenghunjiclient.fragment.discover.MyDynamicFragment;
import com.gyf.barlibrary.ImmersionBar;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by cmk03 on 2018/1/25.
 */

public class MyDynamicActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private List<Fragment> mFragments;
    private TabLayout mTabLayout;
    private MainFragmentAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_dynamic);
        ImmersionBar.with(this)
                .statusBarView(R.id.top_view)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true, 0.2f)
                .init();
        init();
    }

    private void init() {
        mViewPager = (ViewPager) findViewById(R.id.fragment_add_viewpager);
        mTabLayout = (TabLayout) findViewById(R.id.fragment_add_tabs);
        mFragments = new ArrayList<>();
        adapter = new MainFragmentAdapter(getSupportFragmentManager(), mFragments);
        Collections.addAll(mFragments, MyDynamicFragment.newInstance(1), MyDynamicFragment.newInstance(2));
        List<String> names = new ArrayList<>();
        Collections.addAll(names, "动态", "视频");

        adapter.setTitleList(names);
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    protected void onDestroy() {
        ImmersionBar.with(this).destroy();
        super.onDestroy();
    }
}
