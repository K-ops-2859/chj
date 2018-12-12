package com.dikai.chenghunjiclient.activity.newregister;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.bean.BeanCheckCaptcha;
import com.dikai.chenghunjiclient.bean.BeanNewPhoneCode;
import com.dikai.chenghunjiclient.bean.BeanPhone;
import com.dikai.chenghunjiclient.codeinput.CodeInput;
import com.dikai.chenghunjiclient.entity.NewUserInfo;
import com.dikai.chenghunjiclient.entity.ResultMessage;
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

import java.util.Timer;
import java.util.TimerTask;

import dmax.dialog.SpotsDialog;

public class CaptchaActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView resend;
    private Timer timer;
    private int tempNum;
    private String phoneNum;
    private SpotsDialog mDialog;
    private int type;
    private String openid;
    private String token;
    private MaterialDialog dialogBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_captcha);
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
        type = getIntent().getIntExtra("type",0);
        tempNum = getIntent().getIntExtra("time",0);
        phoneNum = getIntent().getStringExtra("phone");
        openid = getIntent().getStringExtra("openid");
        token = getIntent().getStringExtra("token");
        resend = (TextView) findViewById(R.id.resend);
        resend.setOnClickListener(this);
        findViewById(R.id.back).setOnClickListener(this);
        TextView info = (TextView) findViewById(R.id.info);
        CodeInput cInput = (CodeInput) findViewById(R.id.codeInput);
        cInput.setCodeReadyListener(new CodeInput.codeReadyListener() {
            @Override
            public void onCodeReady(Character[] code) {
                String captcha = "";
                for (Character b : code){
                    captcha = captcha + b.toString();
                }
                checkCode(captcha);
            }
        });
        dialogBack = new MaterialDialog(this);
        dialogBack.isTitleShow(false)//
                .btnNum(2)
                .content("短信验证码可能有延迟，确认返回并重新开始")//
                .btnText("确认返回", "继续等待")
                .setOnBtnClickL(new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        dialogBack.dismiss();
                        finish();
                    }
                }, new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        dialogBack.dismiss();
                    }
                });


        info.setText("验证码已发送至+86 "+phoneNum);
        setGetCodeUnUse();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                onBackPressed();
                break;
            case R.id.resend:
                getSMSCode(phoneNum);
                break;
        }
    }

    /**
     * 验证码校验
     */
    private void checkCode(final String code){
        mDialog.show();
        NetWorkUtil.setCallback("HQOAApi/VerificationSMSCode",
                new BeanCheckCaptcha(phoneNum,code),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        mDialog.dismiss();
                        Log.e("返回值",respose);
                        try {
                            ResultMessage result = new Gson().fromJson(respose, ResultMessage.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                if(type == Constants.WX_LOGIN_REGISTER){
                                    startActivity(new Intent(CaptchaActivity.this,NewIdentityActivity.class)
                                            .putExtra("phone",phoneNum)
                                            .putExtra("code",code)
                                            .putExtra("openid",openid)
                                            .putExtra("token",token)
                                            .putExtra("type",Constants.WX_LOGIN_REGISTER));
                                }else if(type == Constants.NEW_FORGET_PWD){
                                    startActivity(new Intent(CaptchaActivity.this,NewPwdActivity.class)
                                            .putExtra("phone",phoneNum)
                                            .putExtra("code",code));
                                }else {
                                    getUserInfo(code);
                                }
                            } else {
                                Toast.makeText(CaptchaActivity.this, result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("json解析出错",e.toString());
                            //Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(final String e) {
                        mDialog.dismiss();
                        Toast.makeText(CaptchaActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * 验证码登录
     */
    private void getUserInfo(final String code){
        mDialog.show();
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
                                if("00000000-0000-0000-0000-000000000000".equals(result.getUserId())){
                                    //未注册
                                    startActivity(new Intent(CaptchaActivity.this, NewIdentityActivity.class)
                                            .putExtra("phone",phoneNum).putExtra("code",code));
                                }else {
                                    UserManager.getInstance(CaptchaActivity.this).setLogin(result,true);
                                    EventBus.getDefault().post(new EventBusBean(Constants.PHONE_LOGIN_SUCCESS));
                                    finish();
                                }
                            } else {
                                Toast.makeText(CaptchaActivity.this, result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("json解析出错",e.toString());
                            //Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(final String e) {
                        mDialog.dismiss();
                        Toast.makeText(CaptchaActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * 发送验证码
     */
    private void getSMSCode(final String phone){
        setGetCodeUnUse();
        NetWorkUtil.setCallback("HQOAApi/GetSMSCode",
                new BeanPhone(phone),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        Log.e("返回值",respose);
                        try {
                            ResultMessage result = new Gson().fromJson(respose, ResultMessage.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                Toast.makeText(CaptchaActivity.this, "验证码已发送", Toast.LENGTH_SHORT).show();
                            } else {
                                setGetCodeUse();
                                Toast.makeText(CaptchaActivity.this, result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            setGetCodeUse();
                            Log.e("json解析出错",e.toString());
                            //Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(final String e) {
                        setGetCodeUse();
                        Toast.makeText(CaptchaActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onBackPressed() {
        dialogBack.show();
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        ImmersionBar.with(this).destroy();
        if (timer != null){
            timer.cancel();
        }
        super.onDestroy();
    }

    //设置按键不可用
    public void setGetCodeUnUse(){
        resend.setClickable(false);
        resend.setTextColor(ContextCompat.getColor(this,R.color.gray_btn));
        resend.setEnabled(false);
        tempNum = 0;
        timer = new Timer(true);
        timer.schedule(new task(), 0, 1000); //延时1000ms后执行，1000ms执行一次
    }
    //恢复按键可用
    public void setGetCodeUse(){
        if (timer != null){
            timer.cancel();
        }
        resend.setText("重新发送");
        resend.setClickable(true);
        resend.setTextColor(ContextCompat.getColor(this,R.color.red_new));
        resend.setEnabled(true);
    }

    private final Handler handler = new Handler(){
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    tempNum++;
                    resend.setText((60 - tempNum) +"s 后可重新发送发送");
                    if(tempNum >= 60){
                        setGetCodeUse();
                    }
                    break;
            }
            super.handleMessage(msg);
        }
    };

    public class task extends TimerTask {
        @Override
        public void run() {
            Message message = new Message();
            message.what = 1;
            handler.sendMessage(message);
        }
    }

    /**
     * 事件总线刷新
     * @param bean
     */
    @Subscribe
    public void onEventMainThread(final EventBusBean bean) {
        runOnUiThread(new Runnable() {
            public void run() {
                if(bean.getType() == Constants.WX_REGISTER_SUCCESS ||
                        bean.getType() == Constants.PHONE_REGISTER_SUCCESS||
                        bean.getType() == Constants.NEW_FORGET_PWD){
                    finish();
                }
            }
        });
    }

}
