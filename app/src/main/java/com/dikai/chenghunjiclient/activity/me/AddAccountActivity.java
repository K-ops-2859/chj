package com.dikai.chenghunjiclient.activity.me;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.activity.newregister.NewForgetPwdActivity;
import com.dikai.chenghunjiclient.bean.BeanNewPwdLogin;
import com.dikai.chenghunjiclient.entity.AccountBean;
import com.dikai.chenghunjiclient.entity.NewUserInfo;
import com.dikai.chenghunjiclient.util.AccountDBManager;
import com.dikai.chenghunjiclient.util.Constants;
import com.dikai.chenghunjiclient.util.EventBusBean;
import com.dikai.chenghunjiclient.util.MD5Util;
import com.dikai.chenghunjiclient.util.NetWorkUtil;
import com.dikai.chenghunjiclient.util.UserManager;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import dmax.dialog.SpotsDialog;

public class AddAccountActivity extends AppCompatActivity implements View.OnClickListener {

    private SpotsDialog mDialog;
    private EditText phoneEdit;
    private EditText pwdEdit;
    private ImageView showPwd;
    private boolean isShowPwd = false;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_account);
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
        findViewById(R.id.login).setOnClickListener(this);
        findViewById(R.id.pwd_login).setOnClickListener(this);
        phoneEdit = (EditText) findViewById(R.id.phone_edit);
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
            case R.id.pwd_login:
                startActivity(new Intent(this,NewForgetPwdActivity.class));
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
            case R.id.login:
                if(phoneEdit.getText() == null || "".equals(phoneEdit.getText().toString())){
                    Toast.makeText(this, "请填写手机号", Toast.LENGTH_SHORT).show();
                }else if(pwdEdit.getText() == null || "".equals(pwdEdit.getText().toString())){
                    Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
                }else {
                    pwdLogin(phoneEdit.getText().toString(),pwdEdit.getText().toString());
                }
                break;
        }
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
                                UserManager.getInstance(AddAccountActivity.this).setLogin(result,true);
                                ((InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE))
                                        .hideSoftInputFromWindow(AddAccountActivity.this.getCurrentFocus()
                                                .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                                finish();
                            } else {
                                Toast.makeText(AddAccountActivity.this, result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("json解析出错",e.toString());
                            //Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(final String e) {
                        mDialog.dismiss();
                        Toast.makeText(AddAccountActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
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
                if(bean.getType() == Constants.NEW_FORGET_PWD){
                    Toast.makeText(AddAccountActivity.this, "添加成功！", Toast.LENGTH_SHORT).show();
                    finish();
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
