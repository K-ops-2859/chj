package com.dikai.chenghunjiclient.entity;

import java.io.Serializable;

/**
 * Created by Lucio on 2018/1/16.
 */

public class ZixunBean implements Serializable{
    private String InformationArticleID;
    private String WeddingInformationID;
    private String Title;
    private String ShowImg;
    private String Content;
    private String TextContent;
    private String Imgs;
    private String Videos;
    private String WeddingInformationTitle;
    private String CreateTime;

    public String getInformationArticleID() {
        return InformationArticleID;
    }

    public String getWeddingInformationID() {
        return WeddingInformationID;
    }

    public String getTitle() {
        return Title;
    }

    public String getShowImg() {
        return ShowImg;
    }

    public String getContent() {
        return Content;
    }

    public String getTextContent() {
        return TextContent;
    }

    public String getImgs() {
        return Imgs;
    }

    public String getVideos() {
        return Videos;
    }

    public String getWeddingInformationTitle() {
        return WeddingInformationTitle;
    }

    public String getCreateTime() {
        return CreateTime;
    }
}
