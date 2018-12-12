package com.dikai.chenghunjiclient.entity;

import java.io.Serializable;

/**
 * Created by Lucio on 2018/5/2.
 */

public class CartBean implements Serializable{
    private String ShoppingCartId;
    private String TypeId;
    private String CommodityId;
    private String PlaceOriginId;
    private String CommodityName;
    private String PlaceOrigin;
    private String CategoryGoodsName;
    private String PlaceOriginName;
    private int Count;
    private String Quota;
    private String BriefIntroduction;
    private boolean isSelected = true;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public void setCount(int count) {
        Count = count;
    }

    public String getShoppingCartId() {
        return ShoppingCartId;
    }

    public String getTypeId() {
        return TypeId;
    }

    public String getCommodityId() {
        return CommodityId;
    }

    public String getPlaceOriginId() {
        return PlaceOriginId;
    }

    public String getCommodityName() {
        return CommodityName;
    }

    public String getPlaceOrigin() {
        return PlaceOrigin;
    }

    public String getCategoryGoodsName() {
        return CategoryGoodsName;
    }

    public String getPlaceOriginName() {
        return PlaceOriginName;
    }

    public int getCount() {
        return Count;
    }

    public String getQuota() {
        return Quota;
    }

    public String getBriefIntroduction() {
        return BriefIntroduction;
    }
}
