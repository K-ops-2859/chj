package com.dikai.chenghunjiclient.activity.ad;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.adapter.ad.MyKeYuanAdapter;
import com.dikai.chenghunjiclient.adapter.me.KeyuanStateAdapter;
import com.dikai.chenghunjiclient.bean.BeanGetSupKeYuan;
import com.dikai.chenghunjiclient.entity.ResultGetSupKeYuan;
import com.dikai.chenghunjiclient.util.Constants;
import com.dikai.chenghunjiclient.util.EventBusBean;
import com.dikai.chenghunjiclient.util.NetWorkUtil;
import com.dikai.chenghunjiclient.util.UserManager;
import com.dikai.chenghunjiclient.view.MyLoadRecyclerView;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;
import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.data.Type;
import com.jzxiang.pickerview.listener.OnDateSetListener;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class SupKeYuanActivity extends AppCompatActivity implements View.OnClickListener {
    private MyLoadRecyclerView mRecyclerView;
    private MyKeYuanAdapter mAdapter;
    private RecyclerView mStateRecycler;
    private KeyuanStateAdapter mStateAdapter;
    private RelativeLayout typeLayout;
    private LinearLayout topLayout;
    private TranslateAnimation mShowAction;
    private TranslateAnimation mHiddenAction;
    private int statePosition;
    private boolean isShowIden = false;
    private int pageIndex = 1;
    private int pageCount = 20;
    private String areaID = "1740";
    private int sortField;
    private int sort;
    
    private LinearLayout placeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sup_ke_yuan);
        ImmersionBar.with(this)
                .statusBarView(R.id.top_view)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true, 0.2f)
                .init();
        init();
    }

    private void init() {
        findViewById(R.id.back).setOnClickListener(this);
        findViewById(R.id.date).setOnClickListener(this);
        findViewById(R.id.apply).setOnClickListener(this);
        findViewById(R.id.bg_view).setOnClickListener(this);
        findViewById(R.id.close).setOnClickListener(this);
        typeLayout = (RelativeLayout) findViewById(R.id.type_layout);
        topLayout = (LinearLayout) findViewById(R.id.top_layout);
        placeLayout = (LinearLayout) findViewById(R.id.place_layout);
        mRecyclerView = (MyLoadRecyclerView) findViewById(R.id.recycler);
        mRecyclerView.setLoadChangeListener(new MyLoadRecyclerView.OnLoadChangeListener() {
            @Override
            public void onRefresh() {
                refresh();
            }

            @Override
            public void onLoadMore() {
                pageIndex++;
                getList(pageIndex,pageCount,false);
            }
        });
        mAdapter = new MyKeYuanAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        initDialog();
        refresh();
    }

    private void refresh(){
        pageIndex = 1;
        pageCount = 20;
        getList(pageIndex,pageCount,true);
    }

    /**
     * 获取记录
     */
    private void getList(int PageIndex, int PageCount, final boolean isRefresh){
        NetWorkUtil.setCallback("HQOAApi/GetFacilitatorIdJSJTableList",
                new BeanGetSupKeYuan(UserManager.getInstance(this).getNewUserInfo().getFacilitatorId(),sortField,sort,PageIndex, PageCount,areaID),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        mRecyclerView.stopLoad();
                        Log.e("返回值",respose);
                        try {
                            ResultGetSupKeYuan result = new Gson().fromJson(respose, ResultGetSupKeYuan.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                if(isRefresh){
                                    mAdapter.refresh(result.getData());
                                    if(result.getData().size() == 0){
                                        placeLayout.setVisibility(View.VISIBLE);
                                    }else {
                                        placeLayout.setVisibility(View.GONE);
                                    }
                                }else {
                                    mAdapter.addAll(result.getData());
                                }
                            } else {
                                Toast.makeText(SupKeYuanActivity.this, result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("json解析出错",e.toString());
                            //Toast.makeText(ADListActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(final String e) {
                        mRecyclerView.stopLoad();
//                        Toast.makeText(ADListActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void initDialog() {
        mShowAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, -1.0f,
                Animation.RELATIVE_TO_SELF, 0.0f);
        mShowAction.setDuration(260);

        mHiddenAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, -1.0f);
        mHiddenAction.setDuration(260);
        mHiddenAction.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}
            @Override
            public void onAnimationEnd(Animation animation) {
                typeLayout.setVisibility(View.GONE);
            }
            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
        mStateRecycler = (RecyclerView) findViewById(R.id.state_recycler);
        mStateRecycler.setLayoutManager(new LinearLayoutManager(this));
        mStateAdapter = new KeyuanStateAdapter(this);
        mStateAdapter.setOnItemClickListener(new KeyuanStateAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position, String bean) {
                if(statePosition != position){
                    statePosition = position;
                    if(position == 0){
                        sortField = 0;
                        sort = 0;
                    }else if(position == 1){
                        sortField = 0;
                        sort = 1;
                    }else if(position == 2){
                        sortField = 0;
                        sort = 0;
                    }else if(position == 3){
                        sortField = 1;
                        sort = 0;
                    }else if(position == 4){
                        sortField = 1;
                        sort = 1;
                    }
                    refresh();
                }
                hidden();
            }
        });

        mStateRecycler.setAdapter(mStateAdapter);
        List<String> temp = new ArrayList<>();
        Collections.addAll(temp,"综合排序","发布时间 远→近","发布时间 近→远","结婚日期 远→近","结婚日期 近→远");
        mStateAdapter.refresh(temp);
        String loca = UserManager.getInstance(this).getLocation();
        if(loca != null && !"".equals(loca)){
            String[] info = loca.split(",");
            areaID = info[0];
            Log.e("数据：=========== ",loca);
        }
    }

    private void show(){
        isShowIden = true;
        typeLayout.setVisibility(View.VISIBLE);
        topLayout.clearAnimation();
        topLayout.setVisibility(View.VISIBLE);
        topLayout.startAnimation(mShowAction);
    }

    private void hidden(){
        isShowIden = false;
        topLayout.clearAnimation();
        topLayout.setVisibility(View.GONE);
        topLayout.startAnimation(mHiddenAction);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                onBackPressed();
                break;
            case R.id.apply:
                EventBus.getDefault().post(new EventBusBean(Constants.KEYUAN_HOME));
                finish();
                break;
            case R.id.date:
                if(isShowIden){
                    hidden();
                }else {
                    show();
                }
                break;
            case R.id.close:
            case R.id.bg_view:
                hidden();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        ImmersionBar.with(this).destroy();
        super.onDestroy();
    }
    
}
