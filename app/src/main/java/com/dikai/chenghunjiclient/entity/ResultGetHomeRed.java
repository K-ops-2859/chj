package com.dikai.chenghunjiclient.entity;

import java.io.Serializable;

/**
 * Created by Lucio on 2018/5/31.
 */

public class ResultGetHomeRed implements Serializable{
    private ResultCode Message;
    private int Popup;
    private String Imgurl;
    private String Detailsurl;
    private String ShareUrl;
    private String ShareTitle;
    private String ShareDescribe;
    private String ShareImg;

    public ResultGetHomeRed(String shareUrl, String shareTitle, String shareDescribe, String shareImg) {
        ShareUrl = shareUrl;
        ShareTitle = shareTitle;
        ShareDescribe = shareDescribe;
        ShareImg = shareImg;
    }

    public String getShareUrl() {
        return ShareUrl;
    }

    public String getShareTitle() {
        return ShareTitle;
    }

    public String getShareDescribe() {
        return ShareDescribe;
    }

    public String getShareImg() {
        return ShareImg;
    }

    public ResultCode getMessage() {
        return Message;
    }

    public int getPopup() {
        return Popup;
    }

    public String getImgurl() {
        return Imgurl;
    }

    public String getDetailsurl() {
        return Detailsurl;
    }
}
