package com.dikai.chenghunjiclient.entity;

import java.util.List;

/**
 * Created by Lucio on 2018/9/20.
 */

public class NewOrderBean {
    private String Id;
    private int IsStatus;
    private String CreateTime;
    private String ExpressName;
    private String ExpressNumber;
    private String Quota;
    private String ActivityCategoryName;
    private String Phone;
    private List<NewOrderItemBean> CommodityData;

    public String getId() {
        return Id;
    }

    public int getIsStatus() {
        return IsStatus;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public String getExpressName() {
        return ExpressName;
    }

    public String getExpressNumber() {
        return ExpressNumber;
    }

    public String getQuota() {
        return Quota;
    }

    public String getActivityCategoryName() {
        return ActivityCategoryName;
    }

    public String getPhone() {
        return Phone;
    }

    public List<NewOrderItemBean> getCommodityData() {
        return CommodityData;
    }
}
