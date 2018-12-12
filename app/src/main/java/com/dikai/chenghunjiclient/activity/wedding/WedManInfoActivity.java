package com.dikai.chenghunjiclient.activity.wedding;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.bean.BeanEditNewsInfo;
import com.dikai.chenghunjiclient.entity.NewUserInfo;
import com.dikai.chenghunjiclient.entity.ResultGetNewsInfo;
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

public class WedManInfoActivity extends AppCompatActivity implements View.OnClickListener, OnDateSetListener {

    private EditText evName;
    private EditText evPhone;
    private TextView tvBirthday;
    private TextView tvStar;
    private EditText editPlace;
    private EditText editProfession;
    private EditText editQQ;
    private EditText editWX;
    private TimePickerDialog mPickerDialog;
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    private SimpleDateFormat format1 = new SimpleDateFormat("MM");
    private SimpleDateFormat format2 = new SimpleDateFormat("dd");
    private ResultGetNewsInfo mNewsInfo;
    private SpotsDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wed_man_info);
        ImmersionBar.with(this)
                .statusBarView(R.id.top_view)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true, 0.2f)
                .init();
        init();
    }

    private void init() {
        mDialog = new SpotsDialog(this,R.style.DialogCustom);
        mNewsInfo = (ResultGetNewsInfo) getIntent().getSerializableExtra("info");
        tvBirthday = (TextView) findViewById(R.id.tv_birthday);
        evName = (EditText) findViewById(R.id.ev_name);
        evPhone = (EditText) findViewById(R.id.ev_phone);
        tvStar = (TextView) findViewById(R.id.tv_start);
        editPlace = (EditText) findViewById(R.id.ev_place);
        editProfession = (EditText) findViewById(R.id.ev_profession);
        editQQ = (EditText) findViewById(R.id.ev_qq);
        editWX = (EditText) findViewById(R.id.ev_wx);
        findViewById(R.id.ll_birthday).setOnClickListener(this);
        findViewById(R.id.back).setOnClickListener(this);
        findViewById(R.id.save).setOnClickListener(this);
        initDialog();
        evName.setText(mNewsInfo.getName());
        evPhone.setText(mNewsInfo.getPhone());
        tvStar.setText(mNewsInfo.getConstellation());
        editPlace.setText(mNewsInfo.getPlaceOfOrigin());
        editProfession.setText(mNewsInfo.getOccupation());
        editQQ.setText(mNewsInfo.getQQNumber());
        editWX.setText(mNewsInfo.getWechatNumber());
        if(mNewsInfo.getDateOfBirth() != null && !"0001-01-01".equals(mNewsInfo.getDateOfBirth())){
            tvBirthday.setText(mNewsInfo.getDateOfBirth());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                onBackPressed();
                break;
            case R.id.ll_birthday:
                mPickerDialog.show(getSupportFragmentManager(), "year_month_day");
                break;
            case R.id.save:
                if(!checkEdit(evName)){
                    Toast.makeText(this, "姓名不能为空！", Toast.LENGTH_SHORT).show();
                }else if(!checkEdit(evPhone)){
                    Toast.makeText(this, "手机不能为空！", Toast.LENGTH_SHORT).show();
                }else {
                    editInfo(evName.getText().toString().trim(),
                            checkText(tvBirthday)?tvBirthday.getText().toString().trim():"",
                            checkEdit(editPlace)?editPlace.getText().toString().trim():"",
                            checkText(tvStar)?tvStar.getText().toString().trim():"",
                            checkEdit(editProfession)?editProfession.getText().toString().trim():"",
                            evPhone.getText().toString().trim(),
                            checkEdit(editQQ)?editQQ.getText().toString().trim():"",
                            checkEdit(editWX)?editWX.getText().toString().trim():"");
                }
                break;
        }
    }

    private boolean checkEdit(EditText text){
        if(text.getText() == null || "".equals(text.getText().toString().trim())){
            return false;
        }else {
            return true;
        }
    }

    private boolean checkText(TextView text){
        if(text.getText() == null || "".equals(text.getText().toString().trim())){
            return false;
        }else {
            return true;
        }
    }

    /**
     * 初始化DatePicker
     */
    private void initDialog(){
        long Years = 100L * 365 * 1000 * 60 * 60 * 24L;
        mPickerDialog = new TimePickerDialog.Builder()
                .setCallBack(this)
                .setCancelStringId("取消")
                .setSureStringId("确定")
                .setTitleStringId("选择日期")
                .setYearText("年")
                .setMonthText("月")
                .setDayText("日")
                .setMinMillseconds(System.currentTimeMillis() - Years)
                .setMaxMillseconds(System.currentTimeMillis())
                .setThemeColor(ContextCompat.getColor(getContext(), R.color.main))
                .setType(Type.YEAR_MONTH_DAY)
                .setWheelItemTextNormalColor(ContextCompat.getColor(getContext(), R.color.timetimepicker_default_text_color))
                .setWheelItemTextSelectorColor(ContextCompat.getColor(getContext(), R.color.main))
                .build();
    }

    private void editInfo(String name,String birth,String place,String star,String profession,String phone,String qq,String wx) {
        mDialog.show();
        NewUserInfo userInfo = UserManager.getInstance(getContext()).getNewUserInfo();
        NetWorkUtil.setCallback("HQOAApi/UpNewPeopleInfo",
                new BeanEditNewsInfo(userInfo.getUserId(),name,birth,place,star,profession,phone,qq,wx),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        mDialog.dismiss();
                        Log.e("返回值",respose);
                        try {
                            ResultMessage result = new Gson().fromJson(respose, ResultMessage.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                EventBus.getDefault().post(new EventBusBean(Constants.NEWS_INFO_CHANGED));
                                finish();
                            } else {
                                Toast.makeText(getContext(), "" + result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("json解析出错",e.toString());
                            //Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(final String e) {
                        mDialog.dismiss();
                        Log.e("网络出错",e.toString());
                    }
                });
    }

    /**
     * 判断星座
     */
    private static String getAstro(int month, int day) {
        String[] starArr = {"魔羯座","水瓶座", "双鱼座", "白羊座",
                "金牛座", "双子座", "巨蟹座", "狮子座", "处女座", "天秤座", "天蝎座", "射手座" };
        int[] DayArr = {22, 20, 19, 21, 21, 21, 22, 23, 23, 23, 23, 22};  // 两个星座分割日
        int index = month;
        // 所查询日期在分割日之前，索引-1，否则不变
        if (day < DayArr[month - 1]) {
            index = index - 1;
        }
        // 返回索引指向的星座string
        return starArr[index];
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImmersionBar.with(this).destroy();
    }

    @Override
    public void onDateSet(TimePickerDialog timePickerView, long millseconds) {
        Date date = new Date(millseconds);
        String time = format.format(date);
        tvBirthday.setText(time);
        int month = Integer.parseInt(format1.format(date));
        int day = Integer.parseInt(format2.format(date));
        tvStar.setText(getAstro(month,day));
    }
}
