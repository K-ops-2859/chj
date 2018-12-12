package com.dikai.chenghunjiclient.activity.wedding;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.adapter.OnItemClickListener;
import com.dikai.chenghunjiclient.adapter.wedding.InvitePrizeAdapter;
import com.dikai.chenghunjiclient.bean.BeanPager;
import com.dikai.chenghunjiclient.entity.PrizeData;
import com.dikai.chenghunjiclient.util.NetWorkUtil;
import com.dikai.chenghunjiclient.view.MyLoadRecyclerView;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;

import java.util.List;

/**
 * Created by cmk03 on 2018/2/5.
 */

public class GiftListActivity extends AppCompatActivity {

    private int pagerIndex = 1;
    private int pagerCount = 100;
    private InvitePrizeAdapter mAdapter;
    private MyLoadRecyclerView mRecyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gift_list);
        ImmersionBar.with(this)
                .statusBarView(R.id.top_view)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true, 0.2f)
                .init();
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mRecyclerView = (MyLoadRecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setGridLayout(2);
        mAdapter = new InvitePrizeAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLoadChangeListener(new MyLoadRecyclerView.OnLoadChangeListener() {
            @Override
            public void onRefresh() {
                getListData();
            }

            @Override
            public void onLoadMore() {
                loadMore();
            }
        });
        mAdapter.setOnItemClickListener(new OnItemClickListener<PrizeData.PrizeDataList>() {
            @Override
            public void onItemClick(View view, int position, PrizeData.PrizeDataList prizeDataList) {
                Intent intent = new Intent(GiftListActivity.this, PrizeDetailsActivity.class);
                int activityPrizesID = prizeDataList.getActivityPrizesID();
                intent.putExtra("prizeId", activityPrizesID);
                startActivity(intent);
            }
        });
        getListData();
    }

    private void getListData() {
        pagerIndex = 1;
        pagerCount = 20;
        NetWorkUtil.setCallback("Corp/GetActivityPrizesList",
                new BeanPager(pagerIndex + "", pagerCount + ""),
                new NetWorkUtil.CallBackListener() {
            @Override
            public void onFinish(String respose) {
                mRecyclerView.stopLoad();
                PrizeData prizeData = new Gson().fromJson(respose, PrizeData.class);
                if ("200".equals(prizeData.getMessage().getCode())) {
                    List<PrizeData.PrizeDataList> data = prizeData.getData();
                    mAdapter.setList(data);
                }
            }

            @Override
            public void onError(String e) {
                mRecyclerView.stopLoad();
            }
        });
    }

    private void loadMore() {
        pagerIndex ++ ;
        NetWorkUtil.setCallback("Corp/GetActivityPrizesList",
                new BeanPager(pagerIndex + "", pagerCount + ""),
                new NetWorkUtil.CallBackListener() {
            @Override
            public void onFinish(String respose) {
                PrizeData prizeData = new Gson().fromJson(respose, PrizeData.class);
                if ("200".equals(prizeData.getMessage().getCode())) {
                    List<PrizeData.PrizeDataList> data = prizeData.getData();
                    mAdapter.setList(data);
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
