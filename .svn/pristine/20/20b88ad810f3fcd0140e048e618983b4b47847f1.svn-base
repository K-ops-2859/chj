package com.dikai.chenghunjiclient.activity.me;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.bean.BeanUserId;
import com.dikai.chenghunjiclient.entity.UserWalletData;
import com.dikai.chenghunjiclient.util.NetWorkUtil;
import com.dikai.chenghunjiclient.util.UserManager;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;

/**
 * Created by cmk03 on 2018/2/26.
 */

public class BalanceActivity extends AppCompatActivity {

    private TextView tvMoney;
    private String money;
    private TextView tvTixian;
    private Toolbar mToolBar;
    private TextView tvTitle;
    private String userID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_balance);
        ImmersionBar.with(this)
                .statusBarView(R.id.top_view)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true, 0.2f)
                .init();

        mToolBar = (Toolbar) findViewById(R.id.toolbar);
        tvMoney = (TextView) findViewById(R.id.tv_money);
        tvTixian = (TextView) findViewById(R.id.tv_tixian);
        tvTitle = (TextView) findViewById(R.id.tv_title);

//        money = getIntent().getStringExtra("MONEY");
//        tvMoney.setText(money + "元");
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
        tvTixian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(money)) {
                    Intent intent = new Intent(BalanceActivity.this, TiXianActivity.class);
                    Intent money1 = intent.putExtra("money", money);
                    startActivity(money1);
                }
            }
        });

        tvTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BalanceActivity.this, TiXianListActivity.class));
            }
        });
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initData() {
        if (userID == null) {
            userID = UserManager.getInstance(this).getUserInfo().getUserID();
        }
        NetWorkUtil.setCallback("User/GetUserWallet", new BeanUserId(userID), new NetWorkUtil.CallBackListener() {
            @Override
            public void onFinish(String respose) {
                UserWalletData walletData = new Gson().fromJson(respose, UserWalletData.class);
                if (walletData.getMessage().getCode().equals("200")) {
                    money = walletData.getBalance();
                    tvMoney.setText(walletData.getBalance() + "元");
                }
            }

            @Override
            public void onError(String e) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        ImmersionBar.with(this).destroy();
        super.onDestroy();
    }
}
