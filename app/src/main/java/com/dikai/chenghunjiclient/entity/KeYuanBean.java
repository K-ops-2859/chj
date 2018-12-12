package com.dikai.chenghunjiclient.entity;

/**
 * Created by Lucio on 2018/10/30.
 */

public class KeYuanBean {
    private String Identity;
    private String Name;
    private String Phone;
    private String WeddingTime;
    private int DistributionType;
    private String FacilitatorName;//0未分配，1已分配
    private String Id;
    private String Meno;
    private String State;
    private String TablesNumber;
    private String MealMark;
    private String Time;
    private String Area;

    public String getState() {
        return State;
    }

    public String getTablesNumber() {
        return TablesNumber;
    }

    public String getMealMark() {
        return MealMark;
    }

    public String getTime() {
        return Time;
    }

    public String getArea() {
        return Area;
    }

    public int getDistributionType() {
        return DistributionType;
    }

    public String getFacilitatorName() {
        return FacilitatorName;
    }

    public String getId() {
        return Id;
    }

    public String getMeno() {
        return Meno;
    }

    public String getIdentity() {
        return Identity;
    }

    public String getName() {
        return Name;
    }

    public String getPhone() {
        return Phone;
    }

    public String getWeddingTime() {
        return WeddingTime;
    }
}
