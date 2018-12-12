package com.dikai.chenghunjiclient.entity;

import java.io.Serializable;

/**
 * Created by Lucio on 2018/8/11.
 */

public class ResultGetNewUserInfo implements Serializable{
    private ResultCode Message;
    private String UserId;
    private String Name;
    private String Headportrait;
    private String Profession;
    private String Phone;
    private String FacilitatorId;
    private String Address;
    private String Abstract;
    private String Identity;
    private String region;
    private String regionname;
    private int IsMotorcade;
    private int IsNews;
    private String CaptainID;

    public ResultCode getMessage() {
        return Message;
    }

    public String getUserId() {
        return UserId;
    }

    public String getName() {
        return Name;
    }

    public String getHeadportrait() {
        return Headportrait;
    }

    public String getProfession() {
        return Profession;
    }

    public String getPhone() {
        return Phone;
    }

    public String getFacilitatorId() {
        return FacilitatorId;
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

    public int getIsMotorcade() {
        return IsMotorcade;
    }

    public int getIsNews() {
        return IsNews;
    }

    public String getCaptainID() {
        return CaptainID;
    }
}
