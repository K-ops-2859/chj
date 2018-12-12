package com.dikai.chenghunjiclient.entity;

/**
 * Created by cmk03 on 2017/11/19.
 */

public class WrapperPrizeData {
    private int Grade;
    private int ActivityPrizesID;
    private String CommodityName;
    private String ShowImg;
    private String Country;
    private String CreateTime;
    private String MarketPrice;
    private boolean isTitle = false;
    private int SignBill;

    public WrapperPrizeData(int signBill) {
        SignBill = signBill;
    }

    public WrapperPrizeData(int grade, boolean isTitle) {
        Grade = grade;
        this.isTitle = isTitle;
    }

    public WrapperPrizeData(int grade, int activityPrizesID, String commodityName, String showImg, String country, String createTime, String marketPrice, boolean isTitle, int signBill) {
        Grade = grade;
        ActivityPrizesID = activityPrizesID;
        CommodityName = commodityName;
        ShowImg = showImg;
        Country = country;
        CreateTime = createTime;
        MarketPrice = marketPrice;
        this.isTitle = isTitle;
        this.SignBill = signBill;
    }

    public void setSignBill(int signBill) {
        SignBill = signBill;
    }

    public boolean isTitle() {
        return isTitle;
    }

    public int getGrade() {
        return Grade;
    }

    public int getActivityPrizesID() {
        return ActivityPrizesID;
    }

    public String getCommodityName() {
        return CommodityName;
    }

    public String getShowImg() {
        return ShowImg;
    }

    public String getCountry() {
        return Country;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public String getMarketPrice() {
        return MarketPrice;
    }

    public int getSignBill() {
        return SignBill;
    }
}
