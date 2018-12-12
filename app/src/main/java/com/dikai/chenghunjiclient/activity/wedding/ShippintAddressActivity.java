package com.dikai.chenghunjiclient.activity.wedding;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.adapter.OnItemClickListener;
import com.dikai.chenghunjiclient.adapter.wedding.ShippingAddressAdapter;
import com.dikai.chenghunjiclient.bean.BeanUserId;
import com.dikai.chenghunjiclient.entity.PersonAddressData;
import com.dikai.chenghunjiclient.util.Constants;
import com.dikai.chenghunjiclient.util.EventBusBean;
import com.dikai.chenghunjiclient.util.NetWorkUtil;
import com.dikai.chenghunjiclient.util.UserManager;
import com.dikai.chenghunjiclient.view.MyLoadRecyclerView;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by cmk03 on 2018/5/2.
 */

public class ShippintAddressActivity extends AppCompatActivity {

    private ShippingAddressAdapter mAdapter;
    private MyLoadRecyclerView mRecyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shippingaddress);
        ImmersionBar.with(this)
                .statusBarView(R.id.top_view)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true, 0.2f)
                .init();

        Toolbar mToolBar = (Toolbar) findViewById(R.id.toolbar);
        TextView tvAdd = (TextView) findViewById(R.id.tv_add);
        mRecyclerView = (MyLoadRecyclerView) findViewById(R.id.recycler_view);

        mAdapter = new ShippingAddressAdapter(this);
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.setLoadChangeListener(new MyLoadRecyclerView.OnLoadChangeListener() {
            @Override
            public void onRefresh() {
                initData();
            }

            @Override
            public void onLoadMore() {

            }
        });

        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ShippintAddressActivity.this, AddAddressActivity.class));
            }
        });

        mAdapter.setOnItemClickListener(new ShippingAddressAdapter.OnItemClickListener() {
            @Override
            public void onClick(PersonAddressData.DataList bean, int position, int type) {
                if(type == 0){
                    EventBus.getDefault().post(new EventBusBean(Constants.SELECT_ADDRESS,bean));
                    finish();
                }else if(type == 1){
                    Intent intent = new Intent(ShippintAddressActivity.this, EditAddressActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("data", bean);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    private void initData() {
        String userID = UserManager.getInstance(this).getNewUserInfo().getUserId();
        NetWorkUtil.setCallback("HQOAApi/GetConsigneeInfoList",
                new BeanUserId(userID), new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(String respose) {
                        try {
                            PersonAddressData data = new Gson().fromJson(respose, PersonAddressData.class);
                            if (data.getMessage().getCode().equals("200")) {
                                mAdapter.setList(data.getData());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        mRecyclerView.stopLoad();
                    }

                    @Override
                    public void onError(String e) {

                    }
        });
    }

    @Override
    public void onDestroy() {
        ImmersionBar.with(this).destroy();
        super.onDestroy();
    }
}
