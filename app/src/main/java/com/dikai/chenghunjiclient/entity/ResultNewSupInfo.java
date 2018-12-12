package com.dikai.chenghunjiclient.entity;

import java.util.List;

/**
 * Created by Lucio on 2018/8/18.
 */

public class ResultNewSupInfo {
    private ResultCode Message;
    private String Id;
    private String UserId;
    private String Name;
    private String Logo;
    private String Phone;
    private String Address;
    private String Abstract;
    private String Identity;
    private String region;
    private String regionname;
    private String Tag;
//    private String CoverMap;
    private int Hldb;
    private int Xfyl;
    private String MealMark;
    private String ServiceCharge;
    private int BasicPreferencesCount;
    private List<ImgBean> Data;

    public int getHldb() {
        return Hldb;
    }

    public int getXfyl() {
        return Xfyl;
    }

    public String getTag() {
        return Tag;
    }

//    public String getCoverMap() {
//        return CoverMap;
//    }

    public ResultCode getMessage() {
        return Message;
    }

    public String getId() {
        return Id;
    }

    public String getUserId() {
        return UserId;
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

    public String getIdentity() {
        return Identity;
    }

    public String getRegion() {
        return region;
    }

    public String getRegionname() {
        return regionname;
    }

    public String getMealMark() {
        return MealMark;
    }

    public String getServiceCharge() {
        return ServiceCharge;
    }

    public int getBasicPreferencesCount() {
        return BasicPreferencesCount;
    }

    public List<ImgBean> getData() {
        return Data;
    }
}
