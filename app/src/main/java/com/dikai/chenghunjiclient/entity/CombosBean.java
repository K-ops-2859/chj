package com.dikai.chenghunjiclient.entity;

import java.io.Serializable;

/**
 * Created by Lucio on 2018/6/14.
 */

public class CombosBean implements Serializable{
    private String Id;
    private String Name;
    private String OriginalPrice;
    private String PresentPrice;
    private String Label;
    private String CoverMap;
    private String ShelfType;
    private String BriefIntroduction;
    private String CostPrice;
    private String HotelImage;
    private String Time;

    public String getId() {
        return Id;
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

    public String getLabel() {
        return Label;
    }

    public String getCoverMap() {
        return CoverMap;
    }

    public String getShelfType() {
        return ShelfType;
    }

    public String getBriefIntroduction() {
        return BriefIntroduction;
    }

    public String getCostPrice() {
        return CostPrice;
    }

    public String getHotelImage() {
        return HotelImage;
    }

    public String getTime() {
        return Time;
    }
}
