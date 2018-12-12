package com.dikai.chenghunjiclient.activity.wedding;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.web.BrowserInterface;
import com.dikai.chenghunjiclient.web.MyChromeClient;
import com.dikai.chenghunjiclient.web.MyWebViewClient;
import com.gyf.barlibrary.ImmersionBar;

import cn.sharesdk.onekeyshare.OnekeyShare;

public class WedDocActivity extends AppCompatActivity implements View.OnClickListener, BrowserInterface {
    private WebView mWebView;
    private ProgressBar mProgressBar;
    private TextView mTitle;
    private String url;
    private String title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wed_doc);
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
        //设置WebViewClient主要帮助WebView处理各种通知、请求事件
        mWebView.setWebChromeClient(new MyChromeClient(this));
        //设置WebChromeClient主要辅助WebView处理Javascript的对话框、网站图标、网站title、加载进度等
        mWebView.setWebViewClient(new MyWebViewClient());
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
        settings.setUserAgentString("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.134 Safari/537.36");
        //自动缩放
        settings.setBuiltInZoomControls(true);
        settings.setSupportZoom(true);
        //支持获取手势焦点
        mWebView.requestFocusFromTouch();

//        WebSettings settings = mWebView.getSettings();
//        settings.setUserAgentString("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.134 Safari/537.36");
//        //自适应屏幕
//        settings.setUseWideViewPort(true);
//        settings.setLoadWithOverviewMode(true);
//        //自动缩放
//        settings.setBuiltInZoomControls(true);
//        settings.setSupportZoom(true);
//        //支持获取手势焦点
//        mWebView.requestFocusFromTouch();
//        mWebView.setWebViewClient(new MyWeb());

        findViewById(R.id.back).setOnClickListener(this);
        findViewById(R.id.share).setOnClickListener(this);
        url = getIntent().getStringExtra("url");
        title = getIntent().getStringExtra("title");
        mTitle.setText(title);
        mWebView.loadUrl(url);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                onBackPressed();
                break;
            case R.id.share:
                showShare();
                break;
        }
    }

//
//    class MyWeb extends WebViewClient {
//        @Override
//        public boolean shouldOverrideUrlLoading(WebView view, String url) {
//            view.loadUrl(url);//在这里设置对应的操作
//            return false;
//        }
//
//        @Override
//        public void onPageFinished(WebView view, String url) {
//            super.onPageFinished(view, url);
//        }
//
//        @Override
//        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
//            super.onReceivedError(view, request, error);
//        }
//    }

    private void showShare() {
        OnekeyShare oks = new OnekeyShare();
//        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // 分享时Notification的图标和文字  2.5.9以后的版本不     调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle(title);
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl(url);
        // text是分享文本，所有平台都需要这个字段
        oks.setText("您如果想打印此文件，请在电脑端打开");
//        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        oks.setImagePath("");//确保SDcard下面存在此张图片
        oks.setImageUrl("http://www.chenghunji.com/img/index/B_LOGO_01.png");
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(url);
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
//        oks.setComment("成婚纪——婚礼原来如此简单");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite("成婚纪");
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl(url);
        // 启动分享GUI
        oks.show(this);
    }
}
