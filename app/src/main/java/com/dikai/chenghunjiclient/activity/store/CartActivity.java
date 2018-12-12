package com.dikai.chenghunjiclient.activity.store;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.adapter.store.CartAdapter;
import com.dikai.chenghunjiclient.adapter.store.CoudanAdapter;
import com.dikai.chenghunjiclient.bean.BeanDelCart;
import com.dikai.chenghunjiclient.bean.BeanEditCart;
import com.dikai.chenghunjiclient.bean.BeanGetCartList;
import com.dikai.chenghunjiclient.bean.BeanIDType;
import com.dikai.chenghunjiclient.bean.BeanUserId;
import com.dikai.chenghunjiclient.entity.CartBean;
import com.dikai.chenghunjiclient.entity.ResultGetCartList;
import com.dikai.chenghunjiclient.entity.ResultGetWedPrizeList;
import com.dikai.chenghunjiclient.entity.ResultMessage;
import com.dikai.chenghunjiclient.entity.WedPrizeBean;
import com.dikai.chenghunjiclient.util.Constants;
import com.dikai.chenghunjiclient.util.EventBusBean;
import com.dikai.chenghunjiclient.util.NetWorkUtil;
import com.dikai.chenghunjiclient.util.UserManager;
import com.dikai.chenghunjiclient.view.MyLoadRecyclerView;
import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.widget.MaterialDialog;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.math.BigDecimal;
import java.util.List;

import dmax.dialog.SpotsDialog;

public class CartActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView editButton;
    private RecyclerView mCartList;
//    private RecyclerView mCoudanList;
    private CartAdapter mCartAdapter;
//    private CoudanAdapter mCoudanAdapter;
    private AppCompatCheckBox mCheckBox;
    private TextView totalPrice;
    private TextView delOrClear;
    private SpotsDialog mSpotsDialog;
    private boolean isClear = true;
    private MaterialDialog dialog;
    private SwipeRefreshLayout fresh;
//    private ImageView coudanImg;
    private LinearLayout placeLayout;
    private LinearLayout bottomLayout;
    private boolean hasData = false;
    private MaterialDialog dialog2;
    private String activityid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        EventBus.getDefault().register(this);
        ImmersionBar.with(this)
                .statusBarView(R.id.top_view)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true, 0.2f)
                .init();
        init();
    }

    private void init() {

        activityid = getIntent().getStringExtra("activityid");
        mSpotsDialog = new SpotsDialog(this,R.style.DialogCustom);
        findViewById(R.id.back).setOnClickListener(this);
        editButton = (TextView) findViewById(R.id.edit);
        editButton.setOnClickListener(this);
        mCheckBox = (AppCompatCheckBox) findViewById(R.id.select_all);
        totalPrice = (TextView) findViewById(R.id.total_price);
        delOrClear = (TextView) findViewById(R.id.deleteOrClear);
//        coudanImg = (ImageView) findViewById(R.id.coudan_img);
        placeLayout = (LinearLayout) findViewById(R.id.place_layout);
        bottomLayout = (LinearLayout) findViewById(R.id.bottom_layout);
        delOrClear.setOnClickListener(this);
        mCartList = (RecyclerView) findViewById(R.id.recycler1);
//        mCoudanList = (RecyclerView) findViewById(R.id.recycler2);

        fresh = (SwipeRefreshLayout) findViewById(R.id.my_load_recycler_fresh);
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
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(this);
        mCartList.setLayoutManager(linearLayoutManager1);

//        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
//        mCoudanList.setLayoutManager(gridLayoutManager);
//        mCoudanList.setLayoutManager(linearLayoutManager2);
        mCartAdapter = new CartAdapter(this);
        mCartAdapter.setOnItemClickListener(new CartAdapter.OnItemClickListener() {
            @Override
            public void onClick(CartBean bean, int position, int type) {
                if(type == 0){
                    Intent intent = new Intent(CartActivity.this, WedPrizeInfoActivity.class);
                    intent.putExtra("prizeId",bean.getCommodityId());
                    startActivity(intent);
                }else if(type == 1){
                    int num = bean.getCount() + 1;
                    editNum(position,bean.getShoppingCartId(),num,bean.getPlaceOriginId());
                }else if(type == 2){
                    if(bean.getCount() > 1){
                        int num = bean.getCount() - 1;
                        editNum(position,bean.getShoppingCartId(),num,bean.getPlaceOriginId());
                    }
                }
            }
        });

        mCartAdapter.setOnPriceChangeListener(new CartAdapter.OnPriceChangeListener() {
            @Override
            public void onChanged(BigDecimal price) {
                totalPrice.setText("￥"+price.toString());
            }
        });

        mCartList.setAdapter(mCartAdapter);

//        mCoudanAdapter = new CoudanAdapter(this);
//        mCoudanAdapter.setOnItemClickListener(new CoudanAdapter.OnItemClickListener() {
//            @Override
//            public void onClick(WedPrizeBean bean) {
//                Intent intent = new Intent(CartActivity.this, WedPrizeInfoActivity.class);
//                intent.putExtra("prizeId",bean.getCommodityId());
//                startActivity(intent);
//            }
//        });
//        mCoudanList.setAdapter(mCoudanAdapter);
        mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mCartAdapter.setAllSelect(isChecked);
            }
        });

        dialog = new MaterialDialog(this);
        dialog.isTitleShow(false)//
                .btnNum(2)
                .content("确定要删除选中商品吗")//
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
                        String cartID = "";
                        List<CartBean> beanList = mCartAdapter.getSelectedList();
                        for (int i = 0; i < beanList.size(); i++) {
                            if(i < beanList.size() - 1){
                                cartID = cartID + beanList.get(i).getShoppingCartId()+",";
                            }else {
                                cartID = cartID + beanList.get(i).getShoppingCartId();
                            }
                        }
                        deleteCart(cartID);
                    }
                });

        dialog2 = new MaterialDialog(this);
        dialog2.isTitleShow(false)//
                .btnNum(2)
                .content("温馨提示：婚礼返还内所有可用额度，在本次结算后即全部清零作废，是否结算？\n")//
                .btnText("取消", "确定")
                .setOnBtnClickL(new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        dialog2.dismiss();
                    }
                }, new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        dialog2.dismiss();
                        getClearList();
                    }
                });

        refresh();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        mCartAdapter.setEdit(false);
        mCheckBox.setChecked(false);
        isClear = true;
        editButton.setText("编辑");
        delOrClear.setText("结算");
//        if(hasData){
//            coudanImg.setVisibility(View.VISIBLE);
//            mCoudanList.setVisibility(View.VISIBLE);
//        }
        mCheckBox.setVisibility(View.INVISIBLE);
        refresh();
    }

    private void refresh(){
        if(isClear){
            getList();
        }else {
            stopLoad();
        }
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                onBackPressed();
                break;
            case R.id.edit:
                if(isClear){
                    mCartAdapter.setEdit(true);
                    isClear = false;
                    editButton.setText("完成");
                    delOrClear.setText("删除");
//                    if(hasData){
//                        coudanImg.setVisibility(View.GONE);
//                        mCoudanList.setVisibility(View.GONE);
//                    }
                    mCheckBox.setVisibility(View.VISIBLE);
                    mCheckBox.setChecked(true);
                }else {
                    mCartAdapter.setEdit(false);
                    mCheckBox.setChecked(false);
                    isClear = true;
                    editButton.setText("编辑");
                    delOrClear.setText("结算");
//                    if(hasData){
//                        coudanImg.setVisibility(View.VISIBLE);
//                        mCoudanList.setVisibility(View.VISIBLE);
//                    }
                    mCheckBox.setVisibility(View.INVISIBLE);
                }
                break;
            case R.id.deleteOrClear:
                if(isClear){
                    dialog2.show();
                }else {
                    if(mCartAdapter.getSelectedList().size()>0){
                        dialog.show();
                    }
                }
                break;
        }
    }

    /**
     * 获取购物车列表
     */
    private void getList(){
        NetWorkUtil.setCallback("HQOAApi/GetShoppingCartList",
                new BeanGetCartList(UserManager.getInstance(this).getNewUserInfo().getUserId(),2,activityid),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        stopLoad();
                        Log.e("返回值",respose);
                        try {
                            ResultGetCartList result = new Gson().fromJson(respose, ResultGetCartList.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                mCartAdapter.refresh(result.getData());
                                if(result.getData() == null || result.getData().size() == 0){
                                    hasData = false;
//                                    coudanImg.setVisibility(View.GONE);
//                                    mCoudanList.setVisibility(View.GONE);
                                    bottomLayout.setVisibility(View.GONE);
                                    editButton.setVisibility(View.INVISIBLE);
                                    placeLayout.setVisibility(View.VISIBLE);
                                }else {
                                    hasData = true;
                                    editButton.setVisibility(View.VISIBLE);
                                    bottomLayout.setVisibility(View.VISIBLE);
//                                    coudanImg.setVisibility(View.VISIBLE);
//                                    mCoudanList.setVisibility(View.VISIBLE);
                                    placeLayout.setVisibility(View.GONE);
//                                    getCoudanList();
                                }
                            } else {
                                stopLoad();
                                Toast.makeText(CartActivity.this, result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            stopLoad();
                            Log.e("json解析出错",e.toString());
                            //Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(final String e) {
                        stopLoad();
                        Toast.makeText(CartActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * 修改购物车商品
     */
    private void editNum(final int position, String cartID, final int num, String typeID){
        mSpotsDialog.show();
        NetWorkUtil.setCallback("HQOAApi/UpShoppingCart",
                new BeanEditCart(cartID,num,typeID,activityid),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        mSpotsDialog.dismiss();
                        Log.e("返回值",respose);
                        try {
                            ResultMessage result = new Gson().fromJson(respose, ResultMessage.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                mCartAdapter.itemChange(position, num);
                                EventBus.getDefault().post(new EventBusBean(Constants.CART_COUNT_CHANGED));
                            } else {
                                Toast.makeText(CartActivity.this, result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("json解析出错",e.toString());
                            //Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(final String e) {
                        mSpotsDialog.dismiss();
                        Toast.makeText(CartActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * 删除购物车商品
     */
    private void deleteCart(String cartID){
        mSpotsDialog.show();
        NetWorkUtil.setCallback("HQOAApi/DelShoppingCart",
                new BeanDelCart(cartID),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        mSpotsDialog.dismiss();
                        Log.e("返回值",respose);
                        try {
                            ResultMessage result = new Gson().fromJson(respose, ResultMessage.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                EventBus.getDefault().post(new EventBusBean(Constants.CART_CHANGED));
                                isClear = true;
                                refresh();
                            } else {
                                Toast.makeText(CartActivity.this, result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("json解析出错",e.toString());
                            //Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(final String e) {
                        mSpotsDialog.dismiss();
                        Toast.makeText(CartActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * 获取凑单列表
     */
    private void getCoudanList(){
        NetWorkUtil.setCallback("HQOAApi/ShoppingCartPieceTogether",
                new BeanUserId(UserManager.getInstance(this).getNewUserInfo().getUserId()),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        stopLoad();
                        Log.e("返回值",respose);
                        try {
                            ResultGetWedPrizeList result = new Gson().fromJson(respose, ResultGetWedPrizeList.class);
                            if ("200".equals(result.getMessage().getCode())) {
//                                mCoudanAdapter.refresh(result.getData());
                            } else {
                                Toast.makeText(CartActivity.this, result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("json解析出错",e.toString());
                            //Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(final String e) {
                        stopLoad();
                        Toast.makeText(CartActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * 获取结算订单
     */
    private void getClearList(){
        mSpotsDialog.show();
        NetWorkUtil.setCallback("HQOAApi/GetShoppingCartList",
                new BeanGetCartList(UserManager.getInstance(this).getNewUserInfo().getUserId(),2,activityid),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        mSpotsDialog.dismiss();
                        Log.e("返回值",respose);
                        try {
                            ResultGetCartList result = new Gson().fromJson(respose, ResultGetCartList.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                if(result.getData().size()>0){
                                    Bundle bundle = new Bundle();
                                    bundle.putSerializable("bean",result);
                                    startActivity(new Intent(CartActivity.this,ClearCartActivity.class)
                                            .putExtras(bundle).putExtra("activityid",activityid));
                                }
                            } else {
                                Toast.makeText(CartActivity.this, result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("json解析出错",e.toString());
                            //Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(final String e) {
                        mSpotsDialog.dismiss();
                        Toast.makeText(CartActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
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
                if(bean.getType() == Constants.CART_CHANGED){
                    mCartAdapter.setEdit(false);
                    mCheckBox.setChecked(false);
                    isClear = true;
                    editButton.setText("编辑");
                    delOrClear.setText("结算");
//                    if(hasData){
//                        coudanImg.setVisibility(View.VISIBLE);
//                        mCoudanList.setVisibility(View.VISIBLE);
//                    }
                    mCheckBox.setVisibility(View.INVISIBLE);
                    refresh();
                }else if(bean.getType() == Constants.CLEARCARTA_FINISH){
                    finish();
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
