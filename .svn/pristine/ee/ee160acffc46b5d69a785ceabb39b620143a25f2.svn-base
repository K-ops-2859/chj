package com.dikai.chenghunjiclient.activity.me;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.adapter.me.MyCollectAdapter;
import com.dikai.chenghunjiclient.adapter.me.SupIdentityAdapter;
import com.dikai.chenghunjiclient.bean.BeanGetMyCollect;
import com.dikai.chenghunjiclient.bean.BeanNull;
import com.dikai.chenghunjiclient.entity.IdentityBean;
import com.dikai.chenghunjiclient.entity.ResultGetIdentity;
import com.dikai.chenghunjiclient.entity.ResultGetMycollect;
import com.dikai.chenghunjiclient.util.NetWorkUtil;
import com.dikai.chenghunjiclient.util.UserManager;
import com.dikai.chenghunjiclient.view.MyLoadRecyclerView;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;

import java.util.ArrayList;
import java.util.List;

public class MyCollectActivity extends AppCompatActivity implements View.OnClickListener {

    private MyCollectAdapter mCollectAdapter;
    private SupIdentityAdapter mIdentityAdapter;
    private RelativeLayout mIdentityLayout;
    private LinearLayout mIdentityBtn;
    private ImageView mArrow;
    private MyLoadRecyclerView mRecyclerView;
    private RecyclerView mIdentityList;
    private TextView mIdentName;
    private int pageIndex = 1;
    private int itemCount = 20;
    private TranslateAnimation mShowAction;
    private TranslateAnimation mHiddenAction;
    private boolean isShowIden;
    private String identity = "";
    private ImageView mBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_collect);

        ImmersionBar.with(this)
                .statusBarView(R.id.top_view)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true, 0.2f)
                .init();
        init();
    }

    private void init() {
        mBack = (ImageView) findViewById(R.id.activity_my_collect_back);
        mIdentityLayout = (RelativeLayout) findViewById(R.id.activity_my_collect_layout);
        mRecyclerView = (MyLoadRecyclerView)findViewById(R.id.activity_my_collect_recycler);
        mIdentityList = (RecyclerView)findViewById(R.id.activity_my_collect_identity_recycler);
        mIdentityBtn = (LinearLayout) findViewById(R.id.activity_my_collect_identity);
        mIdentName = (TextView) findViewById(R.id.activity_my_collect_name);
        mArrow = (ImageView) findViewById(R.id.activity_my_collect_arrow);
        mIdentityAdapter  = new SupIdentityAdapter(this);
        mIdentityAdapter.setOnClickListener(new SupIdentityAdapter.OnClickListener() {
            @Override
            public void onClick(IdentityBean bean) {
                identity = bean.getOccupationCode();
                mIdentName.setText(bean.getOccupationName());
                hidden();
                refresh();
            }
        });

        mCollectAdapter = new MyCollectAdapter(this);
        mRecyclerView.setAdapter(mCollectAdapter);
        mRecyclerView.setLoadChangeListener(new MyLoadRecyclerView.OnLoadChangeListener() {
            @Override
            public void onRefresh() {
                refresh();
            }

            @Override
            public void onLoadMore() {
                pageIndex++;
                getCollection(false, pageIndex, itemCount);
            }
        });

        mIdentityList.setAdapter(mIdentityAdapter);
        mShowAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, -1.0f,
                Animation.RELATIVE_TO_SELF, 0.0f);
        mShowAction.setDuration(260);

        mHiddenAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, -1.0f);
        mHiddenAction.setDuration(260);
        mBack.setOnClickListener(this);
        mIdentityBtn.setOnClickListener(this);
        mIdentityLayout.setOnClickListener(this);
        getIdentity();
        refresh();
    }

    private void refresh() {
        pageIndex = 1;
        itemCount = 20;
        getCollection(true, pageIndex, itemCount);
    }


    @Override
    public void onClick(View v) {
        if(v == mBack){
            onBackPressed();
        }else if(v == mIdentityBtn){
            if(isShowIden){
                hidden();
            }else {
                show();
            }
        }else if(v == mIdentityLayout){
            hidden();
        }
    }

    private void show(){
        isShowIden = true;
        mArrow.setImageResource(R.mipmap.ic_app_yellow_up);
        mIdentityLayout.clearAnimation();
        mIdentityLayout.setVisibility(View.VISIBLE);
        mIdentityLayout.startAnimation(mShowAction);
    }

    private void hidden(){
        isShowIden = false;
        mArrow.setImageResource(R.mipmap.ic_app_yellow_down);
        mIdentityLayout.clearAnimation();
        mIdentityLayout.setVisibility(View.GONE);
        mIdentityLayout.startAnimation(mHiddenAction);
    }

    /**
     * 获取职业
     */
    private void getIdentity(){
        NetWorkUtil.setCallback("User/GetAllOccupationList",
                new BeanNull("2"),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        Log.e("返回值",respose);
                        try {
                            ResultGetIdentity result = new Gson().fromJson(respose, ResultGetIdentity.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                List<IdentityBean> list = new ArrayList<>();
                                list.add(new IdentityBean("","全部职业"));
                                list.addAll(result.getData());
                                mIdentityAdapter.refresh(list);
                            } else {
                                Toast.makeText(MyCollectActivity.this, result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("json解析出错",e.toString());
                            //Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(final String e) {
                        Toast.makeText(MyCollectActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * 获取职业
     */
    private void getCollection(final boolean isRefresh, final int pageIndex, final int itemCount){
        NetWorkUtil.setCallback("User/CollectionList",
                new BeanGetMyCollect("0", UserManager.getInstance(this).getUserInfo().getUserID(),"0",identity,"",pageIndex+"",itemCount + ""),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        mRecyclerView.stopLoad();
                        Log.e("返回值",respose);
                        try {
                            ResultGetMycollect result = new Gson().fromJson(respose, ResultGetMycollect.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                if(isRefresh){
                                    if(result.getData().size() == 0){
                                        mRecyclerView.setHasData(false);
                                    }else {
                                        mRecyclerView.setHasData(true);
                                    }
                                    mCollectAdapter.refresh(result.getData());
                                }else {
                                    mCollectAdapter.addAll(result.getData());
                                }
                            } else {
                                Toast.makeText(MyCollectActivity.this, result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            mRecyclerView.stopLoad();
                            Log.e("json解析出错",e.toString());
                            //Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(final String e) {
                        Toast.makeText(MyCollectActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImmersionBar.with(this).destroy();
    }
}
