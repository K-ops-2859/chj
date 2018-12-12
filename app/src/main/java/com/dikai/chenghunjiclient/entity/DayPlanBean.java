package com.dikai.chenghunjiclient.entity;

import java.io.Serializable;

/**
 * Created by Lucio on 2017/9/21.
 */

public class DayPlanBean implements Serializable{
    private String ScheduleID;
    private String OrderID;
    private String CorpName;
    private String CorpPhone;
    private String CorpLogo;
    private String CorpRummeryName;
    private String RummeryAddress;
    private int ScheduleType;
    private int SettlementStatus;
    private String Title;
    private String LogContent;
    private String CreateTime;
    private String Remark;
    private String WeddingDate;

    public String getWeddingDate() {
        return WeddingDate;
    }

    public String getScheduleID() {
        return ScheduleID;
    }

    public String getOrderID() {
        return OrderID;
    }

    public String getCorpName() {
        return CorpName;
    }

    public String getCorpNamePhone() {
        return CorpPhone;
    }

    public String getCorpLogo() {
        return CorpLogo;
    }

    public String getCorpRummeryName() {
        return CorpRummeryName;
    }

    public String getRummeryAddress() {
        return RummeryAddress;
    }

    public int getScheduleType() {
        return ScheduleType;
    }

    public int getSettlementStatus() {
        return SettlementStatus;
    }

    public String getTitle() {
        return Title;
    }

    public String getLogContent() {
        return LogContent;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public String getRemark() {
        return Remark;
    }
}
