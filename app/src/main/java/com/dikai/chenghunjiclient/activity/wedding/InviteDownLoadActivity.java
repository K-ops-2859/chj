package com.dikai.chenghunjiclient.activity.wedding;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.adapter.OnItemClickListener;
import com.dikai.chenghunjiclient.adapter.wedding.InviteDownLoadAdapter;
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

/**
 * Created by cmk03 on 2017/12/6.
 */

public class InviteDownLoadActivity extends AppCompatActivity {

    private TextView tvInvitePerson;
    private TextView tvGetGift;
    private List<WrapperPrizeData> wrapperData = new ArrayList<>();
    private InviteDownLoadAdapter mAdapter;
    private ActivityInfoData infoData;
    private WrapperPrizeData wrapperPrizeData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_download);
        ImmersionBar.with(this)
                .statusBarView(R.id.top_view)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true, 0.2f)
                .init();
        ImageView mBack = (ImageView) findViewById(R.id.activity_zone_back);
        tvInvitePerson = (TextView) findViewById(R.id.tv_invited_person);
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        tvGetGift = (TextView) findViewById(R.id.tv_get_gift);

        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mRecyclerView.setNestedScrollingEnabled(false);
        mAdapter = new InviteDownLoadAdapter(this);
        mRecyclerView.setAdapter(mAdapter);

        getInfo();
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

//        mAdapter.setOnAdapterViewClickListener(new OnAdapterViewClickListener() {
//            @Override
//            public void onAdapterClick(View view, int position) {
//                wrapperPrizeData = wrapperData.get(position);
//                int grade = wrapperPrizeData.getGrade();
//                Intent intent = new Intent(InviteDownLoadActivity.this, ShippingAddressActivity.class);
//                intent.putExtra("type", 1);
//                startActivityForResult(intent, 1);
//            }
//        });

        mAdapter.setOnItemClickListener(new OnItemClickListener<WrapperPrizeData>() {
            @Override
            public void onItemClick(View view, int position, WrapperPrizeData wrapperPrizeData) {
                Intent intent = new Intent(InviteDownLoadActivity.this, PrizeDetailsActivity.class);
                int activityPrizesID = wrapperPrizeData.getActivityPrizesID();
                intent.putExtra("prizeId", activityPrizesID);
                startActivity(intent);
            }
        });
    }

    private void setData(final ActivityInfoData infoData) {
        tvInvitePerson.setText("已邀请" + infoData.getInvitedNumber() + "人");

        initData(infoData);
        tvGetGift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InviteDownLoadActivity.this, GetPrizeActivity.class);
                if (infoData != null) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("info_data", infoData);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
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


    private void initData(final ActivityInfoData infoData) {
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


    private void getInfo() {
        UserInfo info = UserManager.getInstance(this).getUserInfo();
        NetWorkUtil.setCallback("Corp/GetActivityInfo", new GetPrizeBean("1", info.getUserID()), new NetWorkUtil.CallBackListener() {
            @Override
            public void onFinish(String respose) {
                infoData = new Gson().fromJson(respose, ActivityInfoData.class);
                if ("200".equals(infoData.getMessage().getCode())) {
                    setData(infoData);
                } else if ("201".equals(infoData.getMessage().getCode())) {

                }
            }

            @Override
            public void onError(String e) {

            }
        });
    }

    private void addPrize(int addressId) {
        NetWorkUtil.setCallback("Corp/AddAcquiringPrizes", new BeanAddPrize("1", UserManager.getInstance(this).getUserInfo().getUserID(),
                infoData.getActivityID() + "", addressId + "", wrapperPrizeData.getGrade() + "", wrapperPrizeData.getActivityPrizesID(), ""), new NetWorkUtil.CallBackListener() {
            @Override
            public void onFinish(String respose) {
                ResultMessage data = new Gson().fromJson(respose, ResultMessage.class);
                if (data.getMessage().getCode().equals("200")) {
                    startActivity(new Intent(InviteDownLoadActivity.this, GetPrizeSuccessActivity.class));
                    System.out.println("===============");
                } else {
                    Toast.makeText(InviteDownLoadActivity.this, data.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(String e) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImmersionBar.with(this).destroy();
    }
}
