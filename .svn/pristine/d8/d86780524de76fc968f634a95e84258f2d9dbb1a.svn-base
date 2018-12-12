package com.dikai.chenghunjiclient.activity.register;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.bean.BeanRegister;
import com.dikai.chenghunjiclient.entity.ResultMessage;
import com.dikai.chenghunjiclient.util.Constants;
import com.dikai.chenghunjiclient.util.EventBusBean;
import com.dikai.chenghunjiclient.util.MD5Util;
import com.dikai.chenghunjiclient.util.NetWorkUtil;
import com.dikai.chenghunjiclient.util.UpLoadImgThread;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;

public class SetNewsPwdActivity extends AppCompatActivity implements View.OnClickListener {

    //=======================
    private EditText pwd1;
    private EditText pwd2;
    private BeanRegister mBeanRegister;
    private SpotsDialog mDialog;
    private EditText codeEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_news_pwd);
        ImmersionBar.with(this)
                .statusBarView(R.id.top_view)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true, 0.2f)
                .init();
        initView();
    }

    private void initView() {
        mBeanRegister = (BeanRegister) getIntent().getSerializableExtra("register");
        mDialog = new SpotsDialog(this, R.style.DialogCustom);
        pwd1 = (EditText) findViewById(R.id.activity_reset_pwd_pwd1);
        pwd2 = (EditText) findViewById(R.id.activity_reset_pwd_pwd2);
        codeEdit = (EditText) findViewById(R.id.activity_reset_code);
        findViewById(R.id.activity_reset_pwd_back).setOnClickListener(this);
        findViewById(R.id.activity_reset_pwd_next).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.activity_register_car_back:
                onBackPressed();
                break;
            case R.id.activity_reset_pwd_next:
                next();
                break;
        }
    }

    private void next() {
        if(pwd1.getText() == null || "".equals(pwd1.getText().toString().trim())){
            Toast.makeText(this, "请输入密码！", Toast.LENGTH_SHORT).show();
        }else if(pwd2.getText() == null || "".equals(pwd2.getText().toString().trim())){
            Toast.makeText(this, "请确认密码！", Toast.LENGTH_SHORT).show();
        }else if(!pwd1.getText().toString().trim().equals(pwd2.getText().toString().trim())){
            Toast.makeText(this, "两次密码输入不一致！", Toast.LENGTH_SHORT).show();
        }else{
            //注册
            mDialog.show();
            mBeanRegister.setUserPwd(MD5Util.md5(pwd1.getText().toString().trim()));
            mBeanRegister.setYQCode((codeEdit.getText() == null || "".equals(codeEdit.getText().toString().trim()))?
                    "":codeEdit.getText().toString().trim());
            List<String> list = new ArrayList<>();
            list.add(mBeanRegister.getLogo());
            upload(list);
        }
    }

    private void upload(List<String> list){
        UpLoadImgThread.getInstance(this).upLoad(list, "http://121.42.156.151:93/FileStorage.aspx",
                "0", "1", new UpLoadImgThread.CallBackListener()  {
                    @Override
                    public void onFinish(List<String> values) {
                        mBeanRegister.setLogo(values.get(0));
                        mBeanRegister.setProfession("SF_12001000");
                        mBeanRegister.setFrontIDcard("");
                        mBeanRegister.setNegativeIDcard("");
                        mBeanRegister.setHandheldIDcard("");
                        mBeanRegister.setBusinesslicense("");
                        mBeanRegister.setDrivinglicense("");
                        mBeanRegister.setModelID("0");
                        mBeanRegister.setAge("0");
                        mBeanRegister.setRegion("");
                        mBeanRegister.setAdress("");
                        register();
                    }

                    @Override
                    public void onError(String e) {
                        mDialog.dismiss();
                        Toast.makeText(SetNewsPwdActivity.this, "" + e.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
    /**
     * 注册
     */
    private void register(){
        NetWorkUtil.setCallback("User/RegisterSupplier", mBeanRegister,
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        mDialog.dismiss();
                        Log.e("返回值",respose);
                        try {
                            ResultMessage result = new Gson().fromJson(respose, ResultMessage.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                EventBus.getDefault().post(new EventBusBean(Constants.USER_REGISTER_SUCCESS));
                                Toast.makeText(SetNewsPwdActivity.this, result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(SetNewsPwdActivity.this, result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("json解析出错",e.toString());
                            //Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(final String e) {
                        mDialog.dismiss();
                        Toast.makeText(SetNewsPwdActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ImmersionBar.with(this).destroy();
//        EventBus.getDefault().unregister(this);
    }
}
