package com.dikai.chenghunjiclient.activity.me;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.bean.BeanApplicationPresentation;
import com.dikai.chenghunjiclient.entity.ResultMessage;
import com.dikai.chenghunjiclient.util.ActivityManager;
import com.dikai.chenghunjiclient.util.NetWorkUtil;
import com.dikai.chenghunjiclient.util.UserManager;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;
import com.jakewharton.rxbinding.widget.RxCompoundButton;

import rx.functions.Action1;
/**
 * Created by cmk03 on 2018/2/26.
 */

public class TiXianActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView[] textViews;
    private TextView tvMoney1;
    private TextView tvMoney2;
    private TextView tvMoney3;
    private TextView tvMoney4;
    private TextView tvMoney5;
    private boolean isClick = false;
    private TextView tvTiXian;
    private CheckBox mCheckBox;
    private final int REQUSET = 100;
    private final int RESULT_OK = 101;
    private TextView tvNumber;
    private SharedPreferences sp;
    private String money;
    private double doubleMoney;
    private int[] moneys;
    private int JinE;
    private String zhifubao;
    private int num;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tixian);
        ImmersionBar.with(this)
                .statusBarView(R.id.top_view)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true, 0.2f)
                .init();

        Toolbar mToolBar = (Toolbar) findViewById(R.id.toolbar);
        FrameLayout flZhifubao = (FrameLayout) findViewById(R.id.fl_zhifubao);
        tvMoney1 = (TextView) findViewById(R.id.tv_money_1);
        tvMoney2 = (TextView) findViewById(R.id.tv_money_2);
        tvMoney3 = (TextView) findViewById(R.id.tv_money_3);
        tvMoney4 = (TextView) findViewById(R.id.tv_money_4);
        tvMoney5 = (TextView) findViewById(R.id.tv_money_5);
        tvTiXian = (TextView) findViewById(R.id.tv_tixian);
        mCheckBox = (CheckBox) findViewById(R.id.checkbox);
        tvNumber = (TextView) findViewById(R.id.tv_number);

        sp = getSharedPreferences("accountCode", MODE_PRIVATE);
        zhifubao = sp.getString("accountCode", "输入您的支付宝账号");
        tvNumber.setText(zhifubao);
        textViews = new TextView[5];
        textViews[0] = tvMoney1;
        textViews[1] = tvMoney2;
        textViews[2] = tvMoney3;
        textViews[3] = tvMoney4;
        textViews[4] = tvMoney5;
        moneys = new int[5];
        moneys[0] = 10;
        moneys[1] = 20;
        moneys[2] = 30;
        moneys[3] = 50;
        moneys[4] = 100;
        money = getIntent().getStringExtra("money");
        try {
            doubleMoney = Double.parseDouble(money);
        } catch (Exception e) {
            e.toString();
        }
        flZhifubao.setOnClickListener(this);
        tvMoney1.setOnClickListener(this);
        tvMoney2.setOnClickListener(this);
        tvMoney3.setOnClickListener(this);
        tvMoney4.setOnClickListener(this);
        tvMoney5.setOnClickListener(this);
        tvTiXian.setOnClickListener(this);

        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        RxCompoundButton.checkedChanges(mCheckBox).subscribe(new Action1<Boolean>() {
            @Override
            public void call(Boolean aBoolean) {
                tvTiXian.setBackgroundResource(aBoolean ? R.drawable.bg_btn_pink_deep : R.drawable.bg_btn_gray_deep);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        mCheckBox.setChecked(false);
        isClick = false;
        num = 0;
        for (TextView textView : textViews) {
            textView.setBackgroundResource(R.drawable.bg_gray_border);
            textView.setTextColor(Color.BLACK);
        }
    }

    private int isChecked(TextView textView) {
        num = 0;
        for (int i = 0; i < textViews.length; i++) {
            if (textViews[i] == textView) {
                mCheckBox.setChecked(true);
                isClick = true;
                num = moneys[i];
                textView.setBackgroundResource(R.drawable.bg_btn_pink_solid);
                textView.setTextColor(Color.WHITE);
                for (int j = i + 1; j < textViews.length; j++) {
                    textViews[j].setBackgroundResource(R.drawable.bg_gray_border);
                    textViews[j].setTextColor(Color.BLACK);
                }

                for (int k = 0; k < i; k++) {
                    textViews[k].setBackgroundResource(R.drawable.bg_gray_border);
                    textViews[k].setTextColor(Color.BLACK);
                }
            }
        }
        return num;
    }

    private void initData(String money, String accountCode, String accountType) {
        String userID = UserManager.getInstance(this).getUserInfo().getUserID();
        NetWorkUtil.setCallback("User/ApplicationPresentation", new BeanApplicationPresentation(userID, money, accountCode, accountType), new NetWorkUtil.CallBackListener() {
            @Override
            public void onFinish(String respose) {
                ResultMessage message = new Gson().fromJson(respose, ResultMessage.class);
                if (message.getMessage().getCode().equals("200")) {
                    ActivityManager.getInstance().addActivity(TiXianActivity.this);
                    startActivity(new Intent(TiXianActivity.this, TiXianCompleteActivity.class));
                } else {
                    Toast.makeText(TiXianActivity.this, message.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(String e) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUSET && resultCode == RESULT_OK) {
            zhifubao = data.getExtras().getString("result");
            if (!TextUtils.isEmpty(zhifubao)) {
                tvNumber.setText(zhifubao);
                sp.edit().putString("accountCode", zhifubao).apply();
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fl_zhifubao:
                Intent intent = new Intent(this, EditZhiActivity.class);
                startActivityForResult(intent, REQUSET);
                break;
            case R.id.tv_money_1:
                if (doubleMoney < 10) {
                    Toast.makeText(this, "您的余额不足10元", Toast.LENGTH_SHORT).show();
                } else {
                    JinE = isChecked(tvMoney1);
                }
                break;
            case R.id.tv_money_2:
                if (doubleMoney < 20) {
                    Toast.makeText(this, "您的余额不足20元", Toast.LENGTH_SHORT).show();
                } else {
                    JinE = isChecked(tvMoney2);
                }
                break;
            case R.id.tv_money_3:
                if (doubleMoney < 30) {
                    Toast.makeText(this, "您的余额不足30元", Toast.LENGTH_SHORT).show();
                } else {
                    JinE = isChecked(tvMoney3);
                }
                break;
            case R.id.tv_money_4:
                if (doubleMoney < 50) {
                    Toast.makeText(this, "您的余额不足50元", Toast.LENGTH_SHORT).show();
                } else {
                    JinE = isChecked(tvMoney4);
                }
                break;
            case R.id.tv_money_5:
                if (doubleMoney < 100) {
                    Toast.makeText(this, "您的余额不足100元", Toast.LENGTH_SHORT).show();
                } else {
                    JinE = isChecked(tvMoney5);
                }
                break;
            case R.id.tv_tixian:
                if (!TextUtils.isEmpty(zhifubao)) {
                    if (mCheckBox.isChecked()) {
                        initData(JinE + "", zhifubao, "1");
                    } else {
                        Toast.makeText(this, "请选择提现金额", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "请输入支付宝账号", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        ImmersionBar.with(this).destroy();
        super.onDestroy();
    }
}
