package com.dikai.chenghunjiclient.activity.me;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.adapter.store.MainFragmentAdapter;
import com.dikai.chenghunjiclient.bean.BeanQuitTeam;
import com.dikai.chenghunjiclient.bean.BeanUserInfo;
import com.dikai.chenghunjiclient.entity.ResultGetUserInfo;
import com.dikai.chenghunjiclient.entity.ResultMessage;
import com.dikai.chenghunjiclient.entity.UserInfo;
import com.dikai.chenghunjiclient.fragment.me.MyInviteFragment;
import com.dikai.chenghunjiclient.fragment.me.TeamIntroFragment;
import com.dikai.chenghunjiclient.fragment.me.DriverFragment;
import com.dikai.chenghunjiclient.fragment.store.CarsFragment;
import com.dikai.chenghunjiclient.util.Constants;
import com.dikai.chenghunjiclient.util.EventBusBean;
import com.dikai.chenghunjiclient.util.NetWorkUtil;
import com.dikai.chenghunjiclient.util.UserManager;
import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.widget.MaterialDialog;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import dmax.dialog.SpotsDialog;

public class MyTeamActivity extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout mPageLayout;
    private ImageView mBack;
    private TextView editTeam;
    private LinearLayout noTeam;
    private TextView createTeam;
    private FrameLayout mFrameLayout;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private List<Fragment> mFragments;
    private int type;
    private MyInviteFragment mInviteFragment;
    private TeamIntroFragment mIntroFragment;
    private MaterialDialog dialog;
    private MaterialDialog dialog1;
    private SpotsDialog mDialog;
    private String captainID;
    private int isAudit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_team);
        EventBus.getDefault().register(this);
        ImmersionBar.with(this)
                .statusBarView(R.id.top_view)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true, 0.2f)
                .init();
        init();
    }

    private void init() {
        mDialog = new SpotsDialog(this, R.style.DialogCustom);
        mPageLayout = (LinearLayout) findViewById(R.id.activity_my_team_page);
        mBack = (ImageView) findViewById(R.id.activity_my_team_back);
        editTeam = (TextView) findViewById(R.id.activity_my_team_edit);
        noTeam = (LinearLayout) findViewById(R.id.activity_my_team_no);
        createTeam = (TextView) findViewById(R.id.activity_my_team_add);
        mFrameLayout = (FrameLayout) findViewById(R.id.activity_my_team_frame);
        mTabLayout = (TabLayout) findViewById(R.id.activity_my_team_tabs);
        mViewPager = (ViewPager) findViewById(R.id.activity_my_team_viewpager);
        createTeam.setOnClickListener(this);
        mBack.setOnClickListener(this);
        editTeam.setOnClickListener(this);
        dialog = new MaterialDialog(this);
        dialog.isTitleShow(false)//
                .btnNum(2)
                .content("你确定退出该车队吗？")//
                .btnText("取消", "确定")
                .setOnBtnClickL(new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        dialog.dismiss();
                    }
                }, new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        dialog.dismiss();
                        quit();
                    }
                });
        dialog1 = new MaterialDialog(this);
        dialog1.isTitleShow(false)//
                .btnNum(2)
                .content("你确定解散该车队吗？")//
                .btnText("取消", "确定")
                .setOnBtnClickL(new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        dialog1.dismiss();
                    }
                }, new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        dialog1.dismiss();
                        dissolve();
                    }
                });
        getType();
    }

    @Override
    public void onClick(View v) {
        if(v == mBack){
            onBackPressed();
        }else if(v == editTeam){
            if(type == 2){
                dialog.show();
            }else if(type == 3){
                dialog1.show();
            }
        }else if(v == createTeam){
            if(isAudit == 0 || isAudit == 2){
                Toast.makeText(this, "您还未通过审核，审核通过后即可创建车队！", Toast.LENGTH_SHORT).show();
            }else {
                startActivity(new Intent(this, CreateTeamActivity.class));
            }
        }
    }

    private void setupViewPager() {
        UserInfo info = UserManager.getInstance(this).getUserInfo();
        mFragments = new ArrayList<>();
        Collections.addAll(mFragments, TeamIntroFragment.newInstance(UserManager.getInstance(this).getUserInfo().getSupplierID()),
                CarsFragment.newInstance(info.getSupplierID()),DriverFragment.newInstance(info.getSupplierID(),1));
        // 第二步：为ViewPager设置适配器
        MainFragmentAdapter adapter =
                new MainFragmentAdapter(getSupportFragmentManager(), mFragments);
        List<String> names = new ArrayList<>();
        Collections.addAll(names, "简介", "车型", "成员");
        adapter.setTitleList(names);
        mViewPager.setAdapter(adapter);
        //  第三步：将ViewPager与TableLayout 绑定在一起
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private void setView(){
        noTeam.setVisibility(View.GONE);
        mFrameLayout.setVisibility(View.GONE);
        mPageLayout.setVisibility(View.GONE);
        if(type == -1){
            Toast.makeText(this, "未知错误！", Toast.LENGTH_SHORT).show();
        }else if(type == 0){
            noTeam.setVisibility(View.VISIBLE);
            editTeam.setVisibility(View.INVISIBLE);
        }else if(type == 1){
            editTeam.setVisibility(View.INVISIBLE);
            mFrameLayout.setVisibility(View.VISIBLE);
            if(mInviteFragment == null){
                mInviteFragment = new MyInviteFragment();
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.add(R.id.activity_my_team_frame,mInviteFragment);
                transaction.show(mInviteFragment);
                transaction.commit();
            }else {
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.show(mInviteFragment);
                transaction.commit();
            }
        }else if(type == 2){
            editTeam.setVisibility(View.VISIBLE);
            editTeam.setText("退队");
            mFrameLayout.setVisibility(View.VISIBLE);
            if(mIntroFragment == null){
                mIntroFragment = TeamIntroFragment.newInstance(captainID);
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.add(R.id.activity_my_team_frame,mIntroFragment);
                transaction.show(mIntroFragment);
                transaction.commit();
            }else {
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.show(mIntroFragment);
                transaction.commit();
            }
        }else if(type == 3){
            editTeam.setVisibility(View.VISIBLE);
            editTeam.setText("解散");
            mPageLayout.setVisibility(View.VISIBLE);
            setupViewPager();
        }
    }

    private void getType() {
        UserInfo info = UserManager.getInstance(this).getUserInfo();
        String code = info.getProfession();
        Log.e("MyTeam:getType() === ",code);
        switch (code) {
            case "SF_2001000"://婚车
                type = 3;
                setView();
                break;
            case "SF_13001000"://车手
                getInfo();
                break;
            default:
                type = -1;
                setView();
                break;
        }
    }

    /**
     * 车手是否加入车队
     */
    private void getInfo(){
        NetWorkUtil.setCallback("User/GetUserInfo",
                new BeanUserInfo(UserManager.getInstance(this).getUserInfo().getUserID()),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        Log.e("返回值",respose);
                        try {
                            ResultGetUserInfo result = new Gson().fromJson(respose, ResultGetUserInfo.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                if(result.getIsMotorcade() == 0){
                                    isAudit = result.getStatusType();
                                    if(result.getIsNews() == 0){
                                        type = 0;
                                    }else {
                                        type = 1;
                                    }
                                }else {
                                    type = 2;
                                    captainID = result.getCaptainID();
                                }
                                Log.e("MyTeam:getInfo() === ","type: " + type + " isAudit：" + isAudit);
                                setView();
                            } else {
                                Toast.makeText(MyTeamActivity.this, result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("json解析出错",e.toString());
                            //Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(final String e) {
                        Toast.makeText(MyTeamActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
                    }
                });
    }


    /**
     * 离开/解散车队
     */
    private void quit(){
        mDialog.show();
        UserInfo info = UserManager.getInstance(this).getUserInfo();
        NetWorkUtil.setCallback("User/DirverPull",
                new BeanQuitTeam(info.getUserID(), captainID,"0"),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        Log.e("返回值",respose);
                        try {
                            ResultMessage result = new Gson().fromJson(respose, ResultMessage.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                upUser();
                            } else {
                                Toast.makeText(MyTeamActivity.this, result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("json解析出错",e.toString());
                            //Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(final String e) {
                        mDialog.dismiss();
                        Toast.makeText(MyTeamActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * 解散车队
     */
    private void dissolve(){
        mDialog.show();
        UserInfo info = UserManager.getInstance(this).getUserInfo();
        NetWorkUtil.setCallback("User/DirverPull",
                new BeanQuitTeam(info.getUserID(), info.getSupplierID(),"1"),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        Log.e("返回值",respose);
                        try {
                            ResultMessage result = new Gson().fromJson(respose, ResultMessage.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                upUser();
                            } else {
                                Toast.makeText(MyTeamActivity.this, result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("json解析出错",e.toString());
                            //Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(final String e) {
                        mDialog.dismiss();
                        Toast.makeText(MyTeamActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void upUser(){
        UserManager.getInstance(this).autoLogin(new UserManager.OnLoginListener() {
            @Override
            public void onFinish() {
                mDialog.dismiss();
                EventBus.getDefault().post(new EventBusBean(Constants.USER_INFO_CHANGE));
                finish();
            }

            @Override
            public void onError(String e) {
                mDialog.dismiss();
                EventBus.getDefault().post(new EventBusBean(Constants.USER_RELOGIN));
                finish();
            }
        });
    }

    /**
     * 事件总线刷新
     * @param bean
     */
    @Subscribe
    public void onEventMainThread(final EventBusBean bean) {
        runOnUiThread(new Runnable() {
            public void run() {
                if(bean.getType() == Constants.USER_INFO_CHANGE){
                    getType();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        ImmersionBar.with(this).destroy(); //必须调用该方法，防止内存泄漏
        super.onDestroy();
    }


}
