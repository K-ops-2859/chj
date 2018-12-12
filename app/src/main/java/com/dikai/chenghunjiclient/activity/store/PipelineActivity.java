package com.dikai.chenghunjiclient.activity.store;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.adapter.store.MainFragmentAdapter;
import com.dikai.chenghunjiclient.fragment.store.PipelineFragment;
import com.gyf.barlibrary.ImmersionBar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PipelineActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pipeline);
        ImmersionBar.with(this)
                .statusBarView(R.id.top_view)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true, 0.2f)
                .init();
        init();
    }

    private void init() {
        ViewPager mViewPager = (ViewPager) findViewById(R.id.view_pager);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        findViewById(R.id.back).setOnClickListener(this);
        ArrayList<Fragment> mFragments = new ArrayList<>();
        List<String> titles =  new ArrayList<>();
        Collections.addAll(mFragments, PipelineFragment.newInstance(0),PipelineFragment.newInstance(1),
                PipelineFragment.newInstance(2),PipelineFragment.newInstance(3));
        Collections.addAll(titles, "全部","待付款","已付款","已拒单");
        MainFragmentAdapter mPagerAdapter = new MainFragmentAdapter(getSupportFragmentManager(), mFragments);
        mPagerAdapter.setTitleList(titles);
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setOffscreenPageLimit(4);
        tabLayout.setupWithViewPager(mViewPager);
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
