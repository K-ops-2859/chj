package com.dikai.chenghunjiclient.bean;

/**
 * Created by cmk03 on 2017/11/17.
 */

public class GetPrizeBean {
    private String ObjectTypes;
    private String ObjectID;

    public GetPrizeBean(String objectTypes, String objectID) {
        ObjectTypes = objectTypes;
        ObjectID = objectID;
    }

    public String getObjectID() {
        return ObjectID;
    }

    public String getObjectTypes() {
        return ObjectTypes;
    }

    public void setObjectID(String objectID) {
        ObjectID = objectID;
    }

    public void setObjectTypes(String objectTypes) {
        ObjectTypes = objectTypes;
    }
}
