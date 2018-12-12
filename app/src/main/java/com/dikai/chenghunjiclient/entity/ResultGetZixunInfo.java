package com.dikai.chenghunjiclient.entity;

/**
 * Created by Lucio on 2018/1/22.
 */

public class ResultGetZixunInfo {
    private ResultCode Message;
    private String InformationArticleID;
    private String WeddingInformationID;
    private String Title;
    private String ShowImg;
    private String Content;
    private String TextContent;
    private String Imgs;
    private String Videos;
    private String CreateTime;

    public String getTextContent() {
        return TextContent;
    }

    public ResultCode getMessage() {
        return Message;
    }

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

    public String getImgs() {
        return Imgs;
    }

    public String getVideos() {
        return Videos;
    }

    public String getCreateTime() {
        return CreateTime;
    }
}
