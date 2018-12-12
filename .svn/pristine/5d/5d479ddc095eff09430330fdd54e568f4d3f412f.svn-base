package com.dikai.chenghunjiclient.activity.me;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.activity.register.CarTypeActivity;
import com.dikai.chenghunjiclient.activity.register.RegisterCarActivity;
import com.dikai.chenghunjiclient.bean.BeanCarInfo;
import com.dikai.chenghunjiclient.bean.BeanEditUserInfo;
import com.dikai.chenghunjiclient.entity.ResultGetCarInfo;
import com.dikai.chenghunjiclient.entity.ResultMessage;
import com.dikai.chenghunjiclient.tongxunlu.CarBean;
import com.dikai.chenghunjiclient.util.Constants;
import com.dikai.chenghunjiclient.util.EventBusBean;
import com.dikai.chenghunjiclient.util.NetWorkUtil;
import com.dikai.chenghunjiclient.util.UserManager;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class MyCarActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mBack;
    private TextView edit;
    private ImageView pic;
    private TextView brand;
    private TextView type;
    private TextView color;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_car);
        ImmersionBar.with(this)
                .statusBarView(R.id.top_view)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true, 0.2f)
                .init();
        init();
    }

    private void init() {
        EventBus.getDefault().register(this);
        mBack = (ImageView) findViewById(R.id.activity_my_car_back);
        pic = (ImageView) findViewById(R.id.activity_my_car_pic);
        edit = (TextView) findViewById(R.id.activity_my_car_edit);
        brand = (TextView) findViewById(R.id.activity_my_car_brand);
        type = (TextView) findViewById(R.id.activity_my_car_type);
        color = (TextView) findViewById(R.id.activity_my_car_color);
        mBack.setOnClickListener(this);
        edit.setOnClickListener(this);
        getIdentity(UserManager.getInstance(this).getUserInfo().getModelID());
    }

    @Override
    public void onClick(View v) {
        if(v == mBack){
            onBackPressed();
        }else {
            startActivity(new Intent(this, RegisterCarActivity.class).putExtra("type",1));
        }
    }

    private void setData(ResultGetCarInfo result) {
        brand.setText(result.getBrandName());
        type.setText(result.getName());
        color.setText(result.getColor());
        Glide.with(this).load(result.getCarImg()).into(pic);
    }

    /**
     * 获取信息
     */
    private void getIdentity(String mode){
        NetWorkUtil.setCallback("User/GetCarModel",
                new BeanCarInfo(mode),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        Log.e("返回值",respose);
                        try {
                            ResultGetCarInfo result = new Gson().fromJson(respose, ResultGetCarInfo.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                setData(result);
                            } else {
                                Toast.makeText(MyCarActivity.this, result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("json解析出错",e.toString());
                            //Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(final String e) {
                        Toast.makeText(MyCarActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
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
                if(bean.getType() == Constants.EDIT_MY_CAR){
                    getIdentity(bean.getData());
                    EventBus.getDefault().post(new EventBusBean(Constants.USER_RELOGIN));
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        ImmersionBar.with(this).destroy();
    }
}
