package com.dikai.chenghunjiclient.activity.wedding;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.bean.PhoneNumberInviteBean;
import com.dikai.chenghunjiclient.entity.ResultMessage;
import com.dikai.chenghunjiclient.util.NetWorkUtil;
import com.dikai.chenghunjiclient.util.UserManager;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;

/**
 * Created by cmk03 on 2017/12/6.
 */

public class PhoneNumberInviteActivity extends AppCompatActivity {

    private EditText etPhone;
    private EditText etName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_number_invite);
        ImmersionBar.with(this)
                .statusBarView(R.id.top_view)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true, 0.2f)
                .init();
        ImageView mBack = (ImageView) findViewById(R.id.activity_zone_back);
        etPhone = (EditText) findViewById(R.id.et_phone);
        etName = (EditText) findViewById(R.id.et_name);
        TextView tvAdd = (TextView) findViewById(R.id.tv_add);

        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        tvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verify();
            }
        });
    }

    private void verify() {
        String name = etName.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        if (name.equals("")) {
            Toast.makeText(this, "请输入姓名", Toast.LENGTH_SHORT).show();
            return;
        }

        if (phone.equals("")) {
            Toast.makeText(this, "请输入手机号", Toast.LENGTH_SHORT).show();
            return;
        }

        initData(name, phone);
    }

    private void initData(String name, String phone) {
        String userID = UserManager.getInstance(this).getUserInfo().getUserID();
        NetWorkUtil.setCallback("User/AddInvite", new PhoneNumberInviteBean(userID, name, phone), new NetWorkUtil.CallBackListener() {
            @Override
            public void onFinish(String respose) {
                ResultMessage message = new Gson().fromJson(respose, ResultMessage.class);
                if (message.getMessage().getCode().equals("200")) {
                    Toast.makeText(PhoneNumberInviteActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(PhoneNumberInviteActivity.this, message.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(String e) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImmersionBar.with(this).destroy();
    }
}
