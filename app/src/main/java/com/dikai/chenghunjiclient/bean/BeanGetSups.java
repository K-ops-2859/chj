package com.dikai.chenghunjiclient.bean;

/**
 * Created by Lucio on 2018/8/10.
 */

public class BeanGetSups {
    private String AreaID;
    private String Identity;
    private String PageIndex;
    private String PageCount;
    private String Name;

    public BeanGetSups(String areaID, String identity, String pageIndex, String pageCount, String name) {
        AreaID = areaID;
        Identity = identity;
        PageIndex = pageIndex;
        PageCount = pageCount;
        Name = name;
    }
}
