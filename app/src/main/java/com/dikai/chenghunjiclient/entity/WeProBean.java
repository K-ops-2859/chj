package com.dikai.chenghunjiclient.entity;

import java.io.Serializable;

/**
 * Created by Lucio on 2017/12/12.
 */

public class WeProBean implements Serializable{
    private String NewPeopleCustomID;
    private int Budget;
    private String WeddingDay;
    private String HotelName;
    private String HotelAddress;
    private String HallName;
    private String TableCount;
    private String RummeryImg;
    private String RummeryXls;
    private String InvitationCode;
    private int IsLEDScreen;
    private String SpecialVersion;

    public String getNewPeopleCustomID() {
        return NewPeopleCustomID;
    }

    public String getSpecialVersion() {
        return SpecialVersion;
    }

    public int getBudget() {
        return Budget;
    }

    public String getWeddingDay() {
        return WeddingDay;
    }

    public String getHotelName() {
        return HotelName;
    }

    public String getHotelAddress() {
        return HotelAddress;
    }

    public String getHallName() {
        return HallName;
    }

    public String getTableCount() {
        return TableCount;
    }

    public String getRummeryImg() {
        return RummeryImg;
    }

    public String getRummeryXls() {
        return RummeryXls;
    }

    public String getInvitationCode() {
        return InvitationCode;
    }

    public int getIsLEDScreen() {
        return IsLEDScreen;
    }
}
