package com.dikai.chenghunjiclient.activity.ad;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.activity.me.ServiceActivity;
import com.dikai.chenghunjiclient.bean.BeanAddADCustom;
import com.dikai.chenghunjiclient.citypicker.SelectCityActivity;
import com.dikai.chenghunjiclient.entity.NewUserInfo;
import com.dikai.chenghunjiclient.entity.ResultMessage;
import com.dikai.chenghunjiclient.util.Constants;
import com.dikai.chenghunjiclient.util.EventBusBean;
import com.dikai.chenghunjiclient.util.NetWorkUtil;
import com.dikai.chenghunjiclient.util.UserManager;
import com.dikai.chenghunjiclient.view.MyImageView;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;
import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.data.Type;
import com.jzxiang.pickerview.listener.OnDateSetListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.text.SimpleDateFormat;
import java.util.Date;

import dmax.dialog.SpotsDialog;

public class CustomActivity extends AppCompatActivity implements View.OnClickListener, OnDateSetListener {

    private String code;
    private MyImageView banner;
    private TextView area;
    private TextView date;
    private EditText money;
    private EditText tables;
    private EditText price;
    private EditText remark;
    private TimePickerDialog mPickerDialog;
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    private String areaID;
    private SpotsDialog mDialog;

    private LinearLayout hotelLayout;
    private LinearLayout hunqingLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom);
        EventBus.getDefault().register(this);
        ImmersionBar.with(this)
                .statusBarView(R.id.top_view)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true, 0.2f)
                .init();
        init();
    }

    private void init() {
        code = getIntent().getStringExtra("code");
        mDialog = new SpotsDialog(this,R.style.DialogCustom);
//        TextView title = (TextView) findViewById(R.id.title);
//        title.setText(getIntent().getStringExtra("title"));
        findViewById(R.id.back).setOnClickListener(this);
        findViewById(R.id.call).setOnClickListener(this);
        findViewById(R.id.publish).setOnClickListener(this);
        banner = (MyImageView) findViewById(R.id.banner);
        area = (TextView) findViewById(R.id.area);
        date = (TextView) findViewById(R.id.date);
        tables = (EditText) findViewById(R.id.tables);
        price = (EditText) findViewById(R.id.price);
        money = (EditText) findViewById(R.id.money);
        remark = (EditText) findViewById(R.id.remark);
        hotelLayout = (LinearLayout) findViewById(R.id.hotel_layout);
        hunqingLayout = (LinearLayout) findViewById(R.id.hunqing_layout);
        area.setOnClickListener(this);
        date.setOnClickListener(this);

        long Years = 10L * 365 * 1000 * 60 * 60 * 24L;
        mPickerDialog = new TimePickerDialog.Builder()
                .setCallBack(this)
                .setCancelStringId("取消")
                .setSureStringId("确定")
                .setTitleStringId("选择婚期")
                .setYearText("年")
                .setMonthText("月")
                .setDayText("日")
                .setMinMillseconds(System.currentTimeMillis())
                .setMaxMillseconds(System.currentTimeMillis() + Years)
                .setThemeColor(ContextCompat.getColor(this, R.color.main))
                .setType(Type.YEAR_MONTH_DAY)
                .setWheelItemTextNormalColor(ContextCompat.getColor(this, R.color.timetimepicker_default_text_color))
                .setWheelItemTextSelectorColor(ContextCompat.getColor(this, R.color.main))
                .build();
        if("99C06C5A-DDB8-46A1-B860-CD1227B4DB68".equalsIgnoreCase(code)){//酒店
            hotelLayout.setVisibility(View.VISIBLE);
            hunqingLayout.setVisibility(View.GONE);
        }else {
            hotelLayout.setVisibility(View.GONE);
            hunqingLayout.setVisibility(View.VISIBLE);
        }
        String loca = UserManager.getInstance(this).getLocation();
        if(loca != null && !"".equals(loca)){
            String[] info = loca.split(",");
            areaID = info[0];
            area.setText(info[1]);
            Log.e("数据：=========== ",loca);
        }
        Glide.with(this).load(R.drawable.ic_app_custom).into(banner);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                onBackPressed();
                break;
            case R.id.area:
                startActivity(new Intent(this, SelectCityActivity.class));
                break;
            case R.id.date:
                mPickerDialog.show(getSupportFragmentManager(), "year_month_day");
                break;
            case R.id.call:
                startActivity(new Intent(this, ServiceActivity.class));
                break;
            case R.id.publish:
                if(areaID == null){
                    Toast.makeText(this, "请选择所在区域！", Toast.LENGTH_SHORT).show();
                }else if(!checkInput(date)){
                    Toast.makeText(this, "请选择婚期！", Toast.LENGTH_SHORT).show();
                }else {
                    if("99C06C5A-DDB8-46A1-B860-CD1227B4DB68".equalsIgnoreCase(code)) {//酒店
                        if(!checkInput(tables)){
                            Toast.makeText(this, "请输入预期桌数！", Toast.LENGTH_SHORT).show();
                        }else if(!checkInput(price)){
                            Toast.makeText(this, "请输入用餐标准！", Toast.LENGTH_SHORT).show();
                        }else {
                            publish(code,date.getText().toString(),price.getText().toString(),
                                    tables.getText().toString(),remark.getText() == null?"":remark.getText().toString());
                        }
                    }else {
                        if(!checkInput(money)){
                            Toast.makeText(this, "请输入心理价位！", Toast.LENGTH_SHORT).show();
                        }else {
                            publish(code,date.getText().toString(),money.getText().toString(),"0",remark.getText() == null?"":remark.getText().toString());
                        }
                    }
                }
                break;
        }
    }

    /**
     * 获取记录
     */
    private void publish(String code, String date, String money, String tables, String mark){
        mDialog.show();
        NewUserInfo userInfo = UserManager.getInstance(this).getNewUserInfo();
        NetWorkUtil.setCallback("HQOAApi/AddFacilitatorCustomMade",
                new BeanAddADCustom(code,userInfo.getUserId(),areaID,date, money,tables,mark),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        mDialog.dismiss();
                        Log.e("返回值",respose);
                        try {
                            ResultMessage result = new Gson().fromJson(respose, ResultMessage.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                Toast.makeText(CustomActivity.this, "提交成功", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(CustomActivity.this, result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("json解析出错",e.toString());
                            //Toast.makeText(ADListActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(final String e) {
                        mDialog.dismiss();
//                        Toast.makeText(ADListActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
                    }
                });
    }


    private boolean checkInput(TextView textView){
        if(textView.getText() == null|| "".equals(textView.getText().toString().trim())){
            return false;
        }else {
            return true;
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
                if(bean.getType() == Constants.SELECT_COUNTRY){
                    areaID = bean.getCountry().getRegionId();
                    area.setText(bean.getCountry().getRegionName());
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        ImmersionBar.with(this).destroy();
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public void onDateSet(TimePickerDialog timePickerView, long millseconds) {
        date.setText(format.format(new Date(millseconds)));
    }
}
