package com.dikai.chenghunjiclient.activity.me;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.adapter.me.TiXianListAdapter;
import com.dikai.chenghunjiclient.bean.BeanUserId;
import com.dikai.chenghunjiclient.entity.GetAPData;
import com.dikai.chenghunjiclient.util.ActivityManager;
import com.dikai.chenghunjiclient.util.NetWorkUtil;
import com.dikai.chenghunjiclient.util.UserManager;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;

/**
 * Created by cmk03 on 2018/3/2.
 */

public class TiXianListActivity extends AppCompatActivity {

    private TiXianListAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tixian_list);

        ImmersionBar.with(this)
                .statusBarView(R.id.top_view)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true, 0.2f)
                .init();

        Toolbar mToolBar = (Toolbar) findViewById(R.id.toolbar);
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mAdapter = new TiXianListAdapter(this);
        mRecyclerView.setAdapter(mAdapter);

        initData();

        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityManager.getInstance().finishAll();
                finish();
            }
        });
    }

    private void initData() {
        String userID = UserManager.getInstance(this).getUserInfo().getUserID();
        NetWorkUtil.setCallback("User/GetAPList", new BeanUserId(userID), new NetWorkUtil.CallBackListener() {
            @Override
            public void onFinish(String respose) {
                GetAPData data = new Gson().fromJson(respose, GetAPData.class);
                if (data.getMessage().getCode().equals("200")) {
                    mAdapter.setList(data.getData());
                }
            }

            @Override
            public void onError(String e) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        ImmersionBar.with(this).destroy();
        super.onDestroy();
    }
}
