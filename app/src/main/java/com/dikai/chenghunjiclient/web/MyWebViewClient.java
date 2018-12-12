package com.dikai.chenghunjiclient.web;

import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by Lucio.
 * Date: 2016/4/19
 * Email:lixiao2000best@163.com
 * Project: FirstDaDa
 */
public class MyWebViewClient extends WebViewClient {
    /**
     * 如果这个方法返回true 那么WebView不会自己加载网址
     * 相当于网址的拦截
     * @param view
     * @return
     */
//    @Override
//    public boolean shouldOverrideUrlLoading(WebView view, String url) {
//        return super.shouldOverrideUrlLoading(view, url);
//    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        return super.shouldOverrideUrlLoading(view, request);
    }
}
