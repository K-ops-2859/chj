package com.dikai.chenghunjiclient.activity.invitation;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.adapter.OnItemClickListener;
import com.dikai.chenghunjiclient.adapter.invitation.ComInviteRecordAdapter;
import com.dikai.chenghunjiclient.bean.BeanInviteFR;
import com.dikai.chenghunjiclient.entity.InviteFRData;
import com.dikai.chenghunjiclient.util.NetWorkUtil;
import com.dikai.chenghunjiclient.util.UserManager;
import com.dikai.chenghunjiclient.view.MyLoadRecyclerView;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;

import java.util.List;

/**
 * Created by cmk03 on 2018/10/15.
 */

public class ComInviteRecordActivity extends AppCompatActivity {

    private int pageIndex = 1;
    private int pageCoutent = 20;
    private ComInviteRecordAdapter mAdapter;
    private MyLoadRecyclerView mRecyclerView;
    private String userId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cominvite_record);
        ImmersionBar.with(this)
                .statusBarView(R.id.top_view)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true, 0.2f)
                .init();

        initView();
    }

    private void initView() {
        Toolbar mToolBar = (Toolbar) findViewById(R.id.toolbar);
        mRecyclerView = (MyLoadRecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new ComInviteRecordAdapter(this);
        mRecyclerView.setAdapter(mAdapter);

        initData();

        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

//        mAdapter.setOnItemClickListener(new OnItemClickListener() {
//            @Override
//            public void onItemClick(View view, int position, Object o) {
//                startActivity(new Intent(ComInviteRecordActivity.this, VipInviteBusinessActivity.class));
//            }
//        });

        mRecyclerView.setLoadChangeListener(new MyLoadRecyclerView.OnLoadChangeListener() {
            @Override
            public void onRefresh() {
                mRecyclerView.startRefresh();
                initData();
            }

            @Override
            public void onLoadMore() {
                loadData();
            }
        });
    }

    private void initData() {
        pageIndex = 1;
        if (userId == null) {
            userId = UserManager.getInstance(this).getNewUserInfo().getUserId();
        }
        NetWorkUtil.setCallback("HQOAApi/GetHistoryInvitationRecord", new BeanInviteFR(userId, pageIndex, pageCoutent), new NetWorkUtil.CallBackListener() {
            @Override
            public void onFinish(String respose) {
                try {
                    InviteFRData data = new Gson().fromJson(respose, InviteFRData.class);
                    if (data.getMessage().getCode().equals("200")) {
                        List<InviteFRData.DataList> dataList = data.getData();
                        mAdapter.setList(dataList);
                        mRecyclerView.setFresh(false);
                    } else {
                        Toast.makeText(ComInviteRecordActivity.this, data.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Log.e("错误", e.toString());
                }
            }

            @Override
            public void onError(String e) {

            }
        });
    }

    private void loadData() {
        pageIndex++;
        NetWorkUtil.setCallback("HQOAApi/GetHistoryInvitationRecord", new BeanInviteFR(userId, pageIndex, pageCoutent), new NetWorkUtil.CallBackListener() {
            @Override
            public void onFinish(String respose) {
                try {
                    InviteFRData data = new Gson().fromJson(respose, InviteFRData.class);
                    if (data.getMessage().getCode().equals("200")) {
                        List<InviteFRData.DataList> dataList = data.getData();
                        mAdapter.setList(dataList);
                        mRecyclerView.stopLoad();
                    } else {
                        pageIndex--;
                        Toast.makeText(ComInviteRecordActivity.this, data.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Log.e("错误", e.toString());
                }
            }

            @Override
            public void onError(String e) {
                pageIndex--;
            }
        });
    }
}
