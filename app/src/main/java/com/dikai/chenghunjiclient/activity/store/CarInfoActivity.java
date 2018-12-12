package com.dikai.chenghunjiclient.activity.store;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.adapter.store.MainFragmentAdapter;
import com.dikai.chenghunjiclient.bean.BeanBook;
import com.dikai.chenghunjiclient.bean.BeanCollect;
import com.dikai.chenghunjiclient.bean.BeanGetSupList;
import com.dikai.chenghunjiclient.entity.ResultGetSupplierInfo;
import com.dikai.chenghunjiclient.entity.ResultMessage;
import com.dikai.chenghunjiclient.fragment.discover.BeanDynamFragment;
import com.dikai.chenghunjiclient.fragment.store.CarInfoFragment;
import com.dikai.chenghunjiclient.fragment.store.CarsFragment;
import com.dikai.chenghunjiclient.fragment.store.HotelInfoFragment;
import com.dikai.chenghunjiclient.fragment.store.HotelRoomFragment;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import dmax.dialog.SpotsDialog;

public class CarInfoActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int CALL_REQUEST_CODE = 101;
    private LinearLayout mCollect;
    private LinearLayout mCall;
    private LinearLayout mBook;
    private ImageView mCollectPic;
    private ViewPager mViewPager;
    private List<Fragment> mFragments;
    private TabLayout mTabLayout;
    private Toolbar toolbar;
    private ImageView logo;
    private CollapsingToolbarLayout mToolbarLayout;
    private boolean isCollect;
    private String supId;
    private Intent intent;
    private MaterialDialog dialog;
    private String userID;
    private CarInfoFragment mCarInfoFragment;
    private SpotsDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_info);
        mDialog = new SpotsDialog(this,R.style.DialogCustom);
        supId = getIntent().getStringExtra("id");
        userID = getIntent().getStringExtra("userid");
        mToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.activity_hotel_collapsing);
        mCollect = (LinearLayout) findViewById(R.id.activity_info_collect_layout);
        mCall = (LinearLayout) findViewById(R.id.activity_info_call_layout);
        mBook = (LinearLayout) findViewById(R.id.book);
        mCollectPic = (ImageView) findViewById(R.id.activity_info_collect_img);
        logo = (ImageView) findViewById(R.id.activity_hotel_logo);
        toolbar = (Toolbar) findViewById(R.id.activity_hotel_toolbar);
        ImmersionBar.with(this).titleBar(toolbar).init();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mViewPager = (ViewPager) findViewById(R.id.activity_hotel_viewpager);
        mTabLayout = (TabLayout) findViewById(R.id.activity_hotel_tabs);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        setupViewPager();
        mCollect.setOnClickListener(this);
        mCall.setOnClickListener(this);
        mBook.setOnClickListener(this);
        getIdentity();
    }

    private void setupViewPager() {
        mFragments = new ArrayList<>();
        mCarInfoFragment = CarInfoFragment.newInstance(supId);
        Collections.addAll(mFragments, mCarInfoFragment,
                CarsFragment.newInstance(supId), BeanDynamFragment.newInstance(userID));
        // 第二步：为ViewPager设置适配器
        MainFragmentAdapter adapter =
                new MainFragmentAdapter(getSupportFragmentManager(), mFragments);
        List<String> names = new ArrayList<>();
        Collections.addAll(names, "简介", "车型", "动态");
        adapter.setTitleList(names);
        mViewPager.setAdapter(adapter);
        mViewPager.setOffscreenPageLimit(3);
        //  第三步：将ViewPager与TableLayout 绑定在一起
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public void onClick(View v) {
        if(v == mCollect){
            if(isCollect){
                collect("1");
            }else {
                collect("0");
            }
        }else if(v == mCall){
            dialog.show();
        }else if(v == mBook){
            book();
        }
    }

    /**
     * 获取信息
     */
    private void getIdentity(){
        NetWorkUtil.setCallback("User/GetSupplierInfo",
                new BeanGetSupList(supId, UserManager.getInstance(this).getUserInfo().getUserID(),"0"),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        Log.e("返回值",respose);
                        try {
                            ResultGetSupplierInfo result = new Gson().fromJson(respose, ResultGetSupplierInfo.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                setData(result);
                            } else {
                                Toast.makeText(CarInfoActivity.this, result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("json解析出错",e.toString());
                            //Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(final String e) {
                        Toast.makeText(CarInfoActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * 收藏
     */
    private void collect(final String type){
//        NetWorkUtil.setCallback("User/AddAndDelCollectionInfo",
//                new BeanCollect("0", supId, "0", UserManager.getInstance(this).getUserInfo().getUserID(), type),
//                new NetWorkUtil.CallBackListener() {
//                    @Override
//                    public void onFinish(final String respose) {
//                        Log.e("返回值",respose);
//                        try {
//                            ResultMessage result = new Gson().fromJson(respose, ResultMessage.class);
//                            if ("200".equals(result.getMessage().getCode())) {
//                                if("1".equals(type)){
//                                    isCollect = false;
//                                    mCollectPic.setImageResource(R.mipmap.ic_app_uncollect);
//                                }else {
//                                    isCollect = true;
//                                    mCollectPic.setImageResource(R.mipmap.ic_app_collect);
//                                }
//                            } else {
//                                Toast.makeText(CarInfoActivity.this, result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
//                            }
//                        } catch (Exception e) {
//                            Log.e("json解析出错",e.toString());
//                            //Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//
//                    @Override
//                    public void onError(final String e) {
//                        Toast.makeText(CarInfoActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
//                    }
//                });
    }

    /**
     * 预约
     */
    private void book(){
        mDialog.show();
        NetWorkUtil.setCallback("User/CreateUserAppointment",
                new BeanBook(UserManager.getInstance(this).getUserInfo().getUserID(), "", supId),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        mDialog.dismiss();
                        Log.e("返回值",respose);
                        try {
                            ResultMessage result = new Gson().fromJson(respose, ResultMessage.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                Toast.makeText(CarInfoActivity.this, "您的预约已送达，客服会尽快与您联系", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(CarInfoActivity.this, result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("json解析出错",e.toString());
                            //Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(final String e) {
                        mDialog.dismiss();
//                        Toast.makeText(HotelInfoActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void setData(ResultGetSupplierInfo info) {
        mToolbarLayout.setTitle(info.getName());
        if(info.getIsCollection() == 0){
            isCollect = false;
            mCollectPic.setImageResource(R.mipmap.ic_app_uncollect);
        }else {
            isCollect = true;
            mCollectPic.setImageResource(R.mipmap.ic_app_collect);
        }
        mCarInfoFragment.setData(info);
        dialog = new MaterialDialog(this);
        dialog.isTitleShow(false)//
                .btnNum(2)
                .content("是否拨打此联系人电话: " +info.getPhoneNo()+ "？")//
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
                        request();
                    }
                });
        intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + info.getPhoneNo()));
        Glide.with(CarInfoActivity.this).load(info.getHeadportrait()).into(logo);
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

