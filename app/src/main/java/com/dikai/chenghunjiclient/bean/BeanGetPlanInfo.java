package com.dikai.chenghunjiclient.bean;

/**
 * Created by Lucio on 2017/9/21.
 */

public class BeanGetPlanInfo {
    private String ObjectType;
    private String ObjectID;
    private String PageIndex;
    private String PageCount;

    public BeanGetPlanInfo(String objectType, String objectID, String pageIndex, String pageCount) {
        ObjectType = objectType;
        ObjectID = objectID;
        PageIndex = pageIndex;
        PageCount = pageCount;
    }
}
