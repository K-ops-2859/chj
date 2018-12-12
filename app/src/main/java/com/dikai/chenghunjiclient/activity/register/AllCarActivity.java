package com.dikai.chenghunjiclient.activity.register;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.bean.BeanGetCar;
import com.dikai.chenghunjiclient.entity.ResultGetAllCar;
import com.dikai.chenghunjiclient.tongxunlu.CarBean;
import com.dikai.chenghunjiclient.tongxunlu.FancyIndexer;
import com.dikai.chenghunjiclient.tongxunlu.PingyinAdapter;
import com.dikai.chenghunjiclient.util.Constants;
import com.dikai.chenghunjiclient.util.EventBusBean;
import com.dikai.chenghunjiclient.util.NetWorkUtil;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

public class AllCarActivity extends AppCompatActivity implements View.OnClickListener {

    private ExpandableListView lv_content;
    private PingyinAdapter adapter;
    private FancyIndexer mFancyIndexer;
    private List<CarBean> empList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_car);
        EventBus.getDefault().register(this);
        ImmersionBar.with(this)
                .statusBarView(R.id.top_view)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true, 0.2f)
                .init();
        initView();
    }

    private void initView() {
        lv_content = (ExpandableListView) findViewById(R.id.lv_content);
        mFancyIndexer = (FancyIndexer) findViewById(R.id.bar);
        lv_content.setGroupIndicator(new GradientDrawable());

        findViewById(R.id.activity_all_car_back).setOnClickListener(this);
        findViewById(R.id.activity_all_car_add).setOnClickListener(this);

        /**加入支持泛型*/
        adapter = new PingyinAdapter(lv_content,new ArrayList<CarBean>());
        adapter.setOnCarClickListener(new PingyinAdapter.OnCarClickListener() {
            @Override
            public void onClick(CarBean bean) {
                EventBus.getDefault().post(new EventBusBean(Constants.SELECT_CAR_BRAND, bean));
                finish();
            }
        });

        mFancyIndexer.setOnTouchLetterChangedListener(new FancyIndexer.OnTouchLetterChangedListener() {
            @Override
            public void onTouchLetterChanged(String letter) {
                for (int i = 0, j = adapter.getKeyMapList().getTypes().size(); i < j; i++) {
                    String str = adapter.getKeyMapList().getTypes().get(i);
                    if (letter.toUpperCase().equals(str.toUpperCase())) {
                        /**跳转到选中的字母表*/
                        lv_content.setSelectedGroup(i);
                    }
                }
            }
        });

        getAllCar();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.activity_all_car_back:
                onBackPressed();
                break;
            case R.id.activity_all_car_add:
                startActivity(new Intent(this, AddBrandActivity.class));
                break;
        }
    }

    /**
     * 获取品牌
     */
    private void getAllCar(){
        NetWorkUtil.setCallback("User/GetCarModelList",
                new BeanGetCar("1","1","200"),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        Log.e("返回值",respose);
                        try {
                            ResultGetAllCar result = new Gson().fromJson(respose, ResultGetAllCar.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                empList = result.getData();
                                /**更新adapter*/
                                adapter.refresh(empList);
                                /**展开并设置adapter*/
                                adapter.expandGroup();
                            } else {
                                Toast.makeText(AllCarActivity.this, result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("json解析出错",e.toString());
                            //Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(final String e) {
                        Toast.makeText(AllCarActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
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
//                if(bean.getType() == Constants.SELECT_COUNTRY){
//                    areaID = bean.getCountry().getRegionId();
//                    mlocationText.setText(bean.getCountry().getRegionName());
//                }else if(bean.getType() == Constants.SELECT_IDENTITY){
//                    mIdentText.setText(bean.getIdentityBean().getOccupationName());
//                    if(bean.getIdentityBean().getOccupationName().equals("酒店")){
//                        areaLayout.setVisibility(View.VISIBLE);
//                    }else {
//                        areaLayout.setVisibility(View.GONE);
//                    }
//                }
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ImmersionBar.with(this).destroy();
        EventBus.getDefault().unregister(this);
    }
}
