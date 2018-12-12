package com.dikai.chenghunjiclient.entity;

/**
 * Created by Lucio on 2018/8/30.
 */

public class ResultGetAdInfo {
    private ResultCode Message;
    private String Id;
    private String FacilitatorId;
    private String CoverMap;
    private String Details;
    private String Createtime;
    private String ShareTitle;
    private String ShareDescribe;

    public String getShareTitle() {
        return ShareTitle;
    }

    public String getShareDescribe() {
        return ShareDescribe;
    }

    public ResultCode getMessage() {
        return Message;
    }

    public String getId() {
        return Id;
    }

    public String getFacilitatorId() {
        return FacilitatorId;
    }

    public String getCoverMap() {
        return CoverMap;
    }

    public String getDetails() {
        return Details;
    }

    public String getCreatetime() {
        return Createtime;
    }
}
