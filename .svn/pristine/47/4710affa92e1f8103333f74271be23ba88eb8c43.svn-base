package com.dikai.chenghunjiclient.activity.store;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.adapter.store.CoudanAdapter;
import com.dikai.chenghunjiclient.adapter.store.WedPrizeListAdapter;
import com.dikai.chenghunjiclient.bean.BeanGetTypeGood;
import com.dikai.chenghunjiclient.entity.ResultGetWedPrizeList;
import com.dikai.chenghunjiclient.entity.WedPrizeBean;
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
import java.util.Collections;
import java.util.List;

public class WedPrizeListActivity extends AppCompatActivity implements View.OnClickListener {

    private MyLoadRecyclerView mRecyclerView;
    private CoudanAdapter mAdapter;
    private String typeID;
    private String activityid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wed_prize_list);
        EventBus.getDefault().register(this);
        ImmersionBar.with(this)
                .statusBarView(R.id.top_view)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true, 0.2f)
                .init();
        init();
    }

    private void init() {
        activityid = getIntent().getStringExtra("activityid");
        TextView title = (TextView) findViewById(R.id.title);
        findViewById(R.id.back).setOnClickListener(this);
        mRecyclerView = (MyLoadRecyclerView) findViewById(R.id.recycler);
        mAdapter = new CoudanAdapter(this);
        mAdapter.setOnItemClickListener(new CoudanAdapter.OnItemClickListener() {
            @Override
            public void onClick(WedPrizeBean bean) {
                Intent intent = new Intent(WedPrizeListActivity.this, WedPrizeInfoActivity.class);
                intent.putExtra("prizeId",bean.getCommodityId());
                intent.putExtra("activityid",activityid);
                startActivity(intent);
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
        title.setText(getIntent().getStringExtra("title"));
        typeID = getIntent().getStringExtra("typeID");
        refresh();
    }

    private void refresh() {
        getCoudanList(typeID);
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
    private void getCoudanList(String typeID){
        NetWorkUtil.setCallback("HQOAApi/GetCommodityList",
                new BeanGetTypeGood("1",typeID),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        mRecyclerView.stopLoad();
                        Log.e("返回值",respose);
                        try {
                            ResultGetWedPrizeList result = new Gson().fromJson(respose, ResultGetWedPrizeList.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                mAdapter.refresh(result.getData());
                            } else {
                                Toast.makeText(WedPrizeListActivity.this, result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("json解析出错",e.toString());
                            //Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(final String e) {
                        mRecyclerView.stopLoad();
                        Toast.makeText(WedPrizeListActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
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
                 if(bean.getType() == Constants.CLEARCARTA_FINISH){
                    finish();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        ImmersionBar.with(this).destroy();
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
