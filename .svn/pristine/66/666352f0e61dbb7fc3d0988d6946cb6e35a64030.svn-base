package com.dikai.chenghunjiclient.activity.store;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.activity.wedding.WedRuleActivity;
import com.dikai.chenghunjiclient.entity.ResultGetHomeRed;
import com.dikai.chenghunjiclient.web.BrowserInterface;
import com.dikai.chenghunjiclient.web.MyChromeClient;
import com.dikai.chenghunjiclient.web.MyWebViewClient;
import com.gyf.barlibrary.ImmersionBar;

import java.util.HashMap;
import java.util.Map;

import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;

public class WeChatPayH5Activity extends AppCompatActivity implements View.OnClickListener, BrowserInterface {

    private WebView mWebView;
    private ProgressBar mProgressBar;
    private TextView mTitle;
    private ImageView share;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_we_chat_pay_h5);
        ImmersionBar.with(this)
                .statusBarView(R.id.top_view)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true, 0.2f)
                .init();
        init();
    }

    private void init() {
        mWebView = (WebView) findViewById(R.id.activity_vip_web);
        mProgressBar = (ProgressBar) findViewById(R.id.activity_vip_progress);
        mTitle = (TextView) findViewById(R.id.title);
        share = (ImageView) findViewById(R.id.share);
        //设置WebViewClient主要帮助WebView处理各种通知、请求事件
        mWebView.setWebChromeClient(new MyChromeClient(this));
        //设置WebChromeClient主要辅助WebView处理Javascript的对话框、网站图标、网站title、加载进度等
        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.e("w微信支付url:",url);
                if (url.startsWith("weixin://wap/pay?")) {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(url));
                    startActivity(intent);
                    return true;
                } else {
                    //H5微信支付要用，不然说"商家参数格式有误"
                    Map<String, String> extraHeaders = new HashMap<>();
                    extraHeaders.put("Referer", "http://www.chenghunji.com/");
                    view.loadUrl(url, extraHeaders);
                }
                return true;
            }
        });
        //设置 开启JS
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        // 设置出现缩放工具
//        mWebView.getSettings().setBuiltInZoomControls(true);
        //扩大比例的缩放
        mWebView.getSettings().setUseWideViewPort(true);
        //自适应屏幕
        mWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        mWebView.getSettings().setLoadWithOverviewMode(true);
        findViewById(R.id.back).setOnClickListener(this);
        url = getIntent().getStringExtra("url");
        if(getIntent().getIntExtra("type",0) == 1){
            Log.e("网址",url);
            mTitle.setVisibility(View.VISIBLE);
            String title = getIntent().getStringExtra("title");
            mTitle.setText(title);
        }
        mWebView.loadUrl(url);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                onBackPressed();
                break;
//            case R.id.share:
//                showShare();
//                break;
            default:
                break;
        }
    }

    @Override
    public void onReceivedTitle(String title) {

    }

    @Override
    public void onProgressChanged(int progress) {
        mProgressBar.setProgress(progress);
        if(progress >= 100){
            mProgressBar.setVisibility(View.GONE);
        }else {
            mProgressBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onBackPressed() {
        if(mWebView.canGoBack()){
            mWebView.goBack();
        }else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImmersionBar.with(this).destroy();
    }


//    private void showShare() {
//        OnekeyShare oks = new OnekeyShare();
//        //关闭sso授权
//        oks.disableSSOWhenAuthorize();
//        // 分享时Notification的图标和文字  2.5.9以后的版本不     调用此方法
//        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
//        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
//        oks.setTitle(mHomeRed.getShareTitle());
//        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
//        oks.setTitleUrl(mHomeRed.getShareUrl());
//        // text是分享文本，所有平台都需要这个字段
//        oks.setText(mHomeRed.getShareDescribe());
//        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
//        oks.setImagePath("");//确保SDcard下面存在此张图片
//        oks.setImageUrl(mHomeRed.getShareImg());
//        // url仅在微信（包括好友和朋友圈）中使用
//        oks.setUrl(url);
//        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
//        // oks.setComment("成婚纪——婚礼原来如此简单");
//        // site是分享此内容的网站名称，仅在QQ空间使用
//        oks.setSite("成婚纪");
//        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
//        oks.setSiteUrl("http://www.chenghunji.com/");
//        oks.addHiddenPlatform(SinaWeibo.NAME);
//        oks.addHiddenPlatform(QZone.NAME);
//        oks.addHiddenPlatform(QQ.NAME);
//        if(isToFriends){
//            oks.setPlatform(Wechat.NAME);//微信好友
//        }else {
//            oks.setPlatform(WechatMoments.NAME);//微信朋友圈
//        }
//
//        oks.setCallback(new PlatformActionListener() {
//            @Override
//            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
//                mRedDialog.dismiss();
//                mRedDialog.setFinishShare(true);
//                mRedDialog.setUiBeforShow();
//                mRedDialog.show();
//                getRedPacket();
//                Toast.makeText(MainActivity.this, "分享成功！", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onError(Platform platform, int i, Throwable throwable) {
//                Toast.makeText(MainActivity.this, "分享失败！", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onCancel(Platform platform, int i) {
//                Toast.makeText(MainActivity.this, "取消分享！", Toast.LENGTH_SHORT).show();
//            }
//        });
//        // 启动分享GUI
//        oks.show(WedRuleActivity.this);
//    }

}

