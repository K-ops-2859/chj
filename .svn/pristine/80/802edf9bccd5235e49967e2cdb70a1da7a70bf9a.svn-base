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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.activity.PhotoActivity;
import com.dikai.chenghunjiclient.adapter.store.HotelPicAdapter;
import com.dikai.chenghunjiclient.adapter.store.MainFragmentAdapter;
import com.dikai.chenghunjiclient.bean.BeanBook;
import com.dikai.chenghunjiclient.bean.BeanCollect;
import com.dikai.chenghunjiclient.bean.BeanGetSupList;
import com.dikai.chenghunjiclient.bean.BeanID;
import com.dikai.chenghunjiclient.entity.ImgBean;
import com.dikai.chenghunjiclient.entity.ResultGetSupplierInfo;
import com.dikai.chenghunjiclient.entity.ResultMessage;
import com.dikai.chenghunjiclient.entity.ResultNewSupInfo;
import com.dikai.chenghunjiclient.fragment.discover.BeanDynamFragment;
import com.dikai.chenghunjiclient.fragment.store.CorpInfoFragment;
import com.dikai.chenghunjiclient.fragment.store.SupADBeanFragment;
import com.dikai.chenghunjiclient.util.Constants;
import com.dikai.chenghunjiclient.util.EventBusBean;
import com.dikai.chenghunjiclient.util.NetWorkUtil;
import com.dikai.chenghunjiclient.util.UserManager;
import com.dikai.chenghunjiclient.view.MyRecyclerView;
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

import dmax.dialog.SpotsDialog;

public class CorpInfoActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int CALL_REQUEST_CODE = 101;
    private String supId;
//    private LinearLayout mCollect;
    private TextView mCall;
    private TextView mBook;
//    private ImageView mCollectPic;
    private SelectableRoundedImageView logo;
    private TextView identity;
//    private boolean isCollect;
    private SpotsDialog mDialog;
    private int type;
    private Intent intent;
    private MaterialDialog dialog;
    private AppBarLayout mAppBar;
    private String userID;
    private CollapsingToolbarLayout mToolbarLayout;
    private ViewPager mViewPager;
    private List<Fragment> mFragments;
    private TabLayout mTabLayout;

    private LinearLayout tag1Layout;
    private TextView hotelTag1;
    private TextView hotelTag2;
    private TextView hotelTag3;
    private TextView picNumber;
    private TextView address;
    private List<String> pics;
    private TextView name;
    private TextView infoAll;
    private TextView infoText;
    private ImageView back;
    private boolean isAllInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_corp_info);
        ImmersionBar.with(this)
                .statusBarView(R.id.top_view)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true, 0.2f)
                .init();
        init();
    }

    private void init() {
        type = getIntent().getIntExtra("type",0);
        supId = getIntent().getStringExtra("id");
        userID = getIntent().getStringExtra("userid");
        mToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.activity_hotel_collapsing);
        back = (ImageView) findViewById(R.id.back);
        logo = (SelectableRoundedImageView) findViewById(R.id.activity_hotel_logo);
        identity = (TextView) findViewById(R.id.identity);
        picNumber = (TextView) findViewById(R.id.pic_number);
        name = (TextView) findViewById(R.id.name);
        infoText = (TextView) findViewById(R.id.info);
        address = (TextView) findViewById(R.id.address);
        tag1Layout = (LinearLayout) findViewById(R.id.tag_1_layout);
        hotelTag1 = (TextView) findViewById(R.id.tag_1);
        hotelTag2 = (TextView) findViewById(R.id.tag_2);
        hotelTag3 = (TextView) findViewById(R.id.tag_3);
        infoAll = (TextView) findViewById(R.id.info_all);
        mViewPager = (ViewPager) findViewById(R.id.activity_hotel_viewpager);
        mTabLayout = (TabLayout) findViewById(R.id.activity_hotel_tabs);
        mDialog = new SpotsDialog(this,R.style.DialogCustom);
        mCall = (TextView) findViewById(R.id.activity_info_call_layout);
        mBook = (TextView) findViewById(R.id.book);
        mCall.setOnClickListener(this);
        mBook.setOnClickListener(this);
        picNumber.setOnClickListener(this);
        logo.setOnClickListener(this);
        back.setOnClickListener(this);
        infoAll.setOnClickListener(this);

        if(supId!= null && userID != null){
            setupViewPager();
        }else {
            Toast.makeText(this, "发生错误", Toast.LENGTH_SHORT).show();
        }
    }

    private void setupViewPager() {
        mFragments = new ArrayList<>();
        Collections.addAll(mFragments, BeanDynamFragment.newInstance(userID),
                SupADBeanFragment.newInstance(supId), CorpInfoFragment.newInstance(supId));
        // 第二步：为ViewPager设置适配器
        MainFragmentAdapter adapter =
                new MainFragmentAdapter(getSupportFragmentManager(), mFragments);
        List<String> names = new ArrayList<>();
        Collections.addAll(names, "动态", "特惠", "案例");
        adapter.setTitleList(names);
        mViewPager.setAdapter(adapter);
        mViewPager.setOffscreenPageLimit(3);
        // 第三步：将ViewPager与TableLayout 绑定在一起
        mTabLayout.setupWithViewPager(mViewPager);
        getIdentity();
    }

    @Override
    protected void onDestroy() {
        ImmersionBar.with(this).destroy(); //必须调用该方法，防止内存泄漏
        super.onDestroy();
    }

    private void setData(ResultNewSupInfo result){
        tag1Layout.setVisibility(View.GONE);
        hotelTag2.setVisibility(View.GONE);
        hotelTag3.setVisibility(View.GONE);

        name.setText(result.getName());
        identity.setText(UserManager.getInstance(this).getProfession(result.getIdentity()));
        infoText.setText(result.getAbstract());
        address.setText(result.getAddress());

        if(result.getTag() != null && !"".equals(result.getTag())){
            hotelTag1.setText(result.getTag());
            tag1Layout.setVisibility(View.VISIBLE);
        }
        if(result.getXfyl() == 1){
            hotelTag2.setVisibility(View.VISIBLE);
        }
        if(result.getHldb() == 1){
            hotelTag3.setVisibility(View.VISIBLE);
        }
        dialog = new MaterialDialog(this);
        dialog.isTitleShow(false)//
                .btnNum(2)
                .content("是否拨打此联系人电话: " +result.getPhone()+ "？")//
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
        intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + result.getPhone()));
        Glide.with(CorpInfoActivity.this).load(result.getLogo()).into(logo);

        if(result.getData().size() == 0){
            Glide.with(CorpInfoActivity.this).load(result.getLogo()).into(logo);
            picNumber.setText("0 张图");
        }else {
            pics =  new ArrayList<>();
            for (ImgBean bean : result.getData()) {
                pics.add(bean.getImgUrl());
            }
            picNumber.setText(pics.size() + " 张图");
            Glide.with(CorpInfoActivity.this).load(pics.get(0)).into(logo);
        }
    }

    @Override
    public void onClick(View v) {
        if(v==back){
            onBackPressed();
        }else if(v == mCall){
            dialog.show();
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
    private void getIdentity(){
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
                                Toast.makeText(CorpInfoActivity.this, result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("json解析出错",e.toString());
                            //Toast.makeText(CorpInfoActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(final String e) {
                        Toast.makeText(CorpInfoActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
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
//                                Toast.makeText(CorpInfoActivity.this, result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
//                            }
//                        } catch (Exception e) {
//                            Log.e("json解析出错",e.toString());
//                            //Toast.makeText(CorpInfoActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//
//                    @Override
//                    public void onError(final String e) {
//                        Toast.makeText(CorpInfoActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
//                    }
//                });
//    }


    /**
     * 预约
     */
    private void book(){
        mDialog.show();
        NetWorkUtil.setCallback("HQOAApi/CreateUserAppointment",
                new BeanBook(UserManager.getInstance(this).getNewUserInfo().getUserId(), "0", supId),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        mDialog.dismiss();
                        Log.e("返回值",respose);
                        try {
                            ResultMessage result = new Gson().fromJson(respose, ResultMessage.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                Toast.makeText(CorpInfoActivity.this, "您的预约已送达，客服会尽快与您联系", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(CorpInfoActivity.this, result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
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


}
