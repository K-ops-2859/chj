package com.dikai.chenghunjiclient.activity.newregister;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.bean.BeanPhone;
import com.dikai.chenghunjiclient.entity.ResultMessage;
import com.dikai.chenghunjiclient.util.Constants;
import com.dikai.chenghunjiclient.util.EventBusBean;
import com.dikai.chenghunjiclient.util.NetWorkUtil;
import com.flyco.dialog.widget.base.BaseDialog;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Timer;
import java.util.TimerTask;

import dmax.dialog.SpotsDialog;

public class NewForgetPwdActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int CALL_REQUEST_CODE = 101;
    private TextView sendCode;
    private TextView title;
    private TextView info;
    private EditText phoneCode;
    private Timer timer;
    private int tempNum;
    private SpotsDialog mDialog;
    private Intent intent;
    private ServiceDialog mSerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_forget_pwd);
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
        findViewById(R.id.service).setOnClickListener(this);
        title = (TextView) findViewById(R.id.title);
        info = (TextView) findViewById(R.id.info);
        sendCode = (TextView) findViewById(R.id.send);
        phoneCode = (EditText) findViewById(R.id.phone);
        sendCode.setOnClickListener(this);
        mSerDialog = new ServiceDialog(this);
        mSerDialog.widthScale(1);
        mSerDialog.heightScale(1);
        if(getIntent().getIntExtra("type",0) == 1){//设置新密码
            title.setText("修改密码");
            info.setText("验证手机号以设置新密码");
        }
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
            case R.id.service:
                mSerDialog.show();
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
                                Toast.makeText(NewForgetPwdActivity.this, "验证码已发送", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(NewForgetPwdActivity.this, CaptchaActivity.class)
                                        .putExtra("time",tempNum)
                                        .putExtra("phone",phone)
                                        .putExtra("type", Constants.NEW_FORGET_PWD));
                            } else {
                                setGetCodeUse();
                                Toast.makeText(NewForgetPwdActivity.this, result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(NewForgetPwdActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
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
        timer.schedule(new NewForgetPwdActivity.task(), 0, 1000); //延时1000ms后执行，1000ms执行一次
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

    public class ServiceDialog extends BaseDialog<ServiceDialog> implements View.OnClickListener {
        private RelativeLayout cancel;
        private RelativeLayout sure;
        public ServiceDialog(Context context) {
            super(context);
        }

        @Override
        public View onCreateView() {
            View view = View.inflate(mContext, R.layout.dialog_call_service, null);
            return view;
        }

        @Override
        public void onViewCreated(View view) {
            super.onViewCreated(view);
            cancel = (RelativeLayout) view.findViewById(R.id.button_cancel);
            sure = (RelativeLayout) view.findViewById(R.id.button_sure);
            cancel.setOnClickListener(this);
            sure.setOnClickListener(this);
        }

        @Override
        public void setUiBeforShow() {

        }

        @Override
        public void onClick(View v) {
            if(v == cancel){
                this.dismiss();
            }else if(v == sure){
                this.dismiss();
                intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "15192055999"));
                request();
            }
        }
    }

    private void request() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {
            //申请CALL_PHONE权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, CALL_REQUEST_CODE);
        } else {
            startActivity(intent);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        doNext(requestCode, grantResults);
    }

    private void doNext(int requestCode, int[] grantResults) {
        if (requestCode == CALL_REQUEST_CODE) {
            if (grantResults != null && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startActivity(intent);
            } else {
                Toast.makeText(this, "已禁止", Toast.LENGTH_SHORT).show();
            }
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
                if(bean.getType() == Constants.NEW_FORGET_PWD){
                    finish();
                }
            }
        });
    }
}
