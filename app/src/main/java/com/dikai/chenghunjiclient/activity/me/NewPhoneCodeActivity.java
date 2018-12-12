package com.dikai.chenghunjiclient.activity.me;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.bean.BeanGetAuthCode;
import com.dikai.chenghunjiclient.bean.BeanInspectCode;
import com.dikai.chenghunjiclient.entity.ResultCheckCode;
import com.dikai.chenghunjiclient.entity.ResultMessage;
import com.dikai.chenghunjiclient.util.Constants;
import com.dikai.chenghunjiclient.util.EventBusBean;
import com.dikai.chenghunjiclient.util.NetWorkUtil;
import com.dikai.chenghunjiclient.util.UserManager;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import java.util.Timer;
import java.util.TimerTask;
import dmax.dialog.SpotsDialog;

public class NewPhoneCodeActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mBackLayout;
    private EditText mCode;
    private TextView getCode;
    private TextView mark;
    private TextView mNext;
    private Timer timer;
    private int tempNum;
    private String phone;
    private String code;
    private SpotsDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_phonef);
        ImmersionBar.with(this)
                .statusBarView(R.id.top_view)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true, 0.2f)
                .init();
        initView();
    }

    private void initView() {
        mDialog = new SpotsDialog(this,R.style.DialogCustom);
        mBackLayout = (ImageView) findViewById(R.id.activity_new_phone_back);
        mark = (TextView) findViewById(R.id.activity_new_phone_mark);
        mCode = (EditText) findViewById(R.id.activity_register_code);
        mNext = (TextView) findViewById(R.id.activity_new_phone_edit);
        getCode = (TextView) findViewById(R.id.tv_get_code);
        mBackLayout.setOnClickListener(this);
        mNext.setOnClickListener(this);
        getCode.setOnClickListener(this);
        String oldPhone = UserManager.getInstance(this).getUserInfo().getPhone();
        phone = oldPhone;
        String l = oldPhone.substring(0 , 3) + "****" + oldPhone.substring(oldPhone.length() -4 , oldPhone.length());
        Log.e(phone,"---" + l);
        mark.setText("请输入手机" + l + "收到的短信验证码");
    }


    @Override
    public void onClick(View v) {
        if (v == mBackLayout){
            onBackPressed();
        }else if(v == getCode){
            getAuthCode();
        }else if(v == mNext){
            if(mCode.getText() != null && !"".equals(mCode.getText().toString().trim())){
                code = mCode.getText().toString().trim();
                inspectCode();
            }
        }
    }

    private void next(ResultCheckCode result){
        startActivity(new Intent(NewPhoneCodeActivity.this, NewPhoneActivity.class)
                .putExtra("codeid",result.getAuthCodeID()));
    }

    /**
     * 获取验证码
     */
    private void getAuthCode(){
        setGetCodeUnUse();
        NetWorkUtil.setCallback("User/GetSMSCode",
                new BeanGetAuthCode("1", phone, "3"),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        Log.e("返回值",respose);
                        try {
                            ResultMessage result = new Gson().fromJson(respose, ResultMessage.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                setfocus();
                                Toast.makeText(NewPhoneCodeActivity.this, "验证码已发送", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(NewPhoneCodeActivity.this, result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("json解析出错",e.toString());
                            //Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(final String e) {
                        Toast.makeText(NewPhoneCodeActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * 检测验证码
     */
    private void inspectCode(){
        mDialog.show();
        NetWorkUtil.setCallback("User/VerifySMSCode",
                new BeanInspectCode("1",phone,"3",code),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        mDialog.dismiss();
                        Log.e("返回值",respose);
                        try {
                            ResultCheckCode result = new Gson().fromJson(respose, ResultCheckCode.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                setGetCodeUse();
                                next(result);
                            } else {
                                Toast.makeText(NewPhoneCodeActivity.this, result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("json解析出错",e.toString());
                            //Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(final String e) {
                        mDialog.dismiss();
                        Toast.makeText(NewPhoneCodeActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    //设置按键不可用
    public void setGetCodeUnUse(){
        getCode.setClickable(false);
        getCode.setBackgroundResource(R.drawable.bg_btn_gray_solid);
//        getCode.setTextColor(0xffffffff);
        getCode.setEnabled(false);
        tempNum = 0;
        timer = new Timer(true);
        timer.schedule(new NewPhoneCodeActivity.task(), 0, 1000); //延时1000ms后执行，1000ms执行一次
    }
    //恢复按键可用
    public void setGetCodeUse(){
        if (timer != null){
            timer.cancel();
        }
        getCode.setText("获取验证码");
        getCode.setClickable(true);
        getCode.setBackgroundResource(R.drawable.select_btn_main);
        getCode.setEnabled(true);
    }

    private void setfocus(){
        mCode.setFocusable(true);
        mCode.setFocusableInTouchMode(true);
        mCode.requestFocus();
    }

    private final Handler handler = new Handler(){
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    tempNum++;
                    getCode.setText(60 - tempNum +"s");
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
                if(bean.getType() == Constants.USER_REGISTER_SUCCESS){
                    finish();
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        ImmersionBar.with(this).destroy();
        if (timer != null){
            timer.cancel();
        }
        super.onDestroy();
    }
}
