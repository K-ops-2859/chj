package com.dikai.chenghunjiclient.activity.wedding;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.adapter.OnAdapterViewClickListener;
import com.dikai.chenghunjiclient.adapter.OnItemClickListener;
import com.dikai.chenghunjiclient.adapter.wedding.SignBillAdapter;
import com.dikai.chenghunjiclient.bean.BeanAddPrize;
import com.dikai.chenghunjiclient.bean.BeanPrizeInfo;
import com.dikai.chenghunjiclient.bean.GetPrizeBean;
import com.dikai.chenghunjiclient.entity.ActivityInfoData;
import com.dikai.chenghunjiclient.entity.GetPrizeData;
import com.dikai.chenghunjiclient.entity.ResultMessage;
import com.dikai.chenghunjiclient.entity.UserInfo;
import com.dikai.chenghunjiclient.entity.WrapperPrizeData;
import com.dikai.chenghunjiclient.util.NetWorkUtil;
import com.dikai.chenghunjiclient.util.UserManager;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by cmk03 on 2017/12/13.
 */

public class SignBillWelfareActivity extends AppCompatActivity {

    private SignBillAdapter mAdapter;
    private List<WrapperPrizeData> wrapperData = new ArrayList<>();
    private ActivityInfoData infoData;
    private WrapperPrizeData wrapperPrizeData;
    private Timer timer;
    private int num;
    private Toast toast;
    private FrameLayout flVerify;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signbillwelfare);
        ImmersionBar.with(this)
                .statusBarView(R.id.top_view)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true, 0.2f)
                .init();
        flVerify = (FrameLayout) findViewById(R.id.fl_verify);
        ImageView mBack = (ImageView) findViewById(R.id.activity_zone_back);
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mAdapter = new SignBillAdapter(this);
        mRecyclerView.setAdapter(mAdapter);

        getInfo();

        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mAdapter.setOnAdapterViewClickListener(new OnAdapterViewClickListener() {
            @Override
            public void onAdapterClick(View view, int position, Object o) {
                wrapperPrizeData = wrapperData.get(position);
                Intent intent = new Intent(SignBillWelfareActivity.this, ShippingAddressActivity.class);
                intent.putExtra("type", 4);
                startActivityForResult(intent, 1);
            }
        });

        mAdapter.setOnItemClickListener(new OnItemClickListener<WrapperPrizeData>() {
            @Override
            public void onItemClick(View view, int position, WrapperPrizeData wrapperPrizeData) {
                Intent intent = new Intent(SignBillWelfareActivity.this, PrizeDetailsActivity.class);
                int activityPrizesID = wrapperPrizeData.getActivityPrizesID();
                intent.putExtra("prizeId", activityPrizesID);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == 100) {
            int addressID = data.getExtras().getInt("addressID");
            System.out.println("=======================返回的id:::" + addressID);
            if (addressID != -1) {
                addPrize(addressID);
            }
        }
    }

    private void addPrize(int addressId) {
        NetWorkUtil.setCallback("Corp/AddAcquiringPrizes", new BeanAddPrize("4", UserManager.getInstance(this).getUserInfo().getUserID(),
                infoData.getActivityID() + "", addressId + "", wrapperPrizeData.getGrade() + "", wrapperPrizeData.getActivityPrizesID(), ""), new NetWorkUtil.CallBackListener() {
            @Override
            public void onFinish(String respose) {
                ResultMessage data = new Gson().fromJson(respose, ResultMessage.class);
                if (data.getMessage().getCode().equals("200")) {
                    startActivity(new Intent(SignBillWelfareActivity.this, GetPrizeSuccessActivity.class));
                    System.out.println("===============");
                } else {
                    Toast.makeText(SignBillWelfareActivity.this, data.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(String e) {

            }
        });
    }

    private void getInfo() {
        final UserInfo info = UserManager.getInstance(this).getUserInfo();
        NetWorkUtil.setCallback("Corp/GetActivityInfo", new GetPrizeBean("4", info.getUserID()), new NetWorkUtil.CallBackListener() {

            @Override
            public void onFinish(String respose) {
                infoData = new Gson().fromJson(respose, ActivityInfoData.class);
                if ("200".equals(infoData.getMessage().getCode())) {
                    flVerify.setVisibility(View.GONE);
                    mAdapter.setStatusData(infoData.getSignatureNumber(), infoData.getBudget());
                    getPrizeData(infoData);
                } else if ("201".equals(infoData.getMessage().getCode())) {
                    flVerify.setVisibility(View.VISIBLE);
                    initToast();
                }
            }

            @Override
            public void onError(String e) {

            }
        });
    }

    private void getPrizeData(final ActivityInfoData infoData) {

        NetWorkUtil.setCallback("Corp/ActivityPrizesList", new BeanPrizeInfo(infoData.getActivityID() + ""), new NetWorkUtil.CallBackListener() {
            @Override
            public void onFinish(String respose) {
                GetPrizeData dataList = new Gson().fromJson(respose, GetPrizeData.class);
                if (dataList.getMessage().getCode().equals("200")) {
                    List<GetPrizeData.GradeData> gradeData = dataList.getGradeData();
                    for (GetPrizeData.GradeData datalist : gradeData) {
                        if (!datalist.getData().isEmpty()) {
                            wrapperData.add(new WrapperPrizeData(datalist.getGrade(), true));
                            List<GetPrizeData.GradeData.DagaList> data1 = datalist.getData();
                            for (GetPrizeData.GradeData.DagaList dagaList : data1) {
                                System.out.println("==================" + data1.size());
                                wrapperData.add(new WrapperPrizeData(datalist.getGrade(), dagaList.getActivityPrizesID(), dagaList.getCommodityName(),
                                        dagaList.getShowImg(), dagaList.getCountry(), dagaList.getCreateTime(), dagaList.getMarketPrice(), false, infoData.getSignatureNumber()));
                            }
                        }
                    }
                    mAdapter.setList(wrapperData);
                }
            }

            @Override
            public void onError(String e) {
                Log.e("错误", e);
            }
        });
    }

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            Log.v("num=", String.valueOf(num));
            num++;
            if (num < 4) {
                toast.setText(String.valueOf(3 - num) + "秒后退出!");
            } else {
                timer.cancel();
                finish();
            }
        }

    };

    private void initToast() {
        toast = Toast.makeText(this, "活动已结束", Toast.LENGTH_LONG);
        toast.show();
        num = 0;
        TimerTask task = new TimerTask() {
            public void run() {
                Message message = new Message();
                handler.sendMessage(message);
            }
        };

        timer = new Timer(true);
        timer.schedule(task, 1000, 1000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImmersionBar.with(this).destroy();
    }
}
