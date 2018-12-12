package com.dikai.chenghunjiclient.activity.register;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.adapter.plan.SelectIdentityAdapter;
import com.dikai.chenghunjiclient.bean.BeanNull;
import com.dikai.chenghunjiclient.bean.BeanRegister;
import com.dikai.chenghunjiclient.entity.IdentityBean;
import com.dikai.chenghunjiclient.entity.ResultGetIdentity;
import com.dikai.chenghunjiclient.util.Constants;
import com.dikai.chenghunjiclient.util.EventBusBean;
import com.dikai.chenghunjiclient.util.NetWorkUtil;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;

public class SelectIdentActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView mRecyclerView;
    private SelectIdentityAdapter mAdapter;
    private ImageView mBack;
    private SpotsDialog mDialog;
    private BeanRegister mBeanRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_ident);
        EventBus.getDefault().register(this);
        ImmersionBar.with(this)
                .statusBarView(R.id.top_view)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true, 0.2f)
                .init();
        init();
    }

    private void init() {
        mBeanRegister = (BeanRegister) getIntent().getSerializableExtra("register");
        mDialog = new SpotsDialog(this, R.style.DialogCustom);
        mRecyclerView = (RecyclerView) findViewById(R.id.activity_select_ident_recycler);
        mBack = (ImageView) findViewById(R.id.activity_select_ident_back);
        mBack.setOnClickListener(this);
        mAdapter = new SelectIdentityAdapter(this);
        mAdapter.setItemClickListener(new SelectIdentityAdapter.OnItemClickListener() {
            @Override
            public void onClick(IdentityBean bean) {
                mBeanRegister.setProfession(bean.getOccupationCode());
                Bundle bundle = new Bundle();
                bundle.putSerializable("register",mBeanRegister);
                startActivity(new Intent(SelectIdentActivity.this, CreateIdentityActivity.class).putExtras(bundle));
            }
        });

        GridLayoutManager manager = new GridLayoutManager(this,3);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);
        getIdentity();
    }

    @Override
    public void onClick(View v) {
        if(v == mBack){
            onBackPressed();
        }
    }

    /**
     * 事件总线刷新
     * @param bean
     */
    @Subscribe
    public void onEventMainThread(final EventBusBean bean) {
        runOnUiThread(new Runnable() {
            public void run() {
                if(bean.getType() == Constants.USER_REGISTER_SUCCESS){
                    finish();
                }
            }
        });
    }


    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        ImmersionBar.with(this).destroy();
        super.onDestroy();
    }

    private void setData(List<IdentityBean> list){
        mAdapter.refresh(list);
    }

    /**
     * 获取职业
     */
    private void getIdentity(){
        mDialog.show();
        NetWorkUtil.setCallback("User/GetAllOccupationList",
                new BeanNull("1"),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        mDialog.dismiss();
                        Log.e("返回值",respose);
                        try {
                            ResultGetIdentity result = new Gson().fromJson(respose, ResultGetIdentity.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                setData(result.getData());
                            } else {
                                Toast.makeText(SelectIdentActivity.this, result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("json解析出错",e.toString());
                            //Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(final String e) {
                        mDialog.dismiss();
                        Toast.makeText(SelectIdentActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
                    }
                });
    }

}
