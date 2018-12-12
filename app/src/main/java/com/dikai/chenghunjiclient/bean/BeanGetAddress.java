package com.dikai.chenghunjiclient.bean;

/**
 * Created by cmk03 on 2017/11/18.
 */

public class BeanGetAddress {
    private String PageIndex;
    private String PageCount;
    private String ObjectTypes;
    private String ObjectID;

    public BeanGetAddress(String pageIndex, String pageCount, String objectTypes, String objectID) {
        PageIndex = pageIndex;
        PageCount = pageCount;
        ObjectTypes = objectTypes;
        ObjectID = objectID;
    }
}
