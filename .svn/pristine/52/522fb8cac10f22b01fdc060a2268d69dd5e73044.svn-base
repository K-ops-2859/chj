package com.dikai.chenghunjiclient.activity.store;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.dikai.chenghunjiclient.MainActivity;
import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.activity.invitation.ComInviteMainActivity;
import com.dikai.chenghunjiclient.activity.invitation.VipInviteMainActivity;
import com.dikai.chenghunjiclient.activity.me.ShareAppActivity;
import com.dikai.chenghunjiclient.activity.newregister.NewLoginActivity;
import com.dikai.chenghunjiclient.activity.register.LoginActivity;
import com.dikai.chenghunjiclient.activity.wedding.FreeWedActivity;
import com.dikai.chenghunjiclient.activity.wedding.InviteWedActivity;
import com.dikai.chenghunjiclient.activity.wedding.MakeProjectActivity;
import com.dikai.chenghunjiclient.activity.wedding.WeddingAssureActivity;
import com.dikai.chenghunjiclient.adapter.ad.AdListAdapter;
import com.dikai.chenghunjiclient.bean.BeanNone;
import com.dikai.chenghunjiclient.bean.BeanUserId;
import com.dikai.chenghunjiclient.entity.NewAdBean;
import com.dikai.chenghunjiclient.entity.ResultGetInvitationProfit;
import com.dikai.chenghunjiclient.entity.ResultGetNewAd;
import com.dikai.chenghunjiclient.util.NetWorkUtil;
import com.dikai.chenghunjiclient.util.UserManager;
import com.dikai.chenghunjiclient.view.MyLoadRecyclerView;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;

import dmax.dialog.SpotsDialog;

public class NewHomeADActivity extends AppCompatActivity implements View.OnClickListener {


    private MyLoadRecyclerView mRecyclerView;
    private AdListAdapter mAdapter;
    private SpotsDialog mDialog;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_home_ad);
        ImmersionBar.with(this)
                .statusBarView(R.id.top_view)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true, 0.2f)
                .init();
        init();
    }

    private void init() {
        mDialog = new SpotsDialog(this,R.style.DialogCustom);
        findViewById(R.id.back).setOnClickListener(this);
        mRecyclerView = (MyLoadRecyclerView) findViewById(R.id.recycler);
        mRecyclerView.setLoadChangeListener(new MyLoadRecyclerView.OnLoadChangeListener() {
            @Override
            public void onRefresh() {
                refresh();
            }

            @Override
            public void onLoadMore() {

            }
        });
        mAdapter = new AdListAdapter(this);
        mAdapter.setOnItemClickListener(new AdListAdapter.OnItemClickListener() {
            @Override
            public void onClick(NewAdBean bean) {
                action(bean);
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        refresh();
    }

    private void refresh(){
        getList();
    }
    //    邀请好友办婚礼    —————YQBHL
    //    免费办婚礼          —————FreeBHL
    //    分享APP赚现金 —————ShareApp
    //    免费领爆米花 —————-FreeBMH
    //    婚礼担保————————HLDB
    //    我要出方案 ——————ChuFA
    //    共享方案 ———————GongxianFA
    //    婚礼返还———————-HunLiFH
    //    酒店活动———————-JDHD
    //    婚嫁采购节———————-HJCGJ
    //    伴手礼———————-BSL
    //    代收———————-DS
    //    6239F1CA-E3CE-49E7-AEB3-21DBE83070B7	代收
    //    51F48013-9ADA-4342-96BF-259C345832AE	婚礼返还
    //    75B1E2BB-13BB-45B5-8B28-B6F31A97F8EC	伴手礼


    private void action(NewAdBean bean){
        if("YQBHL".equals(bean.getActivityCode())){
            if(UserManager.getInstance(this).isLogin()){
                getProfit(UserManager.getInstance(this).getNewUserInfo().getUserId());
            }else {
                getProfit(UserManager.getInstance(this).getNewUserInfo().getUserId());
            }
        }else if("FreeBHL".equals(bean.getActivityCode())){
            startActivity(new Intent(this, FreeWedActivity.class));
        }else if("ShareApp".equals(bean.getActivityCode())){
            startActivity(new Intent(this, ShareAppActivity.class));
        }else if("FreeBMH".equals(bean.getActivityCode())){
            if(!UserManager.getInstance(this).isLogin()){
                startActivity(new Intent(this, NewLoginActivity.class));
            }else{
                startActivity(new Intent(this, BoomActivity.class));
            }
        }else if("HLDB".equals(bean.getActivityCode())){
            startActivity(new Intent(this, WeddingAssureActivity.class));
        }else if("ChuFA".equals(bean.getActivityCode())){
            startActivity(new Intent(this, MakeProjectActivity.class));
        }else if("JDHD".equals(bean.getActivityCode())){
            startActivity(new Intent(this, HotelADActivity.class));
        }else if("HunLiFH".equals(bean.getActivityCode())){
            if(!UserManager.getInstance(this).isLogin()){
                startActivity(new Intent(this, NewLoginActivity.class));
            }else{
                startActivity(new Intent(this, WeddingStoreActivity.class)
                        .putExtra("code",bean.getActivityCode()));
            }
        }else if("BSL".equals(bean.getActivityCode())){
            if(!UserManager.getInstance(this).isLogin()){
                startActivity(new Intent(this, NewLoginActivity.class));
            }else{
                startActivity(new Intent(this, WeddingStoreActivity.class)
                        .putExtra("code",bean.getActivityCode()));
            }
        }else if("DS".equals(bean.getActivityCode())){
            if(!UserManager.getInstance(this).isLogin()){
                startActivity(new Intent(this, NewLoginActivity.class));
            }else{
                startActivity(new Intent(this, WeddingStoreActivity.class)
                        .putExtra("code",bean.getActivityCode()));
            }
        }else if("HJCGJ".equals(bean.getActivityCode())){
            if(!UserManager.getInstance(this).isLogin()){
                startActivity(new Intent(this, NewLoginActivity.class));
            }else{
                startActivity(new Intent(this, CaigoujieActivity.class));
            }
        }else {
            Toast.makeText(this, "参与该活动,请更新版本!", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 获取活动列表
     */
    private void getList() {
        NetWorkUtil.setCallback("HQOAApi/GetWeChatActivityList",
                new BeanNone(),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(String respose) {
                        mRecyclerView.stopLoad();
                        Log.e("返回值",respose);
                        try {
                            ResultGetNewAd result = new Gson().fromJson(respose, ResultGetNewAd.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                mAdapter.refresh(result.getData());
                                if(result.getData().size() == 0){
                                    mRecyclerView.setHasData(false);
                                }else {
                                    mRecyclerView.setHasData(true);
                                }
                            } else {
                                Toast.makeText(NewHomeADActivity.this, result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("json解析出错",e.toString());
                            //Toast.makeText(this, "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onError(String e) {
                        mRecyclerView.stopLoad();
                    }
                });

    }
    /**
     * 获取邀请结婚收益
     */
    public void getProfit(String userID) {
        mDialog.show();
        NetWorkUtil.setCallback("HQOAApi/GetInvitationProfit",
                new BeanUserId(userID), new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(String respose) {
                        mDialog.dismiss();
                        Log.e("返回值",respose);
                        try {
                            ResultGetInvitationProfit result = new Gson().fromJson(respose, ResultGetInvitationProfit.class);
                            if (result.getMessage().getCode().equals("200")) {
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("profit",result);
                                if (result.getRefereeStatus() == 0){//普通用户
                                    startActivity(new Intent(NewHomeADActivity.this, ComInviteMainActivity.class).putExtras(bundle));
                                }else {//Vip
                                    startActivity(new Intent(NewHomeADActivity.this, VipInviteMainActivity.class).putExtras(bundle));
                                }
                            }else {
                                Toast.makeText(NewHomeADActivity.this, result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        }catch (Exception e){
                            Log.e("",e.toString());
                        }
                    }
                    @Override
                    public void onError(String e) {
                        mDialog.dismiss();
                        Log.e("网络请求错误",e);
                    }
                });
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
        ImmersionBar.with(this).destroy(); //必须调用该方法，防止内存泄漏
        super.onDestroy();
    }
}
