package com.dikai.chenghunjiclient.activity.store;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.activity.newregister.NewLoginActivity;
import com.dikai.chenghunjiclient.activity.register.LoginActivity;
import com.dikai.chenghunjiclient.activity.wedding.RuleActivity;
import com.dikai.chenghunjiclient.activity.wedding.WedRuleActivity;
import com.dikai.chenghunjiclient.adapter.me.WedStoreAdapter;
import com.dikai.chenghunjiclient.adapter.store.NewWedFHAdapter;
import com.dikai.chenghunjiclient.bean.BeanGetFanhuanList;
import com.dikai.chenghunjiclient.bean.BeanGetQuotas;
import com.dikai.chenghunjiclient.bean.BeanType;
import com.dikai.chenghunjiclient.bean.BeanUserId;
import com.dikai.chenghunjiclient.entity.GoodsItemBean;
import com.dikai.chenghunjiclient.entity.GoodsTypeBean;
import com.dikai.chenghunjiclient.entity.ResultGetGoodListHead;
import com.dikai.chenghunjiclient.entity.ResultGoodsTypeList;
import com.dikai.chenghunjiclient.util.Constants;
import com.dikai.chenghunjiclient.util.EventBusBean;
import com.dikai.chenghunjiclient.util.NetWorkUtil;
import com.dikai.chenghunjiclient.util.UserManager;
import com.dikai.chenghunjiclient.view.MyLoadRecyclerView;
import com.flyco.dialog.widget.base.BaseDialog;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import q.rorbin.badgeview.QBadgeView;

public class WeddingStoreActivity extends AppCompatActivity implements View.OnClickListener {

    private MyLoadRecyclerView mRecyclerView;
    private NewWedFHAdapter mStoreAdapter;
    private ServiceDialog applyDialog;
    private List<Object> mGoods;
    private ImageView cartImg;
    private QBadgeView mQBadgeView;
    private String activityID;
    private TextView title;
    private int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wedding_store);
        EventBus.getDefault().register(this);
        ImmersionBar.with(this)
                .statusBarView(R.id.top_view)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true, 0.2f)
                .init();
        init();
    }

    private void init() {
        title = (TextView)findViewById(R.id.title);
        //    6239F1CA-E3CE-49E7-AEB3-21DBE83070B7	代收
        //    51F48013-9ADA-4342-96BF-259C345832AE	婚礼返还
        //    75B1E2BB-13BB-45B5-8B28-B6F31A97F8EC	伴手礼
        String code = getIntent().getStringExtra("code");
        if("HunLiFH".equals(code) || code == null){
            type = 1;
            activityID = "51F48013-9ADA-4342-96BF-259C345832AE";
            title.setText("婚礼返还");
        }else if("BSL".equals(code)){
            type = 0;
            activityID = "75B1E2BB-13BB-45B5-8B28-B6F31A97F8EC";
            title.setText("伴手礼");
        }else if("DS".equals(code)){
            type = 2;
            activityID = "6239F1CA-E3CE-49E7-AEB3-21DBE83070B7";
            title.setText("代收");
        }
        findViewById(R.id.back).setOnClickListener(this);
        cartImg = (ImageView) findViewById(R.id.cart);
        cartImg.setOnClickListener(this);
        mRecyclerView = (MyLoadRecyclerView) findViewById(R.id.recycler);
        mStoreAdapter = new NewWedFHAdapter(this);
        if(type == 1){
            mStoreAdapter.setFanhuan(true);
        }else {
            mStoreAdapter.setFanhuan(false);
        }
        mStoreAdapter.setOnItemClickListener(new NewWedFHAdapter.OnItemClickListener() {
            @Override
            public void onClick(int type, Object bean) {
                if(!UserManager.getInstance(WeddingStoreActivity.this).isLogin()){
                    startActivity(new Intent(WeddingStoreActivity.this, NewLoginActivity.class));
                }else if(type == 0){
                    if(((ResultGetGoodListHead)bean).getIsUse() == 1){
                        Toast.makeText(WeddingStoreActivity.this, "您的额度已经使用！", Toast.LENGTH_SHORT).show();
                    }else {
                        startActivity(new Intent(WeddingStoreActivity.this,ApplyPrizeActivity.class));
                    }
                }else if(type == 1){
                    startActivity(new Intent(WeddingStoreActivity.this,PrizeRuleActivity.class)
                            .putExtra("rule",((ResultGetGoodListHead)bean).getRulesActivity()));
                }else if(type == 2){
                    startActivity(new Intent(WeddingStoreActivity.this,WedPrizeListActivity.class)
                            .putExtra("title",((GoodsTypeBean)bean).getTypeName())
                            .putExtra("typeID",((GoodsTypeBean)bean).getTypeId())
                            .putExtra("activityid",activityID));
                }else if(type == 3){

                    startActivity(new Intent(WeddingStoreActivity.this,WedPrizeListActivity.class)
                            .putExtra("title",((GoodsItemBean)bean).getTypeName())
                            .putExtra("typeID",((GoodsItemBean)bean).getTypeId())
                            .putExtra("activityid",activityID));

//                    Intent intent = new Intent(WeddingStoreActivity.this, WedPrizeInfoActivity.class);
//                    intent.putExtra("prizeId",((GoodsItemBean)bean).getCommodityId());
//                    startActivity(intent);
                }
            }
        });
        mRecyclerView.setAdapter(mStoreAdapter);
        mRecyclerView.setLoadChangeListener(new MyLoadRecyclerView.OnLoadChangeListener() {
            @Override
            public void onRefresh() {
                refresh();
            }

            @Override
            public void onLoadMore() {

            }
        });
        applyDialog = new ServiceDialog(this);
        applyDialog.widthScale(1);
        applyDialog.heightScale(1);
        mQBadgeView = new QBadgeView(this);
        mQBadgeView.bindTarget(cartImg);
        mQBadgeView.setBadgeNumber(0);
        refresh();
    }

    private void refresh() {
        getHead(false);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                onBackPressed();
                break;
            case R.id.rule:
                startActivity(new Intent(this, WedRuleActivity.class)
                        .putExtra("type",1)
                        .putExtra("title","活动规则")
                        .putExtra("url","http://www.baidu.com"));
                break;
            case R.id.cart:
                if(!UserManager.getInstance(WeddingStoreActivity.this).isLogin()){
                    startActivity(new Intent(WeddingStoreActivity.this, NewLoginActivity.class));
                }else {
                    startActivity(new Intent(this, CartActivity.class).putExtra("activityid",activityID));
                }
                break;
        }
    }

    /**
     * 获取Head
     */
    private void getHead(final boolean getNum){
        NetWorkUtil.setCallback("HQOAApi/GetUserQuotas",
                new BeanGetQuotas(UserManager.getInstance(this).getNewUserInfo().getUserId(),type),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        Log.e("返回值",respose);
                        try {
                            ResultGetGoodListHead result = new Gson().fromJson(respose, ResultGetGoodListHead.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                UserManager.getInstance(WeddingStoreActivity.this).setMyCartNum(result.getCarCount());
                                EventBus.getDefault().post(new EventBusBean(Constants.CART_NUM));
                                mQBadgeView.setBadgeNumber(result.getCarCount());
                                if(!getNum){
                                    mGoods = new ArrayList<>();
                                    mGoods.add(result);
                                    getList();
                                }
                            } else {
                                mRecyclerView.stopLoad();
                                Toast.makeText(WeddingStoreActivity.this, result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            mRecyclerView.stopLoad();
                            Log.e("json解析出错",e.toString());
                            //Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(final String e) {
                        mRecyclerView.stopLoad();
                        Toast.makeText(WeddingStoreActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * 获取商品列表
     */
    private void getList(){
        NetWorkUtil.setCallback("HQOAApi/GetCommodityTypeTableList",
                new BeanGetFanhuanList(1,activityID),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        Log.e("返回值",respose);
                        mRecyclerView.stopLoad();
                        try {
                            ResultGoodsTypeList result = new Gson().fromJson(respose, ResultGoodsTypeList.class);
                            if ("200".equals(result.getMessage().getCode())) {

                                for (GoodsTypeBean bean: result.getData()) {
                                    if(bean.getData()!=null && bean.getData().size()>0){
                                        mGoods.add(bean);
                                        for (int i = 0; i < bean.getData().size(); i++) {
                                            if(i <= 0){
                                                GoodsItemBean itemBean = bean.getData().get(i);
                                                itemBean.setTypeName(bean.getTypeName());
                                                mGoods.add(itemBean);
                                            }
                                        }
                                    }
                                }
                                mStoreAdapter.refresh(mGoods);
                            } else {
                                Toast.makeText(WeddingStoreActivity.this, result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("json解析出错",e.toString());
                            //Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(final String e) {
                        mRecyclerView.stopLoad();
                        Toast.makeText(WeddingStoreActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     *rule
     */
    private class ServiceDialog extends BaseDialog<ServiceDialog> implements View.OnClickListener {
        private ImageView close;

        ServiceDialog(Context context) {
            super(context);
        }
        @Override
        public View onCreateView() {
            View view = View.inflate(mContext, R.layout.dialog_edu_apply_layout, null);
            close = (ImageView) view.findViewById(R.id.close);
            close.setOnClickListener(this);
            return view;
        }
        @Override
        public void onViewCreated(View view) {
            super.onViewCreated(view);
        }
        @Override
        public void setUiBeforShow() {}
        @Override
        public void onClick(View v) {
            if(v == close){
                this.dismiss();
            }
        }
    }

    /**
     * 事件总线刷新
     * @param bean
     */
    @Subscribe
    public void onEventMainThread(final EventBusBean bean) {
        runOnUiThread(new Runnable() {
            public void run() {
                if(bean.getType() == Constants.APPLY_WED_PRIZE){
                    applyDialog.show();
                }else if(bean.getType() == Constants.CART_CHANGED){
                    getHead(true);
                }else if(bean.getType() == Constants.CART_COUNT_CHANGED){
                    getHead(true);
                }else if(bean.getType() == Constants.CLEARCARTA_FINISH){
                    getHead(false);
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        ImmersionBar.with(this).destroy();
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
