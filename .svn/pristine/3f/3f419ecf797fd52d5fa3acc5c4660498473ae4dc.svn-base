package com.dikai.chenghunjiclient.activity.store;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.adapter.store.HomeSupListAdapter;
import com.dikai.chenghunjiclient.adapter.store.SupHolderAdapter;
import com.dikai.chenghunjiclient.bean.BeanGetSupHolder;
import com.dikai.chenghunjiclient.bean.BeanGetSups;
import com.dikai.chenghunjiclient.entity.NewIdentityBean;
import com.dikai.chenghunjiclient.entity.ResultGetSupHolder;
import com.dikai.chenghunjiclient.entity.ResultNewSups;
import com.dikai.chenghunjiclient.util.NetWorkUtil;
import com.dikai.chenghunjiclient.view.MyLoadRecyclerView;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;

import java.util.ArrayList;
import java.util.List;

public class HomeSupActivity extends AppCompatActivity implements View.OnClickListener {

    private NewIdentityBean mIdentityBean;
    private HomeSupListAdapter mAdapter;
    private MyLoadRecyclerView mRecyclerView;
    private SupHolderAdapter mHolderAdapter;
    private MyLoadRecyclerView holderList;
    private LinearLayout holder;
    private String areaID;
    private int pageIndex = 1;
    private int itemCount = 20;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_sup);
        ImmersionBar.with(this)
                .statusBarView(R.id.top_view)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true, 0.2f)
                .init();
        init();
    }

    private void init() {
        mIdentityBean = (NewIdentityBean) getIntent().getSerializableExtra("ident");
        areaID = getIntent().getStringExtra("areaID");
        findViewById(R.id.back).setOnClickListener(this);
        mRecyclerView = (MyLoadRecyclerView) findViewById(R.id.fragment_project_recycler);
        holderList = (MyLoadRecyclerView) findViewById(R.id.holder_recycler);
        holder = (LinearLayout) findViewById(R.id.holder);
        TextView title = (TextView) findViewById(R.id.title);
        mRecyclerView.setLoadChangeListener(new MyLoadRecyclerView.OnLoadChangeListener() {
            @Override
            public void onRefresh() {
                refresh();
            }

            @Override
            public void onLoadMore() {
                pageIndex++;
                getHomeSup(pageIndex,itemCount,false);

            }
        });
        mAdapter = new HomeSupListAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        holderList.setLoadChangeListener(new MyLoadRecyclerView.OnLoadChangeListener() {
            @Override
            public void onRefresh() {
                refresh();
            }

            @Override
            public void onLoadMore() {
            }
        });
        mHolderAdapter = new SupHolderAdapter(this);
        mHolderAdapter.setTitle(mIdentityBean.getOccupationName(),mIdentityBean.getOccupationID());
        holderList.setAdapter(mHolderAdapter);
        title.setText(mIdentityBean.getOccupationName());
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
        pageIndex = 1;
        itemCount = 20;
        getHomeSup(pageIndex,itemCount,true);
    }

    /**
     * 获取供应商
     */
    private void getHomeSup(int pageIndex, int pageCount, final boolean isRefresh){
        NetWorkUtil.setCallback("HQOAApi/GetFacilitatorList",
                new BeanGetSups(areaID, mIdentityBean.getOccupationID(),pageIndex+"",pageCount+"",""),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        Log.e("返回值",respose);
                        mRecyclerView.stopLoad();
                        holderList.stopLoad();
                        try {
                            ResultNewSups result = new Gson().fromJson(respose, ResultNewSups.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                mRecyclerView.setTotalCount(result.getTotalCount());
                                if(isRefresh){
                                    mAdapter.refresh(result.getData());
                                    if(result.getData().size() > 0){
                                        mRecyclerView.setVisibility(View.VISIBLE);
                                        holder.setVisibility(View.GONE);
                                        holderList.setVisibility(View.GONE);
                                    }else {
                                        mRecyclerView.setVisibility(View.GONE);
                                        holder.setVisibility(View.VISIBLE);
                                        holderList.setVisibility(View.VISIBLE);
                                        getHolderList();
                                    }
                                }else {
                                    mAdapter.addAll(result.getData());
                                }
                            } else {
                                Toast.makeText(HomeSupActivity.this, result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("json解析出错",e.toString());
                            //Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(final String e) {
                        holderList.stopLoad();
                        mRecyclerView.stopLoad();
                        Toast.makeText(HomeSupActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * 获取推荐供应商
     */
    private void getHolderList(){
        NetWorkUtil.setCallback("HQOAApi/GetDefaultSupplierInfoList",
                new BeanGetSupHolder(mIdentityBean.getOccupationID()),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        holderList.stopLoad();
                        Log.e("返回值",respose);
                        try {
                            ResultGetSupHolder result = new Gson().fromJson(respose, ResultGetSupHolder.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                List<Object> list = new ArrayList<>();
                                list.add("");
                                list.addAll(result.getData());
                                mHolderAdapter.refresh(list);
                                if(result.getData()== null || result.getData().size() == 0){
                                    holderList.setVisibility(View.GONE);
                                }else {
                                    holderList.setVisibility(View.VISIBLE);
                                }
                            } else {
                                Toast.makeText(HomeSupActivity.this, result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("json解析出错",e.toString());
                            //Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(final String e) {
                        holderList.stopLoad();
                        Toast.makeText(HomeSupActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    protected void onDestroy() {
        ImmersionBar.with(this).destroy();
        super.onDestroy();
    }
}
