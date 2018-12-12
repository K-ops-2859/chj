package com.dikai.chenghunjiclient.activity.me;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.bean.BeanGetWalletInfo;
import com.dikai.chenghunjiclient.entity.NewUserInfo;
import com.dikai.chenghunjiclient.entity.ResultGetWalletInfo;
import com.dikai.chenghunjiclient.util.Constants;
import com.dikai.chenghunjiclient.util.EventBusBean;
import com.dikai.chenghunjiclient.util.NetWorkUtil;
import com.dikai.chenghunjiclient.util.UserManager;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class WalletActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView balance;
    private TextView cardNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        setContentView(R.layout.activity_wallet);
        ImmersionBar.with(this)
                .statusBarView(R.id.top_view)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true, 0.2f)
                .init();
        init();
    }

    private void init() {
        findViewById(R.id.back).setOnClickListener(this);
        findViewById(R.id.record).setOnClickListener(this);
        findViewById(R.id.withdraw).setOnClickListener(this);
        findViewById(R.id.my_card).setOnClickListener(this);
        ImageView bg = (ImageView) findViewById(R.id.wallet_bg);
        balance = (TextView) findViewById(R.id.balance);
        cardNum = (TextView) findViewById(R.id.card_num);
        Glide.with(this).load(R.drawable.ic_app_wallet_bg).into(bg);
        getAccount();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                onBackPressed();
                break;
            case R.id.record:
                startActivity(new Intent(this,WalletRecordActivity.class));
                break;
            case R.id.withdraw:
                startActivity(new Intent(this,WithdrawActivity.class));
                break;
            case R.id.my_card:
                startActivity(new Intent(this,WalletCardActivity.class));
                break;
        }
    }

    private void setData(ResultGetWalletInfo result){
        balance.setText(result.getBalance());
        cardNum.setText("x " + result.getAccountNumber());
    }

    /**
     * 获取账号信息
     */
    private void getAccount(){
        NewUserInfo userInfo = UserManager.getInstance(this).getNewUserInfo();
        String supId;
        int type;
        if("70CD854E-D943-4607-B993-91912329C61E".equals(userInfo.getProfession().toUpperCase())){
            supId = userInfo.getUserId();
            type = 1;
        }else {
            supId = userInfo.getFacilitatorId();
            type = 2;
        }
        NetWorkUtil.setCallback("HQOAApi/GetFacilitatorMoneyFlowingWaterList",
                new BeanGetWalletInfo(1,1,supId,type),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        Log.e("返回值",respose);
                        try {
                            ResultGetWalletInfo result = new Gson().fromJson(respose, ResultGetWalletInfo.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                setData(result);
                            } else {
                                Toast.makeText(WalletActivity.this, result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("json解析出错",e.toString());
                            //Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(final String e) {
                        Toast.makeText(WalletActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * 事件总线刷新
     * @param bean
     */
    @Subscribe
    public void onEventMainThread(final EventBusBean bean) {
        runOnUiThread(new Runnable() {
            public void run() {
                if(bean.getType() == Constants.ADD_ALIPAY_ACCOUNT ||
                        bean.getType() == Constants.ADD_BLANK_ACCOUNT){
                    getAccount();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        ImmersionBar.with(this).destroy();
        super.onDestroy();
    }
}
