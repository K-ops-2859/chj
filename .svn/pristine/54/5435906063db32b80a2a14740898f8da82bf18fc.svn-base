package com.dikai.chenghunjiclient.activity.store;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.activity.me.ShareAppActivity;
import com.dikai.chenghunjiclient.activity.register.LoginActivity;
import com.dikai.chenghunjiclient.activity.wedding.FreeWedActivity;
import com.dikai.chenghunjiclient.activity.wedding.InviteWedActivity;
import com.dikai.chenghunjiclient.adapter.store.HomeFuliAdapter;
import com.dikai.chenghunjiclient.bean.BeanNone;
import com.dikai.chenghunjiclient.entity.ResultGetHomeFuli;
import com.dikai.chenghunjiclient.util.NetWorkUtil;
import com.dikai.chenghunjiclient.util.UserManager;
import com.dikai.chenghunjiclient.view.MyLoadRecyclerView;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;

public class HomeFuliActivity extends AppCompatActivity {

    private MyLoadRecyclerView mRecyclerView;
    private HomeFuliAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_fuli);
        ImmersionBar.with(this)
                .statusBarView(R.id.top_view)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true, 0.2f)
                .init();
        init();
    }

    private void init() {
        mRecyclerView = (MyLoadRecyclerView) findViewById(R.id.fuli_list);
        mAdapter = new HomeFuliAdapter(this);
        mAdapter.setOnItemClickListener(new HomeFuliAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                if (UserManager.getInstance(HomeFuliActivity.this).isLogin()) {
                    if(position == 0){
                        startActivity(new Intent(HomeFuliActivity.this, InviteWedActivity.class));
                    }else if(position == 1){
                        startActivity(new Intent(HomeFuliActivity.this, FreeWedActivity.class));
                    }else {
                        startActivity(new Intent(HomeFuliActivity.this, ShareAppActivity.class));
                    }
                } else {
                    startActivity(new Intent(HomeFuliActivity.this, LoginActivity.class));
                }
            }
        });

        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLoadChangeListener(new MyLoadRecyclerView.OnLoadChangeListener() {
            @Override
            public void onRefresh() {
                mRecyclerView.stopLoad();
                getFuli();
            }

            @Override
            public void onLoadMore() {
            }
        });
        getFuli();
    }

    /**
     * 获取福利列表
     */
    private void getFuli() {
        NetWorkUtil.setCallback("User/GetWeChatActivityList",
                new BeanNone(),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(String respose) {
                        Log.e("返回值",respose);
                        try {
                            ResultGetHomeFuli result = new Gson().fromJson(respose, ResultGetHomeFuli.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                mAdapter.refresh(result.getData());
                            } else {
                                Toast.makeText(HomeFuliActivity.this, result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("json解析出错",e.toString());
                            //Toast.makeText(HomeFuliActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onError(String e) {}
                });

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImmersionBar.with(this).destroy();
    }
}
