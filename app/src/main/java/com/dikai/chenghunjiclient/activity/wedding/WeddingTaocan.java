package com.dikai.chenghunjiclient.activity.wedding;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.activity.register.LoginActivity;
import com.dikai.chenghunjiclient.activity.store.ComboActivity;
import com.dikai.chenghunjiclient.adapter.store.CombosAdapter;
import com.dikai.chenghunjiclient.bean.BeanGetCombos;
import com.dikai.chenghunjiclient.entity.CombosBean;
import com.dikai.chenghunjiclient.entity.ResultGetCombos;
import com.dikai.chenghunjiclient.util.NetWorkUtil;
import com.dikai.chenghunjiclient.util.UserManager;
import com.dikai.chenghunjiclient.view.MyLoadRecyclerView;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;

/**
 * Created by cmk03 on 2018/6/8.
 */

public class WeddingTaocan extends AppCompatActivity {

    private CombosAdapter mAdapter;
    private MyLoadRecyclerView mRecyclerView;
    private int pageIndex = 1;
    private int itemCount = 20;

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
        mRecyclerView.setLoadChangeListener(new MyLoadRecyclerView.OnLoadChangeListener() {
            @Override
            public void onRefresh() {
                refresh();
            }

            @Override
            public void onLoadMore() {
                pageIndex++;
                getCombos(pageIndex,itemCount,false);
            }
        });
        mAdapter = new CombosAdapter(this);
        mAdapter.setOnItemClickListener(new CombosAdapter.OnItemClickListener() {
            @Override
            public void onClick(CombosBean bean) {
                if(!UserManager.getInstance(WeddingTaocan.this).isLogin()){
                    startActivity(new Intent(WeddingTaocan.this,LoginActivity.class));
                }else {
                    startActivity(new Intent(WeddingTaocan.this,ComboActivity.class).putExtra("id",bean.getId()));
                }
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        refresh();
    }

    private void refresh() {
        pageIndex = 1;
        itemCount = 20;
        getCombos(pageIndex,itemCount,true);
    }

    /**
     * 获取供应商
     */
    private void getCombos(int pageIndex, int pageCount, final boolean isRefresh){
        NetWorkUtil.setCallback("HQOAApi/GetWeddingPackageList",
                new BeanGetCombos(pageIndex+"",pageCount+"",1),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        mRecyclerView.stopLoad();
                        Log.e("返回值",respose);
                        try {
                            ResultGetCombos result = new Gson().fromJson(respose, ResultGetCombos.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                mRecyclerView.setTotalCount(result.getTotalCount());
                                if(isRefresh){
                                    mAdapter.refresh(result.getData());
                                    if(result.getData().size() == 0){
                                        mRecyclerView.setHasData(false);
                                    }else {
                                        mRecyclerView.setHasData(true);
                                    }
                                }else {
                                    mAdapter.addAll(result.getData());
                                }
                            } else {
                                Toast.makeText(WeddingTaocan.this, result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("json解析出错",e.toString());
                            //Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(final String e) {
                        mRecyclerView.stopLoad();
                        Toast.makeText(WeddingTaocan.this, "网络请求错误", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImmersionBar.with(this).destroy();
    }
}
