package com.dikai.chenghunjiclient.activity.newregister;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dikai.chenghunjiclient.BaseApplication;
import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.activity.store.BannerInfoActivity;
import com.dikai.chenghunjiclient.bean.BeanNewPwdLogin;
import com.dikai.chenghunjiclient.bean.BeanPhone;
import com.dikai.chenghunjiclient.bean.BeanWXGetUserInfo;
import com.dikai.chenghunjiclient.entity.NewUserInfo;
import com.dikai.chenghunjiclient.entity.ResultMessage;
import com.dikai.chenghunjiclient.entity.ResultNewPhoneCode;
import com.dikai.chenghunjiclient.entity.WXTokenBean;
import com.dikai.chenghunjiclient.util.Constants;
import com.dikai.chenghunjiclient.util.EventBusBean;
import com.dikai.chenghunjiclient.util.MD5Util;
import com.dikai.chenghunjiclient.util.NetWorkUtil;
import com.dikai.chenghunjiclient.util.UserManager;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;
import com.tencent.mm.opensdk.modelmsg.SendAuth;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dmax.dialog.SpotsDialog;

public class NewLoginActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView title;
    private TextView titleInfo;
    private TextView sendCode;
    private EditText phoneCode;
    private EditText phoneEdit;
    private EditText pwdEdit;
    private ImageView showPwd;
    private LinearLayout phoneLayout;
    private LinearLayout loginLayout;
    private boolean isLoginNow = false;
    private boolean isShowPwd = false;

//    private Timer timer;
//    private int tempNum;
    private SpotsDialog mDialog;
    private WXTokenBean mWXTokenBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_login);
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
        findViewById(R.id.area_num).setOnClickListener(this);
        findViewById(R.id.pwd_login).setOnClickListener(this);
        findViewById(R.id.phone_login).setOnClickListener(this);
        findViewById(R.id.login).setOnClickListener(this);
        findViewById(R.id.forget_pwd).setOnClickListener(this);
        findViewById(R.id.wechat_login).setOnClickListener(this);
        findViewById(R.id.xieyi).setOnClickListener(this);
        sendCode = (TextView) findViewById(R.id.send);
        phoneLayout = (LinearLayout) findViewById(R.id.phone_layout);
        loginLayout = (LinearLayout) findViewById(R.id.login_layout);
        title = (TextView) findViewById(R.id.title);
        titleInfo = (TextView) findViewById(R.id.info);
        phoneCode = (EditText) findViewById(R.id.phone);
        phoneEdit = (EditText) findViewById(R.id.phone_edit);
        pwdEdit = (EditText) findViewById(R.id.pwd_edit);
        showPwd = (ImageView) findViewById(R.id.show_pwd);
        showPwd.setOnClickListener(this);
        sendCode.setOnClickListener(this);
        phoneLayout.setVisibility(View.VISIBLE);
        loginLayout.setVisibility(View.GONE);
//        phoneCode.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if(isMobile(s.toString())){
//                    setGetCodeUse();
//                }else {
//                    setGetCodeUnUse();
//                }
//            }
//            @Override
//            public void afterTextChanged(Editable s) {}
//        });
//        setGetCodeUnUse();
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        ImmersionBar.with(this).destroy();
//        if (timer != null){
//            timer.cancel();
//        }
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                onBackPressed();
                break;
            case R.id.area_num:
                Toast.makeText(this, "暂时只支持中国大陆地区！", Toast.LENGTH_SHORT).show();
                break;
            case R.id.pwd_login:
                isLoginNow = true;
                phoneLayout.setVisibility(View.GONE);
                loginLayout.setVisibility(View.VISIBLE);
                break;
            case R.id.phone_login:
                isLoginNow = false;
                phoneLayout.setVisibility(View.VISIBLE);
                loginLayout.setVisibility(View.GONE);
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
                if(phoneCode.getText() != null || !"".equals(phoneCode.getText().toString())){
                    getSMSCode(phoneCode.getText().toString());
                }else {
                    Toast.makeText(this, "请填写手机号", Toast.LENGTH_SHORT).show();
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
            case R.id.forget_pwd:
                startActivity(new Intent(this,NewForgetPwdActivity.class));
                break;
            case R.id.xieyi:
                startActivity(new Intent(this,BannerInfoActivity.class)
                        .putExtra("url","http://www.chenghunji.com/capital/useragreement")
                        .putExtra("title","用户须知"));
                break;
            case R.id.wechat_login:
                wxLogin();
                break;
        }
    }

    private void wxLogin() {
        if (!((BaseApplication)getApplication()).getWxApi().isWXAppInstalled()) {
            Toast.makeText(this, "您还未安装微信客户端", Toast.LENGTH_SHORT).show();
            return;
        }
        final SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "diandi_wx_login";
        ((BaseApplication)getApplication()).getWxApi().sendReq(req);
    }

    /**
     * 发送验证码
     */
    private void getSMSCode(final String phone){
//        setGetCodeUnUse();
        NetWorkUtil.setCallback("HQOAApi/GetSMSCode",
                new BeanPhone(phone),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        Log.e("返回值",respose);
//                        setGetCodeUse();
                        try {
                            ResultMessage result = new Gson().fromJson(respose, ResultMessage.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                Toast.makeText(NewLoginActivity.this, "验证码已发送", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(NewLoginActivity.this, CaptchaActivity.class)
                                        .putExtra("time",0)
                                        .putExtra("phone",phone));
                            } else {
                                Toast.makeText(NewLoginActivity.this, result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("json解析出错",e.toString());
                            //Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(final String e) {
//                        setGetCodeUse();
                        Toast.makeText(NewLoginActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
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
                                UserManager.getInstance(NewLoginActivity.this).setLogin(result,true);
                                EventBus.getDefault().post(new EventBusBean(Constants.PHONE_LOGIN_SUCCESS));
                                ((InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(NewLoginActivity.this.getCurrentFocus().getWindowToken(),
                                        InputMethodManager.HIDE_NOT_ALWAYS);
                                finish();
                            } else {
                                Toast.makeText(NewLoginActivity.this, result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("json解析出错",e.toString());
                            //Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(final String e) {
                        mDialog.dismiss();
                        Toast.makeText(NewLoginActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * 获取微信token
     */
    private void getWXToken(String code){
        NetWorkUtil.wxLogin("https://api.weixin.qq.com/sns/oauth2/access_token?appid="+
                        Constants.WX_APP_ID+"&secret="+Constants.WX_APP_SECRET+"&code="+code+"&grant_type=authorization_code",
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(String respose) {
                        Log.e("返回值",respose);
                        try {
                            mWXTokenBean = new Gson().fromJson(respose, WXTokenBean.class);
                            getUserInfo(mWXTokenBean.getOpenid());
                        } catch (Exception e) {
                            Log.e("json解析出错",e.toString());
                            Toast.makeText(NewLoginActivity.this, "微信请求错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(String e) {
                        Log.e("网络错误",e);
                    }
                });
    }

    /**
     * 微信OpenID登录
     */
    private void getUserInfo(final String openid){
        mDialog.show();
        NetWorkUtil.setCallback("HQOAApi/WXGetUserInfo",
                new BeanWXGetUserInfo(openid),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        mDialog.dismiss();
                        Log.e("返回值",respose);
                        try {
                            NewUserInfo result = new Gson().fromJson(respose, NewUserInfo.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                if("00000000-0000-0000-0000-000000000000".equals(result.getUserId())){
                                    //未注册
                                    startActivity(new Intent(NewLoginActivity.this, WXPhoneActivity.class)
                                            .putExtra("openid",mWXTokenBean.getOpenid())
                                            .putExtra("token",mWXTokenBean.getAccess_token()));
                                }else {
                                    Toast.makeText(NewLoginActivity.this, "微信登录成功！", Toast.LENGTH_SHORT).show();
                                    UserManager.getInstance(NewLoginActivity.this).setLogin(result,true);
                                    finish();
                                }
                            } else {
                                Toast.makeText(NewLoginActivity.this, result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("json解析出错",e.toString());
                            //Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(final String e) {
                        mDialog.dismiss();
                        Toast.makeText(NewLoginActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
                    }
                });
    }

//    //校验手机号
//    public boolean isMobile(String mobile) {
//        String regex = "^1([38][0-9]|4[579]|5[0-3,5-9]|6[6]|7[0135678]|9[89])\\d{8}$";
//        Pattern p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
//        Matcher m = p.matcher(mobile);
//        return m.matches();
//    }
//
//    //设置按键不可用
//    public void setGetCodeUnUse(){
//        sendCode.setClickable(false);
//        sendCode.setBackgroundResource(R.drawable.text_bg_gray_new);
//        sendCode.setEnabled(false);
////        tempNum = 0;
////        timer = new Timer(true);
////        timer.schedule(new task(), 0, 1000); //延时1000ms后执行，1000ms执行一次
//    }
//
//    //恢复按键可用
//    public void setGetCodeUse(){
////        if (timer != null){
////            timer.cancel();
////        }
////        sendCode.setText("发送验证码");
//        sendCode.setClickable(true);
//        sendCode.setBackgroundResource(R.drawable.text_bg_pink_new);
//        sendCode.setEnabled(true);
//    }

//    private final Handler handler = new Handler(){
//        public void handleMessage(Message msg) {
//            switch (msg.what) {
//                case 1:
//                    tempNum++;
////                    sendCode.setText("发送验证码(" + (60 - tempNum) +"s)");
//                    if(tempNum >= 60){
//                        setGetCodeUse();
//                    }
//                    break;
//            }
//            super.handleMessage(msg);
//        }
//    };
//
//    public class task extends TimerTask {
//        @Override
//        public void run() {
//            Message message = new Message();
//            message.what = 1;
//            handler.sendMessage(message);
//        }
//    }

    /**
     * 事件总线刷新
     * @param bean
     */
    @Subscribe
    public void onEventMainThread(final EventBusBean bean) {
        runOnUiThread(new Runnable() {
            public void run() {
                if(bean.getType() == Constants.WX_LOGIN_TOKEN){
                    getWXToken(bean.getData());
                }else if(bean.getType() == Constants.WX_REGISTER_SUCCESS){
                    finish();
                }else if(bean.getType() == Constants.PHONE_LOGIN_SUCCESS||
                        bean.getType() == Constants.NEW_FORGET_PWD){
                    finish();
                }
            }
        });
    }

}
