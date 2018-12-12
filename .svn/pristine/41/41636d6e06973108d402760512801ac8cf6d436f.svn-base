package com.dikai.chenghunjiclient.activity.me;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.activity.store.WedPrizeInfoActivity;
import com.dikai.chenghunjiclient.adapter.me.MyOrderAdapter;
import com.dikai.chenghunjiclient.bean.BeanUserId;
import com.dikai.chenghunjiclient.entity.MyOrderBean;
import com.dikai.chenghunjiclient.entity.ResultGetMyOrder;
import com.dikai.chenghunjiclient.entity.ResultGetOrder;
import com.dikai.chenghunjiclient.util.NetWorkUtil;
import com.dikai.chenghunjiclient.util.UserManager;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;

public class MyOrderActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView mRecyclerView;
    private MyOrderAdapter mAdapter;
    private SwipeRefreshLayout fresh;
    private LinearLayout placeLayout;
    private LinearLayout mCardView;
    private TextView orderInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);
        ImmersionBar.with(this)
                .statusBarView(R.id.top_view)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true, 0.2f)
                .init();
        init();
    }

    private void init() {
        findViewById(R.id.back).setOnClickListener(this);
        orderInfo = (TextView) findViewById(R.id.order_info);
        placeLayout = (LinearLayout) findViewById(R.id.place_layout);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler);
        mCardView = (LinearLayout) findViewById(R.id.bottom_layout);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new MyOrderAdapter(this);
        mAdapter.setOnItemClickListener(new MyOrderAdapter.OnItemClickListener() {
            @Override
            public void onClick(MyOrderBean bean) {
                Intent intent = new Intent(MyOrderActivity.this, WedPrizeInfoActivity.class);
                intent.putExtra("prizeId",bean.getCommodityId());
                startActivity(intent);
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        fresh = (SwipeRefreshLayout) findViewById(R.id.my_load_recycler_fresh);
        //设置刷新时动画的颜色，可以设置4个
        fresh.setColorSchemeResources(R.color.main);
        fresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fresh.post(new Runnable() {
                    @Override
                    public void run() {
                        fresh.setRefreshing(true);
                    }
                });
                refresh();
            }
        });
        refresh();
    }

    private void refresh() {
        getCoudanList();
    }
    public void stopLoad(){
        if(fresh.isRefreshing()){
            fresh.post(new Runnable() {
                @Override
                public void run() {
                    fresh.setRefreshing(false);
                }
            });
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                onBackPressed();
                break;
        }
    }

    /**
     * 获取凑单列表
     */
    private void getCoudanList(){
        NetWorkUtil.setCallback("HQOAApi/GetShoppingOrderList",
                new BeanUserId(UserManager.getInstance(this).getNewUserInfo().getUserId()),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        stopLoad();
                        Log.e("返回值",respose);
                        try {
                            ResultGetMyOrder result = new Gson().fromJson(respose, ResultGetMyOrder.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                mAdapter.refresh(result.getData());
                                if(result.getData() == null || result.getData().size() == 0){
                                    mCardView.setVisibility(View.GONE);
                                    placeLayout.setVisibility(View.VISIBLE);
                                }else {
                                    mCardView.setVisibility(View.VISIBLE);
                                    placeLayout.setVisibility(View.GONE);
                                    MyOrderBean myOrderBean = result.getData().get(0);
                                    String info = "收货人："+myOrderBean.getConsignee()+" " +
                                            myOrderBean.getConsigneePhone()+"\n收货地址：" + myOrderBean.getAddress();
                                    orderInfo.setText(info);
                                }
                            } else {
                                Toast.makeText(MyOrderActivity.this, result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("json解析出错",e.toString());
                            //Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(final String e) {
                        stopLoad();
                        Toast.makeText(MyOrderActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    protected void onDestroy() {
        ImmersionBar.with(this).destroy();
        super.onDestroy();
    }
}
