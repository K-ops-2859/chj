package com.dikai.chenghunjiclient.activity.store;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.adapter.store.CaigoujiePicAdapter;
import com.dikai.chenghunjiclient.adapter.store.CaigoujieSupAdapter;
import com.dikai.chenghunjiclient.bean.BeanBuyCaigoujie;
import com.dikai.chenghunjiclient.bean.BeanGetCaigoujieArea;
import com.dikai.chenghunjiclient.bean.BeanGetCaigoujieSups;
import com.dikai.chenghunjiclient.bean.BeanID;
import com.dikai.chenghunjiclient.bean.BeanUserId;
import com.dikai.chenghunjiclient.entity.CaigoujieAreaBean;
import com.dikai.chenghunjiclient.entity.CaigoujieSupBean;
import com.dikai.chenghunjiclient.entity.CasesBean;
import com.dikai.chenghunjiclient.entity.ResultBuyCaigoujie;
import com.dikai.chenghunjiclient.entity.ResultCaigoujieSup;
import com.dikai.chenghunjiclient.entity.ResultGetCaigoujieArea;
import com.dikai.chenghunjiclient.entity.ResultGetCaigoujieSups;
import com.dikai.chenghunjiclient.util.Constants;
import com.dikai.chenghunjiclient.util.NetWorkUtil;
import com.dikai.chenghunjiclient.util.PayUtil;
import com.dikai.chenghunjiclient.util.UserManager;
import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.widget.MaterialDialog;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;

import java.util.ArrayList;
import java.util.List;

public class CaigoujieActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView suopiao1;
    private TextView suopiao2;
    private TextView suopiao3;
    private ImageView endImg;
    private RecyclerView mHeadPics;
    private RecyclerView suppliers;
    private RecyclerView endPics;
    private CaigoujiePicAdapter mPicAdapter;
    private CaigoujiePicAdapter mEndPicAdapter;
    private CaigoujieSupAdapter mSupAdapter;
    private SwipeRefreshLayout fresh;
    private TabLayout mTabLayout;
    private List<CaigoujieAreaBean> mAreaList;
    private String buyID;
    private String zhubanInfo;
    private MaterialDialog dialog2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caigoujie);
        ImmersionBar.with(this)
                .statusBarView(R.id.top_view)
                .statusBarColor(R.color.pink_light3)
                .statusBarDarkFont(false)
                .init();
        init();
    }

    private void init() {
        findViewById(R.id.back).setOnClickListener(this);
        findViewById(R.id.my_piao).setOnClickListener(this);
        findViewById(R.id.buy_piao).setOnClickListener(this);
        findViewById(R.id.main_info).setOnClickListener(this);
        suopiao1 = (TextView) findViewById(R.id.suopiao_text_1);
        suopiao2 = (TextView) findViewById(R.id.suopiao_text_2);
        suopiao3 = (TextView) findViewById(R.id.youhuijia);
        endPics = (RecyclerView) findViewById(R.id.end_pic);
        mHeadPics = (RecyclerView) findViewById(R.id.head_pic);
        suppliers = (RecyclerView) findViewById(R.id.supplier);
        mTabLayout = (TabLayout) findViewById(R.id.tabs);
        fresh = (SwipeRefreshLayout) findViewById(R.id.fresh);
        //设置刷新时动画的颜色，可以设置4个
        fresh.setColorSchemeResources(R.color.main);
        fresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fresh.post(new Runnable() {
                    @Override
                    public void run() {
                        fresh.setRefreshing(true);
                    }
                });
                getArea();
            }
        });
        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                getSup(mAreaList.get(tab.getPosition()).getId());
                getZhuban(mAreaList.get(tab.getPosition()).getId());
                buyID = mAreaList.get(tab.getPosition()).getId();
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}
            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
        mEndPicAdapter = new CaigoujiePicAdapter(this);
        endPics.setAdapter(mEndPicAdapter);
        endPics.setNestedScrollingEnabled(false);
        mPicAdapter = new CaigoujiePicAdapter(this);
        mHeadPics.setAdapter(mPicAdapter);
        mHeadPics.setNestedScrollingEnabled(false);
        mSupAdapter = new CaigoujieSupAdapter(this);
        mSupAdapter.setOnItemClickListener(new CaigoujieSupAdapter.OnItemClickListener() {
            @Override
            public void onClick(CaigoujieSupBean bean) {
                startActivity(new Intent(CaigoujieActivity.this,CaigoujieInfoActivity.class)
                        .putExtra("info",bean.getExhibitorInfo()));
            }
        });
        suppliers.setAdapter(mSupAdapter);
        mHeadPics.setNestedScrollingEnabled(false);
        dialog2 = new MaterialDialog(this);
        dialog2.isTitleShow(false)//
                .btnNum(2)
                .content("将使用微信支付购买门票")//
                .btnText("取消", "确定")
                .setOnBtnClickL(new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        dialog2.dismiss();
                    }
                }, new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        dialog2.dismiss();
                        buyTicket(buyID);
                    }
                });
        getArea();
    }

    public void stopLoad(){
        if(fresh.isRefreshing()){
            fresh.post(new Runnable() {
                @Override
                public void run() {
                    fresh.setRefreshing(false);
                }
            });
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                onBackPressed();
                break;
            case R.id.my_piao:
                startActivity(new Intent(this,MyTicketActivity.class));
                break;
            case R.id.main_info:
                if (zhubanInfo != null && !"".equals(zhubanInfo.trim())){
                    startActivity(new Intent(this,CaigoujieInfoActivity.class).putExtra("info",zhubanInfo));
                }else {
                    Toast.makeText(this, "主办方信息为空！", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.buy_piao:
                dialog2.show();
                break;
        }
    }

    /**
     * 获取地区
     */
    private void getArea(){
        NetWorkUtil.setCallback("HQOAApi/GetActivityAreaList",
                new BeanGetCaigoujieArea(1),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        stopLoad();
                        Log.e("返回值",respose);
                        try {
                            ResultGetCaigoujieArea result = new Gson().fromJson(respose, ResultGetCaigoujieArea.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                mPicAdapter.refresh(result.getImgData());
                                mAreaList = result.getData();
                                mTabLayout.removeAllTabs();
                                for (CaigoujieAreaBean bean : result.getData()) {
                                    mTabLayout.addTab(mTabLayout.newTab().setText(bean.getName()));
                                }
                                mEndPicAdapter.refresh(result.getEndImgData());
                            } else {
                                Toast.makeText(CaigoujieActivity.this, result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("json解析出错",e.toString());
                            //Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(final String e) {
                        stopLoad();
                        Toast.makeText(CaigoujieActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * 获取主办方
     */
    private void getZhuban(String id){
        NetWorkUtil.setCallback("HQOAApi/GetActivitySponsor",
                new BeanID(id),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        stopLoad();
                        Log.e("返回值",respose);
                        try {
                            ResultCaigoujieSup result = new Gson().fromJson(respose, ResultCaigoujieSup.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                String text1 = "前往各地主办方购买"+result.getSponsorPrice()+"元门票";
                                String text2 = "通过成婚纪，订购"+result.getTicketPrice()+"元优惠门票，并在现场领取由成婚纪送出的价值2580元国际品牌豪礼";
                                suopiao1.setText(text1);
                                suopiao2.setText(text2);
                                suopiao3.setText(result.getTicketPrice());
                                zhubanInfo = result.getSponsorInfo();
                            } else {
                                Toast.makeText(CaigoujieActivity.this, result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("json解析出错",e.toString());
                            //Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(final String e) {
                        stopLoad();
                        Toast.makeText(CaigoujieActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * 获取供应商
     */
    private void getSup(String id){
        NetWorkUtil.setCallback("HQOAApi/GetExhibitorInformationList",
                new BeanGetCaigoujieSups(id,"1","1000"),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        Log.e("返回值",respose);
                        try {
                            ResultGetCaigoujieSups result = new Gson().fromJson(respose, ResultGetCaigoujieSups.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                mSupAdapter.refresh(result.getData());
                            } else {
                                Toast.makeText(CaigoujieActivity.this, result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("json解析出错",e.toString());
                            //Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(final String e) {
                        Toast.makeText(CaigoujieActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
                    }
                });
    }


    /**
     * 购买门票
     */
    private void buyTicket(String id){
        NetWorkUtil.setCallback("HQOAApi/Payment",
                new BeanBuyCaigoujie(UserManager.getInstance(this).getNewUserInfo().getUserId(), "0","1",id),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        Log.e("返回值",respose);
                        try {
                            ResultBuyCaigoujie result = new Gson().fromJson(respose, ResultBuyCaigoujie.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                pay(result.getTransNumber());
                            } else {
                                Toast.makeText(CaigoujieActivity.this, result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("json解析出错",e.toString());
                            //Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(final String e) {
                        Toast.makeText(CaigoujieActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void pay(String orderNum){
        PayUtil.getInstance(CaigoujieActivity.this).setType(Constants.BUY_TICKET);
        PayUtil.getInstance(CaigoujieActivity.this).wxPay(orderNum, new PayUtil.OnPayListener() {
            @Override
            public void onFinish(String info) {

            }

            @Override
            public void onError(String e) {
                Toast.makeText(CaigoujieActivity.this, e, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDestroy() {
        ImmersionBar.with(this).destroy();
        super.onDestroy();
    }
}
