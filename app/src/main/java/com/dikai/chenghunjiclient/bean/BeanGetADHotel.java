package com.dikai.chenghunjiclient.bean;

/**
 * Created by Lucio on 2018/10/25.
 */

public class BeanGetADHotel {
    private String AreaID;
    private String PageIndex;
    private String PageCount;
    private String Name;

    public BeanGetADHotel(String areaID, String pageIndex, String pageCount, String name) {
        AreaID = areaID;
        PageIndex = pageIndex;
        PageCount = pageCount;
        Name = name;
    }
}
