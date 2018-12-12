package com.dikai.chenghunjiclient.activity.wedding;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.adapter.store.MainFragmentAdapter;
import com.dikai.chenghunjiclient.bean.BeanGetWedCase;
import com.dikai.chenghunjiclient.bean.BeanNone;
import com.dikai.chenghunjiclient.bean.BeanWedCase;
import com.dikai.chenghunjiclient.entity.GetProjectBean;
import com.dikai.chenghunjiclient.entity.ResultGetColor;
import com.dikai.chenghunjiclient.entity.ResultGetWedCase;
import com.dikai.chenghunjiclient.entity.UserInfo;
import com.dikai.chenghunjiclient.fragment.BaseFragmentAdapter;
import com.dikai.chenghunjiclient.fragment.wedding.ProjectBeanFragment;
import com.dikai.chenghunjiclient.util.NetWorkUtil;
import com.dikai.chenghunjiclient.util.UserManager;
import com.dikai.chenghunjiclient.view.MyLoadRecyclerView;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WedCaseActivity extends AppCompatActivity {

    private ArrayList<Fragment> mFragments;
    private ViewPager mViewPager;
    private TabLayout tabLayout;
    private MainFragmentAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wed_case);
        ImmersionBar.with(this)
                .statusBarView(R.id.top_view)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true, 0.2f)
                .init();
        init();
    }

    private void init() {
        Toolbar mToolBar = (Toolbar) findViewById(R.id.toolbar);
        ImageView ivSearch = (ImageView) findViewById(R.id.iv_search);
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);

       mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               finish();
           }
       });

        ivSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(WedCaseActivity.this, SearchProjectActivity.class));
            }
        });

        getColorList();
    }

    private void initFragment(ResultGetColor result) {
        List<String> colors = Arrays.asList(result.getColor().split(","));
        mFragments = new ArrayList<>();
        for (String color : colors) {
            System.out.println("颜色----" + color);
            mFragments.add(ProjectBeanFragment.newInstance(color, 2));
        }
        mPagerAdapter = new MainFragmentAdapter(getSupportFragmentManager(), mFragments);
        mPagerAdapter.setTitleList(colors);
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setOffscreenPageLimit(2);
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
    }

    public void getColorList() {
        NetWorkUtil.setCallback("HQOAApi/PlanColorList",
                new BeanNone(),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        Log.e("返回值", respose);
                        try {
                            ResultGetColor result = new Gson().fromJson(respose, ResultGetColor.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                initFragment(result);
                            } else {
                                Toast.makeText(WedCaseActivity.this, "" + result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("json解析出错", e.toString());
                            //Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(final String e) {
                        Log.e("网络出错", e.toString());
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImmersionBar.with(this).destroy();
    }
}
