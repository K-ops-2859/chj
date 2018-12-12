package com.dikai.chenghunjiclient.activity.newregister;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.bean.BeanPhone;
import com.dikai.chenghunjiclient.entity.ResultMessage;
import com.dikai.chenghunjiclient.util.Constants;
import com.dikai.chenghunjiclient.util.EventBusBean;
import com.dikai.chenghunjiclient.util.NetWorkUtil;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Timer;
import java.util.TimerTask;

import dmax.dialog.SpotsDialog;

public class WXPhoneActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView sendCode;
    private EditText phoneCode;
    private Timer timer;
    private int tempNum;
    private SpotsDialog mDialog;
    private String openid;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wxphone);
        EventBus.getDefault().register(this);
        ImmersionBar.with(this)
                .statusBarView(R.id.top_view)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true, 0.2f)
                .init();
        init();
    }

    private void init() {
        openid = getIntent().getStringExtra("openid");
        token = getIntent().getStringExtra("token");
        mDialog = new SpotsDialog(this,R.style.DialogCustom);
        findViewById(R.id.back).setOnClickListener(this);
        findViewById(R.id.area_num).setOnClickListener(this);
        sendCode = (TextView) findViewById(R.id.send);
        phoneCode = (EditText) findViewById(R.id.phone);
        sendCode.setOnClickListener(this);
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
            case R.id.send:
                if(phoneCode.getText() != null || !"".equals(phoneCode.getText().toString())){
                    getSMSCode(phoneCode.getText().toString());
                }else {
                    Toast.makeText(this, "请填写手机号", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    /**
     * 发送验证码
     */
    private void getSMSCode(final String phone){
        setGetCodeUnUse();
        mDialog.show();
        NetWorkUtil.setCallback("HQOAApi/GetSMSCode",
                new BeanPhone(phone),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        mDialog.dismiss();
                        Log.e("返回值",respose);
                        try {
                            ResultMessage result = new Gson().fromJson(respose, ResultMessage.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                Toast.makeText(WXPhoneActivity.this, "验证码已发送", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(WXPhoneActivity.this, CaptchaActivity.class)
                                        .putExtra("time",tempNum)
                                        .putExtra("phone",phone)
                                        .putExtra("openid",openid)
                                        .putExtra("token",token)
                                        .putExtra("type",Constants.WX_LOGIN_REGISTER));
                            } else {
                                setGetCodeUse();
                                Toast.makeText(WXPhoneActivity.this, result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            setGetCodeUse();
                            Log.e("json解析出错",e.toString());
                            //Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(final String e) {
                        mDialog.dismiss();
                        setGetCodeUse();
                        Toast.makeText(WXPhoneActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
                    }
                });
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
        sendCode.setClickable(false);
        sendCode.setBackgroundResource(R.drawable.text_bg_gray_new);
        sendCode.setEnabled(false);
        tempNum = 0;
        timer = new Timer(true);
        timer.schedule(new WXPhoneActivity.task(), 0, 1000); //延时1000ms后执行，1000ms执行一次
    }
    //恢复按键可用
    public void setGetCodeUse(){
        if (timer != null){
            timer.cancel();
        }
        sendCode.setText("发送验证码");
        sendCode.setClickable(true);
        sendCode.setBackgroundResource(R.drawable.text_bg_pink_new);
        sendCode.setEnabled(true);
    }

    private final Handler handler = new Handler(){
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    tempNum++;
                    sendCode.setText("发送验证码(" + (60 - tempNum) +"s)");
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
                if(bean.getType() == Constants.WX_REGISTER_SUCCESS){
                    finish();
                }
            }
        });
    }

}
