package com.dikai.chenghunjiclient.bean;

/**
 * Created by Lucio on 2017/9/22.
 */

public class BeanAddMark {
    private String SupplierID;
    private String ScheduleID;
    private String Remark;

    public BeanAddMark(String supplierID, String scheduleID, String remark) {
        SupplierID = supplierID;
        ScheduleID = scheduleID;
        Remark = remark;
    }
}
