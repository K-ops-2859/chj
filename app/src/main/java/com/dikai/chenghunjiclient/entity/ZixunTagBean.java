package com.dikai.chenghunjiclient.entity;

/**
 * Created by Lucio on 2018/1/16.
 */

public class ZixunTagBean {
    private String WeddingInformationID;
    private String Title;
    private String CreateTime;

    public ZixunTagBean(String weddingInformationID, String title, String createTime) {
        WeddingInformationID = weddingInformationID;
        Title = title;
        CreateTime = createTime;
    }

    public String getWeddingInformationID() {
        return WeddingInformationID;
    }

    public String getTitle() {
        return Title;
    }

    public String getCreateTime() {
        return CreateTime;
    }
}
