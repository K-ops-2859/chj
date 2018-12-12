package com.dikai.chenghunjiclient.web;

import android.webkit.WebChromeClient;
import android.webkit.WebView;

/**
 * Created by Lucio.
 * Date: 2016/4/19
 * Email:lixiao2000best@163.com
 * Project: FirstDaDa
 */
public class MyChromeClient extends WebChromeClient {
    private BrowserInterface mBrowserInterface;

    public MyChromeClient(BrowserInterface browserInterface) {
        mBrowserInterface = browserInterface;
    }

    /**
     * 当网页的标题获得之后 会回调这个方法 显示标题
     * @param view
     * @param title
     */
    @Override
    public void onReceivedTitle(WebView view, String title) {
        //TODO:显示网页标题
        if (mBrowserInterface != null) {
            mBrowserInterface.onReceivedTitle(title);
        }
    }

    /**
     * 网页加载的百分比
     * @param view
     * @param newProgress
     */
    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        if (mBrowserInterface != null) {
            mBrowserInterface.onProgressChanged(newProgress);
        }
    }
}
