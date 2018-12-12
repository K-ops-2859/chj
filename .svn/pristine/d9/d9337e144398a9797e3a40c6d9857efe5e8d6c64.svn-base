package com.dikai.chenghunjiclient.activity.newregister;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.adapter.newregister.NewIdentityAdapter;
import com.dikai.chenghunjiclient.bean.BeanNewPhoneCode;
import com.dikai.chenghunjiclient.bean.BeanPhoneRegister;
import com.dikai.chenghunjiclient.bean.BeanType;
import com.dikai.chenghunjiclient.bean.BeanWXRegister;
import com.dikai.chenghunjiclient.entity.NewIdentityBean;
import com.dikai.chenghunjiclient.entity.NewUserInfo;
import com.dikai.chenghunjiclient.entity.ResultMessage;
import com.dikai.chenghunjiclient.entity.ResultNewIdentity;
import com.dikai.chenghunjiclient.util.Constants;
import com.dikai.chenghunjiclient.util.EventBusBean;
import com.dikai.chenghunjiclient.util.NetWorkUtil;
import com.dikai.chenghunjiclient.util.UserManager;
import com.dikai.chenghunjiclient.view.MyLoadRecyclerView;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;

import org.greenrobot.eventbus.EventBus;

import dmax.dialog.SpotsDialog;

public class SelectSupActivity extends AppCompatActivity implements View.OnClickListener {

    private NewIdentityAdapter mAdapter;
    private MyLoadRecyclerView mRecyclerView;
    private String phone;
    private String code;
    private SpotsDialog mDialog;
    private String supCode;
    private String openid;
    private int type;
    private String token;
    private String areaID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_sup);
        ImmersionBar.with(this)
                .statusBarView(R.id.top_view)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true, 0.2f)
                .init();
        init();
    }

    private void init() {
        mDialog = new SpotsDialog(this, R.style.DialogCustom);
        type = getIntent().getIntExtra("type",0);
        phone = getIntent().getStringExtra("phone");
        code = getIntent().getStringExtra("code");
        openid = getIntent().getStringExtra("openid");
        token = getIntent().getStringExtra("token");
        areaID = getIntent().getStringExtra("area");
        findViewById(R.id.back).setOnClickListener(this);
        findViewById(R.id.next).setOnClickListener(this);
        mRecyclerView = (MyLoadRecyclerView) findViewById(R.id.recycler);
        mRecyclerView.setLoadChangeListener(new MyLoadRecyclerView.OnLoadChangeListener() {
            @Override
            public void onRefresh() {
                refresh();
            }

            @Override
            public void onLoadMore() {}
        });
        mAdapter = new NewIdentityAdapter(this);
        mAdapter.setOnItemClickListener(new NewIdentityAdapter.OnItemClickListener() {
            @Override
            public void onClick(NewIdentityBean bean) {
                supCode = bean.getOccupationID();
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        refresh();
    }

    private void refresh() {
        getSup();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                onBackPressed();
                break;
            case R.id.next:
                if(type == Constants.WX_LOGIN_REGISTER){
                    wxRegister();
                }else {
                    phoneRegister();
                }
                break;
        }
    }
    /**
     * 获取供应商身份
     */
    private void getSup(){
        NetWorkUtil.setCallback("HQOAApi/GetAllOccupationList",
                new BeanType(3),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        mRecyclerView.stopLoad();
                        Log.e("返回值",respose);
                        try {
                            ResultNewIdentity result = new Gson().fromJson(respose, ResultNewIdentity.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                supCode = result.getData().get(0).getOccupationID();
                                mAdapter.refresh(result.getData());
                            } else {
                                Toast.makeText(SelectSupActivity.this, result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("json解析出错",e.toString());
                            //Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(final String e) {
                        mRecyclerView.stopLoad();
                        Toast.makeText(SelectSupActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * 注册
     */
    private void phoneRegister(){
        mDialog.show();
        NetWorkUtil.setCallback("HQOAApi/PhoneCodeRegister",
                new BeanPhoneRegister(phone,supCode,code,"",areaID),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        Log.e("返回值",respose);
                        try {
                            ResultMessage result = new Gson().fromJson(respose, ResultMessage.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                EventBus.getDefault().post(new EventBusBean(Constants.PHONE_REGISTER_SUCCESS));
                                getUserInfo(phone,code);
                            } else {
                                mDialog.dismiss();
                                Toast.makeText(SelectSupActivity.this, result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            mDialog.dismiss();
                            Log.e("json解析出错",e.toString());
                            //Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(final String e) {
                        mDialog.dismiss();
                        Toast.makeText(SelectSupActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * 微信注册
     */
    private void wxRegister(){
        mDialog.show();
        NetWorkUtil.setCallback("HQOAApi/WXRegister",
                new BeanWXRegister(openid,phone,supCode,code,token,"",areaID),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        Log.e("返回值",respose);
                        try {
                            ResultMessage result = new Gson().fromJson(respose, ResultMessage.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                EventBus.getDefault().post(new EventBusBean(Constants.WX_REGISTER_SUCCESS));
                                getUserInfo(phone,code);
                            } else {
                                mDialog.dismiss();
                                Toast.makeText(SelectSupActivity.this, result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            mDialog.dismiss();
                            Log.e("json解析出错",e.toString());
                            //Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(final String e) {
                        mDialog.dismiss();
                        Toast.makeText(SelectSupActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
                    }
                });
    }


    /**
     * 验证码登录
     */
    private void getUserInfo(final String phoneNum, final String code){
        NetWorkUtil.setCallback("HQOAApi/SMSCodeGetUserInfo",
                new BeanNewPhoneCode(phoneNum,code),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        mDialog.dismiss();
                        Log.e("返回值",respose);
                        try {
                            NewUserInfo result = new Gson().fromJson(respose, NewUserInfo.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                UserManager.getInstance(SelectSupActivity.this).setLogin(result,true);
                                EventBus.getDefault().post(new EventBusBean(Constants.PHONE_LOGIN_SUCCESS));
                                startActivity(new Intent(SelectSupActivity.this,PerfectActivity.class));
                                finish();
                            } else {
                                Toast.makeText(SelectSupActivity.this, result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("json解析出错",e.toString());
                            //Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(final String e) {
                        mDialog.dismiss();
                        Toast.makeText(SelectSupActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    
    @Override
    protected void onDestroy() {
        ImmersionBar.with(this).destroy();
        super.onDestroy();
    }

}
