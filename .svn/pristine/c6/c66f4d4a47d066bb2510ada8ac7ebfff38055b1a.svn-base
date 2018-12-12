package com.dikai.chenghunjiclient.activity.ad;

import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.adapter.SimplePicAdapter;
import com.dikai.chenghunjiclient.adapter.ad.KeYuanAdapter;
import com.dikai.chenghunjiclient.adapter.ad.TypeSelectAdapter;
import com.dikai.chenghunjiclient.bean.BeanGetKeyuan;
import com.dikai.chenghunjiclient.bean.BeanPublishKeYuan;
import com.dikai.chenghunjiclient.entity.NewIdentityBean;
import com.dikai.chenghunjiclient.entity.ResultGetKeyuan;
import com.dikai.chenghunjiclient.entity.ResultMessage;
import com.dikai.chenghunjiclient.entity.ResultNewIdentity;
import com.dikai.chenghunjiclient.util.NetWorkUtil;
import com.dikai.chenghunjiclient.util.UserManager;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;
import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.data.Type;
import com.jzxiang.pickerview.listener.OnDateSetListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import dmax.dialog.SpotsDialog;

public class KeYuanActivity extends AppCompatActivity implements View.OnClickListener, OnDateSetListener {

    private RecyclerView mSelectRecycler;
    private TypeSelectAdapter mSelectAdapter;
    private RelativeLayout typeLayout;
    private TranslateAnimation mShowAction;
    private TranslateAnimation mHiddenAction;
    private TextView typeText;
    private TextView typeText2;
    private boolean isShowIden = false;
    private NewIdentityBean nowIdentity;
    private LinearLayout topLayout;

    private TextView dateText;
    private TextView dateText2;
    private ImageView date;
    private ImageView date2;
    private TimePickerDialog mPickerDialog;
    private TimePickerDialog mPickerDialog2;
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    private boolean isStartTime = true;
    private String startDate = "";
    private String endDate = "";

    private int pageIndex = 1;
    private int itemCount = 20;
    private RecyclerView sourceRecycler;
    private KeYuanAdapter mKeYuanAdapter;

    private SimplePicAdapter picAdapter;
    private RecyclerView pics;
    private NestedScrollView mScrollView;
    private LinearLayout titleLayout;
    private LinearLayout titleLayout2;
    private LinearLayout barLayout;
    private boolean isTabShow = false;
    private int totalNum;
    private ResultNewIdentity mIdentityData;
    private SwipeRefreshLayout fresh;
    private SpotsDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ke_yuan);
        ImmersionBar.with(this)
                .statusBarView(R.id.top_view)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true, 0.2f)
                .init();
        init();
        initDialog();
    }

    private void init() {
        mDialog = new SpotsDialog(this,R.style.DialogCustom);
        nowIdentity = new NewIdentityBean("00000000-0000-0000-0000-000000000000","全部需求");
        findViewById(R.id.back).setOnClickListener(this);
        findViewById(R.id.bg_view).setOnClickListener(this);
        findViewById(R.id.close).setOnClickListener(this);
        findViewById(R.id.publish).setOnClickListener(this);
        typeText = (TextView)findViewById(R.id.type_text);
        typeText2 = (TextView)findViewById(R.id.type_text2);
        dateText = (TextView)findViewById(R.id.date_text);
        dateText2 = (TextView)findViewById(R.id.date_text2);
        typeLayout = (RelativeLayout) findViewById(R.id.type_layout);
        date = (ImageView) findViewById(R.id.date);
        date2 = (ImageView) findViewById(R.id.date2);
        fresh = (SwipeRefreshLayout) findViewById(R.id.my_load_recycler_fresh);

        pics = (RecyclerView) findViewById(R.id.pics);
        picAdapter = new SimplePicAdapter(this);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        pics.setNestedScrollingEnabled(false);
        pics.setLayoutManager(mLayoutManager);
        pics.setAdapter(picAdapter);

        topLayout = (LinearLayout) findViewById(R.id.top_layout);
        mSelectRecycler = (RecyclerView) findViewById(R.id.type_recycler);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        mSelectRecycler.setLayoutManager(gridLayoutManager);
        mSelectAdapter = new TypeSelectAdapter(this);
        mSelectAdapter.setOnItemClickListener(new TypeSelectAdapter.OnItemClickListener() {
            @Override
            public void onClick(NewIdentityBean bean) {
                hidden();
                if(!nowIdentity.getOccupationID().equals(bean.getOccupationID())){
                    typeText.setText(bean.getOccupationName());
                    typeText2.setText(bean.getOccupationName());
                    nowIdentity = bean;
                    refresh();
                }
            }
        });
        mSelectRecycler.setAdapter(mSelectAdapter);

        sourceRecycler = (RecyclerView) findViewById(R.id.source);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        sourceRecycler.setLayoutManager(linearLayoutManager);
        sourceRecycler.setNestedScrollingEnabled(false);
        mKeYuanAdapter = new KeYuanAdapter(this);
        sourceRecycler.setAdapter(mKeYuanAdapter);

        barLayout = (LinearLayout) findViewById(R.id.bar_layout);
        titleLayout = (LinearLayout) findViewById(R.id.title_layout);
        titleLayout2 = (LinearLayout) findViewById(R.id.title_layout2);
        mScrollView = (NestedScrollView) findViewById(R.id.scrollview);
        mScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
//                Log.e("scrollY:", scrollY + " ===oldScrollY:" + oldScrollY + " ===== now:" + (v.getChildAt(0).getHeight() - v.getHeight()));
                if(scrollY > titleLayout.getBottom() && !isTabShow){
                    isTabShow = true;
                    titleLayout2.setVisibility(View.VISIBLE);
                    barLayout.setVisibility(View.GONE);
                }else if(scrollY < titleLayout.getBottom() && isTabShow){
                    isTabShow = false;
                    titleLayout2.setVisibility(View.GONE);
                    barLayout.setVisibility(View.VISIBLE);
                }
                if(mKeYuanAdapter.getItemCount() < totalNum && scrollY == (v.getChildAt(0).getHeight() - v.getHeight())){
                    Log.e("totalNum:", totalNum + " ===== mZixunAdapter.getItemCount():" + mKeYuanAdapter.getItemCount());
                    pageIndex++;
                    getKeyuan(pageIndex,itemCount,false);
                }
            }
        });

        //设置刷新时动画的颜色，可以设置4个
        fresh.setColorSchemeResources(R.color.main);
        fresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fresh.post(new Runnable() {
                    @Override
                    public void run() {
                        fresh.setRefreshing(true);
                    }
                });
                refresh();
            }
        });
        date.setOnClickListener(this);
        date2.setOnClickListener(this);
        typeText.setOnClickListener(this);
        typeText2.setOnClickListener(this);
    }

    private void initDialog() {
        mShowAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, -1.0f,
                Animation.RELATIVE_TO_SELF, 0.0f);
        mShowAction.setDuration(260);

        mHiddenAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, -1.0f);
        mHiddenAction.setDuration(260);
        mHiddenAction.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}
            @Override
            public void onAnimationEnd(Animation animation) {
                typeLayout.setVisibility(View.GONE);
            }
            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
        long Years = 10L * 365 * 1000 * 60 * 60 * 24L;
        mPickerDialog = new TimePickerDialog.Builder()
                .setCallBack(this)
                .setCancelStringId("取消")
                .setSureStringId("确定")
                .setTitleStringId("婚期开始时间")
                .setYearText("年")
                .setMonthText("月")
                .setDayText("日")
                .setMinMillseconds(System.currentTimeMillis() - Years)
                .setMaxMillseconds(System.currentTimeMillis() + Years)
                .setThemeColor(ContextCompat.getColor(this, R.color.main))
                .setType(Type.YEAR_MONTH_DAY)
                .setWheelItemTextNormalColor(ContextCompat.getColor(this, R.color.timetimepicker_default_text_color))
                .setWheelItemTextSelectorColor(ContextCompat.getColor(this, R.color.main))
                .build();

        mPickerDialog2 = new TimePickerDialog.Builder()
                .setCallBack(this)
                .setCancelStringId("取消")
                .setSureStringId("确定")
                .setTitleStringId("婚期结束时间")
                .setYearText("年")
                .setMonthText("月")
                .setDayText("日")
                .setMinMillseconds(System.currentTimeMillis() - Years)
                .setMaxMillseconds(System.currentTimeMillis() + Years)
                .setThemeColor(ContextCompat.getColor(this, R.color.main))
                .setType(Type.YEAR_MONTH_DAY)
                .setWheelItemTextNormalColor(ContextCompat.getColor(this, R.color.timetimepicker_default_text_color))
                .setWheelItemTextSelectorColor(ContextCompat.getColor(this, R.color.main))
                .build();
        refresh();
    }

    private void refresh() {
        if(mIdentityData == null){
            getIdentity();
        }
        pageIndex = 1;
        itemCount = 20;
        getKeyuan(pageIndex,itemCount,true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                onBackPressed();
                break;
            case R.id.date:
            case R.id.date2:
                isStartTime = true;
                mPickerDialog.show(getSupportFragmentManager(), "year_month_day");
                break;
            case R.id.type_text:
            case R.id.type_text2:
                if(isShowIden){
                    hidden();
                }else {
                    show();
                }
                break;
            case R.id.close:
            case R.id.bg_view:
                hidden();
                break;
            case R.id.publish:
                publish();
                break;
        }
    }

    private void show(){
        isShowIden = true;
        typeLayout.setVisibility(View.VISIBLE);
        topLayout.clearAnimation();
        topLayout.setVisibility(View.VISIBLE);
        topLayout.startAnimation(mShowAction);
    }

    private void hidden(){
        isShowIden = false;
        topLayout.clearAnimation();
        topLayout.setVisibility(View.GONE);
        topLayout.startAnimation(mHiddenAction);
    }

    public void stopLoad(){
        if(fresh.isRefreshing()){
            fresh.post(new Runnable() {
                @Override
                public void run() {
                    fresh.setRefreshing(false);
                }
            });
        }
    }

    /**
     * 获取职业
     */
    private void getIdentity(){
        UserManager.getInstance(this).getProfession(2, new UserManager.OnGetIdentListener() {
            @Override
            public void onFinish(ResultNewIdentity result) {
                List<NewIdentityBean> list = new ArrayList<>();
                list.add(new NewIdentityBean("00000000-0000-0000-0000-000000000000","全部需求"));
                list.addAll(result.getData());
                mIdentityData = result;
                mSelectAdapter.refresh(list);
            }

            @Override
            public void onError(String e) {
                Log.e("出错",e);
            }
        });
    }

    /**
     * 获取客源
     */
    private void getKeyuan(int pageIndex, int pageCount, final boolean isRefresh){
//        NetWorkUtil.setCallback("HQOAApi/GetJSJTableList",
//                new BeanGetKeyuan(nowIdentity.getOccupationID(),startDate,endDate,pageIndex,pageCount,0),
//                new NetWorkUtil.CallBackListener() {
//                    @Override
//                    public void onFinish(final String respose) {
//                        Log.e("返回值",respose);
//                        stopLoad();
//                        try {
//                            ResultGetKeyuan result = new Gson().fromJson(respose, ResultGetKeyuan.class);
//                            if ("200".equals(result.getMessage().getCode())) {
//                                totalNum = result.getTotalCount();
//                                if(isRefresh){
//                                    picAdapter.refresh(Arrays.asList(result.getImg().split(",")));
//                                    mKeYuanAdapter.refresh(result.getData());
//                                }else {
//                                    mKeYuanAdapter.addAll(result.getData());
//                                }
//                            } else {
//                                Toast.makeText(KeYuanActivity.this, result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
//                            }
//                        } catch (Exception e) {
//                            Log.e("json解析出错",e.toString());
//                            //Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//
//                    @Override
//                    public void onError(final String e) {
//                        stopLoad();
//                        Toast.makeText(KeYuanActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
//                    }
//                });
    }

    /**
     * 提交
     */
    private void publish(){
        mDialog.show();
        NetWorkUtil.setCallback("HQOAApi/CreateAgentApplyTable",
                new BeanPublishKeYuan(UserManager.getInstance(this).getNewUserInfo().getUserId(),""),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        mDialog.dismiss();
                        Log.e("返回值",respose);
                        try {
                            ResultMessage result = new Gson().fromJson(respose, ResultMessage.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                Toast.makeText(KeYuanActivity.this, "提交成功！", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(KeYuanActivity.this, result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("json解析出错",e.toString());
                            //Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(final String e) {
                        mDialog.dismiss();
                        Toast.makeText(KeYuanActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
                    }
                });
    }


    @Override
    public void onDateSet(TimePickerDialog timePickerView, long millseconds) {
        if(isStartTime){
            isStartTime = false;
            startDate = format.format(new Date(millseconds));
            mPickerDialog2.show(getSupportFragmentManager(), "year_month_day");
        }else {
            isStartTime = true;
            endDate = format.format(new Date(millseconds));
            dateText.setVisibility(View.VISIBLE);
            dateText.setText(startDate +" 至 " + endDate);
            dateText2.setVisibility(View.VISIBLE);
            dateText2.setText(startDate +" 至 " + endDate);
            refresh();
        }
    }

    @Override
    protected void onDestroy() {
        ImmersionBar.with(this).destroy();
        super.onDestroy();
    }
}
