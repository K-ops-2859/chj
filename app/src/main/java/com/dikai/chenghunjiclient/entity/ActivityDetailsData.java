package com.dikai.chenghunjiclient.entity;

/**
 * Created by cmk03 on 2017/11/18.
 */

public class ActivityDetailsData {

    private ResultCode Message;
    private int InvitedNumber;
    private int ActivityID;
    private String Title;
    private String Content;
    private String StartTime;
    private String EndTime;
    private String[] Rule;
    private String[] Grade;
    private String CreateTime;
    private int ObjectTypes;


    public ResultCode getMessage() {
        return Message;
    }

    public int getInvitedNumber() {
        return InvitedNumber;
    }

    public int getActivityID() {
        return ActivityID;
    }

    public String getTitle() {
        return Title;
    }

    public String getContent() {
        return Content;
    }

    public String getStartTime() {
        return StartTime;
    }

    public String getEndTime() {
        return EndTime;
    }

    public String[] getRule() {
        return Rule;
    }

    public String[] getGrade() {
        return Grade;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public int getObjectTypes() {
        return ObjectTypes;
    }
}
