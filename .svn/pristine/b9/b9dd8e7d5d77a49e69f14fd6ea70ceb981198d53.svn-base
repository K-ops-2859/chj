package com.dikai.chenghunjiclient.activity.wedding;

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

import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.bean.BeanEditWeInfo;
import com.dikai.chenghunjiclient.entity.ResultMessage;
import com.dikai.chenghunjiclient.entity.WeProBean;
import com.dikai.chenghunjiclient.util.Constants;
import com.dikai.chenghunjiclient.util.EventBusBean;
import com.dikai.chenghunjiclient.util.NetWorkUtil;
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

public class WeQuwstionActivity extends AppCompatActivity implements View.OnClickListener, OnDateSetListener {

    private WeProBean mWeProBean;
    private int type;
    private LinearLayout dateLayout;
    private LinearLayout ledLayout;
    private LinearLayout ledLayout1;
    private LinearLayout ledLayout2;
    private EditText mEditText;
    private LinearLayout budgetLayout;

    private SpotsDialog mDialog;
    private TimePickerDialog mPickerDialog;
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    private TextView dateText;
    private ImageView ledImg1;
    private ImageView ledImg2;
    private EditText budgetEdit;
    private String isLED = "0";
    private TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_we_quwstion);
        ImmersionBar.with(this)
                .statusBarView(R.id.top_view)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true, 0.2f)
                .init();
        init();
    }

    private void init() {
        type = getIntent().getIntExtra("type",-1);
        mWeProBean = (WeProBean) getIntent().getSerializableExtra("info");
        mDialog = new SpotsDialog(this,R.style.DialogCustom);
        dateLayout = (LinearLayout) findViewById(R.id.ll_date);
        ledLayout = (LinearLayout) findViewById(R.id.ll_led);
        ledLayout1 = (LinearLayout) findViewById(R.id.ll_led1);
        ledLayout2 = (LinearLayout) findViewById(R.id.ll_led2);
        budgetLayout = (LinearLayout) findViewById(R.id.ll_budget);
        mEditText = (EditText) findViewById(R.id.edit_answer);
        dateText = (TextView) findViewById(R.id.tv_date);
        title = (TextView) findViewById(R.id.title);
        ledImg1 = (ImageView) findViewById(R.id.ll_img1);
        ledImg2 = (ImageView) findViewById(R.id.ll_img2);
        budgetEdit = (EditText) findViewById(R.id.ev_budget);
        dateLayout.setOnClickListener(this);
        ledLayout1.setOnClickListener(this);
        ledLayout2.setOnClickListener(this);
        findViewById(R.id.back).setOnClickListener(this);
        findViewById(R.id.save).setOnClickListener(this);
        initDialog();
        if(type == 2){//婚期
            title.setText("婚期");
            dateLayout.setVisibility(View.VISIBLE);
            if(mWeProBean.getWeddingDay() != null && !"".equals(mWeProBean.getWeddingDay())
                    && !"0001-01-01".equals(mWeProBean.getWeddingDay())){
                dateText.setText(mWeProBean.getWeddingDay());
            }
        }else if(type == 3){//LED
            title.setText("是否需要LED屏幕");
            ledLayout.setVisibility(View.VISIBLE);
        }else if(type == 4){//邀请码
            title.setText("邀请码");
            mEditText.setVisibility(View.VISIBLE);
            mEditText.setHint("请输入邀请码");
            if(mWeProBean.getInvitationCode() != null && !"".equals(mWeProBean.getInvitationCode())){
                mEditText.setText(mWeProBean.getInvitationCode());
            }
        }else if(type == 6){//特别说明
            title.setText("特别说明");
            mEditText.setVisibility(View.VISIBLE);
            mEditText.setHint("请输入对于婚礼的特别说明");
            if(mWeProBean.getSpecialVersion() != null && !"".equals(mWeProBean.getSpecialVersion())){
                mEditText.setText(mWeProBean.getSpecialVersion());
            }
        }else {//预算
            title.setText("场布预算");
            budgetLayout.setVisibility(View.VISIBLE);
            if(mWeProBean.getBudget() != 0){
                mEditText.setText(mWeProBean.getSpecialVersion());
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_date:
                mPickerDialog.show(getSupportFragmentManager(), "year_month_day");
                break;
            case R.id.ll_led1:
                ledImg1.setVisibility(View.VISIBLE);
                ledImg2.setVisibility(View.GONE);
                isLED = "0";
                break;
            case R.id.ll_led2:
                ledImg1.setVisibility(View.GONE);
                ledImg2.setVisibility(View.VISIBLE);
                isLED = "1";
                break;
            case R.id.back:
                onBackPressed();
                break;
            case R.id.save:
                if(type == 2){//婚期
                    if(dateText.getText() == null || "".equals(dateText.getText().toString())){
                        Toast.makeText(this, "请选择婚期！", Toast.LENGTH_SHORT).show();
                    }else {
                        saveQuestion("0",dateText.getText().toString(),"2","","");
                    }
                }else if(type == 3){//LED
                    saveQuestion("0","",isLED,"","");
                }else if(type == 4){//邀请码
                    if(mEditText.getText() == null || "".equals(mEditText.getText().toString().trim())){
                        Toast.makeText(this, "请输入邀请码！", Toast.LENGTH_SHORT).show();
                    }else {
                        saveQuestion("0","","2",mEditText.getText().toString().trim(),"");
                    }
                }else if(type == 6){//特别说明
                    if(mEditText.getText() == null || "".equals(mEditText.getText().toString().trim())){
                        Toast.makeText(this, "请输入邀请码！", Toast.LENGTH_SHORT).show();
                    }else {
                        saveQuestion("0","","2","",mEditText.getText().toString().trim());
                    }
                }else {//预算
                    if(budgetEdit.getText() == null || "".equals(budgetEdit.getText().toString().trim())
                            || "0".equals(budgetEdit.getText().toString().trim())){
                        Toast.makeText(this, "请输入场布预算！", Toast.LENGTH_SHORT).show();
                    }else {
                        saveQuestion(budgetEdit.getText().toString().trim(),"","2","","");
                    }
                }
                break;
        }
    }

    private void saveQuestion(String budget,String date, String led, String code, String info) {
        mDialog.show();
        NetWorkUtil.setCallback("HQOAApi/UpNewPeoplePublic",
                new BeanEditWeInfo(mWeProBean.getNewPeopleCustomID(),""+type,budget,date,"","","","","","",led,code,info),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        mDialog.dismiss();
                        Log.e("返回值",respose);
                        try {
                            ResultMessage result = new Gson().fromJson(respose, ResultMessage.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                EventBus.getDefault().post(new EventBusBean(Constants.WE_INFO_CHANGED));
                                finish();
                            } else {
                                Toast.makeText(WeQuwstionActivity.this, "" + result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
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
     * 初始化DatePicker
     */
    private void initDialog(){
        long Years = 50L * 365 * 1000 * 60 * 60 * 24L;
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
    protected void onDestroy() {
        super.onDestroy();
        ImmersionBar.with(this).destroy();
    }

    @Override
    public void onDateSet(TimePickerDialog timePickerView, long millseconds) {
        Date date = new Date(millseconds);
        String time = format.format(date);
        dateText.setText(time);
    }
}
