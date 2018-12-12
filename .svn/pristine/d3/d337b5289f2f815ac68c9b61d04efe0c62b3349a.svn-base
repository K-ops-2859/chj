package com.dikai.chenghunjiclient.activity.store;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.adapter.store.FanhuanAreaAdpater;
import com.dikai.chenghunjiclient.bean.BeanIdentityId;
import com.dikai.chenghunjiclient.entity.IntervalAmountParamData;
import com.dikai.chenghunjiclient.util.NetWorkUtil;
import com.dikai.chenghunjiclient.util.UserManager;
import com.dikai.chenghunjiclient.view.MyLoadRecyclerView;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;

public class ProfitAreaActivity extends AppCompatActivity implements View.OnClickListener {

    private MyLoadRecyclerView mRecyclerView;
    private FanhuanAreaAdpater mAdpater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profit_area);
        ImmersionBar.with(this)
                .statusBarView(R.id.top_view)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true, 0.2f)
                .init();
        init();
    }

    private void init() {
        findViewById(R.id.back).setOnClickListener(this);
        mRecyclerView = (MyLoadRecyclerView) findViewById(R.id.recycler);
        mRecyclerView.setLoadChangeListener(new MyLoadRecyclerView.OnLoadChangeListener() {
            @Override
            public void onRefresh() {
                refresh();
            }

            @Override
            public void onLoadMore() {}
        });
        mAdpater = new FanhuanAreaAdpater(this);
        mRecyclerView.setAdapter(mAdpater);
        refresh();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                onBackPressed();
                break;
        }
    }

    public void refresh() {
        getArea();
    }

    /**
     * 获取返还区间
     */
    private void getArea() {
        String identity = UserManager.getInstance(this).getNewUserInfo().getIdentity();
        NetWorkUtil.setCallback("HQOAApi/GetIntervalAmountParamList",
                new BeanIdentityId(identity), new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(String respose) {
                        mRecyclerView.stopLoad();
                        try {
                            Log.e("返回值",respose);
                            IntervalAmountParamData data = new Gson().fromJson(respose, IntervalAmountParamData.class);
                            if ("200".equals(data.getMessage().getCode())) {
                                mAdpater.refresh(data.getData());
                            }else {
                                Log.e("====", data.getMessage().getInform());
                                Toast.makeText(ProfitAreaActivity.this, data.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("错误", e.toString());
                        }
                    }
                    @Override
                    public void onError(String e) {
                        mRecyclerView.stopLoad();
                    }
                });
    }

    @Override
    protected void onDestroy() {
        ImmersionBar.with(this).destroy();
        super.onDestroy();
    }
}
