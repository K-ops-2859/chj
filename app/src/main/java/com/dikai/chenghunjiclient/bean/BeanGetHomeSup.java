package com.dikai.chenghunjiclient.bean;

/**
 * Created by Lucio on 2018/1/16.
 */

public class BeanGetHomeSup {
    private String OccupationCode;
    private String PageIndex;
    private String PageCount;
    private String OrderType;
    private String RegionId;
    private String WeddingDate;
    private String NameOrPhone;

    public BeanGetHomeSup(String occupationCode, String pageIndex, String pageCount,
                          String orderType, String regionId, String weddingDate, String nameOrPhone) {
        OccupationCode = occupationCode;
        PageIndex = pageIndex;
        PageCount = pageCount;
        OrderType = orderType;
        RegionId = regionId;
        WeddingDate = weddingDate;
        NameOrPhone = nameOrPhone;
    }
}
