package com.dikai.chenghunjiclient.fragment.plan;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.activity.plan.AddPlanActivity;
import com.dikai.chenghunjiclient.adapter.plan.DriverPlanAdapter;
import com.dikai.chenghunjiclient.adapter.plan.DriverPlanAdapter;
import com.dikai.chenghunjiclient.bean.BeanDelPlan;
import com.dikai.chenghunjiclient.bean.BeanDriverDayPlan;
import com.dikai.chenghunjiclient.bean.BeanDriverDelPlan;
import com.dikai.chenghunjiclient.bean.BeanGetCalendar;
import com.dikai.chenghunjiclient.entity.DriverPlanListBean;
import com.dikai.chenghunjiclient.entity.DriverPlanBean;
import com.dikai.chenghunjiclient.entity.DriverPlanListBean;
import com.dikai.chenghunjiclient.entity.ResultDriverPlan;
import com.dikai.chenghunjiclient.entity.ResultDriverPlan;
import com.dikai.chenghunjiclient.entity.ResultMessage;
import com.dikai.chenghunjiclient.entity.UserInfo;
import com.dikai.chenghunjiclient.util.Constants;
import com.dikai.chenghunjiclient.util.EventBusBean;
import com.dikai.chenghunjiclient.util.NetWorkUtil;
import com.dikai.chenghunjiclient.util.UserManager;
import com.dikai.chenghunjiclient.view.CustomDayView;
import com.dikai.chenghunjiclient.view.MyLoadRecyclerView;
import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.widget.MaterialDialog;
import com.google.gson.Gson;
import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.data.Type;
import com.jzxiang.pickerview.listener.OnDateSetListener;
import com.ldf.calendar.component.CalendarAttr;
import com.ldf.calendar.component.CalendarViewAdapter;
import com.ldf.calendar.interf.OnSelectDateListener;
import com.ldf.calendar.model.CalendarDate;
import com.ldf.calendar.view.Calendar;
import com.ldf.calendar.view.MonthPager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DriverCalenderFragment extends Fragment implements View.OnClickListener, OnDateSetListener {
    private static final int CALL_REQUEST_CODE = 120;
    private MyLoadRecyclerView mRecyclerView;
    private ImageView lastMonth;
    private ImageView nextMonth;
    private TextView nowDate;
    private TextView mark;
    private MonthPager monthPager;
    private ArrayList<Calendar> currentCalendars = new ArrayList<>();
    private CalendarViewAdapter calendarAdapter;
    private OnSelectDateListener onSelectDateListener;
    private int mCurrentPage = MonthPager.CURRENT_DAY_INDEX;
    private CalendarDate currentDate;
    private boolean initiated = false;
    private DriverPlanAdapter mAdapter;
    private ResultDriverPlan nowData;

    private List<DriverPlanBean> mTempList = new ArrayList<>();
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    private SimpleDateFormat format2 = new SimpleDateFormat("yyyy-M-d");
    private SimpleDateFormat formatY = new SimpleDateFormat("yyyy");
    private SimpleDateFormat formatM = new SimpleDateFormat("M");
    private SimpleDateFormat formatD = new SimpleDateFormat("d");
    private String startDate;
    private String endDate;
    private TimePickerDialog mPickerDialog;
    private MaterialDialog dialog;
    private Intent intent;

    private int mPosition;
    private String mPlanID;
    private boolean isFirstInit = true;

    public DriverCalenderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        EventBus.getDefault().register(this);
        return inflater.inflate(R.layout.fragment_driver_calender, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lastMonth = (ImageView) view.findViewById(R.id.last_month);
        nextMonth = (ImageView) view.findViewById(R.id.next_month);
        nowDate = (TextView) view.findViewById(R.id.now_date);
        mark = (TextView) view.findViewById(R.id.fragment_calendar_date);
        monthPager = (MonthPager) view.findViewById(R.id.calendar_view);
        mRecyclerView = (MyLoadRecyclerView)view.findViewById(R.id.list);
        lastMonth.setOnClickListener(this);
        nextMonth.setOnClickListener(this);
        nowDate.setOnClickListener(this);
        mAdapter = new DriverPlanAdapter(getContext());
        mAdapter.setCallClickListener(new DriverPlanAdapter.OnCallClickListener() {
            @Override
            public void onClick(int position, boolean isCall, boolean isDelete, DriverPlanBean bean) {
                if(isCall){
                    intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + bean.getCaptainPhone()));
                    request();
                }else {
                    if(isDelete){
                        mPosition = position;
                        mPlanID = bean.getScheduleID();
                        dialog.show();
                    }else {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("bean",bean);
                        bundle.putInt("type",3);
                        startActivity(new Intent(getContext(), AddPlanActivity.class).putExtras(bundle));
                    }
                }
            }
        });

        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLoadChangeListener(new MyLoadRecyclerView.OnLoadChangeListener() {
            @Override
            public void onRefresh() {
                mRecyclerView.stopLoad();
            }

            @Override
            public void onLoadMore() {
            }
        });
        initCalendarView();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getList(true);
    }

    public void refresh(){
        getList(true);
    }

    @Override
    public void onClick(View v) {
        if(v == lastMonth){
            monthPager.setCurrentItem(monthPager.getCurrentPosition() - 1);
        }else if(v == nextMonth){
            monthPager.setCurrentItem(monthPager.getCurrentPosition() + 1);
        }else if(v == nowDate){
            mPickerDialog.show(getActivity().getSupportFragmentManager(), "year_month_day");
        }
    }

    //===================================================================================================================

    /**
     * 初始化日历
     */
    private void initCalendarView() {
        currentDate = new CalendarDate();
        settime(currentDate.getYear(), currentDate.getMonth() - 1);
        nowDate.setText(currentDate.getYear() + "年" + currentDate.getMonth()+ "月");
        mark.setText(currentDate.getYear() + "年" + currentDate.getMonth()+ "月" + currentDate.getDay()+ "日");
        initListener();
        CustomDayView customDayView = new CustomDayView(getContext(), R.layout.custom_day);
        calendarAdapter = new CalendarViewAdapter(
                getContext(),
                onSelectDateListener,
                CalendarAttr.CalendayType.MONTH,
                customDayView);
        initMonthPager();
        initDialog();
    }

    /**
     * 初始化monthPager，MonthPager继承自ViewPager
     * @return void
     */
    private void initMonthPager() {
        monthPager.setAdapter(calendarAdapter);
        monthPager.setCurrentItem(mCurrentPage - 1);
        monthPager.setPageTransformer(false, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(View page, float position) {
                position = (float) Math.sqrt(1 - Math.abs(position));
                page.setAlpha(position);
            }
        });
        monthPager.addOnPageChangeListener(new MonthPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
                mCurrentPage = position;
                currentCalendars = calendarAdapter.getPagers();
                if (currentCalendars.get(position % currentCalendars.size()) instanceof Calendar) {
                    CalendarDate date = currentCalendars.get(position % currentCalendars.size()).getSeedDate();
                    currentDate = date;
                    settime(currentDate.getYear(), currentDate.getMonth() - 1);
                    nowDate.setText(date.getYear() + "年" + date.getMonth()+ "月");
                    getList(false);
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        calendarAdapter.setMarkData(new HashMap<String, String>());
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
                .setMinMillseconds(System.currentTimeMillis() - tenYears)
                .setMaxMillseconds(System.currentTimeMillis() + tenYears)
                .setThemeColor(ContextCompat.getColor(getContext(), R.color.main))
                .setType(Type.YEAR_MONTH_DAY)
                .setWheelItemTextNormalColor(ContextCompat.getColor(getContext(), R.color.timetimepicker_default_text_color))
                .setWheelItemTextSelectorColor(ContextCompat.getColor(getContext(), R.color.main))
                .build();
        dialog = new MaterialDialog(getContext());
        dialog.isTitleShow(false)//
                .btnNum(2)
                .content("确定删除本条安排吗")//
                .btnText("取消", "确定")
                .setOnBtnClickL(new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        dialog.dismiss();
                    }
                }, new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        dialog.dismiss();
                        deletePlan(mPosition,mPlanID);
                    }
                });
    }

    /**
     * 获取当月档期
     */
    private void getList(final boolean isInit) {
        UserInfo userInfo = UserManager.getInstance(getContext()).getUserInfo();
        NetWorkUtil.setCallback("User/GetDriverScheduleList",
                new BeanDriverDayPlan(userInfo.getUserID(),startDate,endDate),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        Log.e("返回值",respose);
                        try {
                            ResultDriverPlan result = new Gson().fromJson(respose, ResultDriverPlan.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                Log.e("2","执行至此");
                                setMarkData(result, isInit);
                            } else {
                                Toast.makeText(getContext(), "" + result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("json解析出错1",e.toString());
                            //Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(final String e) {
                        Log.e("网络出错",e.toString());
                    }
                });
    }

    /**
     * 删除档期
     */
    private void deletePlan(final int position, String id) {
        UserInfo userInfo = UserManager.getInstance(getContext()).getUserInfo();
        NetWorkUtil.setCallback("User/DelDriverSchedule",
                new BeanDriverDelPlan(userInfo.getUserID(), id),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        Log.e("返回值",respose);
                        try {
                            ResultMessage result = new Gson().fromJson(respose, ResultMessage.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                Toast.makeText(getContext(), "操作成功！", Toast.LENGTH_SHORT).show();
                                mAdapter.remove(position);
                                getList(true);
                            } else {
                                Toast.makeText(getContext(), "" + result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("json解析出错2",e.toString());
                            //Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(final String e) {
                        Log.e("网络出错",e.toString());
                    }
                });
    }

    /**
     * 日期点击监听
     */
    private void initListener() {
        onSelectDateListener = new OnSelectDateListener() {
            @Override
            public void onSelectDate(CalendarDate date) {
                currentDate = date;
                setDayPlan(date);
            }

            @Override
            public void onSelectOtherMonth(int offset) {
                //偏移量 -1表示上一个月 ， 1表示下一个月
                monthPager.selectOtherMonth(offset);
            }
        };
    }

    /**
     * 当月档期
     * @param result
     */
    private void setMarkData(ResultDriverPlan result, boolean isInit) {
        Log.e("3","执行至此");
        nowData = result;
        try {
            List<DriverPlanListBean> list = result.getData();
            HashMap<String , String> markData = new HashMap<>();
            for (DriverPlanListBean bean : list) {
                markData.put(format2.format(format.parse(bean.getWeddingDate())),"1");
            }
            calendarAdapter.setMarkData(markData);
        }catch (Exception e){
            e.printStackTrace();
            Log.e("日期转换出错：",e.toString());
        }
        if(isInit){
            if(isFirstInit){
                isFirstInit = false;
                monthPager.setCurrentItem(monthPager.getCurrentPosition() + 1, true);
                currentDate = new CalendarDate();
            }else {
                calendarAdapter.notifyDataChanged(currentDate);
            }
            calendarAdapter.notifyDataChanged(CalendarViewAdapter.loadDate());
            setDayPlan(currentDate);
        }
    }

    /**
     * 当天安排
     * @param date
     */
    private void setDayPlan(CalendarDate date){
        Log.e("4","执行至此");
        nowDate.setText(date.getYear() + "年" + date.getMonth()+ "月");
        mark.setText(currentDate.getYear() + "年" + currentDate.getMonth()+ "月" + currentDate.getDay()+ "日");
        mRecyclerView.setHasData(false);
        mAdapter.refresh(mTempList);
        String nowDay = "";
        try {
            Date nowDate = format2.parse(date.getYear() + "-" + date.getMonth()+ "-" + date.getDay());
            nowDay = format.format(nowDate);
            Log.e("转换日期：" , nowDay);
        } catch (ParseException e) {
            e.printStackTrace();
            Log.e("日期转换出错：",e.toString());
        }
        for (int i = 0; i < nowData.getData().size(); i++) {
            if(nowDay.equals(nowData.getData().get(i).getWeddingDate())){
                mRecyclerView.setHasData(true);
                mAdapter.refresh(nowData.getData().get(i).getData());
                break;
            }
        }
    }

    /**
     * onWindowFocusChanged回调时，将当前月的种子日期修改为今天
     * @return void
     */
    @Override
    public void onMultiWindowModeChanged(boolean isInMultiWindowMode) {
        super.onMultiWindowModeChanged(isInMultiWindowMode);
        if (isInMultiWindowMode && !initiated) {
            refreshMonthPager();
            initiated = true;
        }
    }

    private void refreshMonthPager() {
//        CalendarDate date = new CalendarDate(2017,7,9);
        CalendarDate today = new CalendarDate();
        calendarAdapter.notifyDataChanged(today);
//        textViewYearDisplay.setText(today.getYear() + "年");
//        textViewMonthDisplay.setText(today.getMonth() + "");
    }

    /**
     * 设置日期
     * @param year
     * @param month
     */
    private void settime(int year, int month){
        startDate = getFirstDayOfMonth(year, month);
        endDate = getLastDayOfMonth(year, month);
        Log.e("当前日期：",startDate + " " +endDate);
    }

    public String getLastDayOfMonth(int year, int month) {
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.set(java.util.Calendar.YEAR, year);
        cal.set(java.util.Calendar.MONTH, month);
        cal.set(java.util.Calendar.DAY_OF_MONTH,cal.getActualMaximum(java.util.Calendar.DATE));
        return  format.format(cal.getTime());
    }

    public String getFirstDayOfMonth(int year, int month) {
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.set(java.util.Calendar.YEAR, year);
        cal.set(java.util.Calendar.MONTH, month);
        cal.set(java.util.Calendar.DAY_OF_MONTH,cal.getMinimum(java.util.Calendar.DATE));
        return   format.format(cal.getTime());
    }

    @Override
    public void onDateSet(TimePickerDialog timePickerView, long millseconds) {
        try {
            int year = Integer.parseInt(formatY.format(new Date(millseconds)));
            int month = Integer.parseInt(formatM.format(new Date(millseconds)));
            int day = Integer.parseInt(formatD.format(new Date(millseconds)));
            settime(year, month - 1);
            nowDate.setText(year + "年" + month+ "月");
            mark.setText(year + "年" + month+ "月" + day+ "日");
            currentDate = new CalendarDate(year, month, day);
            Log.e("1","执行至此");
            getList(true);
        }catch (Exception e){
            Log.e("",e.toString());
        }
    }

    private void request() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {
            //申请CALL_PHONE权限
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE}, CALL_REQUEST_CODE);
        } else {
            startActivity(intent);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        doNext(requestCode, grantResults);
    }

    private void doNext(int requestCode, int[] grantResults) {
        if (requestCode == CALL_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startActivity(intent);
            } else {
                Toast.makeText(getContext(), "已禁止", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * 事件总线刷新
     * @param bean
     */
    @Subscribe
    public void onEventMainThread(final EventBusBean bean) {
        getActivity().runOnUiThread(new Runnable() {
            public void run() {
                if(bean.getType() == Constants.ADD_REMARK_SUCCESS || bean.getType() == Constants.DEL_REMARK_SUCCESS){
                    getList(true);
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

}
