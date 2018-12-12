package com.dikai.chenghunjiclient.entity;

/**
 * Created by Lucio on 2018/6/27.
 */

public class ResultGetAudit {
    private ResultCode Message;
    private int Status;
    private String Website;
    private String ShareUrl;
    private String ShareTitle;
    private String ShareDescribe;
    private String ShareImg;

    public ResultCode getMessage() {
        return Message;
    }

    public int getStatus() {
        return Status;
    }

    public String getWebsite() {
        return Website;
    }

    public String getShareUrl() {
        return ShareUrl;
    }

    public String getShareTitle() {
        return ShareTitle;
    }

    public String getShareDescribe() {
        return ShareDescribe;
    }

    public String getShareImg() {
        return ShareImg;
    }
}
