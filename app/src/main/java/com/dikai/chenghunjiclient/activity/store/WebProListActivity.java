package com.dikai.chenghunjiclient.activity.store;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.adapter.store.WebProAdapter;
import com.dikai.chenghunjiclient.bean.BeanGetHomePro;
import com.dikai.chenghunjiclient.entity.HomeProBean;
import com.dikai.chenghunjiclient.entity.ResultGetHomePro;
import com.dikai.chenghunjiclient.util.NetWorkUtil;
import com.dikai.chenghunjiclient.view.MyLoadRecyclerView;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;

public class WebProListActivity extends AppCompatActivity {

    private WebProAdapter mAdapter;
    private MyLoadRecyclerView mRecyclerView;
    private int pageIndex = 1;
    private int itemCount = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_pro_list);
        ImmersionBar.with(this)
                .statusBarView(R.id.top_view)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true, 0.2f)
                .init();
        init();
    }

    private void init() {
        ImageView mBack = (ImageView) findViewById(R.id.back);
        mRecyclerView = (MyLoadRecyclerView) findViewById(R.id.fragment_project_recycler);
        mRecyclerView.setLoadChangeListener(new MyLoadRecyclerView.OnLoadChangeListener() {
            @Override
            public void onRefresh() {
                refresh();
            }

            @Override
            public void onLoadMore() {
                pageIndex++;
                getProject(false, pageIndex, itemCount);
            }
        });
        mAdapter = new WebProAdapter(this);
        mAdapter.setOnItemClickListener(new WebProAdapter.OnClickListener() {
            @Override
            public void onClick(HomeProBean bean) {
                startActivity(new Intent(WebProListActivity.this,WebProActivity.class).putExtra("id",bean.getPlanId()));
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        refresh();

        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void refresh() {
        pageIndex = 1;
        itemCount = 20;
        getProject(true, pageIndex, itemCount);
    }

    public void getProject(final boolean isRefresh, final int pageIndex, final int itemCount) {
        NetWorkUtil.setCallback("User/GetWebPlanList",
                new BeanGetHomePro(pageIndex,itemCount),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        Log.e("返回值",respose);
                        mRecyclerView.stopLoad();
                        try {
                            ResultGetHomePro result = new Gson().fromJson(respose, ResultGetHomePro.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                mRecyclerView.setTotalCount(result.getTotalCount());
                                if (isRefresh) {
                                    if (result.getData().size() == 0) {
                                        mRecyclerView.setHasData(false);
                                    } else {
                                        mRecyclerView.setHasData(true);
                                    }
                                    mAdapter.refresh(result.getData());
                                } else {
                                    mAdapter.addAll(result.getData());
                                }
                            } else {
                                Toast.makeText(WebProListActivity.this, result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("json解析出错",e.toString());
                            //Toast.makeText(WebProListActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(final String e) {
                        mRecyclerView.stopLoad();
                        Toast.makeText(WebProListActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImmersionBar.with(this).destroy();
    }
}