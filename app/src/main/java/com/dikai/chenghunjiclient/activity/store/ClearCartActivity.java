package com.dikai.chenghunjiclient.activity.store;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.activity.me.MyOrderActivity;
import com.dikai.chenghunjiclient.activity.me.NewOrderActivity;
import com.dikai.chenghunjiclient.activity.wedding.ShippintAddressActivity;
import com.dikai.chenghunjiclient.adapter.store.ClearCartAdapter;
import com.dikai.chenghunjiclient.bean.BeanClearCart;
import com.dikai.chenghunjiclient.entity.CartBean;
import com.dikai.chenghunjiclient.entity.PersonAddressData;
import com.dikai.chenghunjiclient.entity.ResultGetCartList;
import com.dikai.chenghunjiclient.entity.ResultMessage;
import com.dikai.chenghunjiclient.util.Constants;
import com.dikai.chenghunjiclient.util.EventBusBean;
import com.dikai.chenghunjiclient.util.NetWorkUtil;
import com.dikai.chenghunjiclient.util.UserManager;
import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.widget.MaterialDialog;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import dmax.dialog.SpotsDialog;

public class ClearCartActivity extends AppCompatActivity implements View.OnClickListener {

    private CardView addAddress;
    private CardView myAddress;
    private TextView address;
    private TextView moren;
    private RecyclerView mRecyclerView;
    private ResultGetCartList mGetCartList;
    private ClearCartAdapter mAdapter;
    private SpotsDialog mDialog;
    private PersonAddressData.DataList addressBean;
    private String addressID;
    private MaterialDialog dialog;
    private String activityid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clear_cart);
        EventBus.getDefault().register(this);
        ImmersionBar.with(this)
                .statusBarView(R.id.top_view)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true, 0.2f)
                .init();
        init();
    }

    private void init() {
//        activityid
//        婚礼返还 51F48013-9ADA-4342-96BF-259C345832AE
//        伴手礼   75B1E2BB-13BB-45B5-8B28-B6F31A97F8EC
//        代收     6239F1CA-E3CE-49E7-AEB3-21DBE83070B7
        activityid = getIntent().getStringExtra("activityid");
        mDialog = new SpotsDialog(this,R.style.DialogCustom);
        mGetCartList = (ResultGetCartList) getIntent().getSerializableExtra("bean");
        findViewById(R.id.back).setOnClickListener(this);
        findViewById(R.id.next).setOnClickListener(this);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler);
        addAddress = (CardView) findViewById(R.id.select_address);
        myAddress = (CardView) findViewById(R.id.my_address);
        address = (TextView) findViewById(R.id.address);
        moren = (TextView) findViewById(R.id.moren);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new ClearCartAdapter(this);
        mAdapter.setOnItemClickListener(new ClearCartAdapter.OnItemClickListener() {
            @Override
            public void onClick(CartBean bean) {
                Intent intent = new Intent(ClearCartActivity.this, WedPrizeInfoActivity.class);
                intent.putExtra("prizeId",bean.getCommodityId());
                startActivity(intent);
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.refresh(mGetCartList.getData());
        addAddress.setOnClickListener(this);
        myAddress.setOnClickListener(this);
        dialog = new MaterialDialog(this);
        dialog.isTitleShow(false)//
                .btnNum(2)
                .content("确定提交订单吗")//
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
                        clear(addressID);
                    }
                });
        if(mGetCartList == null){
            finish();
        }
        if("75B1E2BB-13BB-45B5-8B28-B6F31A97F8EC".equals(activityid)){
            addAddress.setVisibility(View.GONE);
        }
    }

    private void setAddAddress(PersonAddressData.DataList dataList){
        addAddress.setVisibility(View.GONE);
        myAddress.setVisibility(View.VISIBLE);
        String info = "收货人："+ dataList.getConsignee() + "       " +
                dataList.getConsigneePhone()+"\n收货地址：" + dataList.getDetailedAddress();
        address.setText(info);
        if (dataList.getDefaultAddress()==0) {
            moren.setVisibility(View.GONE);
        } else {
            moren.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                onBackPressed();
                break;
            case R.id.my_address:
                startActivity(new Intent(this, ShippintAddressActivity.class));
                break;
            case R.id.select_address:
                startActivity(new Intent(this, ShippintAddressActivity.class));
                break;
            case R.id.next:
                if("75B1E2BB-13BB-45B5-8B28-B6F31A97F8EC".equals(activityid)){
                    addressID = "00000000-0000-0000-0000-000000000000";
                    dialog.show();
                }else if(addressBean == null){
                    Toast.makeText(this, "请选择收货地址！", Toast.LENGTH_SHORT).show();
                }else {
                    addressID = addressBean.getId();
                    dialog.show();
                }
                break;
        }
    }

    /**
     * 结算
     */
    private void clear(String id){
        mDialog.show();
        NetWorkUtil.setCallback("HQOAApi/PlaceOrder",
                new BeanClearCart(UserManager.getInstance(this).getNewUserInfo().getUserId(),id,activityid),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        mDialog.dismiss();
                        Log.e("返回值",respose);
                        try {
                            ResultMessage result = new Gson().fromJson(respose, ResultMessage.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                if("75B1E2BB-13BB-45B5-8B28-B6F31A97F8EC".equals(activityid)){
                                    Toast.makeText(ClearCartActivity.this, "购买成功，请联系客服到店领取！", Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(ClearCartActivity.this, "购买成功！", Toast.LENGTH_SHORT).show();
                                }
                                EventBus.getDefault().post(new EventBusBean(Constants.CLEARCARTA_FINISH));
                                startActivity(new Intent(ClearCartActivity.this, NewOrderActivity.class));
                                finish();
                            } else {
                                Toast.makeText(ClearCartActivity.this, result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("json解析出错",e.toString());
                            //Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(final String e) {
                        mDialog.dismiss();
                        Toast.makeText(ClearCartActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
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
                if(bean.getType() == Constants.SELECT_ADDRESS){
                    addressBean = bean.getAddressInfo();
                    setAddAddress(bean.getAddressInfo());
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        ImmersionBar.with(this).destroy();
        super.onDestroy();
    }
}
