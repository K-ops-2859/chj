package com.dikai.chenghunjiclient.activity.wedding;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.adapter.wedding.MyInviteAdapter;
import com.dikai.chenghunjiclient.bean.BeanGetMyInvite;
import com.dikai.chenghunjiclient.bean.BeanGetTotalInvite;
import com.dikai.chenghunjiclient.entity.InviteDateBean;
import com.dikai.chenghunjiclient.entity.MyInviteBean;
import com.dikai.chenghunjiclient.entity.MyInviteDayBean;
import com.dikai.chenghunjiclient.entity.ResultGetMyInvite;
import com.dikai.chenghunjiclient.entity.ResultGetTotalInvite;
import com.dikai.chenghunjiclient.util.NetWorkUtil;
import com.dikai.chenghunjiclient.util.UserManager;
import com.dikai.chenghunjiclient.view.MyLoadRecyclerView;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;

import java.util.ArrayList;
import java.util.List;

public class InviteListActivity extends AppCompatActivity implements View.OnClickListener {

    private MyLoadRecyclerView mRecyclerView;
    private MyInviteAdapter mAdapter;
    private int pageIndex = 1;
    private int pageCount = 20;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_list);
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
//                pageIndex++;
//                getInvite(pageIndex,pageCount,false);
            }
        });

        mAdapter = new MyInviteAdapter(this);
        mAdapter.setFromCode(true);
        mRecyclerView.setAdapter(mAdapter);
        refresh();
    }

    private void refresh(){
        pageIndex = 1;
        pageCount = 20;
        getInvite(pageIndex,pageCount,true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                onBackPressed();
                break;
        }
    }

    private void setData(ResultGetMyInvite result){
        List<Object> list = new ArrayList<>();
        if(result.getData() != null && result.getData().size() > 0){
            for (MyInviteDayBean DayBean : result.getData()) {
                list.add(new InviteDateBean(DayBean.getTime(),DayBean.getTimeCount()));
                for (MyInviteBean bean : DayBean.getData()) {
                    list.add(bean);
                }
            }
        }else {
            mRecyclerView.setHasData(false);
        }
        mAdapter.refresh(list);
    }

    /**
     * 获取记录
     */
    private void getInvite(int PageIndex, int PageCount, final boolean isRefresh){
        NetWorkUtil.setCallback("User/GetNewInformationList",
                new BeanGetMyInvite(UserManager.getInstance(this).getUserInfo().getUserID(),"1","1",""+PageIndex,""+PageCount),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        mRecyclerView.stopLoad();
                        Log.e("返回值",respose);
                        try {
                            ResultGetMyInvite result = new Gson().fromJson(respose, ResultGetMyInvite.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                if(isRefresh){
                                    setData(result);
                                }else {
//                                    mAdapter.addAll(result.getData());
                                }
                            } else {
                                Toast.makeText(InviteListActivity.this, result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("json解析出错",e.toString());
                            //Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(final String e) {
                        mRecyclerView.stopLoad();
                        Toast.makeText(InviteListActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    protected void onDestroy() {
        ImmersionBar.with(this).destroy();
        super.onDestroy();
    }
}