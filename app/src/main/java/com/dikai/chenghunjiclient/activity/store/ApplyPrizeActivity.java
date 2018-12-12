package com.dikai.chenghunjiclient.activity.store;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.bean.BeanApplyPrize;
import com.dikai.chenghunjiclient.entity.NewUserInfo;
import com.dikai.chenghunjiclient.entity.ResultMessage;
import com.dikai.chenghunjiclient.entity.UserInfo;
import com.dikai.chenghunjiclient.util.Constants;
import com.dikai.chenghunjiclient.util.EventBusBean;
import com.dikai.chenghunjiclient.util.NetWorkUtil;
import com.dikai.chenghunjiclient.util.UserManager;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;
import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.data.Type;
import com.jzxiang.pickerview.listener.OnDateSetListener;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.Date;

import dmax.dialog.SpotsDialog;

import static com.yanzhenjie.nohttp.NoHttp.getContext;

public class ApplyPrizeActivity extends AppCompatActivity implements View.OnClickListener, OnDateSetListener {

    private EditText name;
    private EditText phone;
    private EditText money;
    private TextView date;
    private EditText wedCorp;
    private EditText hotel;
    private TimePickerDialog mPickerDialog;
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    private SpotsDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_prize);
        ImmersionBar.with(this)
                .statusBarView(R.id.top_view)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true, 0.2f)
                .init();
        init();
    }

    private void init() {
        mDialog = new SpotsDialog(this,R.style.DialogCustom);
        findViewById(R.id.back).setOnClickListener(this);
        findViewById(R.id.publish).setOnClickListener(this);
        findViewById(R.id.date_layout).setOnClickListener(this);
        name = (EditText) findViewById(R.id.name);
        phone = (EditText) findViewById(R.id.phone);
        money = (EditText) findViewById(R.id.money);
        hotel = (EditText) findViewById(R.id.hotel_book);
        date = (TextView) findViewById(R.id.date);
        wedCorp = (EditText) findViewById(R.id.area);
        initDialog();
    }

    private void initDialog() {
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
                .setThemeColor(ContextCompat.getColor(getContext(), R.color.main))
                .setType(Type.YEAR_MONTH_DAY)
                .setWheelItemTextNormalColor(ContextCompat.getColor(getContext(), R.color.timetimepicker_default_text_color))
                .setWheelItemTextSelectorColor(ContextCompat.getColor(getContext(), R.color.main))
                .build();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                onBackPressed();
                break;
            case R.id.date_layout:
                mPickerDialog.show(getSupportFragmentManager(), "year_month_day");
                break;
            case R.id.publish:
                if(canUse(phone)){
                    String nameStr = canUse(name)? date.getText().toString():"";
                    String weddingDate = canUse(date)? date.getText().toString():"";
                    String weddingMoney = canUse(money)? money.getText().toString():"";
                    String weddingHotel = canUse(hotel)? hotel.getText().toString():"";
                    String hunqing = canUse(wedCorp)? wedCorp.getText().toString():"";
                    apply(nameStr,phone.getText().toString().trim(),weddingDate,weddingMoney,hunqing,weddingHotel);
                }else {
                    Toast.makeText(this, "请输入手机！", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private boolean canUse(TextView textView){
        if(textView.getText() == null|| "".equals(textView.getText().toString().trim())){
            return false;
        }else {
            return true;
        }
    }

    /**
     * 申请
     */
    private void apply(String name, String phone,String weddingDate,String money,String hunqing,String hotel){
        mDialog.show();
        NewUserInfo info = UserManager.getInstance(this).getNewUserInfo();
        NetWorkUtil.setCallback("HQOAApi/WeddingAmountApplication",
                new BeanApplyPrize(info.getUserId(),name,phone,weddingDate,money,hunqing,hotel),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        mDialog.dismiss();
                        Log.e("返回值",respose);
                        try {
                            ResultMessage result = new Gson().fromJson(respose, ResultMessage.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                Toast.makeText(ApplyPrizeActivity.this, "邀请成功！", Toast.LENGTH_LONG).show();
                                EventBus.getDefault().post(new EventBusBean(Constants.APPLY_WED_PRIZE));
                                finish();
                            } else {
                                Toast.makeText(getContext(), result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("json解析出错",e.toString());
                            //Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(final String e) {
                        mDialog.dismiss();
                        Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onDestroy() {
        ImmersionBar.with(this).destroy();
        super.onDestroy();
    }

    @Override
    public void onDateSet(TimePickerDialog timePickerView, long millseconds) {
        date.setText(format.format(new Date(millseconds)));
    }
}
