package com.dikai.chenghunjiclient.entity;

import java.math.BigDecimal;

/**
 * Created by Lucio on 2018/4/26.
 */

public class GoodsItemBean {
    private String TypeId;
    private String TypeName;
    private String CommodityId;
    private String CommodityName;
    private String PlaceOrigin;
    private String Quota;
    private String CoverMap;

    public void setTypeName(String typeName) {
        TypeName = typeName;
    }

    public String getTypeName() {
        return TypeName;
    }

    public String getTypeId() {
        return TypeId;
    }

    public String getCommodityId() {
        return CommodityId;
    }

    public String getCommodityName() {
        return CommodityName;
    }

    public String getPlaceOrigin() {
        return PlaceOrigin;
    }

    public String getQuota() {
        return Quota;
    }

    public String getCoverMap() {
        return CoverMap;
    }
}
