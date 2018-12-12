package com.dikai.chenghunjiclient.entity;

/**
 * Created by Lucio on 2018/6/14.
 */

public class ResultGetComboInfo {
    private ResultCode Message;
    private String Name;
    private String OriginalPrice;
    private String PresentPrice;
    private String CostPrice;
    private String Label;
    private String BriefIntroduction;
    private String DetailedList;
    private String CoverMap;
    private String ShelfType;
    private String HotelImage;

    public ResultCode getMessage() {
        return Message;
    }

    public String getName() {
        return Name;
    }

    public String getOriginalPrice() {
        return OriginalPrice;
    }

    public String getPresentPrice() {
        return PresentPrice;
    }

    public String getCostPrice() {
        return CostPrice;
    }

    public String getLabel() {
        return Label;
    }

    public String getBriefIntroduction() {
        return BriefIntroduction;
    }

    public String getDetailedList() {
        return DetailedList;
    }

    public String getCoverMap() {
        return CoverMap;
    }

    public String getShelfType() {
        return ShelfType;
    }

    public String getHotelImage() {
        return HotelImage;
    }
}
