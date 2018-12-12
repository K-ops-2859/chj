package com.dikai.chenghunjiclient.activity.store;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.activity.PhotoActivity;
import com.dikai.chenghunjiclient.activity.me.DiscountsActivity;
import com.dikai.chenghunjiclient.adapter.store.MainFragmentAdapter;
import com.dikai.chenghunjiclient.bean.BeanBook;
import com.dikai.chenghunjiclient.bean.BeanCollect;
import com.dikai.chenghunjiclient.bean.BeanGetSupList;
import com.dikai.chenghunjiclient.bean.BeanID;
import com.dikai.chenghunjiclient.bean.BeanOnlyFacilitatorId;
import com.dikai.chenghunjiclient.entity.DiscountsBean;
import com.dikai.chenghunjiclient.entity.ImgBean;
import com.dikai.chenghunjiclient.entity.ResultDiscounts;
import com.dikai.chenghunjiclient.entity.ResultGetSupplierInfo;
import com.dikai.chenghunjiclient.entity.ResultMessage;
import com.dikai.chenghunjiclient.entity.ResultNewSupInfo;
import com.dikai.chenghunjiclient.fragment.discover.BeanDynamFragment;
import com.dikai.chenghunjiclient.fragment.store.HotelInfoFragment;
import com.dikai.chenghunjiclient.fragment.store.HotelRoomFragment;
import com.dikai.chenghunjiclient.fragment.store.SupADBeanFragment;
import com.dikai.chenghunjiclient.util.Constants;
import com.dikai.chenghunjiclient.util.EventBusBean;
import com.dikai.chenghunjiclient.util.NetWorkUtil;
import com.dikai.chenghunjiclient.util.UserManager;
import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.widget.MaterialDialog;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;
import com.joooonho.SelectableRoundedImageView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class HotelInfoActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int CALL_REQUEST_CODE = 101;
    private TextView mCall;
    private TextView mBook;
    private ViewPager mViewPager;
    private List<Fragment> mFragments;
    private TabLayout mTabLayout;
    private SelectableRoundedImageView logo;
    private String supId;
    private Intent intent;
    private MaterialDialog dialog;
    private String userID;
    private LinearLayout tag1Layout;
    private TextView hotelTag1;
    private TextView hotelTag2;
    private TextView hotelTag3;
    private TextView picNumber;
    private TextView address;
    private List<String> pics;

    private AppBarLayout mBarLayout;
    private TextView identity;
    private TextView name;
    private TextView infoAll;
    private TextView infoText;
    private ImageView back;
    private TextView canbiao;
    private TextView service;
    private TextView discounts;
    private TextView title;
    private boolean isAllInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try{
            setContentView(R.layout.activity_hotel_info);
            ImmersionBar.with(this)
                    .statusBarView(R.id.top_view)
                    .statusBarColor(R.color.white)
                    .statusBarDarkFont(true, 0.2f)
                    .init();
            supId = getIntent().getStringExtra("id");
            userID = getIntent().getStringExtra("userid");
            mBarLayout = (AppBarLayout) findViewById(R.id.activity_hotel_appbar);
            mCall = (TextView) findViewById(R.id.activity_info_call_layout);
            mBook = (TextView) findViewById(R.id.book);
            identity = (TextView) findViewById(R.id.identity);
            name = (TextView) findViewById(R.id.name);
            back = (ImageView) findViewById(R.id.back);
            logo = (SelectableRoundedImageView) findViewById(R.id.activity_hotel_logo);
            tag1Layout = (LinearLayout) findViewById(R.id.tag_1_layout);
            title = (TextView) findViewById(R.id.title);
            hotelTag1 = (TextView) findViewById(R.id.tag_1);
            hotelTag2 = (TextView) findViewById(R.id.tag_2);
            hotelTag3 = (TextView) findViewById(R.id.tag_3);
            picNumber = (TextView) findViewById(R.id.pic_number);
            infoAll = (TextView) findViewById(R.id.info_all);
            canbiao = (TextView) findViewById(R.id.canbiao);
            address = (TextView) findViewById(R.id.address);
            infoText = (TextView) findViewById(R.id.info);
            service = (TextView) findViewById(R.id.service);
            discounts = (TextView) findViewById(R.id.discounts);
            mViewPager = (ViewPager) findViewById(R.id.activity_hotel_viewpager);
            mTabLayout = (TabLayout) findViewById(R.id.activity_hotel_tabs);
            setupViewPager();
            mCall.setOnClickListener(this);
            mBook.setOnClickListener(this);
            hotelTag1.setOnClickListener(this);
            picNumber.setOnClickListener(this);
            logo.setOnClickListener(this);
            back.setOnClickListener(this);
            infoAll.setOnClickListener(this);
            getInfo();
            getDiscounts(supId);
        }catch (Exception e){
            Log.e("",e.toString());
        }
    }

    private void setupViewPager() {
        mFragments = new ArrayList<>();
        Collections.addAll(mFragments,HotelRoomFragment.newInstance(supId), BeanDynamFragment.newInstance(userID),
                SupADBeanFragment.newInstance(supId), HotelInfoFragment.newInstance(supId));
        // 第二步：为ViewPager设置适配器
        MainFragmentAdapter adapter =
                new MainFragmentAdapter(getSupportFragmentManager(), mFragments);
        List<String> names = new ArrayList<>();
        Collections.addAll(names, "宴会厅", "动态", "特惠", "案例");
        adapter.setTitleList(names);
        mViewPager.setAdapter(adapter);
        mViewPager.setOffscreenPageLimit(4);
        //  第三步：将ViewPager与TableLayout 绑定在一起
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public void onClick(View v) {
        if(v == mCall){
            dialog.show();
        }else if(v == back){
            onBackPressed();
        }else if(v == mBook){
            book();
        }else if(v == infoAll){
            if(isAllInfo){
                isAllInfo = false;
                infoText.setMaxLines(3);
                infoAll.setText("查看全部");
            }else {
                isAllInfo = true;
                infoText.setMaxLines(1000);
                infoAll.setText("收起");
            }
        }else if(v == hotelTag1){
            startActivity(new Intent(this,HotelADActivity.class));
        }else if(v == picNumber || v == logo){
            if(pics != null && pics.size() > 0){
                Intent intent = new Intent(this, PhotoActivity.class);
                intent.putExtra("now", 0);
                intent.putStringArrayListExtra("imgs", new ArrayList<>(pics));
                startActivity(intent);
            }else {
                Toast.makeText(this, "暂无封面", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * 获取信息
     */
    private void getInfo(){
        NetWorkUtil.setCallback("HQOAApi/GetFacilitatorInfo",
                new BeanID(supId),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        Log.e("返回值",respose);
                        try {
                            ResultNewSupInfo result = new Gson().fromJson(respose, ResultNewSupInfo.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                setData(result);
                            } else {
                                Toast.makeText(HotelInfoActivity.this, result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("json解析出错",e.toString());
                            //Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(final String e) {
                        Toast.makeText(HotelInfoActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
                    }
                });
    }

//    /**
//     * 收藏
//     */
//    private void collect(final String type){
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
//                                Toast.makeText(HotelInfoActivity.this, result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
//                            }
//                        } catch (Exception e) {
//                            Log.e("json解析出错",e.toString());
//                            //Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//
//                    @Override
//                    public void onError(final String e) {
//                        Toast.makeText(HotelInfoActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
//                    }
//                });
//    }

    /**
     * 预约
     */
    private void book(){
        NetWorkUtil.setCallback("HQOAApi/CreateUserAppointment",
                new BeanBook(UserManager.getInstance(this).getNewUserInfo().getUserId(), "0", supId),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        Log.e("返回值",respose);
                        try {
                            ResultMessage result = new Gson().fromJson(respose, ResultMessage.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                Toast.makeText(HotelInfoActivity.this, "您的预约已送达，客服会尽快与您联系", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(HotelInfoActivity.this, result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("json解析出错",e.toString());
                            //Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(final String e) {
//                        Toast.makeText(HotelInfoActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * 获取优惠
     */
    private void getDiscounts(String supID){
        NetWorkUtil.setCallback("HQOAApi/GetFacilitatorBasicPreferences",
                new BeanOnlyFacilitatorId(supID),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        Log.e("返回值",respose);
                        try {
                            ResultDiscounts result = new Gson().fromJson(respose, ResultDiscounts.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                setDiscounts(result);
                            } else {
                                Toast.makeText(HotelInfoActivity.this, result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("json解析出错",e.toString());
                            //Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(final String e) {
                        Toast.makeText(HotelInfoActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private void setDiscounts(ResultDiscounts result) {
        if(result.getData().size() > 0){
            String temp = "";
            for (DiscountsBean bean:result.getData()) {
                temp = temp + "<br>" + "<font color=\"#FA7896\">●</font>  " + bean.getDiscount();
            }
            temp = temp.replaceFirst("<br>","");
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                discounts.setText(Html.fromHtml(temp,Html.FROM_HTML_MODE_LEGACY));
            } else {
                discounts.setText(Html.fromHtml(temp));
            }
        }else {
            discounts.setText("当前无优惠");
        }
    }

    private void setData(ResultNewSupInfo info) {
        infoText.setText(info.getAbstract());
        address.setText(info.getAddress());
        tag1Layout.setVisibility(View.GONE);
        hotelTag2.setVisibility(View.GONE);
        hotelTag3.setVisibility(View.GONE);
        name.setText(info.getName());
        if(info.getTag() != null && !"".equals(info.getTag())){
            hotelTag1.setText(info.getTag());
            tag1Layout.setVisibility(View.VISIBLE);
        }
        if(info.getXfyl() == 1){
            hotelTag2.setVisibility(View.VISIBLE);
        }
        if(info.getHldb() == 1){
            hotelTag3.setVisibility(View.VISIBLE);
        }
        canbiao.setText("".equals(info.getMealMark())?"无餐标":info.getMealMark().replaceAll(",","/"));
        service.setText("".equals(info.getServiceCharge())?"无服务费":info.getServiceCharge()+"%");

        dialog = new MaterialDialog(this);
        dialog.isTitleShow(false)
                .btnNum(2)
                .content("是否拨打此联系人电话: " +info.getPhone()+ "？")//
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
        intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + info.getPhone()));

        if(info.getData().size() == 0){
            Glide.with(HotelInfoActivity.this).load(info.getLogo()).into(logo);
            picNumber.setText("0 张图");
        }else {
            pics =  new ArrayList<>();
            for (ImgBean bean : info.getData()) {
                pics.add(bean.getImgUrl());
            }
            picNumber.setText(pics.size() + " 张图");
            Glide.with(HotelInfoActivity.this).load(pics.get(0)).into(logo);
        }
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
    public void onDestroy() {
        super.onDestroy();
        ImmersionBar.with(this).destroy();
    }
}
