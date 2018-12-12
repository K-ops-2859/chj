package com.dikai.chenghunjiclient.activity.plan;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.adapter.store.MainFragmentAdapter;
import com.dikai.chenghunjiclient.bean.BeanGetFlow;
import com.dikai.chenghunjiclient.entity.ResultGetFlow;
import com.dikai.chenghunjiclient.entity.UserInfo;
import com.dikai.chenghunjiclient.fragment.plan.CalendarFragment;
import com.dikai.chenghunjiclient.fragment.plan.FlowFragment;
import com.dikai.chenghunjiclient.fragment.plan.ManPlanFragment;
import com.dikai.chenghunjiclient.util.Constants;
import com.dikai.chenghunjiclient.util.EventBusBean;
import com.dikai.chenghunjiclient.util.NetWorkUtil;
import com.dikai.chenghunjiclient.util.UserManager;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import dmax.dialog.SpotsDialog;

public class NewsPlanActivity extends AppCompatActivity implements View.OnClickListener {

    private SpotsDialog mDialog;
    private String customId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_plan);
        ImmersionBar.with(this)
                .statusBarView(R.id.top_view)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true, 0.2f)
                .init();
        init();
    }

    private void init() {
        customId = getIntent().getStringExtra("id");
        mDialog = new SpotsDialog(this, R.style.DialogCustom);
        findViewById(R.id.fragment_add_back).setOnClickListener(this);
        getList();
    }

    private void setView(ResultGetFlow result){
        ViewPager mViewPager = (ViewPager) findViewById(R.id.fragment_add_viewpager);
        TabLayout mTabLayout = (TabLayout) findViewById(R.id.fragment_add_tabs);
        List<Fragment> mFragments = new ArrayList<>();
        Collections.addAll(mFragments, ManPlanFragment.newInstance(result), FlowFragment.newInstance(result));
        // 第二步：为ViewPager设置适配器
        MainFragmentAdapter adapter =
                new MainFragmentAdapter(getSupportFragmentManager(), mFragments);
        List<String> names = new ArrayList<>();
        Collections.addAll(names, "人员安排", "婚礼流程");
        adapter.setTitleList(names);
        mViewPager.setAdapter(adapter);
        //  第三步：将ViewPager与TableLayout 绑定在一起
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fragment_add_back:
                onBackPressed();
                break;
        }
    }

    /**
     * 获取信息
     */
    private void getList(){
        mDialog.show();
        UserInfo info = UserManager.getInstance(this).getUserInfo();
        NetWorkUtil.setCallback("User/GetCustomerInfoDetaill",
                new BeanGetFlow(info.getUserID(), customId),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        mDialog.dismiss();
                        Log.e("返回值",respose);
                        try {
                            ResultGetFlow result = new Gson().fromJson(respose, ResultGetFlow.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                setView(result);
                            } else {
                                Toast.makeText(NewsPlanActivity.this, result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("json解析出错",e.toString());
                            //Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(final String e) {
                        mDialog.dismiss();
                        Toast.makeText(NewsPlanActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImmersionBar.with(this).destroy();
    }
}
