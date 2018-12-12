package com.dikai.chenghunjiclient.activity.newregister;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.activity.me.EditSupInfoActivity;
import com.dikai.chenghunjiclient.activity.me.SupEditInfoActivity;
import com.dikai.chenghunjiclient.activity.me.UserInfoActivity;
import com.dikai.chenghunjiclient.entity.NewUserInfo;
import com.dikai.chenghunjiclient.util.Constants;
import com.dikai.chenghunjiclient.util.EventBusBean;
import com.dikai.chenghunjiclient.util.UserManager;
import com.dikai.chenghunjiclient.view.MyImageView;
import com.gyf.barlibrary.BarHide;
import com.gyf.barlibrary.ImmersionBar;

import org.greenrobot.eventbus.EventBus;

public class PerfectActivity extends AppCompatActivity implements View.OnClickListener {

    private boolean isNews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfect);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        ImmersionBar.with(this)
                .statusBarView(R.id.top_view)
                .fullScreen(true)
                .hideBar(BarHide.FLAG_HIDE_NAVIGATION_BAR)  //隐藏状态栏或导航栏或两者，不写默认不隐藏
                .statusBarColorTransform(R.color.transparent)
                .navigationBarColorTransform(R.color.transparent)
                .addViewSupportTransformColor(toolbar)
                .init();
        init();
    }

    private void init() {
        MyImageView imageView = (MyImageView) findViewById(R.id.img);
        findViewById(R.id.skip).setOnClickListener(this);
        findViewById(R.id.perfect).setOnClickListener(this);
        NewUserInfo info = UserManager.getInstance(this).getNewUserInfo();
        String code = info.getProfession().toUpperCase();
        Log.e("zhiye", code);
        if("70CD854E-D943-4607-B993-91912329C61E".equals(code)){//用户（新人）
            isNews = true;
            Glide.with(this).load("http://www.chenghunji.com/Download/User/wanshan.png")
                    .placeholder(R.color.gray_background).into(imageView);
        }else {
            isNews = false;
            Glide.with(this).load("http://www.chenghunji.com/Download/User/newwanshan.png")
                    .placeholder(R.color.gray_background).into(imageView);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.skip:
                EventBus.getDefault().post(new EventBusBean(Constants.PHONE_LOGIN_SUCCESS));
                finish();
                break;
            case R.id.perfect:
                EventBus.getDefault().post(new EventBusBean(Constants.PHONE_LOGIN_SUCCESS));
                if(UserManager.getInstance(this).getNewUserInfo().getProfession()
                        .toUpperCase().equals("70CD854E-D943-4607-B993-91912329C61E")){
                    startActivity(new Intent(this, UserInfoActivity.class));
                }else {
                    startActivity(new Intent(this, EditSupInfoActivity.class).putExtra("type",1));
                }
                finish();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        ImmersionBar.with(this).destroy();
        super.onDestroy();
    }
}
