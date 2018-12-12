package com.dikai.chenghunjiclient.entity;

/**
 * Created by Lucio on 2017/9/17.
 */

public class ResultGetCarInfo {
    private ResultCode Message;
    private String BrandName;
    private String Name;
    private String CarImg;
    private String Color;

    public ResultCode getMessage() {
        return Message;
    }

    public String getBrandName() {
        return BrandName;
    }

    public String getName() {
        return Name;
    }

    public String getCarImg() {
        return CarImg;
    }

    public String getColor() {
        return Color;
    }
}
