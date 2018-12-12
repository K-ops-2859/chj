package com.dikai.chenghunjiclient.entity;

import java.util.List;

/**
 * Created by Lucio on 2018/1/15.
 */

public class ResultGetBanner {
    private ResultCode Message;
    private List<BannerBean> BannnerUrl;
    private List<BannerBean> BannnerUrl1 ;
    private List<BannerBean> BannnerUrl2;
    private List<BannerBean> BannnerUrl3 ;
    private List<BannerBean> hldanbao;
    private List<BannerBean> yqyouli;
    private List<BannerBean> PhoneBanner;
    private String mianfei;
    private String fanxian;
    private String baomihua;
    private String fangan;

    public String getMianfei() {
        return mianfei;
    }

    public String getFanxian() {
        return fanxian;
    }

    public String getBaomihua() {
        return baomihua;
    }

    public String getFangan() {
        return fangan;
    }

    public List<BannerBean> getPhoneBanner() {
        return PhoneBanner;
    }

    public ResultCode getMessage() {
        return Message;
    }

    public List<BannerBean> getBannnerUrl() {
        return BannnerUrl;
    }

    public List<BannerBean> getBannnerUrl1() {
        return BannnerUrl1;
    }

    public List<BannerBean> getBannnerUrl2() {
        return BannnerUrl2;
    }

    public List<BannerBean> getBannnerUrl3() {
        return BannnerUrl3;
    }

    public List<BannerBean> getHldanbao() {
        return hldanbao;
    }

    public List<BannerBean> getYqyouli() {
        return yqyouli;
    }
}
