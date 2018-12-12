package com.dikai.chenghunjiclient.bean;

/**
 * Created by Lucio on 2017/9/26.
 */

public class BeanDriverDelPlan {
    private String DriverID;
    private String ScheduleID;

    public BeanDriverDelPlan(String driverID, String scheduleID) {
        DriverID = driverID;
        ScheduleID = scheduleID;
    }
}
