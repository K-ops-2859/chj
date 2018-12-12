package com.dikai.chenghunjiclient.activity.store;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.activity.wedding.ShippingAddressActivity;
import com.dikai.chenghunjiclient.adapter.store.BoomRecordAdapter;
import com.dikai.chenghunjiclient.adapter.store.MyPrizeAdapter;
import com.dikai.chenghunjiclient.bean.BeanExchangePrize;
import com.dikai.chenghunjiclient.bean.BeanUserId;
import com.dikai.chenghunjiclient.entity.MyPrizeBean;
import com.dikai.chenghunjiclient.entity.ResultGetBoomRecord;
import com.dikai.chenghunjiclient.entity.ResultGetMyPrize;
import com.dikai.chenghunjiclient.entity.ResultMessage;
import com.dikai.chenghunjiclient.util.Constants;
import com.dikai.chenghunjiclient.util.EventBusBean;
import com.dikai.chenghunjiclient.util.NetWorkUtil;
import com.dikai.chenghunjiclient.util.UserManager;
import com.dikai.chenghunjiclient.view.MyLoadRecyclerView;
import com.flyco.dialog.widget.base.BaseDialog;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class MyPrizeActivity extends AppCompatActivity {

    private MyLoadRecyclerView mRecyclerView;
    private MyPrizeAdapter mAdapter;
    private ServiceDialog ruleDialog;
    private MyPrizeBean mPrizeBean;
    private int mPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_prize);
        EventBus.getDefault().register(this);
        ImmersionBar.with(this)
                .statusBarView(R.id.top_view)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true, 0.2f)
                .init();
        init();
    }

    private void init() {
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mRecyclerView = (MyLoadRecyclerView) findViewById(R.id.boom_record_recycler);
        mAdapter = new MyPrizeAdapter(this);
        mAdapter.setOnItemClickListener(new MyPrizeAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position, MyPrizeBean bean) {
                mPosition = position;
                mPrizeBean = bean;
                if(bean.getType() == 1){//实物
                    Intent intent = new Intent(MyPrizeActivity.this, ShippingAddressActivity.class);
                    intent.putExtra("type", 5);
                    intent.putExtra("id", bean.getId());
                    startActivity(intent);
                }else {//兑换
                    ruleDialog.show();
                }
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLoadChangeListener(new MyLoadRecyclerView.OnLoadChangeListener() {
            @Override
            public void onRefresh() {
                refresh();
            }

            @Override
            public void onLoadMore() {
            }
        });
        ruleDialog = new ServiceDialog(this);
        ruleDialog.widthScale(1);
        ruleDialog.heightScale(1);
        refresh();
    }

    private void refresh() {
        getCollection();
    }

    /**
     * 获取中奖列表
     */
    private void getCollection(){
        NetWorkUtil.setCallback("User/UserPopcornRecord",
                new BeanUserId(UserManager.getInstance(this).getUserInfo().getUserID()),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        mRecyclerView.stopLoad();
                        Log.e("返回值",respose);
                        try {
                            ResultGetMyPrize result = new Gson().fromJson(respose, ResultGetMyPrize.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                if(result.getData() == null || result.getData().size() == 0){
                                    mRecyclerView.setHasData(false);
                                }else {
                                    mRecyclerView.setHasData(true);
                                }
                                mAdapter.refresh(result.getData());
                            } else {
                                Toast.makeText(MyPrizeActivity.this, result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("json解析出错",e.toString());
                            //Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(final String e) {
                        mRecyclerView.stopLoad();
                        Toast.makeText(MyPrizeActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * 兑换
     */
    private void exchange(){
        NetWorkUtil.setCallback("User/UpdatePopcornPrizes",
                new BeanExchangePrize(mPrizeBean.getId()),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        Log.e("返回值",respose);
                        try {
                            ResultMessage result = new Gson().fromJson(respose, ResultMessage.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                ruleDialog.dismiss();
                                mPrizeBean.setIsUse(1);
                                mAdapter.itemChange(mPosition);
                                startActivity(new Intent(MyPrizeActivity.this, ExchangeActivity.class));
                            } else {
                                Toast.makeText(MyPrizeActivity.this, result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("json解析出错",e.toString());
                            //Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(final String e) {
                        Toast.makeText(MyPrizeActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     *rule
     */
    private class ServiceDialog extends BaseDialog<ServiceDialog> implements View.OnClickListener {
        private TextView close;
        private TextView getNow;

        ServiceDialog(Context context) {
            super(context);
        }
        @Override
        public View onCreateView() {
            View view = View.inflate(mContext, R.layout.dialog_get_my_prize, null);
            close = (TextView) view.findViewById(R.id.close);
            getNow = (TextView) view.findViewById(R.id.get_now);
            close.setOnClickListener(this);
            getNow.setOnClickListener(this);
            return view;
        }
        @Override
        public void onViewCreated(View view) {
            super.onViewCreated(view);
        }
        @Override
        public void setUiBeforShow() {}
        @Override
        public void onClick(View v) {
            if(v == close){
                this.dismiss();
            }else if(v == getNow){
                exchange();
            }
        }
    }

    /**
     * 事件总线刷新
     *
     * @param bean
     */
    @Subscribe
    public void onEventMainThread(final EventBusBean bean) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (bean.getType() == Constants.EXCHANGE_PRIZE_SUCCESS) {
                    mPrizeBean.setIsUse(1);
                    mAdapter.itemChange(mPosition);
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        ImmersionBar.with(this).destroy();
    }
}
