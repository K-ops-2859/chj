package com.dikai.chenghunjiclient.entity;

/**
 * Created by Lucio on 2018/4/4.
 */

public class MyPrizeBean {
    private String Id;
    private String Name;
    private String Img;
    private int Type;
    private int IsUse;

    public String getId() {
        return Id;
    }

    public String getName() {
        return Name;
    }

    public String getImg() {
        return Img;
    }

    public int getType() {
        return Type;
    }

    public int getIsUse() {
        return IsUse;
    }

    public void setIsUse(int isUse) {
        IsUse = isUse;
    }
}
