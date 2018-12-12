package com.dikai.chenghunjiclient.activity.me;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.adapter.OnItemClickListener;
import com.dikai.chenghunjiclient.adapter.me.WeddingPhotoAdapter;
import com.dikai.chenghunjiclient.bean.BeanCustomerInfoBySupplier;
import com.dikai.chenghunjiclient.bean.BeanCustomerInfoList;
import com.dikai.chenghunjiclient.entity.CustomerInfoBySupplierData;
import com.dikai.chenghunjiclient.entity.CustomerInfoData;
import com.dikai.chenghunjiclient.util.NetWorkUtil;
import com.dikai.chenghunjiclient.util.UserManager;
import com.dikai.chenghunjiclient.view.MyLoadRecyclerView;
import com.flyco.dialog.widget.base.BaseDialog;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;

import java.util.List;

/**
 * Created by cmk03 on 2018/3/26.
 */

public class WeddingPhotoActivity extends AppCompatActivity implements View.OnClickListener {

    private WeddingPhotoAdapter mAdapter;
    private String identity;
    private MyLoadRecyclerView mRecyclerView;
    private int type;
    private ServiceDialog ruleDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wedding_photo);
        ImmersionBar.with(this)
                .statusBarView(R.id.top_view)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true, 0.2f)
                .init();
        type = getIntent().getIntExtra("type",0);
        identity = getIntent().getStringExtra("identity");
        TextView title = (TextView) findViewById(R.id.title);
        findViewById(R.id.back).setOnClickListener(this);
        findViewById(R.id.rule).setOnClickListener(this);
        mRecyclerView = (MyLoadRecyclerView) findViewById(R.id.recycler_view);
        mAdapter = new WeddingPhotoAdapter(this, identity);
        mRecyclerView.setAdapter(mAdapter);
        if (identity.equals("SF_12001000")) {
            refreshUser();
        } else {
            refreshSupplier();
        }

        if(type == 0){
            title.setText("照片");
        }else {
            title.setText("视频");
        }

        mRecyclerView.setLoadChangeListener(new MyLoadRecyclerView.OnLoadChangeListener() {
            @Override
            public void onRefresh() {
                if (identity.equals("SF_12001000")) {
                    refreshUser();
                } else {
                    refreshSupplier();
                }
            }

            @Override
            public void onLoadMore() {

            }
        });

        if (identity.equals("SF_12001000")) {
            System.out.println("新人选择");
            mAdapter.setOnItemClickListener(new OnItemClickListener<CustomerInfoData.DataList>() {
                @Override
                public void onItemClick(View view, int position, CustomerInfoData.DataList dataList) {
                    if(type == 0){
                        Intent intent = new Intent(WeddingPhotoActivity.this, XinRenPhotoActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("data", dataList);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }else {
                        Intent intent = new Intent(WeddingPhotoActivity.this, MyVideoActivity.class);
                        intent.putExtra("id",dataList.getCustomerInfoID());
                        startActivity(intent);
                    }
                }
            });
        } else if (identity.equals("SF_4001000") || identity.equals("SF_5001000")){
            System.out.println("供应商选择");
            mAdapter.setOnItemClickListener(new OnItemClickListener<CustomerInfoBySupplierData.DataList>() {
                @Override
                public void onItemClick(View view, int position, CustomerInfoBySupplierData.DataList dataList) {
                    if(type == 0){
                        Intent intent = new Intent(WeddingPhotoActivity.this, VideoPhotoDetailsActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("data", dataList);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }else {
                        Intent intent = new Intent(WeddingPhotoActivity.this, MyVideoActivity.class);
                        intent.putExtra("id",dataList.getCustomerid());
                        startActivity(intent);
                    }
                }
            });
        }

        ruleDialog = new ServiceDialog(this);
        ruleDialog.widthScale(1);
        ruleDialog.heightScale(1);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                onBackPressed();
                break;
            case R.id.rule:
                ruleDialog.show();
                break;
        }
    }

    /**
     * 用户选择供应商
     */
    private void refreshUser() {
        String userID = UserManager.getInstance(this).getUserInfo().getUserID();
        NetWorkUtil.setCallback("User/GetCustomerInfoList",
                new BeanCustomerInfoList(userID), new NetWorkUtil.CallBackListener() {
            @Override
            public void onFinish(String respose) {
                CustomerInfoData infoData = new Gson().fromJson(respose, CustomerInfoData.class);
                if (infoData.getMessage().getCode().equals("200")) {
                    List<CustomerInfoData.DataList> dataList = infoData.getData();
                    System.out.println("用户选择供应商" + dataList.size());
                    mAdapter.setData(dataList);
                }
                mRecyclerView.stopLoad();
            }

            @Override
            public void onError(String e) {
                mRecyclerView.stopLoad();
            }
        });
    }

    /**
     * 供应商选择用户
     */
    private void refreshSupplier() {
        String userID = UserManager.getInstance(this).getUserInfo().getUserID();
        NetWorkUtil.setCallback("User/GetCustomerInfoBySupplier",
                new BeanCustomerInfoBySupplier(userID), new NetWorkUtil.CallBackListener() {
            @Override
            public void onFinish(String respose) {
                CustomerInfoBySupplierData supplierData = new Gson().fromJson(respose, CustomerInfoBySupplierData.class);
                if (supplierData.getMessage().getCode().equals("200")) {
                    List<CustomerInfoBySupplierData.DataList> dataList = supplierData.getData();
                    System.out.println("供应商选择用户" + dataList.size());
                    mAdapter.setList(dataList);
                }
                mRecyclerView.stopLoad();
            }

            @Override
            public void onError(String e) {
                mRecyclerView.stopLoad();
            }
        });
    }

    private void loadMoreUser() {

    }

    private void loadMoreSupplier() {

    }

    /**
     *rule
     */
    private class ServiceDialog extends BaseDialog<ServiceDialog> implements View.OnClickListener {
        private TextView close;
        public ServiceDialog(Context context) {
            super(context);
        }
        @Override
        public View onCreateView() {
            View view = View.inflate(mContext, R.layout.dialog_photo_video_know, null);
            close = (TextView) view.findViewById(R.id.close);
            close.setOnClickListener(this);
            return view;
        }
        @Override
        public void onViewCreated(View view) {
            super.onViewCreated(view);
        }
        @Override
        public void setUiBeforShow() {}
        @Override
        public void onClick(View v) {
            if(v == close){
                this.dismiss();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImmersionBar.with(this).destroy();
    }
}
