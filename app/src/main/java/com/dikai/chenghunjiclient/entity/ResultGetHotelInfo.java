package com.dikai.chenghunjiclient.entity;

import java.io.Serializable;

/**
 * Created by Lucio on 2017/9/11.
 */

public class ResultGetHotelInfo implements Serializable{
    private ResultCode Message;
    private String RummeryID;
    private String BriefinTroduction;
    private String Region;
    private String Abbreviation;
    private String RummeryName;
    private String HotelLogo;
    private String HotelImgs;
    private String ContactName;
    private String ContactPhone;
    private String Address;
    private String LowestPrice;
    private String IsStatus;

    public ResultCode getMessage() {
        return Message;
    }

    public String getRummeryID() {
        return RummeryID;
    }

    public String getBriefinTroduction() {
        return BriefinTroduction;
    }

    public String getRegion() {
        return Region;
    }

    public String getAbbreviation() {
        return Abbreviation;
    }

    public String getRummeryName() {
        return RummeryName;
    }

    public String getHotelLogo() {
        return HotelLogo;
    }

    public String getHotelImgs() {
        return HotelImgs;
    }

    public String getContactName() {
        return ContactName;
    }

    public String getContactPhone() {
        return ContactPhone;
    }

    public String getAddress() {
        return Address;
    }

    public String getLowestPrice() {
        return LowestPrice;
    }

    public String getIsStatus() {
        return IsStatus;
    }
}
