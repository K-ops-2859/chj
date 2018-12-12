package com.dikai.chenghunjiclient.activity.discover;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.adapter.discover.MessageDetailsAdapter;
import com.dikai.chenghunjiclient.bean.MessageInfoBean;
import com.dikai.chenghunjiclient.entity.MessageData;
import com.dikai.chenghunjiclient.entity.MessageInfoData;
import com.dikai.chenghunjiclient.util.NetWorkUtil;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * Created by cmk03 on 2018/1/26.
 */

public class MessageActivity extends AppCompatActivity {

    private MessageDetailsAdapter mAdapter;
    private TextView tvTitle;
    private TextView tvShouCang;
    private TextView tvDesc;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        ImmersionBar.with(this)
                .statusBarView(R.id.top_view)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true, 0.2f)
                .init();
        Toolbar mToolBar = (Toolbar) findViewById(R.id.toolbar);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvShouCang = (TextView) findViewById(R.id.tv_shoucang);
        tvDesc = (TextView) findViewById(R.id.tv_desc);
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setNestedScrollingEnabled(false);
        mAdapter = new MessageDetailsAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        initData();
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void setData(MessageInfoData data) {
        tvTitle.setText(data.getTitle());
        tvDesc.setText(data.getContent());
    }

    private void initData() {
         MessageData.DataList data = (MessageData.DataList) getIntent().getSerializableExtra("data");
        NetWorkUtil.setCallback("User/GetInformationArticleInfo", new MessageInfoBean(String.valueOf(data.getInformationArticleID())), new NetWorkUtil.CallBackListener() {
            @Override
            public void onFinish(String respose) {
                MessageInfoData infoData = new Gson().fromJson(respose, MessageInfoData.class);
                if (infoData.getMessage().getCode().equals("200")) {
                    setData(infoData);
                    String[] split = infoData.getImgs().split(",");
                    List<String> list = Arrays.asList(split);
                    mAdapter.setList(list);
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
