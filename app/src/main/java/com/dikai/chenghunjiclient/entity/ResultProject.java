package com.dikai.chenghunjiclient.entity;

import java.io.Serializable;

/**
 * Created by Lucio on 2017/9/17.
 */

public class ResultProject implements Serializable{
    private ResultCode Message;
    private String CaseID;
    private String CoverMap;
    private String LogTitle;
    private String LogContent;
    private String Imgs;
    private String VIDeos;
    private String CreateTime;

    public ResultCode getMessage() {
        return Message;
    }

    public String getCaseID() {
        return CaseID;
    }

    public String getCoverMap() {
        return CoverMap;
    }

    public String getLogTitle() {
        return LogTitle;
    }

    public String getLogContent() {
        return LogContent;
    }

    public String getImgs() {
        return Imgs;
    }

    public String getVIDeos() {
        return VIDeos;
    }

    public String getCreateTime() {
        return CreateTime;
    }
}
