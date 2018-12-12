package com.dikai.chenghunjiclient.activity.me;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.activity.register.LoginActivity;
import com.dikai.chenghunjiclient.bean.BeanChangePhone;
import com.dikai.chenghunjiclient.entity.ResultMessage;
import com.dikai.chenghunjiclient.entity.UserInfo;
import com.dikai.chenghunjiclient.util.Constants;
import com.dikai.chenghunjiclient.util.EventBusBean;
import com.dikai.chenghunjiclient.util.NetWorkUtil;
import com.dikai.chenghunjiclient.util.UserManager;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;

import org.greenrobot.eventbus.EventBus;

import dmax.dialog.SpotsDialog;

public class NewPhoneActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mBackLayout;
    private EditText mPhone;
    private TextView mNext;
    private String codeID;
    private SpotsDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_phone);
        ImmersionBar.with(this)
                .statusBarView(R.id.top_view)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true, 0.2f)
                .init();
        init();
    }

    private void init() {
        mDialog = new SpotsDialog(this, R.style.DialogCustom);
        codeID = getIntent().getStringExtra("codeid");
        mBackLayout = (ImageView) findViewById(R.id.activity_new_phone_back);
        mPhone = (EditText) findViewById(R.id.activity_new_phone_code);
        mNext = (TextView) findViewById(R.id.activity_new_phone_edit);
        mBackLayout.setOnClickListener(this);
        mNext.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == mBackLayout){
            onBackPressed();
        }else if(v == mNext){
            if(mPhone.getText() != null && !"".equals(mPhone.getText().toString().trim())){
                changePhone();
            }else {
                Toast.makeText(this, "请输入新手机号！", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * 更换手机号
     */
    private void changePhone(){
        mDialog.show();
        UserInfo info = UserManager.getInstance(this).getUserInfo();
        NetWorkUtil.setCallback("User/ReplacePhone",
                new BeanChangePhone(info.getPhone(),mPhone.getText().toString().trim(),codeID,"1",info.getUserID()),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        mDialog.dismiss();
                        Log.e("返回值",respose);
                        try {
                            ResultMessage result = new Gson().fromJson(respose, ResultMessage.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                Toast.makeText(NewPhoneActivity.this, "手机号更换成功！", Toast.LENGTH_SHORT).show();
                                EventBus.getDefault().post(new EventBusBean(Constants.USER_REGISTER_SUCCESS));
                                UserManager.getInstance(NewPhoneActivity.this).logout();
                                startActivity(new Intent(NewPhoneActivity.this, LoginActivity.class));
                                finish();
                            } else {
                                Toast.makeText(NewPhoneActivity.this, result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("json解析出错",e.toString());
                            //Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(final String e) {
                        mDialog.dismiss();
                        Toast.makeText(NewPhoneActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImmersionBar.with(this).destroy();
    }
}
