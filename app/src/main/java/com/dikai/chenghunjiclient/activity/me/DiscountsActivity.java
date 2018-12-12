package com.dikai.chenghunjiclient.activity.me;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.adapter.me.DiscountsAdapter;
import com.dikai.chenghunjiclient.bean.BeanID;
import com.dikai.chenghunjiclient.bean.BeanOnlyFacilitatorId;
import com.dikai.chenghunjiclient.entity.DiscountsBean;
import com.dikai.chenghunjiclient.entity.ResultDiscounts;
import com.dikai.chenghunjiclient.entity.ResultMessage;
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

import dmax.dialog.SpotsDialog;

public class DiscountsActivity extends AppCompatActivity implements View.OnClickListener {

    private SpotsDialog mDialog;
    private TextView save;
    private MyLoadRecyclerView mRecyclerView;
    private ImageView placeImg;
    private DiscountsAdapter mAdapter;
    private MaterialDialog dialogDelete;
    private String tempID;
    private int nowPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discounts);
        EventBus.getDefault().register(this);
        ImmersionBar.with(this)
                .statusBarView(R.id.top_view)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true, 0.2f)
                .init();
        init();
    }

    private void init() {
        mDialog = new SpotsDialog(this,R.style.DialogCustom);
        findViewById(R.id.back).setOnClickListener(this);
        findViewById(R.id.add).setOnClickListener(this);
        save = (TextView) findViewById(R.id.save);
        save.setOnClickListener(this);
        placeImg = (ImageView) findViewById(R.id.place);
        mRecyclerView = (MyLoadRecyclerView) findViewById(R.id.recycler);
        mRecyclerView.setLoadChangeListener(new MyLoadRecyclerView.OnLoadChangeListener() {
            @Override
            public void onRefresh() {
                refresh();
            }

            @Override
            public void onLoadMore() {}
        });
        mAdapter = new DiscountsAdapter(this);
        mAdapter.setOnItemClickListener(new DiscountsAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position, DiscountsBean bean) {
                nowPosition = position;
                tempID = bean.getId();
                dialogDelete.show();
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        dialogDelete = new MaterialDialog(this);
        dialogDelete.isTitleShow(false)//
                .btnNum(2)
                .content("是否删除此条优惠？")//
                .btnText("否", "是")
                .setOnBtnClickL(new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        dialogDelete.dismiss();
                    }
                }, new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        dialogDelete.dismiss();
                        deleteDiscounts();
                    }
                });
        refresh();
    }

    private void refresh(){
        getDiscounts();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                onBackPressed();
                break;
            case R.id.save:
                if(mAdapter.isCanDelete()){
                    save.setText("管理");
                    mAdapter.setCanDelete(false);
                }else {
                    save.setText("保存");
                    mAdapter.setCanDelete(true);
                }
                break;
            case R.id.add:
                startActivity(new Intent(this,AddDiscountsActivity.class));
                break;
        }
    }

    /**
     * 获取优惠
     */
    private void getDiscounts(){
        NetWorkUtil.setCallback("HQOAApi/GetFacilitatorBasicPreferences",
                new BeanOnlyFacilitatorId(UserManager.getInstance(this).getNewUserInfo().getFacilitatorId()),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        mRecyclerView.stopLoad();
                        Log.e("返回值",respose);
                        try {
                            ResultDiscounts result = new Gson().fromJson(respose, ResultDiscounts.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                mAdapter.refresh(result.getData());
                                EventBus.getDefault().post(new EventBusBean(Constants.ADD_DISCOUNT_NUM,result.getData().size()));
                                if(result.getData().size() == 0){
                                    placeImg.setVisibility(View.VISIBLE);
                                }else {
                                    placeImg.setVisibility(View.GONE);
                                }
                            } else {
                                Toast.makeText(DiscountsActivity.this, result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("json解析出错",e.toString());
                            //Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(final String e) {
                        mRecyclerView.stopLoad();
                        Toast.makeText(DiscountsActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * 删除优惠
     */
    private void deleteDiscounts(){
        mDialog.show();
        NetWorkUtil.setCallback("HQOAApi/DeleteFacilitatorBasicPreferences",
                new BeanID(tempID),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        mDialog.dismiss();
                        Log.e("返回值",respose);
                        try {
                            ResultMessage result = new Gson().fromJson(respose, ResultMessage.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                mAdapter.delete(nowPosition);
                            } else {
                                Toast.makeText(DiscountsActivity.this, result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("json解析出错",e.toString());
                            //Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(final String e) {
                        mDialog.dismiss();
                        Toast.makeText(DiscountsActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
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
                if(bean.getType() == Constants.ADD_DISCOUNT){
                    refresh();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        ImmersionBar.with(this).destroy();
        super.onDestroy();
    }
}
