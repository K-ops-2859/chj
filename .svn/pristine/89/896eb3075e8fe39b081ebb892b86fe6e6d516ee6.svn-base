package com.dikai.chenghunjiclient.activity.wedding;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.adapter.OnAdapterViewClickListener;
import com.dikai.chenghunjiclient.adapter.wedding.GetMyPrizeAdapter;
import com.dikai.chenghunjiclient.bean.BeanAddPrize;
import com.dikai.chenghunjiclient.entity.ActivityInfoData;
import com.dikai.chenghunjiclient.entity.ResultMessage;
import com.dikai.chenghunjiclient.util.NetWorkUtil;
import com.dikai.chenghunjiclient.util.UserManager;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;

import org.greenrobot.eventbus.EventBus;

import java.util.Arrays;
import java.util.List;

/**
 * Created by cmk03 on 2017/11/15.
 */

public class GetPrizeActivity extends AppCompatActivity {

    private boolean isPrize = false;
    private GetMyPrizeAdapter mAdapter;
    private int currPage = 2;
    private RecyclerView mRecyclerView;
    private LinearLayout llFooter;
    private List<String> list;
    private String selectedGrade;
    private ActivityInfoData infoData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_prize);
        ImmersionBar.with(this)
                .statusBarView(R.id.top_view)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true, 0.2f)
                .init();
        ImageView mBack = (ImageView) findViewById(R.id.activity_zone_back);
        LinearLayout flNotPrize = (LinearLayout) findViewById(R.id.ll_not_prize);
        TextView tvNumber = (TextView) findViewById(R.id.tv_number);
        TextView tvDate = (TextView) findViewById(R.id.tv_date);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        Intent intent = getIntent();
        infoData = (ActivityInfoData) intent.getSerializableExtra("info_data");
       // activityId = intent.getIntExtra("activityId", -1);

        //grades = intent.getStringArrayExtra("Grade");
        String endTime = infoData.getEndTime();
        final int count = infoData.getInvitedNumber();

        tvNumber.setText("已邀请人数: " + count +"人");
        System.out.println("==============" + count);
        tvDate.setText("领取截至日: " + endTime);
        list = Arrays.asList(infoData.getGrade());
        int invitedNumber = infoData.getInvitedNumber();
        if (isPrize) {
            flNotPrize.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
        } else {
            flNotPrize.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
        }

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mAdapter = new GetMyPrizeAdapter(this, endTime, count + "", invitedNumber);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setList(list);
        // mAdapter.addHeaderView(LayoutInflater.from(this).inflate(R.layout.adapter_get_my_prize_header, null));

        mAdapter.setOnAdapterViewClickListener(new OnAdapterViewClickListener() {
            @Override
            public void onAdapterClick(View view, int position, Object o) {
                selectedGrade = list.get(position);
                if (Integer.parseInt(selectedGrade) <= count) {
                    Intent intent = new Intent(GetPrizeActivity.this, ShippingAddressActivity.class);
                    startActivityForResult(intent, 1);
                } else {
                    Toast.makeText(GetPrizeActivity.this, "您邀请的人数不符合标准", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
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
                initData(addressID);
            }

        }
    }

    private void initData(int addressID) {
        NetWorkUtil.setCallback("Corp/AddAcquiringPrizes", new BeanAddPrize("1", UserManager.getInstance(this).getUserInfo().getUserID(),
                infoData.getActivityID() + "", addressID + "", selectedGrade, 0, ""), new NetWorkUtil.CallBackListener() {
            @Override
            public void onFinish(String respose) {
                ResultMessage data = new Gson().fromJson(respose, ResultMessage.class);
                if (data.getMessage().getCode().equals("200")) {
                    startActivity(new Intent(GetPrizeActivity.this, GetPrizeSuccessActivity.class));
                    System.out.println("===============");
                } else {
                    Toast.makeText(GetPrizeActivity.this, data.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(String e) {

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
