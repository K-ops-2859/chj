package com.dikai.chenghunjiclient.activity.newregister;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.bean.BeanNewPhoneCode;
import com.dikai.chenghunjiclient.bean.BeanPhoneRegister;
import com.dikai.chenghunjiclient.bean.BeanWXRegister;
import com.dikai.chenghunjiclient.citypicker.SelectCityActivity;
import com.dikai.chenghunjiclient.entity.NewUserInfo;
import com.dikai.chenghunjiclient.entity.ResultMessage;
import com.dikai.chenghunjiclient.util.Constants;
import com.dikai.chenghunjiclient.util.DensityUtil;
import com.dikai.chenghunjiclient.util.EventBusBean;
import com.dikai.chenghunjiclient.util.NetWorkUtil;
import com.dikai.chenghunjiclient.util.UserManager;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;
import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.data.Type;
import com.jzxiang.pickerview.listener.OnDateSetListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import dmax.dialog.SpotsDialog;

public class NewIdentityActivity extends AppCompatActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    private String phone;
    private String code;
    private String identityCode;
    private boolean isSup = false;
    private boolean isNews = true;
    private int type;
    private TextView area;
    private SpotsDialog mDialog;
    private String openid;
    private String token;
    private String weddingDate = "";
    private String areaID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_identity);
        EventBus.getDefault().register(this);
        ImmersionBar.with(this)
                .statusBarView(R.id.top_view)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true, 0.2f)
                .init();
        init();
    }

    private void init() {
        findViewById(R.id.back).setOnClickListener(this);
        findViewById(R.id.next).setOnClickListener(this);
        area = (TextView) findViewById(R.id.area);
        area.setOnClickListener(this);
        initRadio();
    }

    /**
     * 初始化RadioGroup
     */
    public void initRadio(){
        mDialog = new SpotsDialog(this, R.style.DialogCustom);
        type = getIntent().getIntExtra("type",0);
        phone = getIntent().getStringExtra("phone");
        code = getIntent().getStringExtra("code");
        openid = getIntent().getStringExtra("openid");
        token = getIntent().getStringExtra("token");
        int iconW = DensityUtil.dip2px(this, 22);
        RadioGroup mGroup = (RadioGroup) findViewById(R.id.select_group);
        Drawable drawable1 = ContextCompat.getDrawable(this,R.drawable.select_identity_btn);
        drawable1.setBounds(0,0,iconW,iconW);
        Drawable drawable2 = ContextCompat.getDrawable(this,R.drawable.select_identity_btn);
        drawable2.setBounds(0,0,iconW,iconW);
        Drawable drawable3 = ContextCompat.getDrawable(this,R.drawable.select_identity_btn);
        drawable3.setBounds(0,0,iconW,iconW);
        Drawable drawable4 = ContextCompat.getDrawable(this,R.drawable.select_identity_btn);
        drawable4.setBounds(0,0,iconW,iconW);
        List<Drawable> draw_list = new ArrayList<>();
        Collections.addAll(draw_list, drawable1, drawable2, drawable3, drawable4);
        for (int i = 0; i < 4; i++) {
            RadioButton button = (RadioButton) mGroup.getChildAt(i);
            button.setCompoundDrawables(draw_list.get(i),null, null, null);
            button.setCompoundDrawablePadding(DensityUtil.dip2px(this, 14));
        }

//        long Years = 10L * 365 * 1000 * 60 * 60 * 24L;
//        mPickerDialog = new TimePickerDialog.Builder()
//                .setCallBack(this)
//                .setCancelStringId("取消")
//                .setSureStringId("确定")
//                .setTitleStringId("选择您的预计婚期时间")
//                .setYearText("年")
//                .setMonthText("月")
//                .setDayText("日")
//                .setMinMillseconds(System.currentTimeMillis() - Years)
//                .setMaxMillseconds(System.currentTimeMillis() + Years)
//                .setThemeColor(ContextCompat.getColor(this, R.color.main))
//                .setType(Type.YEAR_MONTH_DAY)
//                .setWheelItemTextNormalColor(ContextCompat.getColor(this, R.color.timetimepicker_default_text_color))
//                .setWheelItemTextSelectorColor(ContextCompat.getColor(this, R.color.main))
//                .build();

        mGroup.setOnCheckedChangeListener(this);
        mGroup.check(R.id.select_1);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        isSup = false;
        switch (checkedId){
            case R.id.select_1:
                isNews = true;
                identityCode = "70CD854E-D943-4607-B993-91912329C61E";
                break;
            case R.id.select_2:
                isSup = true;
                isNews = false;
                break;
            case R.id.select_3:
                isNews = false;
                identityCode = "99C06C5A-DDB8-46A1-B860-CD1227B4DB68";
                break;
            case R.id.select_4:
                isNews = false;
                identityCode = "7DC8EDF8-A068-400F-AFD0-417B19DB3C7C";
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                onBackPressed();
                break;
            case R.id.area:
                startActivity(new Intent(this, SelectCityActivity.class));
                break;
            case R.id.next:
                if(areaID == null || "".equals(areaID)){
                    Toast.makeText(this, "请选择您所在区域", Toast.LENGTH_SHORT).show();
                }else {
                    if(isSup){
                        if(type == Constants.WX_LOGIN_REGISTER){
                            startActivity(new Intent(NewIdentityActivity.this, SelectSupActivity.class)
                                    .putExtra("phone",phone)
                                    .putExtra("code",code)
                                    .putExtra("openid",openid)
                                    .putExtra("token",token)
                                    .putExtra("area",areaID)
                                    .putExtra("type",type));
                        }else {
                            startActivity(new Intent(NewIdentityActivity.this, SelectSupActivity.class)
                                    .putExtra("phone",phone)
                                    .putExtra("code",code)
                                    .putExtra("area",areaID)
                                    .putExtra("type",type));
                        }
                    }else {
                        if(isNews){
                            if(type == Constants.WX_LOGIN_REGISTER){
                                startActivity(new Intent(NewIdentityActivity.this, RegisterDateActivity.class)
                                        .putExtra("phone",phone)
                                        .putExtra("code",code)
                                        .putExtra("openid",openid)
                                        .putExtra("token",token)
                                        .putExtra("area",areaID)
                                        .putExtra("type",type));
                            }else {
                                startActivity(new Intent(NewIdentityActivity.this, RegisterDateActivity.class)
                                        .putExtra("phone",phone)
                                        .putExtra("code",code)
                                        .putExtra("area",areaID)
                                        .putExtra("type",type));
                            }
                        }else {
                            if(type == Constants.WX_LOGIN_REGISTER){
                                wxRegister();
                            }else {
                                phoneRegister();
                            }
                        }
                    }
                }
                break;
        }
    }

    /**
     * 验证码注册
     */
    private void phoneRegister(){
        mDialog.show();
        NetWorkUtil.setCallback("HQOAApi/PhoneCodeRegister",
                new BeanPhoneRegister(phone,identityCode,code,weddingDate,areaID),
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
                                Toast.makeText(NewIdentityActivity.this, result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(NewIdentityActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
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
                                UserManager.getInstance(NewIdentityActivity.this).setLogin(result,true);
                                startActivity(new Intent(NewIdentityActivity.this,PerfectActivity.class));
                                EventBus.getDefault().post(new EventBusBean(Constants.PHONE_LOGIN_SUCCESS));
                                finish();
                            } else {
                                Toast.makeText(NewIdentityActivity.this, result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("json解析出错",e.toString());
                            //Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(final String e) {
                        mDialog.dismiss();
                        Toast.makeText(NewIdentityActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
                    }
                });
    }


    /**
     * 微信注册
     */
    private void wxRegister(){
        mDialog.show();
        NetWorkUtil.setCallback("HQOAApi/WXRegister",
                new BeanWXRegister(openid,phone,identityCode,code,token,weddingDate,areaID),
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
                                Toast.makeText(NewIdentityActivity.this, result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(NewIdentityActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
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
                if(bean.getType() == Constants.PHONE_LOGIN_SUCCESS){
                    finish();
                }else if(bean.getType() == Constants.SELECT_COUNTRY){
                    areaID = bean.getCountry().getRegionId();
                    area.setText(bean.getCountry().getRegionName());
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
