package com.dikai.chenghunjiclient.activity.store;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.adapter.store.MainStoreAdapter;
import com.dikai.chenghunjiclient.bean.BeanGetSupplier;
import com.dikai.chenghunjiclient.citypicker.SelectCityActivity;
import com.dikai.chenghunjiclient.entity.ResultGetSupList;
import com.dikai.chenghunjiclient.entity.UserInfo;
import com.dikai.chenghunjiclient.util.Constants;
import com.dikai.chenghunjiclient.util.EventBusBean;
import com.dikai.chenghunjiclient.util.NetWorkUtil;
import com.dikai.chenghunjiclient.util.UserManager;
import com.dikai.chenghunjiclient.view.MyLoadRecyclerView;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;
import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.data.Type;
import com.jzxiang.pickerview.listener.OnDateSetListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StoreSupActivity extends AppCompatActivity implements OnDateSetListener, View.OnClickListener {
    
    private String date = "";
    private String areaID = "0";
    private String identID = "SF_14001000";
    private LinearLayout locationLayout;
    private LinearLayout dateLayout;
    private RelativeLayout search;
    private TextView location;
    private TextView title;
    private MyLoadRecyclerView mRecyclerView;
    private MainStoreAdapter mAdapter;
    private int pageIndex = 1;
    private int itemCount = 20;
    private TimePickerDialog mDialogAll;
    private SimpleDateFormat sf1 = new SimpleDateFormat("yyyy-MM-dd");
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_sup);
        EventBus.getDefault().register(this);
        ImmersionBar.with(this)
                .statusBarView(R.id.top_view)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true, 0.2f)
                .init();
        init();
    }

    private void init() {
        locationLayout = (LinearLayout)findViewById(R.id.fragment_store_location_layout);
        search = (RelativeLayout)findViewById(R.id.fragment_store_search);
        dateLayout = (LinearLayout)findViewById(R.id.fragment_store_date);
        location = (TextView)findViewById(R.id.fragment_store_location);
        title = (TextView)findViewById(R.id.fragment_store_title);
        mRecyclerView = (MyLoadRecyclerView)findViewById(R.id.fragment_store_recycler);
        mAdapter = new MainStoreAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLoadChangeListener(new MyLoadRecyclerView.OnLoadChangeListener() {
            @Override
            public void onRefresh() {
                refresh();
            }

            @Override
            public void onLoadMore() {
                pageIndex++;
                getList(false, pageIndex, itemCount);
            }
        });
        
        long tenYears = 10L * 365 * 1000 * 60 * 60 * 24L;
        mDialogAll = new TimePickerDialog.Builder()
                .setCallBack(this)
                .setCancelStringId("取消")
                .setSureStringId("确定")
                .setTitleStringId("选择日期")
                .setYearText("年")
                .setMonthText("月")
                .setDayText("日")
                .setMinMillseconds(System.currentTimeMillis())
                .setMaxMillseconds(System.currentTimeMillis() + tenYears)
                .setThemeColor(ContextCompat.getColor(this, R.color.main))
                .setType(Type.YEAR_MONTH_DAY)
                .setWheelItemTextNormalColor(ContextCompat.getColor(this, R.color.timetimepicker_default_text_color))
                .setWheelItemTextSelectorColor(ContextCompat.getColor(this, R.color.main))
                .build();
        date = sf1.format(new Date(System.currentTimeMillis()));
        title.setText(date);
        dateLayout.setOnClickListener(this);
        locationLayout.setOnClickListener(this);
        search.setOnClickListener(this);

        List<Object> list = new ArrayList<>();
        list.add("");
        mAdapter.refresh(list);
        if(UserManager.getInstance(this).isLogin()){
            identID = UserManager.getInstance(this).getUserInfo().getProfession();
            String loca = UserManager.getInstance(this).getLocation();
            if(loca != null && !"".equals(loca)){
                String[] info = loca.split(",");
                areaID = info[0];
                location.setText(info[1]);
                refresh();
            }else{
                location.setText("黄岛");
                areaID = "1740";
            }
        }else{
            location.setText("黄岛");
            areaID = "1740";
        }
    }

    @Override
    public void onClick(View v) {
        if(v == locationLayout){
            startActivity(new Intent(this, SelectCityActivity.class).putExtra("type",Constants.HOME_SELECT_CITY));
        }else if(v == dateLayout){
            mDialogAll.show(getSupportFragmentManager(), "year_month_day");
        }else if(v == search){
            if (UserManager.getInstance(this).checkLogin())
                startActivity(new Intent(this, SearchSupActivity.class));
        }
    }

    private void refresh() {
        if(UserManager.getInstance(this).isLogin()){
            pageIndex = 1;
            itemCount = 20;
            mAdapter.setIdent(identID);
            getList(true, pageIndex, itemCount);
        }else {
            List<Object> list = new ArrayList<>();
            list.add("");
            mAdapter.refresh(list);
            mRecyclerView.stopLoad();
        }
    }

    private void getList(final boolean isRefresh, int pageIndex, int itemCount) {
        UserInfo userInfo = UserManager.getInstance(this).getUserInfo();
        NetWorkUtil.setCallback("User/GetSupplierInfoList",
                new BeanGetSupplier(userInfo.getUserID(), identID, date, areaID, "", pageIndex + "",itemCount + ""),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        mRecyclerView.stopLoad();
                        Log.e("返回值",respose);
                        try {
                            ResultGetSupList result = new Gson().fromJson(respose, ResultGetSupList.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                mRecyclerView.setTotalCount(result.getTotalCount());
                                if(isRefresh){
                                    if(result.getData().size() == 0){
                                        EventBus.getDefault().post(new EventBusBean(Constants.HOME_NO_DATA, date));
                                    }else {
                                        EventBus.getDefault().post(new EventBusBean(Constants.HOME_HAS_DATA, date));
                                    }
                                    List<Object> list = new ArrayList<>();
                                    list.add("");
                                    list.addAll(result.getData());
                                    mAdapter.refresh(list);
                                }else {
                                    mAdapter.addAll(result.getData());
                                }
                            } else {
                                Toast.makeText(StoreSupActivity.this, "" + result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("json解析出错",e.toString());
                            //Toast.makeText(this, "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(final String e) {
                        mRecyclerView.stopLoad();
                        Log.e("网络出错",e.toString());
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
                if(bean.getType() == Constants.HOME_SELECT_CITY){
                    areaID = bean.getCountry().getRegionId();
                    location.setText(bean.getCountry().getRegionName());
                    UserManager.getInstance(StoreSupActivity.this).setLocation(bean.getCountry().getRegionId()+","
                            + bean.getCountry().getRegionName());
                    refresh();
                }else if(bean.getType() == Constants.HOME_SELECT_IDENTITY){
                    identID = bean.getIdentityBean().getOccupationCode();
                    refresh();
                }
            }
        });
    }
    
    @Override
    public void onDateSet(TimePickerDialog timePickerView, long millseconds) {
        date = sf1.format(new Date(millseconds));
        title.setText(date);
        refresh();
    }
    
    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        ImmersionBar.with(this).destroy();
        super.onDestroy();
    }
}
