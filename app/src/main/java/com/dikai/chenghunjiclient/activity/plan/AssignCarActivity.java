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
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.adapter.plan.AssignCarListAdapter;
import com.dikai.chenghunjiclient.adapter.plan.TeamCarAdapter;
import com.dikai.chenghunjiclient.bean.BeanAssignCar;
import com.dikai.chenghunjiclient.bean.BeanAssignCarList;
import com.dikai.chenghunjiclient.bean.BeanGetTeamCar;
import com.dikai.chenghunjiclient.entity.AssignCarBean;
import com.dikai.chenghunjiclient.entity.ResultAssignCarList;
import com.dikai.chenghunjiclient.entity.ResultTeamCar;
import com.dikai.chenghunjiclient.entity.TeamCarBean;
import com.dikai.chenghunjiclient.util.Constants;
import com.dikai.chenghunjiclient.util.EventBusBean;
import com.dikai.chenghunjiclient.util.NetWorkUtil;
import com.dikai.chenghunjiclient.util.UserManager;
import com.dikai.chenghunjiclient.view.MyLoadRecyclerView;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import dmax.dialog.SpotsDialog;

public class AssignCarActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int CALL_REQUEST_CODE = 120;
    private MyLoadRecyclerView mRecyclerView;
    private AssignCarListAdapter mAdapter;
    private ImageView mBack;
    private TextView num;
    private TextView mFinish;
    private String date;
    private String orderID;
    private TeamCarBean mTeamCarBean;
    private Intent intent;
    private int mSize;
    private SpotsDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_car);
        ImmersionBar.with(this)
                .statusBarView(R.id.top_view)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true, 0.2f)
                .init();
        init();
    }

    private void init() {
        mDialog = new SpotsDialog(this, R.style.DialogCustom);
        mTeamCarBean = (TeamCarBean) getIntent().getSerializableExtra("bean");
        date = getIntent().getStringExtra("date");
        orderID = getIntent().getStringExtra("order");
        mBack = (ImageView) findViewById(R.id.activity_case_back);
        mFinish = (TextView) findViewById(R.id.activity_case_finish);
        num = (TextView) findViewById(R.id.activity_case_num);
        mRecyclerView = (MyLoadRecyclerView) findViewById(R.id.activity_case_recycler);
        mAdapter = new AssignCarListAdapter(this);
        mAdapter.setMoreClickListener(new AssignCarListAdapter.OnMoreClickListener() {
            @Override
            public void onClick(View view, int position, AssignCarBean bean) {
                intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + bean.getDriverPhone()));
                request();
            }
        });

        mAdapter.setChangeListener(new AssignCarListAdapter.OnCheckedChangeListener() {
            @Override
            public void onChecked(int size) {
                mSize = size;
                num.setText("已选择"+ size + "人");
            }
        });

        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLoadChangeListener(new MyLoadRecyclerView.OnLoadChangeListener() {
            @Override
            public void onRefresh() {
                refresh();
            }

            @Override
            public void onLoadMore() {
            }
        });
        mBack.setOnClickListener(this);
        mFinish.setOnClickListener(this);
        num.setText("已选择"+ mSize + "人");
        refresh();
    }

    private void refresh() {
        getCollection();
    }

    @Override
    public void onClick(View v) {
        if(v == mBack){
            onBackPressed();
        }else if(v == mFinish){
            if(mSize <= 0){
                Toast.makeText(this, "请选择至少一名车手！", Toast.LENGTH_SHORT).show();
            }else {
                String ids = "";
                List<String> list = mAdapter.getIds();
                for (int i = 0; i < list.size(); i++) {
                    if(i < list.size() - 1){
                        ids = ids + list.get(i) + ",";
                    }else {
                        ids = ids + list.get(i);
                    }
                }
                assign(ids);
            }
        }
    }

    /**
     * 获取车手
     */
    private void getCollection(){
        NetWorkUtil.setCallback("User/GetDriverListBySupplierID",
                new BeanAssignCarList(UserManager.getInstance(this).getUserInfo().getSupplierID(), mTeamCarBean.getCarID(),"", date),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        mRecyclerView.stopLoad();
                        Log.e("返回值",respose);
                        try {
                            ResultAssignCarList result = new Gson().fromJson(respose, ResultAssignCarList.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                if(result.getData().size() == 0){
                                    mRecyclerView.setHasData(false);
                                }else {
                                    mRecyclerView.setHasData(true);
                                }
                                mAdapter.refresh(result.getData());
                            } else {
                                Toast.makeText(AssignCarActivity.this, result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("json解析出错",e.toString());
                            //Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(final String e) {
                        mRecyclerView.stopLoad();
                        Toast.makeText(AssignCarActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * 获取案例
     */
    private void assign(String ids){
        mDialog.show();
        NetWorkUtil.setCallback("User/AssignmentDriverSupplierOrder",
                new BeanAssignCar(UserManager.getInstance(this).getUserInfo().getSupplierID(), ids,orderID),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        mDialog.dismiss();
                        Log.e("返回值",respose);
                        try {
                            ResultAssignCarList result = new Gson().fromJson(respose, ResultAssignCarList.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                Toast.makeText(AssignCarActivity.this, "分配成功！", Toast.LENGTH_SHORT).show();
                                EventBus.getDefault().post(new EventBusBean(Constants.ASSIGN_CAR_SUCCESS));
                                finish();
                            } else {
                                Toast.makeText(AssignCarActivity.this, result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("json解析出错",e.toString());
                            //Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(final String e) {
                        mDialog.dismiss();
                        Toast.makeText(AssignCarActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
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
        super.onDestroy();
        ImmersionBar.with(this).destroy();
    }
}
