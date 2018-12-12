package com.dikai.chenghunjiclient.activity.me;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.adapter.GuangGaoAdapter;
import com.dikai.chenghunjiclient.bean.BeanID;
import com.dikai.chenghunjiclient.entity.ImgBean;
import com.dikai.chenghunjiclient.entity.ResultGetRoomInfo;
import com.dikai.chenghunjiclient.util.NetWorkUtil;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;
import com.joooonho.SelectableRoundedImageView;

import java.util.ArrayList;
import java.util.List;

public class NewRoomInfoActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView nowText;
    private TextView totalText;
    private TextView name;
    private TextView info;
    private TextView table;
    private TextView hotel;
    private SelectableRoundedImageView logo;
    private ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_room_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        ImmersionBar.with(this)
                .statusBarView(R.id.top_view)
                .fullScreen(true)
                .statusBarColorTransform(R.color.transparent)
                .navigationBarColorTransform(R.color.transparent)
                .addViewSupportTransformColor(toolbar)
                .init();
        init();
    }

    private void init() {
        findViewById(R.id.back).setOnClickListener(this);
        nowText = (TextView) findViewById(R.id.now);
        totalText = (TextView) findViewById(R.id.total);
        name = (TextView) findViewById(R.id.name);
        info = (TextView) findViewById(R.id.info);
        table = (TextView) findViewById(R.id.table);
        hotel = (TextView) findViewById(R.id.hotel);
        logo = (SelectableRoundedImageView) findViewById(R.id.logo);
        pager = (ViewPager) findViewById(R.id.photo_pager);

        getInfo(getIntent().getStringExtra("roomid"));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                onBackPressed();
                break;
        }
    }

    private void setData(ResultGetRoomInfo result) {
        name.setText(result.getBanquetHallName());
        info.setText("面积："+result.getAcreage()+"㎡      层高："+result.getHeight()
                +"m     长·宽："+result.getLength()+"m · "+result.getWidth()+"m");
        table.setText("容纳桌数："+result.getMinTableCount()+"~"+result.getMaxTableCount()+"桌");
        nowText.setText(result.getData().size()>0?"1":"0");
        totalText.setText("/"+result.getData().size());
        hotel.setText(result.getHotelName());
        ArrayList<String> pics = new ArrayList<>();
        for (ImgBean bean :result.getData()) {
            pics.add(bean.getImgUrl());
        }
        pager.setAdapter(new GuangGaoAdapter(getSupportFragmentManager(),pics));
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}
            @Override
            public void onPageSelected(int position) {
                nowText.setText(position+1+"");
            }
            @Override
            public void onPageScrollStateChanged(int state) {}
        });
        Glide.with(NewRoomInfoActivity.this).load(result.getHotelLogo()).into(logo);
    }

    /**
     * 获取信息
     */
    private void getInfo(String id){
        NetWorkUtil.setCallback("HQOAApi/GetHotelBanquetlInfo",
                new BeanID(id),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        Log.e("返回值",respose);
                        try {
                            ResultGetRoomInfo result = new Gson().fromJson(respose, ResultGetRoomInfo.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                setData(result);
                            } else {
                                Toast.makeText(NewRoomInfoActivity.this, result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("json解析出错",e.toString());
                            //Toast.makeText(CorpInfoActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(final String e) {
                        Toast.makeText(NewRoomInfoActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    protected void onDestroy() {
        ImmersionBar.with(this).destroy();
        super.onDestroy();
    }
}
