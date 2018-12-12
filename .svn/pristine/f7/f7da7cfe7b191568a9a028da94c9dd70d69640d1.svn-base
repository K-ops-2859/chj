package com.dikai.chenghunjiclient.activity.wedding;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.bean.BeanAddPrize;
import com.dikai.chenghunjiclient.bean.GetPrizeBean;
import com.dikai.chenghunjiclient.bean.ShippingAddressBean;
import com.dikai.chenghunjiclient.citypicker.SelectCityActivity;
import com.dikai.chenghunjiclient.entity.ActivityInfoData;
import com.dikai.chenghunjiclient.entity.ResultMessage;
import com.dikai.chenghunjiclient.entity.ShippingAddressData;
import com.dikai.chenghunjiclient.entity.UserInfo;
import com.dikai.chenghunjiclient.util.ActivityManager;
import com.dikai.chenghunjiclient.util.Constants;
import com.dikai.chenghunjiclient.util.EventBusBean;
import com.dikai.chenghunjiclient.util.NetWorkUtil;
import com.dikai.chenghunjiclient.util.UserManager;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * Created by cmk03 on 2017/11/15.
 */

public class ShippingAddressActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvProvince;
    private TextView tvCity;
    private TextView tvArea;
    private String areaID;
    private EditText etName;
    private EditText etPhone;
    private EditText etZipCode;
    private EditText etAddress;
    private ActivityInfoData infoData;
    private int type;
    private int deliveryAddressID;
    private String code="";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipping_address);
        ImmersionBar.with(this)
                .statusBarView(R.id.top_view)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true, 0.2f)
                .init();
        EventBus.getDefault().register(this);
        ImageView mBack = (ImageView) findViewById(R.id.activity_zone_back);
        etName = (EditText) findViewById(R.id.et_name);
        etPhone = (EditText) findViewById(R.id.et_phone);
        etZipCode = (EditText) findViewById(R.id.et_zip_code);
        etAddress = (EditText) findViewById(R.id.et_details_address);
        TextView tvOK = (TextView) findViewById(R.id.tv_ok);

        LinearLayout llSelectProvince = (LinearLayout) findViewById(R.id.ll_select_province);
        tvProvince = (TextView) findViewById(R.id.tv_province);
        LinearLayout llSelectCity = (LinearLayout) findViewById(R.id.ll_select_city);
        tvCity = (TextView) findViewById(R.id.tv_city);
        LinearLayout llSelectArea = (LinearLayout) findViewById(R.id.ll_select_area);
        tvArea = (TextView) findViewById(R.id.tv_area);

        Intent intent = getIntent();
        type = intent.getIntExtra("type", -1);

        findViewById(R.id.address_layout).setOnClickListener(this);
        tvOK.setOnClickListener(this);
        mBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_zone_back:
                finish();
                break;
            case R.id.address_layout:
                startActivity(new Intent(this, SelectCityActivity.class));
                break;
            case R.id.tv_ok:
                verify();
                break;
            default:
                break;
        }
    }

    private void verify() {
        String name = etName.getText().toString();
        String phone = etPhone.getText().toString();
        String zipCode = etZipCode.getText().toString();
        String address = etAddress.getText().toString();
        if (name.equals("")) {
            Toast.makeText(this, "请输入收货人姓名", Toast.LENGTH_SHORT).show();
            return;
        } else if (phone.equals("")) {
            Toast.makeText(this, "请输入手机号", Toast.LENGTH_SHORT).show();
            return;
        } else if (zipCode.equals("")) {
            Toast.makeText(this, "请输入邮编", Toast.LENGTH_SHORT).show();
            return;
        } else if (areaID == null || areaID.equals("")) {
            Toast.makeText(this, "请选择地区", Toast.LENGTH_SHORT).show();
            return;
        } else if (address.equals("")) {
            Toast.makeText(this, "请输入详细地址", Toast.LENGTH_SHORT).show();
            return;
        }

        if (type == 2) {
            getAddressId(areaID, address, name, phone, zipCode);
            System.out.println("微信====");
        } else {
            initData(areaID, address, name, phone, zipCode);
            System.out.println("普通==============");
        }
    }

    private void initData(String areaID, String address, String Consignee, String ConsigneePhone, String PostCode) {
        String objId = getIntent().getStringExtra("id");
        NetWorkUtil.setCallback("Corp/AddDeliveryAddress", new ShippingAddressBean(type + "", objId == null || "0".equals(objId) ?"0":objId,
                areaID, address, Consignee, ConsigneePhone, PostCode), new NetWorkUtil.CallBackListener() {
            @Override
            public void onFinish(String respose) {
                ShippingAddressData data = new Gson().fromJson(respose, ShippingAddressData.class);
                if (data.getMessage().getCode().equals("200")) {
                    EventBus.getDefault().post(new EventBusBean(Constants.EXCHANGE_PRIZE_SUCCESS));
                    Intent intent = new Intent();
                    intent.putExtra("addressID", data.getDeliveryAddressID());
                    ShippingAddressActivity.this.setResult(100, intent);
                    ActivityManager.getInstance().addActivity(ShippingAddressActivity.this);
                    ShippingAddressActivity.this.finish();
                } else {
                    Toast.makeText(ShippingAddressActivity.this, data.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                    Log.e("错误", data.getMessage().getInform());
                }
            }

            @Override
            public void onError(String e) {
                Log.e("", e);
            }
        });
    }

    private void getAddressId(String areaID, String address, String Consignee, String ConsigneePhone, String PostCode) {
        final UserInfo info = UserManager.getInstance(this).getUserInfo();
        NetWorkUtil.setCallback("/Corp/AddDeliveryAddress",
                new ShippingAddressBean(type + "", info.getUserID(), areaID, address, Consignee, ConsigneePhone, PostCode), new NetWorkUtil.CallBackListener() {
            @Override
            public void onFinish(String respose) {
                ShippingAddressData data = new Gson().fromJson(respose, ShippingAddressData.class);
                if (data.getMessage().getCode().equals("200")) {
                    deliveryAddressID = data.getDeliveryAddressID();
                    getInfo(deliveryAddressID);
                } else {
                    Log.e("错误", data.getMessage().getInform());
                }
            }

            @Override
            public void onError(String e) {
                Log.e("", e);
            }
        });
    }

    private void getInfo(final int deliveryAddressID) {
        final UserInfo info = UserManager.getInstance(this).getUserInfo();
        NetWorkUtil.setCallback("Corp/GetActivityInfo",
                new GetPrizeBean(type + "", info.getUserID()), new NetWorkUtil.CallBackListener() {
            @Override
            public void onFinish(String respose) {
                infoData = new Gson().fromJson(respose, ActivityInfoData.class);
                String[] grade = infoData.getGrade();
                if ("200".equals(infoData.getMessage().getCode())) {
                    addPrize(deliveryAddressID, infoData);
                } else if ("201".equals(infoData.getMessage().getCode())) {
                    Log.e("失败",infoData.getMessage().getInform());
                }
            }

            @Override
            public void onError(String e) {
                Log.e("失败",e);
            }
        });
    }

    private void addPrize(int addressId, ActivityInfoData infoData) {
        String code = getIntent().getStringExtra("code");
        if (code != null && !code.equals("")) {
            this.code = code;
        }
        System.out.println("code=======================" + code);
        NetWorkUtil.setCallback("Corp/AddAcquiringPrizes",
                new BeanAddPrize(type + "", UserManager.getInstance(this).getUserInfo().getUserID(),
                infoData.getActivityID() + "", addressId + "", 0 + "", 0, code), new NetWorkUtil.CallBackListener() {
            @Override
            public void onFinish(String respose) {
                ResultMessage data = new Gson().fromJson(respose, ResultMessage.class);
                if (data.getMessage().getCode().equals("200")) {
                    startActivity(new Intent(ShippingAddressActivity.this, GetPrizeSuccessActivity.class));
                } else {
                    Toast.makeText(ShippingAddressActivity.this, data.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(String e) {

            }
        });
    }


    /**
     * 事件总线刷新
     *
     * @param bean
     */
    @Subscribe
    public void onEventMainThread(final EventBusBean bean) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (bean.getType() == Constants.SELECT_PROVINCE) {//省
                    tvProvince.setText(bean.getProvince().getRegionName());
                } else if (bean.getType() == Constants.SELECT_CITY) {//市
                    tvCity.setText(bean.getCity().getRegionName());
                } else if (bean.getType() == Constants.SELECT_COUNTRY) {//区
                    areaID = bean.getCountry().getRegionId();
                    tvArea.setText(bean.getCountry().getRegionName());
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        ImmersionBar.with(this).destroy();
    }
}
