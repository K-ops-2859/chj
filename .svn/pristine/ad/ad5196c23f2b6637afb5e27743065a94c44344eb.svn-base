package com.dikai.chenghunjiclient.activity.newregister;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.bean.BeanNewPhoneCode;
import com.dikai.chenghunjiclient.bean.BeanPhoneRegister;
import com.dikai.chenghunjiclient.bean.BeanWXRegister;
import com.dikai.chenghunjiclient.entity.NewUserInfo;
import com.dikai.chenghunjiclient.entity.ResultMessage;
import com.dikai.chenghunjiclient.util.Constants;
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
import java.util.Date;

import dmax.dialog.SpotsDialog;

public class RegisterDateActivity extends AppCompatActivity implements View.OnClickListener, OnDateSetListener {

    private String phone;
    private String code;
    private int type;
    private SpotsDialog mDialog;
    private String openid;
    private String token;
    private String weddingDate = "";
    private String areaID;
    private TimePickerDialog mPickerDialog;
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    private TextView dateText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_date);
        ImmersionBar.with(this)
                .statusBarView(R.id.top_view)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true, 0.2f)
                .init();
        init();
    }

    private void init() {

        type = getIntent().getIntExtra("type",0);
        phone = getIntent().getStringExtra("phone");
        code = getIntent().getStringExtra("code");
        openid = getIntent().getStringExtra("openid");
        token = getIntent().getStringExtra("token");
        areaID = getIntent().getStringExtra("area");

        mDialog = new SpotsDialog(this,R.style.DialogCustom);
        dateText = (TextView) findViewById(R.id.date);
        findViewById(R.id.back).setOnClickListener(this);
        findViewById(R.id.skip).setOnClickListener(this);
        findViewById(R.id.date).setOnClickListener(this);
        findViewById(R.id.next).setOnClickListener(this);

        long Years = 10L * 365 * 1000 * 60 * 60 * 24L;
        mPickerDialog = new TimePickerDialog.Builder()
                .setCallBack(this)
                .setCancelStringId("取消")
                .setSureStringId("确定")
                .setTitleStringId("选择您的预计婚期时间")
                .setYearText("年")
                .setMonthText("月")
                .setDayText("日")
                .setMinMillseconds(System.currentTimeMillis() - Years)
                .setMaxMillseconds(System.currentTimeMillis() + Years)
                .setThemeColor(ContextCompat.getColor(this, R.color.main))
                .setType(Type.YEAR_MONTH_DAY)
                .setWheelItemTextNormalColor(ContextCompat.getColor(this, R.color.timetimepicker_default_text_color))
                .setWheelItemTextSelectorColor(ContextCompat.getColor(this, R.color.main))
                .build();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                onBackPressed();
                break;
            case R.id.skip:
                if(type == Constants.WX_LOGIN_REGISTER){
                    wxRegister();
                }else {
                    phoneRegister();
                }
                break;
            case R.id.date:
                mPickerDialog.show(getSupportFragmentManager(), "year_month_day");
                break;
            case R.id.next:
                if(weddingDate == null || "".equals(weddingDate)){
                    Toast.makeText(this, "请选择您的婚期", Toast.LENGTH_SHORT).show();
                }else {
                    if(type == Constants.WX_LOGIN_REGISTER){
                        wxRegister();
                    }else {
                        phoneRegister();
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
                new BeanPhoneRegister(phone,"70CD854E-D943-4607-B993-91912329C61E",code,weddingDate,areaID),
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
                                Toast.makeText(RegisterDateActivity.this, result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(RegisterDateActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
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
                                UserManager.getInstance(RegisterDateActivity.this).setLogin(result,true);
                                EventBus.getDefault().post(new EventBusBean(Constants.PHONE_LOGIN_SUCCESS));
                                startActivity(new Intent(RegisterDateActivity.this,PerfectActivity.class));
                                finish();
                            } else {
                                Toast.makeText(RegisterDateActivity.this, result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("json解析出错",e.toString());
                            //Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(final String e) {
                        mDialog.dismiss();
                        Toast.makeText(RegisterDateActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
                    }
                });
    }


    /**
     * 微信注册
     */
    private void wxRegister(){
        mDialog.show();
        NetWorkUtil.setCallback("HQOAApi/WXRegister",
                new BeanWXRegister(openid,phone,"70CD854E-D943-4607-B993-91912329C61E",code,token,weddingDate,areaID),
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
                                Toast.makeText(RegisterDateActivity.this, result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(RegisterDateActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    protected void onDestroy() {
        ImmersionBar.with(this).destroy();
        super.onDestroy();
    }

    @Override
    public void onDateSet(TimePickerDialog timePickerView, long millseconds) {
        weddingDate = format.format(new Date(millseconds));
        dateText.setText(weddingDate);
    }
}
