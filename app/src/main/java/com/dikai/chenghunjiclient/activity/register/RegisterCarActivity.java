package com.dikai.chenghunjiclient.activity.register;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.activity.me.MyCarActivity;
import com.dikai.chenghunjiclient.bean.BeanEditUserInfo;
import com.dikai.chenghunjiclient.bean.BeanRegister;
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

public class RegisterCarActivity extends AppCompatActivity implements View.OnClickListener {

    private CarBean carBrand;
    private CarBean carType;
    private TextView brandText;
    private TextView typeText;
    private TextView next;
    private BeanRegister mBeanRegister;
    private int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_car);
        EventBus.getDefault().register(this);
        ImmersionBar.with(this)
                .statusBarView(R.id.top_view)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true, 0.2f)
                .init();
        initView();
    }

    private void initView() {
        type = getIntent().getIntExtra("type",Constants.USER_TYPE_DRIVER);
        brandText = (TextView) findViewById(R.id.activity_register_car_brand);
        typeText = (TextView) findViewById(R.id.activity_register_car_type);
        findViewById(R.id.activity_register_car_back).setOnClickListener(this);
        findViewById(R.id.activity_register_car_type_layout).setOnClickListener(this);
        findViewById(R.id.activity_register_car_brand_layout).setOnClickListener(this);
        next = (TextView) findViewById(R.id.activity_register_car_next);
        next.setOnClickListener(this);
        if(type == Constants.USER_TYPE_DRIVER){
            mBeanRegister = (BeanRegister) getIntent().getSerializableExtra("register");
        }else {
            next.setText("完成");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.activity_register_car_back:
                onBackPressed();
                break;
            case R.id.activity_register_car_type_layout:
                if(carBrand == null){
                    Toast.makeText(this, "请先选择品牌！", Toast.LENGTH_SHORT).show();
                }else {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("brand",carBrand);
                    startActivity(new Intent(this, CarTypeActivity.class).putExtras(bundle));
                }
                break;
            case R.id.activity_register_car_brand_layout:
                startActivity(new Intent(this, AllCarActivity.class));
                break;
            case R.id.activity_register_car_next:
                if(type == Constants.USER_TYPE_DRIVER){
                    if(carType == null){
                        Toast.makeText(this, "请先选择车型！", Toast.LENGTH_SHORT).show();
                    }else {
                        mBeanRegister.setModelID(carType.getCarModelID());
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("register",mBeanRegister);
                        startActivity(new Intent(this, VerifyActivity.class)
                                .putExtras(bundle).putExtra("type",Constants.USER_TYPE_DRIVER));
                    }
                }else {
                    if(carType == null){
                        Toast.makeText(this, "请先选择车型！", Toast.LENGTH_SHORT).show();
                    }else {
                        editInfo(carType.getCarModelID());
                    }
                }
                break;
        }
    }

    /**
     * 更新信息
     */
    private void editInfo(final String mode){
//        NetWorkUtil.setCallback("User/UpUserInfo",
//                new BeanEditUserInfo(UserManager.getInstance(this).getUserInfo().getUserID(),"","","0",mode),
//                new NetWorkUtil.CallBackListener() {
//                    @Override
//                    public void onFinish(final String respose) {
//                        Log.e("返回值",respose);
//                        try {
//                            ResultMessage result = new Gson().fromJson(respose, ResultMessage.class);
//                            if ("200".equals(result.getMessage().getCode())) {
//                                Toast.makeText(RegisterCarActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
//                                EventBus.getDefault().post(new EventBusBean(Constants.EDIT_MY_CAR,mode));
//                                finish();
//                            } else {
//                                Toast.makeText(RegisterCarActivity.this, result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
//                            }
//                        } catch (Exception e) {
//                            Log.e("json解析出错",e.toString());
//                            //Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//
//                    @Override
//                    public void onError(final String e) {
//                        Toast.makeText(RegisterCarActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
//                    }
//                });
    }

    /**
     * 事件总线刷新
     * @param bean
     */
    @Subscribe
    public void onEventMainThread(final EventBusBean bean) {
        runOnUiThread(new Runnable() {
            public void run() {
                if(bean.getType() == Constants.SELECT_CAR_BRAND){
                    carBrand = bean.getCarBean();
                    brandText.setText(carBrand.getName());
                }else if(bean.getType() == Constants.SELECT_CAR_TYPE){
                    carType = bean.getCarBean();
                    typeText.setText(carType.getName());
                }else if(bean.getType() == Constants.USER_REGISTER_SUCCESS){
                    finish();
                }
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
