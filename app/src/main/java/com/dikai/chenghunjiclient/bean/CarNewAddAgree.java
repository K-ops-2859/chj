package com.dikai.chenghunjiclient.bean;

/**
 * Created by Lucio on 2017/9/22.
 */

public class CarNewAddAgree {
    private String UserID;
    private String ScheduleID;
    private String StatuType;

    public CarNewAddAgree(String userID, String scheduleID, String statuType) {
        UserID = userID;
        ScheduleID = scheduleID;
        StatuType = statuType;
    }
}
