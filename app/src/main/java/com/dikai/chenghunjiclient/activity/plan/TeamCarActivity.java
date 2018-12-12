package com.dikai.chenghunjiclient.activity.plan;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.adapter.plan.TeamCarAdapter;
import com.dikai.chenghunjiclient.bean.BeanGetTeamCar;
import com.dikai.chenghunjiclient.entity.ResultTeamCar;
import com.dikai.chenghunjiclient.util.NetWorkUtil;
import com.dikai.chenghunjiclient.util.UserManager;
import com.dikai.chenghunjiclient.view.MyLoadRecyclerView;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;

import java.util.ArrayList;
import java.util.List;

public class TeamCarActivity extends AppCompatActivity implements View.OnClickListener {

    private MyLoadRecyclerView mRecyclerView;
    private TeamCarAdapter mAdapter;
    private ImageView mBack;
    private String date;
    private String orderID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_car);
        ImmersionBar.with(this)
                .statusBarView(R.id.top_view)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true, 0.2f)
                .init();
        init();
    }

    private void init() {
        date = getIntent().getStringExtra("date");
        orderID = getIntent().getStringExtra("order");
        mBack = (ImageView) findViewById(R.id.activity_case_back);
        mRecyclerView = (MyLoadRecyclerView) findViewById(R.id.activity_case_recycler);
        mAdapter = new TeamCarAdapter(this);
        mAdapter.setDate(date);
        mAdapter.setOrderID(orderID);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLoadChangeListener(new MyLoadRecyclerView.OnLoadChangeListener() {
            @Override
            public void onRefresh() {
                refresh();
            }

            @Override
            public void onLoadMore() {
            }
        });
        mBack.setOnClickListener(this);
        refresh();
    }

    private void refresh() {
        getCollection();
    }


    /**
     * 获取案例
     */
    private void getCollection(){
        NetWorkUtil.setCallback("User/GetModelsListBySupplierID",
                new BeanGetTeamCar(UserManager.getInstance(this).getUserInfo().getSupplierID(), date),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        mRecyclerView.stopLoad();
                        Log.e("返回值",respose);
                        try {
                            ResultTeamCar result = new Gson().fromJson(respose, ResultTeamCar.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                if(result.getData().size() == 0){
                                    mRecyclerView.setHasData(false);
                                }else {
                                    mRecyclerView.setHasData(true);
                                    List<Object> list = new ArrayList<>();
                                    list.add("");
                                    list.addAll(result.getData());
                                    mAdapter.refresh(list);
                                }
                            } else {
                                Toast.makeText(TeamCarActivity.this, result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("json解析出错",e.toString());
                            //Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(final String e) {
                        mRecyclerView.stopLoad();
                        Toast.makeText(TeamCarActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onClick(View v) {
        if(v == mBack){
            onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImmersionBar.with(this).destroy();
    }
}
