package com.dikai.chenghunjiclient.activity.wedding;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.adapter.wedding.AdapterTaocan;
import com.dikai.chenghunjiclient.bean.TaocanBean;
import com.dikai.chenghunjiclient.entity.TaocanData;
import com.dikai.chenghunjiclient.util.NetWorkUtil;
import com.dikai.chenghunjiclient.view.MyLoadRecyclerView;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;

/**
 * Created by cmk03 on 2018/6/8.
 */

public class WeddingTaocanActivity extends AppCompatActivity {

    private AdapterTaocan mAdapter;
    private MyLoadRecyclerView mRecyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taocan);

        ImmersionBar.with(this)
                .statusBarView(R.id.top_view)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true, 0.2f)
                .init();

        ImageView mBack = (ImageView) findViewById(R.id.back);
        mRecyclerView = (MyLoadRecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLinearLayout(new LinearLayoutManager(this));
        mAdapter = new AdapterTaocan(this);
        mRecyclerView.setAdapter(mAdapter);

        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        initData();
    }


    private void initData() {
        NetWorkUtil.setCallback("User/GetWeddingPackageList", new TaocanBean(0), new NetWorkUtil.CallBackListener() {
            @Override
            public void onFinish(String respose) {
                try {
                    TaocanData data = new Gson().fromJson(respose, TaocanData.class);
                    if (data.getMessage().getCode().equals("200")) {
                        mAdapter.setList(data.getData());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
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
