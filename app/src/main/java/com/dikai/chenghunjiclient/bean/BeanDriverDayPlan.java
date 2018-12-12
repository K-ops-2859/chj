package com.dikai.chenghunjiclient.bean;

/**
 * Created by Lucio on 2017/9/26.
 */

public class BeanDriverDayPlan {

    private String DriverID;
    private String BeginTime;
    private String EndTime;

    public BeanDriverDayPlan(String driverID, String beginTime, String endTime) {
        DriverID = driverID;
        BeginTime = beginTime;
        EndTime = endTime;
    }
}
