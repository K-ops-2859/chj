package com.dikai.chenghunjiclient.bean;

/**
 * Created by Lucio on 2017/9/21.
 */

public class BeanDelPlan {
    private String SupplierID;
    private String ScheduleID;

    public BeanDelPlan(String supplierID, String scheduleID) {
        SupplierID = supplierID;
        ScheduleID = scheduleID;
    }
}
