package com.dikai.chenghunjiclient.activity.wedding;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.activity.register.LoginActivity;
import com.dikai.chenghunjiclient.util.UserManager;
import com.gyf.barlibrary.ImmersionBar;

import cn.sharesdk.onekeyshare.OnekeyShare;

public class FreeWedActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_free_wed);
        ImmersionBar.with(this)
                .statusBarView(R.id.top_view)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true, 0.2f)
                .init();
        init();
    }

    private void init() {
        findViewById(R.id.back).setOnClickListener(this);
        findViewById(R.id.apply).setOnClickListener(this);
        findViewById(R.id.rule).setOnClickListener(this);
        findViewById(R.id.share).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                onBackPressed();
                break;
            case R.id.apply:
                if(UserManager.getInstance(FreeWedActivity.this).isLogin()){
                    startActivity(new Intent(this, MyWedApplyActivity.class));
                }else {
                    startActivity(new Intent(FreeWedActivity.this, LoginActivity.class));
                }
                break;
            case R.id.share:
                if(UserManager.getInstance(FreeWedActivity.this).isLogin()){
                    showShare();
                }else {
                    startActivity(new Intent(FreeWedActivity.this, LoginActivity.class));
                }
                break;
            case R.id.rule:
                startActivity(new Intent(this, WedRuleActivity.class)
                        .putExtra("url","http://www.chenghunji.com/Download/Rules/MyFreeWedding.html"));
                break;
        }
    }

    private void showShare() {
        OnekeyShare oks = new OnekeyShare();
//        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // 分享时Notification的图标和文字  2.5.9以后的版本不     调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle("【0元办婚礼】赶紧领取您的专属免单特权！");
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl("http://www.chenghunji.com/Redbag/yqoqingjiehun");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("省钱小能手已上线！来成婚纪办婚礼真“免”单，点开立享10%奖励金，就是这么任！性!");
//        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        oks.setImagePath("");//确保SDcard下面存在此张图片
        oks.setImageUrl("http://www.chenghunji.com/img/index/B_LOGO_01.png");
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://www.chenghunji.com/Redbag/yqoqingjiehun");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
//        oks.setComment("成婚纪——婚礼原来如此简单");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite("成婚纪");
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://www.chenghunji.com/");
        // 启动分享GUI
        oks.show(FreeWedActivity.this);
    }

    @Override
    protected void onDestroy() {
        ImmersionBar.with(this).destroy();
        super.onDestroy();
    }
}
