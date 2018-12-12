package com.dikai.chenghunjiclient.activity.store;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.adapter.store.HotelADAdapter;
import com.dikai.chenghunjiclient.bean.BeanGetADHotel;
import com.dikai.chenghunjiclient.bean.BeanGetHomeSup;
import com.dikai.chenghunjiclient.bean.BeanGetSups;
import com.dikai.chenghunjiclient.citypicker.SelectCityActivity;
import com.dikai.chenghunjiclient.entity.ActivityHotelBean;
import com.dikai.chenghunjiclient.entity.ActivityHotelBean;
import com.dikai.chenghunjiclient.entity.ResultActivityHotel;
import com.dikai.chenghunjiclient.entity.ResultGetHomeSup;
import com.dikai.chenghunjiclient.entity.ResultNewSups;
import com.dikai.chenghunjiclient.util.Constants;
import com.dikai.chenghunjiclient.util.EventBusBean;
import com.dikai.chenghunjiclient.util.NetWorkUtil;
import com.dikai.chenghunjiclient.util.UserManager;
import com.dikai.chenghunjiclient.view.MyLoadRecyclerView;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

public class HotelADActivity extends AppCompatActivity implements View.OnClickListener {

    private MyLoadRecyclerView mRecyclerView;
    private HotelADAdapter mAdapter;
    private String areaID;
    private String location;
    private String rule;
    private int pageIndex = 1;
    private int itemCount = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_ad);
        EventBus.getDefault().register(this);
        ImmersionBar.with(this)
                .statusBarView(R.id.top_view)
                .statusBarColor(R.color.light_blue2)
                .statusBarDarkFont(false)
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
                pageIndex++;
                getHomeSup(pageIndex,itemCount,false);

            }
        });
        mAdapter = new HotelADAdapter(this);
        mAdapter.setOnItemClickListener(new HotelADAdapter.OnItemClickListener() {
            @Override
            public void onClick(int type, int position, ActivityHotelBean bean) {
                if(type == 0){
                    startActivity(new Intent(HotelADActivity.this, SelectCityActivity.class).putExtra("type",Constants.SELECT_HOTEL_CITY));
                }else if(type == 1){
                    startActivity(new Intent(HotelADActivity.this,PrizeRuleActivity.class)
                            .putExtra("rule",rule));
                }else if(type == 2){
                    startActivity(new Intent(HotelADActivity.this, HotelInfoActivity.class)
                                .putExtra("id", bean.getFacilitatorId()).putExtra("userid",bean.getUserId()));
                }
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        String loca = UserManager.getInstance(this).getLocation();
        if(loca != null && !"".equals(loca)){
            String[] info = loca.split(",");
            areaID = info[0];
            location = info[1];
            Log.e("数据：=========== ",loca);
        }else {
            areaID = "1740";
            location = "黄岛区";
        }
        mAdapter.setLocation(location);
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
//        NetWorkUtil.setCallback("User/GetWebSupplierList",
//                new BeanGetHomeSup("SF_1001000",pageIndex+"",pageCount+"","3",areaID,"",""),
//                new NetWorkUtil.CallBackListener() {
//                    @Override
//                    public void onFinish(final String respose) {
//                        Log.e("返回值",respose);
//                        mRecyclerView.stopLoad();
//                        try {
//                            ResultGetHomeSup result = new Gson().fromJson(respose, ResultGetHomeSup.class);
        NetWorkUtil.setCallback("HQOAApi/GetActivityHotelList",
                new BeanGetADHotel(areaID,pageIndex+"",pageCount+"",""),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        Log.e("返回值",respose);
                        mRecyclerView.stopLoad();
                        try {
                            ResultActivityHotel result = new Gson().fromJson(respose, ResultActivityHotel.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                mRecyclerView.setTotalCount(result.getTotalCount());
                                if(isRefresh){
                                    mAdapter.setHeadImg(result.getHeadImg());
                                    mAdapter.setStartImgData(result.getStartImgData());
                                    mAdapter.setEndImgListData(result.getEndImgListData());
                                    rule = result.getRulesActivity();
                                    List<Object> list = new ArrayList<>();
                                    list.add("");
                                    if(result.getData().size() > 0){
                                        list.addAll(result.getData());
                                        list.add("");
                                        mAdapter.refresh(list);
                                    }else {
                                        list.add(new ActivityHotelBean(true));
                                        mAdapter.refresh(list);
                                    }
                                }else {
                                    mAdapter.addAll(result.getData());
                                }
                            } else {
                                Toast.makeText(HotelADActivity.this, result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("json解析出错",e.toString());
                            //Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(final String e) {
                        mRecyclerView.stopLoad();
                        Toast.makeText(HotelADActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * 事件总线刷新
     * @param bean
     */
    @Subscribe
    public void onEventMainThread(final EventBusBean bean) {
        runOnUiThread(new Runnable() {
            public void run() {
                if(bean.getType() == Constants.SELECT_HOTEL_CITY){
                    areaID = bean.getCountry().getRegionId();
                    location = bean.getCountry().getRegionName();
                    mAdapter.setLocation(location);
                    refresh();
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        ImmersionBar.with(this).destroy();
        super.onDestroy();
    }

}
