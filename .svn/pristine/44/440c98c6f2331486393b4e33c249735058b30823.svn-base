package com.dikai.chenghunjiclient.activity.me;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.adapter.me.WalletAccountAdapter;
import com.dikai.chenghunjiclient.bean.BeanFacilitatorID;
import com.dikai.chenghunjiclient.entity.NewUserInfo;
import com.dikai.chenghunjiclient.entity.ResultGetWalletAccount;
import com.dikai.chenghunjiclient.entity.WalletAccountBean;
import com.dikai.chenghunjiclient.util.Constants;
import com.dikai.chenghunjiclient.util.EventBusBean;
import com.dikai.chenghunjiclient.util.NetWorkUtil;
import com.dikai.chenghunjiclient.util.UserManager;
import com.dikai.chenghunjiclient.view.MyLoadRecyclerView;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

public class WithdrawActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView withdraw;
    private EditText withdrawNum;
    private MyLoadRecyclerView mRecyclerView;
    private WalletAccountAdapter mAdapter;
    private WalletAccountBean nowBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdraw);
        EventBus.getDefault().register(this);
        ImmersionBar.with(this)
                .statusBarView(R.id.top_view)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true, 0.2f)
                .init();
        init();
    }

    private void init() {
        findViewById(R.id.back).setOnClickListener(this);
        withdraw = (TextView) findViewById(R.id.withdraw);
        withdraw.setOnClickListener(this);
        withdrawNum = (EditText) findViewById(R.id.withdraw_num);
        mRecyclerView = (MyLoadRecyclerView) findViewById(R.id.recycler);
        mRecyclerView.setLoadChangeListener(new MyLoadRecyclerView.OnLoadChangeListener() {
            @Override
            public void onRefresh() {
                getAccount();
            }

            @Override
            public void onLoadMore() {}
        });

        mAdapter = new WalletAccountAdapter(this);
        mAdapter.setOnItemClickListener(new WalletAccountAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position, WalletAccountBean bean) {
                if(bean.getSourceType() == 1){//支付宝
                    if(bean.getIsValid() == 0){//可用
                        mAdapter.setSelectPosition(position);
                        setGetCodeUse();
                        nowBean = bean;
                    }else {//不可用
                        setGetCodeUnUse();
                        mAdapter.setSelectPosition(-1);
                        startActivity(new Intent(WithdrawActivity.this,AddAliPayActivity.class));
                    }
                }else {//银行卡
                    if(bean.getIsValid() == 0){//可用
                        mAdapter.setSelectPosition(position);
                        setGetCodeUse();
                        nowBean = bean;
                    }else {//不可用
                        setGetCodeUnUse();
                        mAdapter.setSelectPosition(-1);
                        startActivity(new Intent(WithdrawActivity.this,WalletAddCardActivity.class));
                    }
                }
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        getAccount();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                onBackPressed();
                break;
            case R.id.withdraw:
                if(withdrawNum.getText() == null || "".equals(withdrawNum.getText().toString())){
                    Toast.makeText(this, "请输入提现金额", Toast.LENGTH_SHORT).show();
                }else {
                    Bundle bundle = new Bundle();
                    bundle.putString("money",withdrawNum.getText().toString());
                    bundle.putSerializable("bean",nowBean);
                    startActivity(new Intent(this,AliPayWithDrawActivity.class).putExtras(bundle));
                }
                break;
        }
    }

    /**
     * 获取账户列表
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
        NetWorkUtil.setCallback("HQOAApi/GetFacilitatorAccountNumberList",
                new BeanFacilitatorID(supId,type),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        mRecyclerView.stopLoad();
                        Log.e("返回值",respose);
                        try {
                            ResultGetWalletAccount result = new Gson().fromJson(respose, ResultGetWalletAccount.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                List<WalletAccountBean> list = new ArrayList<>();
                                list.add(new WalletAccountBean(result.getNumber(),result.getId(),result.getAccountName(),1,"",result.getAlipayType()));
                                list.addAll(result.getData());
                                list.add(new WalletAccountBean("","","",0,"",1));
                                mAdapter.refresh(list);
                            } else {
                                Toast.makeText(WithdrawActivity.this, result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("json解析出错",e.toString());
                            //Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(final String e) {
                        mRecyclerView.stopLoad();
                        Toast.makeText(WithdrawActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    //设置按键不可用
    public void setGetCodeUnUse(){
        withdraw.setClickable(false);
        withdraw.setBackgroundResource(R.drawable.text_bg_gray_new);
        withdraw.setTextColor(ContextCompat.getColor(this,R.color.gray_text));
        withdraw.setEnabled(false);
    }
    //恢复按键可用
    public void setGetCodeUse(){
        withdraw.setClickable(true);
        withdraw.setBackgroundResource(R.drawable.text_bg_black_all);
        withdraw.setTextColor(ContextCompat.getColor(this,R.color.gold));
        withdraw.setEnabled(true);
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
