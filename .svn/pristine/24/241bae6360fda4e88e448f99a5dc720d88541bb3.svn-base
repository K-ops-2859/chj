package com.dikai.chenghunjiclient.activity.wedding;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.adapter.wedding.PhoneInviteAdapter;
import com.dikai.chenghunjiclient.bean.InvitePhoneBean;
import com.dikai.chenghunjiclient.entity.InvitePhoneData;
import com.dikai.chenghunjiclient.util.NetWorkUtil;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

/**
 * Created by cmk03 on 2017/12/4.
 */

public class PhoneInviteActivity extends AppCompatActivity {

    private int pageIndex = 1;
    private int pageCount = 20;
    private PhoneInviteAdapter mAdapter;
    private XRecyclerView mRecyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_invite);

        ImmersionBar.with(this)
                .statusBarView(R.id.top_view)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true, 0.2f)
                .init();

        ImageView mBack = (ImageView) findViewById(R.id.activity_zone_back);
        LinearLayout llInvitePhone = (LinearLayout) findViewById(R.id.ll_invite_phone);
        mRecyclerView = (XRecyclerView) findViewById(R.id.recycler_view);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
       // View view = LayoutInflater.from(this).inflate(R.layout.layout_default_loading, null);

        mAdapter = new PhoneInviteAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setPullRefreshEnabled(false);

        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        llInvitePhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PhoneInviteActivity.this, PhoneNumberInviteActivity.class));
            }
        });

        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {

            }

            @Override
            public void onLoadMore() {
                loadMore();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    private void initData() {
        pageIndex = 1;
        NetWorkUtil.setCallback("User/GetInviteList", new InvitePhoneBean(pageIndex + "", pageCount + "", 0+""), new NetWorkUtil.CallBackListener() {
            @Override
            public void onFinish(String respose) {
                InvitePhoneData data = new Gson().fromJson(respose, InvitePhoneData.class);
                mAdapter.setList(data.getData());
            }

            @Override
            public void onError(String e) {

            }
        });
    }

    private void loadMore() {
        pageIndex++;
        NetWorkUtil.setCallback("User/GetInviteList", new InvitePhoneBean(pageIndex + "", pageCount + "", 0+""), new NetWorkUtil.CallBackListener() {
            @Override
            public void onFinish(String respose) {
                InvitePhoneData data = new Gson().fromJson(respose, InvitePhoneData.class);
                mAdapter.append(data.getData());
                mRecyclerView.loadMoreComplete();
            }

            @Override
            public void onError(String e) {

            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ImmersionBar.with(this).destroy();
    }
}
