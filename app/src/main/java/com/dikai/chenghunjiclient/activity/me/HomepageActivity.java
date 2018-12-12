package com.dikai.chenghunjiclient.activity.me;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.activity.wedding.WedRuleActivity;
import com.dikai.chenghunjiclient.bean.BeanAuditStatus;
import com.dikai.chenghunjiclient.entity.ResultGetAudit;
import com.dikai.chenghunjiclient.entity.ResultGetHomeRed;
import com.dikai.chenghunjiclient.util.NetWorkUtil;
import com.dikai.chenghunjiclient.util.UserManager;
import com.dikai.chenghunjiclient.web.BrowserInterface;
import com.dikai.chenghunjiclient.web.MyChromeClient;
import com.dikai.chenghunjiclient.web.MyWebViewClient;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;

import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;

public class HomepageActivity extends AppCompatActivity implements BrowserInterface, View.OnClickListener {

    private WebView mWebView;
    private ProgressBar mProgressBar;
    private TextView mTitle;
    private ImageView share;
    private String url;
    private ResultGetAudit mGetAudit;
    private String pageId;
    private String pageType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
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
        findViewById(R.id.back).setOnClickListener(this);
        share.setOnClickListener(this);
        pageId = getIntent().getStringExtra("pageId");
        pageType = getIntent().getStringExtra("pageType");
        if(getIntent().getIntExtra("type",0) == 1){
            Log.e("网址",url);
            mTitle.setVisibility(View.VISIBLE);
            String title = getIntent().getStringExtra("title");
            mTitle.setText(title);
        }
        getStatus();
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

    /**
     * 获取主页状态
     */
    private void getStatus(){
        NetWorkUtil.setCallback("HQOAApi/AuditStatus",
                new BeanAuditStatus(pageId),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        Log.e("返回值",respose);
                        try {
                            ResultGetAudit result = new Gson().fromJson(respose, ResultGetAudit.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                setData(result);
                            } else {
                                Toast.makeText(HomepageActivity.this, result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("json解析出错",e.toString());
                            //Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(final String e) {
                    }
                });
    }

    private void setData(ResultGetAudit result) {
        mGetAudit = result;
        if(result.getStatus() == 2){
            share.setVisibility(View.VISIBLE);
        }else {
            share.setVisibility(View.GONE);
        }
        mWebView.loadUrl(result.getWebsite());
    }

    private void showShare() {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // 分享时Notification的图标和文字  2.5.9以后的版本不     调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle(mGetAudit.getShareTitle());
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl(mGetAudit.getShareUrl());
        // text是分享文本，所有平台都需要这个字段
        oks.setText(mGetAudit.getShareDescribe());
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        oks.setImagePath("");//确保SDcard下面存在此张图片
        oks.setImageUrl(mGetAudit.getShareImg());
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(mGetAudit.getShareUrl());
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        // oks.setComment("成婚纪——婚礼原来如此简单");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite("成婚纪");
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://www.chenghunji.com/");
//        oks.addHiddenPlatform(SinaWeibo.NAME);
//        oks.addHiddenPlatform(QZone.NAME);
//        oks.addHiddenPlatform(QQ.NAME);
        oks.show(HomepageActivity.this);
    }
}
