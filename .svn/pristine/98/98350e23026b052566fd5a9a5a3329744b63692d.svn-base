package com.dikai.chenghunjiclient.activity.plan;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.adapter.plan.PlanFlowAdapter;
import com.dikai.chenghunjiclient.bean.BeanAddMark;
import com.dikai.chenghunjiclient.bean.BeanGetPlanInfo;
import com.dikai.chenghunjiclient.bean.BeanOrderAgree;
import com.dikai.chenghunjiclient.entity.DayPlanBean;
import com.dikai.chenghunjiclient.entity.GetSupOrderBean;
import com.dikai.chenghunjiclient.entity.ResultGetPlanInfo;
import com.dikai.chenghunjiclient.entity.ResultGetSupOrder;
import com.dikai.chenghunjiclient.entity.ResultMessage;
import com.dikai.chenghunjiclient.entity.UserInfo;
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

import dmax.dialog.SpotsDialog;

public class PlanInfoActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int CALL_REQUEST_CODE = 120;
    private ImageView mBack;
    private RecyclerView mRecyclerView;
    private PlanFlowAdapter mAdapter;
    private TextView room;
    private TextView hotel;
    private TextView address;
    private TextView bride;
    private TextView groom;
    private TextView agree;
    private TextView refuse;
    private TextView name;
    private TextView remark;
    private LinearLayout remarkLayout;
    private TextView phone;
    private ImageView call;
    private ImageView logo;
    private SpotsDialog mDialog;
    private GetSupOrderBean mBean;
    private DayPlanBean mDayPlanBean;
    private int type;
    private int markType;
    private Intent intent;
    private MaterialDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_info);
        EventBus.getDefault().register(this);
        ImmersionBar.with(this)
                .statusBarView(R.id.top_view)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true, 0.2f)
                .init();
        init();
    }

    private void init() {
        type = getIntent().getIntExtra("type", 0);
        mDialog = new SpotsDialog(this, R.style.DialogCustom);
        mBack = (ImageView) findViewById(R.id.activity_plan_info_back);
        mRecyclerView = (RecyclerView) findViewById(R.id.activity_plan_info_recycler);
        remarkLayout = (LinearLayout) findViewById(R.id.activity_plan_info_remark_layout);
        remark = (TextView) findViewById(R.id.activity_plan_info_remark);
        room = (TextView) findViewById(R.id.activity_plan_info_room);
        hotel = (TextView) findViewById(R.id.activity_plan_info_hotel);
        address = (TextView) findViewById(R.id.activity_plan_info_address);
        bride = (TextView) findViewById(R.id.activity_plan_info_bride);
        groom = (TextView) findViewById(R.id.activity_plan_info_groom);
        name = (TextView) findViewById(R.id.activity_plan_info_name);
        phone = (TextView) findViewById(R.id.activity_plan_info_phone);
        agree = (TextView) findViewById(R.id.activity_plan_info_agree);
        refuse = (TextView) findViewById(R.id.activity_plan_info_refuse);
        logo = ((ImageView) findViewById(R.id.activity_plan_info_logo));
        call = ((ImageView) findViewById(R.id.activity_plan_info_call));
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(manager);
        mAdapter = new PlanFlowAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        mBack.setOnClickListener(this);
        call.setOnClickListener(this);
        refuse.setOnClickListener(this);
        agree.setOnClickListener(this);

        dialog = new MaterialDialog(this);
        dialog.isTitleShow(false)//
                .btnNum(2)
                .content("确定删除备注吗")//
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
                        deleteRemark();
                    }
                });

        if(type == 0){
            mBean = (GetSupOrderBean) getIntent().getSerializableExtra("bean");
            agree.setText("接受");
            refuse.setText("拒绝");
        }else {
            mDayPlanBean = (DayPlanBean) getIntent().getSerializableExtra("bean");
            if(mDayPlanBean.getRemark() == null || "".equals(mDayPlanBean.getRemark().trim())){
                remarkLayout.setVisibility(View.GONE);
                markType = 0;
                refuse.setVisibility(View.GONE);
                agree.setText("添加备注");
            }else {
                remarkLayout.setVisibility(View.VISIBLE);
                remark.setText(mDayPlanBean.getRemark());
                refuse.setVisibility(View.VISIBLE);
                refuse.setText("修改备注");
                agree.setText("删除备注");
                markType = 1;
            }
        }
        getList();
    }

    @Override
    public void onClick(View v) {
        if(v == mBack){
            onBackPressed();
        }else if(v == agree){
            if(type == 0){
                agree(mBean.getSupplierOrderID(),"1");
            }else {
                if(markType == 0){
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("bean",mDayPlanBean);
                    bundle.putInt("type",0);
                    startActivity(new Intent(this, AddMarkActivity.class).putExtras(bundle));
                }else {
                    dialog.show();
                }
            }
        }else if(v == refuse){
            if(type == 0){
                agree(mBean.getSupplierOrderID(),"2");
            }else {
                Bundle bundle = new Bundle();
                bundle.putSerializable("bean",mDayPlanBean);
                bundle.putInt("type",1);
                startActivity(new Intent(this, AddMarkActivity.class).putExtras(bundle));
            }
        }else if(v == call){
            intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + mBean.getCorpNamePhone()));
            request();
        }
    }

    private void setData(ResultGetPlanInfo result) {
        name.setText(mBean.getCorpName());
        phone.setText(mBean.getCorpNamePhone());
        groom.setText(result.getGroom());
        bride.setText(result.getBride());
        hotel.setText(result.getRummeryName());
        address.setText(result.getRummeryAddress());
        room.setText(result.getBanquetHallName());
        mAdapter.refresh(result.getData());
        Glide.with(this).load(mBean.getCorpLogo()).into(logo);
    }

    /**
     * 同意拒绝
     */
    private void agree(String orderID, String type) {
        mDialog.show();
        UserInfo userInfo = UserManager.getInstance(this).getUserInfo();
        NetWorkUtil.setCallback("User/SupplierOrderReview",
                new BeanOrderAgree(userInfo.getSupplierID(), orderID, type),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        mDialog.dismiss();
                        Log.e("返回值",respose);
                        try {
                            ResultGetSupOrder result = new Gson().fromJson(respose, ResultGetSupOrder.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                Toast.makeText(PlanInfoActivity.this, "操作成功！", Toast.LENGTH_SHORT).show();
                                getList();
                            } else {
                                Toast.makeText(PlanInfoActivity.this, "" + result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("json解析出错",e.toString());
                            //Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(final String e) {
                        mDialog.dismiss();
                        Log.e("网络出错",e.toString());
                    }
                });
    }

    /**
     * 获取新增
     */
    private void getList() {
        mDialog.show();
//        UserInfo userInfo = UserManager.getInstance(this).getUserInfo();
        NetWorkUtil.setCallback("User/GetSupplierrOrderInfo",
                new BeanGetPlanInfo("1",mBean.getSupplierOrderID(),"1","200"),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        mDialog.dismiss();
                        Log.e("返回值",respose);
                        try {
                            ResultGetPlanInfo result = new Gson().fromJson(respose, ResultGetPlanInfo.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                setData(result);
                            } else {
                                Toast.makeText(PlanInfoActivity.this, "" + result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("json解析出错",e.toString());
                            //Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(final String e) {
                        mDialog.dismiss();
                        Log.e("网络出错",e.toString());
                    }
                });
    }

    /**
     * 删除备注
     */
    private void deleteRemark() {
        mDialog.show();
        UserInfo userInfo = UserManager.getInstance(this).getUserInfo();
        NetWorkUtil.setCallback("User/AddScheduleRemark",
                new BeanAddMark(userInfo.getSupplierID(), mDayPlanBean.getScheduleID(), ""),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        mDialog.dismiss();
                        Log.e("返回值",respose);
                        try {
                            ResultMessage result = new Gson().fromJson(respose, ResultMessage.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                Toast.makeText(PlanInfoActivity.this, "已删除！", Toast.LENGTH_SHORT).show();
                                remarkLayout.setVisibility(View.GONE);
                                markType = 0;
                                refuse.setVisibility(View.GONE);
                                agree.setText("添加备注");
                            } else {
                                Toast.makeText(PlanInfoActivity.this, "" + result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("json解析出错",e.toString());
                            //Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(final String e) {
                        mDialog.dismiss();
                        Log.e("网络出错",e.toString());
                    }
                });
    }

    private void request() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {
            //申请CALL_PHONE权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, CALL_REQUEST_CODE);
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
                Toast.makeText(this, "已禁止", Toast.LENGTH_SHORT).show();
            }
        }
    }
    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        ImmersionBar.with(this).destroy(); //必须调用该方法，防止内存泄漏
        super.onDestroy();
    }

    /**
     * 事件总线刷新
     * @param bean
     */
    @Subscribe
    public void onEventMainThread(final EventBusBean bean) {
        runOnUiThread(new Runnable() {
            public void run() {
                if(bean.getType() == Constants.ADD_REMARK_SUCCESS){
                    remarkLayout.setVisibility(View.VISIBLE);
                    remark.setText(bean.getData());
                    markType = 1;
                    refuse.setVisibility(View.GONE);
                    agree.setText("添加备注");
                }
            }
        });
    }
}
