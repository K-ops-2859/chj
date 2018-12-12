package com.dikai.chenghunjiclient.activity.discover;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.activity.store.CarInfoActivity;
import com.dikai.chenghunjiclient.activity.store.CorpInfoActivity;
import com.dikai.chenghunjiclient.activity.store.HotelInfoActivity;
import com.dikai.chenghunjiclient.adapter.OnItemClickListener;
import com.dikai.chenghunjiclient.adapter.discover.LikePersonAdapter;
import com.dikai.chenghunjiclient.bean.DynamicLikeBean;
import com.dikai.chenghunjiclient.entity.DynamicDetailsData;
import com.dikai.chenghunjiclient.entity.LikePersonData;
import com.dikai.chenghunjiclient.util.NetWorkUtil;
import com.dikai.chenghunjiclient.util.UserManager;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;

import java.io.Serializable;
import java.util.List;

/**
 * Created by cmk03 on 2018/1/26.
 */

public class LikePersonActivity extends AppCompatActivity {

    private LikePersonAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_like_person);
        ImmersionBar.with(this)
                .statusBarView(R.id.top_view)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true, 0.2f)
                .init();
        Toolbar mToolBar = (Toolbar) findViewById(R.id.toolbar);
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new LikePersonAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        getLikeList();
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mAdapter.setOnItemClickListener(new OnItemClickListener<LikePersonData.DataList>() {
            @Override
            public void onItemClick(View view, int position, LikePersonData.DataList dataList) {
                String code = dataList.getOccupationCode();
                if("SF_13001000".equals(code)||"SF_12001000".equals(code)){
                    Intent intent = new Intent(LikePersonActivity.this, LikeDetailsActivity.class);
                    Bundle bundle = new Bundle();
                    dataList.setOccupationCode(UserManager.getInstance(LikePersonActivity.this).getIdentity(code));
                    bundle.putSerializable("data", dataList);
                    bundle.putInt("type", getIntent().getIntExtra("type",0));
                    intent.putExtras(bundle);
                    startActivity(intent);
                }else if("SF_1001000".equals(code)){//酒店
                    startActivity(new Intent(LikePersonActivity.this, HotelInfoActivity.class)
                            .putExtra("id", dataList.getSupplierID()).putExtra("userid",dataList.getObjectId()));
                }else if("SF_2001000".equals(code)){//婚车
                    startActivity(new Intent(LikePersonActivity.this, CarInfoActivity.class)
                            .putExtra("id", dataList.getSupplierID()).putExtra("userid",dataList.getObjectId()));
                }else if("SF_14001000".equals(code)){//婚庆
                    startActivity(new Intent(LikePersonActivity.this, CorpInfoActivity.class)
                            .putExtra("id", dataList.getSupplierID())
                            .putExtra("type",1).putExtra("userid",dataList.getObjectId()));
                }else {//其他
                    startActivity(new Intent(LikePersonActivity.this, CorpInfoActivity.class)
                            .putExtra("id", dataList.getSupplierID())
                            .putExtra("type",0)
                            .putExtra("userid",dataList.getObjectId()));
                }

            }
        });
    }

    private void getLikeList() {
        String userID = getIntent().getStringExtra("objectID");
        String dynamicID = getIntent().getStringExtra("dynamicID");
        NetWorkUtil.setCallback("User/GetGivethumbList", new DynamicLikeBean(userID, dynamicID, 1, 9999),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(String respose) {
                        Log.e("返回值：",respose);
                        try {
                            LikePersonData data = new Gson().fromJson(respose, LikePersonData.class);
                            if (data.getMessage().getCode().equals("200")) {
                                mAdapter.setList(data.getData());
                            }else {
                                Toast.makeText(LikePersonActivity.this, data.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        }catch (Exception e){
                            Log.e("",e.toString());
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
