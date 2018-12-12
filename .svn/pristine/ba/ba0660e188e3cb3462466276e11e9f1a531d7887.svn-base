package com.dikai.chenghunjiclient.activity.me;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.adapter.me.YouHuiAdapter;
import com.dikai.chenghunjiclient.bean.BeanUserId;
import com.dikai.chenghunjiclient.entity.UserWalletData;
import com.dikai.chenghunjiclient.util.NetWorkUtil;
import com.dikai.chenghunjiclient.util.UserManager;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;

/**
 * Created by cmk03 on 2018/2/26.
 */

public class MyWalletActivity extends AppCompatActivity {

    private YouHuiAdapter mAdapter;
    private TextView tvYouHuiNumber;
    private TextView tvYue;
    private UserWalletData walletData;
    private Toolbar mToolBar;
    private TextView tvTixian;
    private LinearLayout llYouHui;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mywallet);

        ImmersionBar.with(this)
                .statusBarView(R.id.top_view)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true, 0.2f)
                .init();
        mToolBar = (Toolbar) findViewById(R.id.toolbar);
        tvTixian = (TextView) findViewById(R.id.tv_tixian);
        llYouHui = (LinearLayout) findViewById(R.id.ll_youhui);
        tvYue = (TextView) findViewById(R.id.tv_yue);
        tvYouHuiNumber = (TextView) findViewById(R.id.tv_youhui_number);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();

        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tvTixian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (walletData.getBalance() != null) {
                    Intent intent = new Intent(MyWalletActivity.this, BalanceActivity.class);
                    Intent money = intent.putExtra("MONEY", walletData.getBalance());
                    startActivity(money);
                }
            }
        });

        llYouHui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MyWalletActivity.this, YouHuiActivity.class));
            }
        });
    }

    private void initData() {
        String userID = UserManager.getInstance(this).getUserInfo().getUserID();
        NetWorkUtil.setCallback("User/GetUserWallet", new BeanUserId(userID), new NetWorkUtil.CallBackListener() {
            @Override
            public void onFinish(String respose) {
                walletData = new Gson().fromJson(respose, UserWalletData.class);
                if (walletData.getMessage().getCode().equals("200")) {
                    tvYue.setText(walletData.getBalance() + "元");
                    tvYouHuiNumber.setText(walletData.getCouponCount() + "张");
                }
            }

            @Override
            public void onError(String e) {

            }
        });
//        NetWorkUtil.setCallback("User/GetAPList", new BeanUserId(userID), new NetWorkUtil.CallBackListener() {
//            @Override
//            public void onFinish(String respose) {
//                GetAPData data = new Gson().fromJson(respose, GetAPData.class);
//                if (data.getMessage().getCode().equals("200")) {
//                }
//            }
//
//            @Override
//            public void onError(String e) {
//
//            }
//        });
    }

    @Override
    protected void onDestroy() {
        ImmersionBar.with(this).destroy();
        super.onDestroy();
    }
}
