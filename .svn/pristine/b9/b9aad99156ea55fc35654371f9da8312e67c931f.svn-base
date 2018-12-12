package com.dikai.chenghunjiclient.activity.newregister;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.bean.BeanNewPwd;
import com.dikai.chenghunjiclient.bean.BeanNewPwdLogin;
import com.dikai.chenghunjiclient.entity.NewUserInfo;
import com.dikai.chenghunjiclient.entity.ResultNewPhoneCode;
import com.dikai.chenghunjiclient.util.Constants;
import com.dikai.chenghunjiclient.util.EventBusBean;
import com.dikai.chenghunjiclient.util.MD5Util;
import com.dikai.chenghunjiclient.util.NetWorkUtil;
import com.dikai.chenghunjiclient.util.UserManager;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;

import org.greenrobot.eventbus.EventBus;

import dmax.dialog.SpotsDialog;

public class NewPwdActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText pwdEdit;
    private ImageView showPwd;
    private SpotsDialog mDialog;
    private boolean isShowPwd = false;
    private String phone;
    private String code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_pwd);
        ImmersionBar.with(this)
                .statusBarView(R.id.top_view)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true, 0.2f)
                .init();
        init();
    }

    private void init() {
        mDialog = new SpotsDialog(this,R.style.DialogCustom);
        phone = getIntent().getStringExtra("phone");
        code = getIntent().getStringExtra("code");
        findViewById(R.id.back).setOnClickListener(this);
        findViewById(R.id.send).setOnClickListener(this);
        pwdEdit = (EditText) findViewById(R.id.pwd_edit);
        showPwd = (ImageView) findViewById(R.id.show_pwd);
        showPwd.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                onBackPressed();
                break;
            case R.id.show_pwd:
                if(isShowPwd){
                    showPwd.setImageResource(R.mipmap.ic_hide_pwd);
                    pwdEdit.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    isShowPwd = false;
                }else {
                    showPwd.setImageResource(R.mipmap.ic_show_pwd);
                    pwdEdit.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    isShowPwd = true;
                }
                break;
            case R.id.send:
                if(pwdEdit.getText() == null || "".equals(pwdEdit.getText().toString())){
                    Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
                }else {
                    newPwd(phone,pwdEdit.getText().toString(),code);
                }
                break;
        }
    }

    /**
     * 提交新密码
     */
    private void newPwd(final String phone, final String pwd, String code){
        mDialog.show();
        NetWorkUtil.setCallback("HQOAApi/UpdatePassWord",
                new BeanNewPwd(phone, MD5Util.md5(pwd),code),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        mDialog.dismiss();
                        Log.e("返回值",respose);
                        try {
                            ResultNewPhoneCode result = new Gson().fromJson(respose, ResultNewPhoneCode.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                pwdLogin(phone,pwd);
                            } else {
                                Toast.makeText(NewPwdActivity.this, result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("json解析出错",e.toString());
                            //Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(final String e) {
                        mDialog.dismiss();
                        Toast.makeText(NewPwdActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * 密码登录
     */
    private void pwdLogin(String phone, String pwd){
        mDialog.show();
        NetWorkUtil.setCallback("HQOAApi/PhoneGetUserInfo",
                new BeanNewPwdLogin(phone, MD5Util.md5(pwd)),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        mDialog.dismiss();
                        Log.e("返回值",respose);
                        try {
                            NewUserInfo result = new Gson().fromJson(respose, NewUserInfo.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                UserManager.getInstance(NewPwdActivity.this).setLogin(result,true);
                                EventBus.getDefault().post(new EventBusBean(Constants.NEW_FORGET_PWD));
                                finish();
                            } else {
                                Toast.makeText(NewPwdActivity.this, result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("json解析出错",e.toString());
                            //Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(final String e) {
                        mDialog.dismiss();
                        Toast.makeText(NewPwdActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    protected void onDestroy() {
        ImmersionBar.with(this).destroy();
        super.onDestroy();
    }

}
