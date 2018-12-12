package com.dikai.chenghunjiclient.activity.plan;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.bean.BeanAddPlan;
import com.dikai.chenghunjiclient.bean.BeanDriverAddPlan;
import com.dikai.chenghunjiclient.bean.BeanDriverEditPlan;
import com.dikai.chenghunjiclient.bean.BeanEditPlan;
import com.dikai.chenghunjiclient.entity.DayPlanBean;
import com.dikai.chenghunjiclient.entity.DriverPlanBean;
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
import com.ldf.calendar.model.CalendarDate;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.Date;

import dmax.dialog.SpotsDialog;

import static com.yanzhenjie.nohttp.NoHttp.getContext;

public class AddPlanActivity extends AppCompatActivity implements View.OnClickListener, OnDateSetListener {

    private ImageView mBack;
    private TextView mFinish;
    private EditText title;
    private EditText content;
    private TextView date;
    private String time;
    private int type;
    private SpotsDialog mDialog;
    private DayPlanBean mPlanBean;
    private DriverPlanBean mDriverPlanBean;
    private TimePickerDialog mPickerDialog;
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_plan);
        ImmersionBar.with(this)
                .statusBarView(R.id.top_view)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true, 0.2f)
                .init();
        init();
    }

    private void init() {
        type = getIntent().getIntExtra("type",0);
        mDialog = new SpotsDialog(this, R.style.DialogCustom);
        mBack = (ImageView) findViewById(R.id.activity_add_plan_back);
        mFinish = (TextView) findViewById(R.id.activity_add_plan_finish);
        title = (EditText) findViewById(R.id.activity_add_plan_title);
        content = (EditText) findViewById(R.id.activity_add_plan_content);
        date = (TextView) findViewById(R.id.activity_add_plan_time);
        mBack.setOnClickListener(this);
        mFinish.setOnClickListener(this);
        date.setOnClickListener(this);
        initDialog();
        if(type == 1){
            mPlanBean = (DayPlanBean) getIntent().getSerializableExtra("bean");
            title.setText(mPlanBean.getTitle());
            content.setText(mPlanBean.getLogContent());
            time = mPlanBean.getWeddingDate();
            date.setText(time);
        }else if(type == 3){
            mDriverPlanBean = (DriverPlanBean) getIntent().getSerializableExtra("bean");
            title.setText(mDriverPlanBean.getTitle());
            content.setText(mDriverPlanBean.getLogContent());
            time = mDriverPlanBean.getWeddingDate();
            date.setText(time);
        }
    }

    @Override
    public void onClick(View v) {
        if(v == mBack){
            onBackPressed();
        }else if(v == date){
            mPickerDialog.show(getSupportFragmentManager(), "year_month_day");
        }else if(v == mFinish){
            if(title.getText() == null || "".equals(title.getText().toString().trim())){
                Toast.makeText(this, "标题不能为空！", Toast.LENGTH_SHORT).show();
            }else if (time == null || "".equals(time.trim())){
                Toast.makeText(this, "日期不能为空！", Toast.LENGTH_SHORT).show();
            }else if(content.getText() == null || "".equals(content.getText().toString().trim())){
                Toast.makeText(this, "内容不能为空！", Toast.LENGTH_SHORT).show();
            }else {
                if(type == 1){
                    edit();
                }else if(type == 0){
                    add();
                }else if(type == 2){
                    addDriver();
                }else if(type == 3){
                    editDriver();
                }
            }
        }
    }

    /**
     * 初始化DatePicker
     */
    private void initDialog(){
        long tenYears = 10L * 365 * 1000 * 60 * 60 * 24L;
        mPickerDialog = new TimePickerDialog.Builder()
                .setCallBack(this)
                .setCancelStringId("取消")
                .setSureStringId("确定")
                .setTitleStringId("选择日期")
                .setYearText("年")
                .setMonthText("月")
                .setDayText("日")
                .setMinMillseconds(System.currentTimeMillis())
                .setMaxMillseconds(System.currentTimeMillis() + tenYears)
                .setThemeColor(ContextCompat.getColor(getContext(), R.color.main))
                .setType(Type.YEAR_MONTH_DAY)
                .setWheelItemTextNormalColor(ContextCompat.getColor(getContext(), R.color.timetimepicker_default_text_color))
                .setWheelItemTextSelectorColor(ContextCompat.getColor(getContext(), R.color.main))
                .build();
    }

    /**
     * 添加
     */
    private void add() {
        mDialog.show();
        UserInfo userInfo = UserManager.getInstance(this).getUserInfo();
        NetWorkUtil.setCallback("User/AddSupplierSchedule",
                new BeanAddPlan(userInfo.getSupplierID(), title.getText().toString().trim(), time, content.getText().toString().trim()),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        mDialog.dismiss();
                        Log.e("返回值",respose);
                        try {
                            ResultMessage result = new Gson().fromJson(respose, ResultMessage.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                Toast.makeText(AddPlanActivity.this, "添加成功！", Toast.LENGTH_SHORT).show();
                                EventBus.getDefault().post(new EventBusBean(Constants.ADD_REMARK_SUCCESS));
                                finish();
                            } else {
                                Toast.makeText(AddPlanActivity.this, "" + result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
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
     * 修改
     */
    private void edit() {
        mDialog.show();
        UserInfo userInfo = UserManager.getInstance(this).getUserInfo();
        NetWorkUtil.setCallback("User/UpSupplierSchedule",
                new BeanEditPlan(userInfo.getSupplierID(), mPlanBean.getScheduleID(), title.getText().toString().trim(), time, content.getText().toString().trim()),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        mDialog.dismiss();
                        Log.e("返回值",respose);
                        try {
                            ResultMessage result = new Gson().fromJson(respose, ResultMessage.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                Toast.makeText(AddPlanActivity.this, "修改成功！", Toast.LENGTH_SHORT).show();
                                EventBus.getDefault().post(new EventBusBean(Constants.ADD_REMARK_SUCCESS));
                                finish();
                            } else {
                                Toast.makeText(AddPlanActivity.this, "" + result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
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
     * 添加
     */
    private void addDriver() {
        mDialog.show();
        UserInfo userInfo = UserManager.getInstance(this).getUserInfo();
        NetWorkUtil.setCallback("User/AddDriverSchedule",
                new BeanDriverAddPlan(userInfo.getUserID(), title.getText().toString().trim(), time, content.getText().toString().trim()),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        mDialog.dismiss();
                        Log.e("返回值",respose);
                        try {
                            ResultMessage result = new Gson().fromJson(respose, ResultMessage.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                Toast.makeText(AddPlanActivity.this, "添加成功！", Toast.LENGTH_SHORT).show();
                                EventBus.getDefault().post(new EventBusBean(Constants.ADD_REMARK_SUCCESS));
                                finish();
                            } else {
                                Toast.makeText(AddPlanActivity.this, "" + result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
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
     * 修改
     */
    private void editDriver() {
        mDialog.show();
        UserInfo userInfo = UserManager.getInstance(this).getUserInfo();
        NetWorkUtil.setCallback("User/UpDriverSchedule",
                new BeanDriverEditPlan(userInfo.getUserID(), mDriverPlanBean.getScheduleID(), title.getText().toString().trim(), time, content.getText().toString().trim()),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        mDialog.dismiss();
                        Log.e("返回值",respose);
                        try {
                            ResultMessage result = new Gson().fromJson(respose, ResultMessage.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                Toast.makeText(AddPlanActivity.this, "修改成功！", Toast.LENGTH_SHORT).show();
                                EventBus.getDefault().post(new EventBusBean(Constants.ADD_REMARK_SUCCESS));
                                finish();
                            } else {
                                Toast.makeText(AddPlanActivity.this, "" + result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImmersionBar.with(this).destroy();
    }

    @Override
    public void onDateSet(TimePickerDialog timePickerView, long millseconds) {
        time = format.format(new Date(millseconds));
        date.setText(time);
    }
}
