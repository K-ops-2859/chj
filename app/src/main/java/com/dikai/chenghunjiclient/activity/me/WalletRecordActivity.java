package com.dikai.chenghunjiclient.activity.me;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.adapter.me.WithdrawRecordAdapter;
import com.dikai.chenghunjiclient.bean.BeanGetWalletInfo;
import com.dikai.chenghunjiclient.entity.NewUserInfo;
import com.dikai.chenghunjiclient.entity.ResultGetWalletInfo;
import com.dikai.chenghunjiclient.util.NetWorkUtil;
import com.dikai.chenghunjiclient.util.UserManager;
import com.dikai.chenghunjiclient.view.MyLoadRecyclerView;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;

public class WalletRecordActivity extends AppCompatActivity implements View.OnClickListener {

    private MyLoadRecyclerView mRecyclerView;
    private WithdrawRecordAdapter mAdapter;
    private int pageIndex = 1;
    private int itemCount = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_record);
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
            public void onLoadMore() {
                pageIndex++;
                getList(pageIndex,itemCount,false);

            }
        });
        mAdapter = new WithdrawRecordAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        refresh();
    }

    public void refresh() {
        pageIndex = 1;
        itemCount = 20;
        getList(pageIndex,itemCount,true);
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
     * 获取明细记录
     */
    private void getList(int pageIndex, int pageCount, final boolean isRefresh){
        NewUserInfo userInfo = UserManager.getInstance(this).getNewUserInfo();
        String supId;
        int type;
        if("70CD854E-D943-4607-B993-91912329C61E".equals(userInfo.getProfession().toUpperCase())){
            supId = userInfo.getUserId();
            type = 1;
        }else {
            supId = userInfo.getFacilitatorId();
            type = 2;
        }
        NetWorkUtil.setCallback("HQOAApi/GetFacilitatorMoneyFlowingWaterList",
                new BeanGetWalletInfo(pageIndex,pageCount, supId,type),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        Log.e("返回值",respose);
                        mRecyclerView.stopLoad();
                        try {
                            ResultGetWalletInfo result = new Gson().fromJson(respose, ResultGetWalletInfo.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                mRecyclerView.setTotalCount(result.getTotalCount());
                                if(isRefresh){
                                    mAdapter.refresh(result.getData());
                                    if(result.getData().size() > 0){
                                        mRecyclerView.setHasData(true);
                                    }else {
                                        mRecyclerView.setHasData(false);
                                    }
                                }else {
                                    mAdapter.addAll(result.getData());
                                }
                            } else {
                                Toast.makeText(WalletRecordActivity.this, result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("json解析出错",e.toString());
                            //Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(final String e) {
                        mRecyclerView.stopLoad();
                        Toast.makeText(WalletRecordActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
                    }
                });
    }


    @Override
    protected void onDestroy() {
        ImmersionBar.with(this).destroy();
        super.onDestroy();
    }
}
