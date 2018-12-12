package com.dikai.chenghunjiclient.entity;

/**
 * Created by Lucio on 2017/9/18.
 */

public class ResultGetUserInfo {

    private ResultCode Message;
    private String UserID;
    private String SupplierID;
    private String PhoneNo;
    private String TrueName;
    private String Profession;
    private int StatusType;
    private String Headportrait;
    private String Age;
    private String ModelID;
    private String CreateTime;
    private int IsMotorcade;
    private String CaptainID;
    private int IsNews;

    public ResultCode getMessage() {
        return Message;
    }

    public String getUserID() {
        return UserID;
    }

    public String getSupplierID() {
        return SupplierID;
    }

    public String getPhoneNo() {
        return PhoneNo;
    }

    public String getTrueName() {
        return TrueName;
    }

    public String getProfession() {
        return Profession;
    }

    public int getStatusType() {
        return StatusType;
    }

    public String getHeadportrait() {
        return Headportrait;
    }

    public String getAge() {
        return Age;
    }

    public String getModelID() {
        return ModelID;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public int getIsMotorcade() {
        return IsMotorcade;
    }

    public String getCaptainID() {
        return CaptainID;
    }

    public int getIsNews() {
        return IsNews;
    }
}
