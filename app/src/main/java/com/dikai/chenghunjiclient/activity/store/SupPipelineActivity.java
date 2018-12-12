package com.dikai.chenghunjiclient.activity.store;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.adapter.store.MainFragmentAdapter;
import com.dikai.chenghunjiclient.fragment.store.SupPipelineFragment;
import com.dikai.chenghunjiclient.util.UserManager;
import com.flyco.dialog.widget.popup.base.BasePopup;
import com.gyf.barlibrary.ImmersionBar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SupPipelineActivity extends AppCompatActivity implements View.OnClickListener {

    private SimpleCustomPop mSimpleCustomPop;
    private TextView add;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sup_pipeline);
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
        add = (TextView) findViewById(R.id.add);
        add.setOnClickListener(this);
        ArrayList<Fragment> mFragments = new ArrayList<>();
        List<String> titles =  new ArrayList<>();
        Collections.addAll(mFragments, SupPipelineFragment.newInstance(0),SupPipelineFragment.newInstance(1),
                SupPipelineFragment.newInstance(2),SupPipelineFragment.newInstance(3));
        Collections.addAll(titles, "全部","待支付","已支付","已提现");
        MainFragmentAdapter mPagerAdapter = new MainFragmentAdapter(getSupportFragmentManager(), mFragments);
        mPagerAdapter.setTitleList(titles);
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setOffscreenPageLimit(4);
        tabLayout.setupWithViewPager(mViewPager);
        mSimpleCustomPop = new SimpleCustomPop(this);
        if(UserManager.getInstance(this).getNewUserInfo().getProfession().toUpperCase().equals("99C06C5A-DDB8-46A1-B860-CD1227B4DB68")){
            add.setText("添加伴手礼");
        }else {
            add.setText("添加订单");
        }
//        add.setText("添加订单");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                onBackPressed();
                break;
            case R.id.add:
//                startActivity(new Intent(SupPipelineActivity.this,AddGiftActivity.class));
                if(UserManager.getInstance(this).getNewUserInfo().getProfession().toUpperCase().equals("99C06C5A-DDB8-46A1-B860-CD1227B4DB68")){
                    startActivity(new Intent(SupPipelineActivity.this,AddGiftActivity.class));
                }else {
                    mSimpleCustomPop.anchorView(add).gravity(Gravity.BOTTOM).show();
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImmersionBar.with(this).destroy();
    }


    /**
     * 右上角弹窗
     */
    public class SimpleCustomPop extends BasePopup<SimpleCustomPop> implements View.OnClickListener {
        public SimpleCustomPop(Context context) {
            super(context);
        }

        @Override
        public View onCreatePopupView() {
            View mDialogView = View.inflate(getContext(), R.layout.item_sup_pipeline_dialog, null);
            mDialogView.findViewById(R.id.scan).setOnClickListener(this);
            mDialogView.findViewById(R.id.pay).setOnClickListener(this);
            return  mDialogView;
        }

        @Override
        public void setUiBeforShow() {

        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.scan:
                    this.dismiss();
                    startActivity(new Intent(SupPipelineActivity.this,AddGiftActivity.class));
                    break;
                case R.id.pay:
                    this.dismiss();
//                    startActivity(new Intent(SupPipelineActivity.this,AddCollectionActivity.class));
                    startActivity(new Intent(SupPipelineActivity.this,AddFanhuanActivity.class));
                    break;

            }
        }
    }
}