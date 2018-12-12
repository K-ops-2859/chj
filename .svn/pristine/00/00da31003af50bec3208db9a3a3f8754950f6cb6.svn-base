package com.dikai.chenghunjiclient.activity.me;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.adapter.store.MainFragmentAdapter;
import com.dikai.chenghunjiclient.fragment.me.FocusFragment;
import com.gyf.barlibrary.ImmersionBar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FocusActivity extends AppCompatActivity implements View.OnClickListener {

    private ArrayList<Fragment> mFragments;
    private ViewPager mViewPager;
    private TabLayout tabLayout;
    private MainFragmentAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_focus);
        ImmersionBar.with(this)
                .statusBarView(R.id.top_view)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true, 0.2f)
                .init();
        init();
    }

    private void init() {
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        findViewById(R.id.back).setOnClickListener(this);
        mFragments = new ArrayList<>();
        List<String> titles =  new ArrayList<>();
        Collections.addAll(mFragments, FocusFragment.newInstance(0),FocusFragment.newInstance(1));
        Collections.addAll(titles, "关注","粉丝");
        mPagerAdapter = new MainFragmentAdapter(getSupportFragmentManager(), mFragments);
        mPagerAdapter.setTitleList(titles);
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setOffscreenPageLimit(2);
        tabLayout.setupWithViewPager(mViewPager);
        if(getIntent().getIntExtra("type",0) == 1){
            mViewPager.setCurrentItem(1,false);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                onBackPressed();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImmersionBar.with(this).destroy();
    }

}
