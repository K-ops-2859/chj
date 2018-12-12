package com.dikai.chenghunjiclient.activity.register;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.adapter.me.CarTypeAdapter;
import com.dikai.chenghunjiclient.adapter.store.RoomPhotoAdapter;
import com.dikai.chenghunjiclient.bean.BeanGetCar;
import com.dikai.chenghunjiclient.entity.CarsBean;
import com.dikai.chenghunjiclient.entity.ResultGetAllCar;
import com.dikai.chenghunjiclient.tongxunlu.CarBean;
import com.dikai.chenghunjiclient.util.Constants;
import com.dikai.chenghunjiclient.util.EventBusBean;
import com.dikai.chenghunjiclient.util.NetWorkUtil;
import com.dikai.chenghunjiclient.view.MyLoadRecyclerView;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CarTypeActivity extends AppCompatActivity implements View.OnClickListener {

    private CarBean brand;
    private TextView title;
    private MyLoadRecyclerView mRecyclerView;
    private CarTypeAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_type);
        ImmersionBar.with(this)
                .statusBarView(R.id.top_view)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true, 0.2f)
                .init();
        initView();
    }

    private void initView() {
        brand = (CarBean) getIntent().getSerializableExtra("brand");
        title = (TextView) findViewById(R.id.activity_car_type_title);
        findViewById(R.id.activity_car_type_back).setOnClickListener(this);
        findViewById(R.id.activity_car_type_finish).setOnClickListener(this);
        mRecyclerView = (MyLoadRecyclerView) findViewById(R.id.activity_car_type_recycler);
        mAdapter = new CarTypeAdapter(this);
        mAdapter.setOnCarClickListener(new CarTypeAdapter.OnCarClickListener() {
            @Override
            public void onClick(CarBean bean) {
                EventBus.getDefault().post(new EventBusBean(Constants.SELECT_CAR_TYPE, bean));
                finish();
            }
        });

        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLoadChangeListener(new MyLoadRecyclerView.OnLoadChangeListener() {
            @Override
            public void onRefresh() {
                getAllCar();
            }

            @Override
            public void onLoadMore() {
            }
        });

        if(brand != null){
            title.setText(brand.getName());
            getAllCar();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.activity_car_type_back:
                onBackPressed();
                break;
            case R.id.activity_car_type_finish:
                if(brand != null){
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("brand",brand);
                    startActivity(new Intent(this, AddCarActivity.class).putExtras(bundle));
                }
                break;

        }
    }

    /**
     * 获取型号
     */
    private void getAllCar(){
        NetWorkUtil.setCallback("User/GetCarModelList",
                new BeanGetCar(brand.getCarModelID(),"1","200"),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        mRecyclerView.stopLoad();
                        Log.e("返回值",respose);
                        try {
                            ResultGetAllCar result = new Gson().fromJson(respose, ResultGetAllCar.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                mAdapter.refresh(result.getData());
                            } else {
                                Toast.makeText(CarTypeActivity.this, result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("json解析出错",e.toString());
                            //Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(final String e) {
                        mRecyclerView.stopLoad();
                        Toast.makeText(CarTypeActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    protected void onDestroy() {
        ImmersionBar.with(this).destroy(); //必须调用该方法，防止内存泄漏
        super.onDestroy();
    }
    
}
