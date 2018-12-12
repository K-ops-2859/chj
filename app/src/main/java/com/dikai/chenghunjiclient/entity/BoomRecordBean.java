package com.dikai.chenghunjiclient.entity;

/**
 * Created by Lucio on 2018/4/2.
 */

public class BoomRecordBean {
    private String PopcornReceiveID;
    private String UserId;
    private String ShopId;
    private String ShopAdress;
    private int Glass;
    private int AwardType;
    private String CreateTime;

    public String getShopAdress() {
        return ShopAdress;
    }

    public String getPopcornReceiveID() {
        return PopcornReceiveID;
    }

    public String getUserId() {
        return UserId;
    }

    public String getShopId() {
        return ShopId;
    }

    public int getGlass() {
        return Glass;
    }

    public int getAwardType() {
        return AwardType;
    }

    public String getCreateTime() {
        return CreateTime;
    }
}
