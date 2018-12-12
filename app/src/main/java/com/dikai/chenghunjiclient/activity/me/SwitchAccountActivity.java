package com.dikai.chenghunjiclient.activity.me;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.adapter.me.AccountAdpater;
import com.dikai.chenghunjiclient.bean.BeanID;
import com.dikai.chenghunjiclient.entity.AccountBean;
import com.dikai.chenghunjiclient.entity.NewUserInfo;
import com.dikai.chenghunjiclient.util.AccountDBManager;
import com.dikai.chenghunjiclient.util.Constants;
import com.dikai.chenghunjiclient.util.EventBusBean;
import com.dikai.chenghunjiclient.util.NetWorkUtil;
import com.dikai.chenghunjiclient.util.UserManager;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import dmax.dialog.SpotsDialog;

public class SwitchAccountActivity extends AppCompatActivity implements View.OnClickListener {

    private AccountAdpater mAdpater;
    private SpotsDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_switch_account);
        EventBus.getDefault().register(this);
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
        findViewById(R.id.clear).setOnClickListener(this);
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recycler);
        LinearLayoutManager linearManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setNestedScrollingEnabled(false);
        mAdpater = new AccountAdpater(this);
        mAdpater.setOnItemClickListener(new AccountAdpater.OnItemClickListener() {
            @Override
            public void onClick(AccountBean bean) {
                Log.e("AccountBean",bean.isADD()+"");
                if(bean.isADD()){
                    startActivity(new Intent(SwitchAccountActivity.this,AddAccountActivity.class));
                }else {
                    if(!bean.getUserId().equals(UserManager.getInstance(SwitchAccountActivity.this).getNewUserInfo().getUserId())){
                        getUser(bean.getUserId());
                    }
                }
            }
        });
        mRecyclerView.setAdapter(mAdpater);
        refresh();
    }

    private void refresh() {
        List<AccountBean> list = AccountDBManager.getInstance(this).getRecord();
        if(list.size() <= 1){
            list.add(new AccountBean(true));
        }
        mAdpater.refresh(list);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                onBackPressed();
                break;
            case R.id.clear:
                AccountDBManager.getInstance(this)
                        .clearAdd(UserManager.getInstance(this).getNewUserInfo().getUserId());
                refresh();
                break;
        }
    }
    /**
     * 获取用户信息
     */
    public void getUser(String userID) {
        mDialog.show();
        NetWorkUtil.setCallback("HQOAApi/GetUserFacilitatorInfo",
                new BeanID(userID), new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(String respose) {
                        Log.e("返回值",respose);
                        mDialog.dismiss();
                        try {
                            NewUserInfo result = new Gson().fromJson(respose, NewUserInfo.class);
                            if (result.getMessage().getCode().equals("200")) {
                                UserManager.getInstance(SwitchAccountActivity.this).setLogin(result,true);
                            }else {
                                Toast.makeText(SwitchAccountActivity.this, result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
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
    /**
     * 事件总线刷新
     * @param bean
     */
    @Subscribe
    public void onEventMainThread(final EventBusBean bean) {
        runOnUiThread(new Runnable() {
            public void run() {
                if(bean.getType() == Constants.USER_INFO_CHANGE){
                    refresh();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        ImmersionBar.with(this).destroy();
        super.onDestroy();
    }
}
