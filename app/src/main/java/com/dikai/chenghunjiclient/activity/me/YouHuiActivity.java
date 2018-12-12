package com.dikai.chenghunjiclient.activity.me;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.adapter.me.YouHuiAdapter;
import com.dikai.chenghunjiclient.bean.BeanUserId;
import com.dikai.chenghunjiclient.entity.UserCouponListData;
import com.dikai.chenghunjiclient.util.NetWorkUtil;
import com.dikai.chenghunjiclient.util.UserManager;
import com.dikai.chenghunjiclient.view.MyLoadRecyclerView;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;

import java.util.List;

/**
 * Created by cmk03 on 2018/3/1.
 */

public class YouHuiActivity extends AppCompatActivity {

    private YouHuiAdapter mAdapter;
    private LinearLayout llNoYouHui;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youhui);
        ImmersionBar.with(this)
                .statusBarView(R.id.top_view)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true, 0.2f)
                .init();

        Toolbar mToolBar = (Toolbar) findViewById(R.id.toolbar);
        llNoYouHui = (LinearLayout) findViewById(R.id.ll_no_youhui);
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mAdapter = new YouHuiAdapter(this);

        mRecyclerView.setAdapter(mAdapter);

//        mRecyclerView.setLoadChangeListener(new MyLoadRecyclerView.OnLoadChangeListener() {
//            @Override
//            public void onRefresh() {
//                refresh();
//            }
//
//            @Override
//            public void onLoadMore() {
//
//            }
//        });
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        refresh();
    }

    private void refresh() {
        String userID = UserManager.getInstance(this).getUserInfo().getUserID();
        NetWorkUtil.setCallback("User/GetUserCouponList", new BeanUserId(userID), new NetWorkUtil.CallBackListener() {
            @Override
            public void onFinish(String respose) {
                UserCouponListData data = new Gson().fromJson(respose, UserCouponListData.class);
                if (data.getMessage().getCode().equals("200")) {
                    List<UserCouponListData.DataList> dataLists = data.getData();
                    if (dataLists.size() > 0) {
                        llNoYouHui.setVisibility(View.GONE);
                    } else {
                        llNoYouHui.setVisibility(View.VISIBLE);
                    }
                    mAdapter.setList(dataLists);
                }
            }

            @Override
            public void onError(String e) {

            }
        });
    }

    private void loadMore() {
        String userID = UserManager.getInstance(this).getUserInfo().getUserID();
        NetWorkUtil.setCallback("User/GetUserCouponList", new BeanUserId(userID), new NetWorkUtil.CallBackListener() {
            @Override
            public void onFinish(String respose) {
                UserCouponListData data = new Gson().fromJson(respose, UserCouponListData.class);
                if (data.getMessage().getCode().equals("200")) {
                    mAdapter.append(data.getData());
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
