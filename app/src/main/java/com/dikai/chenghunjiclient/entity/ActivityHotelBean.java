package com.dikai.chenghunjiclient.entity;

/**
 * Created by Lucio on 2018/10/26.
 */

public class ActivityHotelBean {
    private String FacilitatorId;
    private String UserId;
    private String ActivityHotelId;
    private String Name;
    private String Logo;
    private String Phone;
    private String Address;
    private String Abstract;
    private int AnliCount;
    private int StateCount;
    private String Describe;
    private int Xfyl;
    private int Hldb;
    private String CreateTime;
    private boolean noData;

    public ActivityHotelBean(boolean noData) {
        this.noData = noData;
    }

    public boolean isNoData() {
        return noData;
    }

    public String getFacilitatorId() {
        return FacilitatorId;
    }

    public String getUserId() {
        return UserId;
    }

    public String getActivityHotelId() {
        return ActivityHotelId;
    }

    public String getName() {
        return Name;
    }

    public String getLogo() {
        return Logo;
    }

    public String getPhone() {
        return Phone;
    }

    public String getAddress() {
        return Address;
    }

    public String getAbstract() {
        return Abstract;
    }

    public int getAnliCount() {
        return AnliCount;
    }

    public int getStateCount() {
        return StateCount;
    }

    public String getDescribe() {
        return Describe;
    }

    public int getXfyl() {
        return Xfyl;
    }

    public int getHldb() {
        return Hldb;
    }

    public String getCreateTime() {
        return CreateTime;
    }
}
