package com.dikai.chenghunjiclient.activity.me;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.adapter.store.MainFragmentAdapter;
import com.dikai.chenghunjiclient.fragment.me.ClearFragment;
import com.dikai.chenghunjiclient.fragment.plan.CalendarFragment;
import com.dikai.chenghunjiclient.fragment.plan.NewAddFragment;
import com.gyf.barlibrary.ImmersionBar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ClearActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private List<Fragment> mFragments;
    private TabLayout mTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clear);
        ImmersionBar.with(this)
                .statusBarView(R.id.top_view)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true, 0.2f)
                .init();
        init();
    }

    private void init() {
        mViewPager = (ViewPager) findViewById(R.id.activity_clear_viewpager);
        mTabLayout = (TabLayout) findViewById(R.id.activity_clear_tabs);
        findViewById(R.id.activity_clear_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mFragments = new ArrayList<>();
        Collections.addAll(mFragments, ClearFragment.newInstance(1), ClearFragment.newInstance(2));
        // 第二步：为ViewPager设置适配器
        MainFragmentAdapter adapter =
                new MainFragmentAdapter(getSupportFragmentManager(), mFragments);
        List<String> names = new ArrayList<>();
        Collections.addAll(names, "未结算", "已结算");
        adapter.setTitleList(names);
        mViewPager.setAdapter(adapter);
        //  第三步：将ViewPager与TableLayout 绑定在一起
        mTabLayout.setupWithViewPager(mViewPager);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImmersionBar.with(this).destroy();
    }
}
