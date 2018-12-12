package com.dikai.chenghunjiclient.activity.me;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.entity.UserInfo;
import com.dikai.chenghunjiclient.fragment.plan.DriverPlanFragment;
import com.dikai.chenghunjiclient.fragment.plan.HotelFragment;
import com.dikai.chenghunjiclient.fragment.plan.HunCheFragment;
import com.dikai.chenghunjiclient.fragment.plan.NewsPlanFragment;
import com.dikai.chenghunjiclient.fragment.plan.OtherFragment;
import com.dikai.chenghunjiclient.util.UserManager;
import com.gyf.barlibrary.ImmersionBar;

public class NewPlanActivity extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout hunqingPlace;
    private FrameLayout mFrame;
    private FragmentManager manager;
    private FragmentTransaction transaction;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_plan);
        ImmersionBar.with(this)
                .statusBarView(R.id.top_view)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true, 0.2f)
                .init();
        init();
    }

    private void init() {
        findViewById(R.id.back).setOnClickListener(this);
        hunqingPlace = (LinearLayout) findViewById(R.id.fragment_hunqing_place);
        mFrame = (FrameLayout) findViewById(R.id.fragment_add_frame);
        setType();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                onBackPressed();
                break;
        }
    }

    public void setType(){
        hunqingPlace.setVisibility(View.GONE);
        mFrame.setVisibility(View.VISIBLE);
        if(UserManager.getInstance(this).isLogin()){
            UserInfo info = UserManager.getInstance(this).getUserInfo();
            String code = info.getProfession();
            switch (code){
                case "SF_1001000"://酒店
                    manager = getSupportFragmentManager();
                    transaction = manager.beginTransaction();
                    transaction.replace(R.id.fragment_add_frame, new HotelFragment());
                    transaction. commit();
                    break;
                case "SF_2001000"://婚车
                    manager = getSupportFragmentManager();
                    transaction = manager.beginTransaction();
                    transaction.replace(R.id.fragment_add_frame, new HunCheFragment());
                    transaction.commitAllowingStateLoss();
                    break;
                case "SF_13001000"://车手
                    manager = getSupportFragmentManager();
                    transaction = manager.beginTransaction();
                    transaction.replace(R.id.fragment_add_frame, new DriverPlanFragment());
                    transaction.commitAllowingStateLoss();
                    break;
                case "SF_14001000"://婚庆
                    mFrame.setVisibility(View.GONE);
                    hunqingPlace.setVisibility(View.VISIBLE);
                    break;
                case "SF_12001000"://新人
                    manager = getSupportFragmentManager();
                    transaction = manager.beginTransaction();
                    transaction.replace(R.id.fragment_add_frame, new NewsPlanFragment());
                    transaction.commit();
                    break;
                default://其他
                    manager = getSupportFragmentManager();
                    transaction = manager.beginTransaction();
                    transaction.replace(R.id.fragment_add_frame, new OtherFragment());
                    transaction.commit();
                    break;
            }
        }
    }
    
    @Override
    protected void onDestroy() {
        ImmersionBar.with(this).destroy();
        super.onDestroy();
    }
}
