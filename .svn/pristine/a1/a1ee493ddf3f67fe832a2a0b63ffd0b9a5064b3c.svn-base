package com.dikai.chenghunjiclient.activity.plan;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.adapter.store.MainFragmentAdapter;
import com.dikai.chenghunjiclient.entity.DayPlanBean;
import com.dikai.chenghunjiclient.fragment.me.DriverFragment;
import com.dikai.chenghunjiclient.fragment.plan.AssignDriverFragment;
import com.dikai.chenghunjiclient.fragment.plan.PlanInfoFragment;
import com.gyf.barlibrary.ImmersionBar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DriverPlanActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mBack;
    private ViewPager mViewPager;
    private List<Fragment> mFragments;
    private TabLayout mTabLayout;
    private DayPlanBean mPlanBean;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_plan);
        ImmersionBar.with(this)
                .statusBarView(R.id.top_view)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true, 0.2f)
                .init();
        init();
    }

    private void init() {
        mPlanBean = (DayPlanBean) getIntent().getSerializableExtra("bean");
        mBack = (ImageView) findViewById(R.id.driver_plan_back);
        mViewPager = (ViewPager) findViewById(R.id.driver_plan_viewpager);
        mTabLayout = (TabLayout) findViewById(R.id.driver_plan_tabs);
        mFragments = new ArrayList<>();
        Collections.addAll(mFragments, PlanInfoFragment.newInstance(mPlanBean), AssignDriverFragment.newInstance(mPlanBean));
        // 第二步：为ViewPager设置适配器
        MainFragmentAdapter adapter =
                new MainFragmentAdapter(getSupportFragmentManager(), mFragments);
        List<String> names = new ArrayList<>();
        Collections.addAll(names, "内容", "车手");
        adapter.setTitleList(names);
        mViewPager.setAdapter(adapter);
        //  第三步：将ViewPager与TableLayout 绑定在一起
        mTabLayout.setupWithViewPager(mViewPager);
        mBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == mBack){
            onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        ImmersionBar.with(this).destroy(); //必须调用该方法，防止内存泄漏
        super.onDestroy();
    }
}
