package com.dikai.chenghunjiclient.bean;

/**
 * Created by Lucio on 2017/9/21.
 */

public class BeanGetCalendar {
    private String SupplierID;
    private String SupplierType;
    private String SettlementType;
    private String BeginTime;
    private String EndTime;
    private String PageIndex;
    private String PageCount;

    public BeanGetCalendar(String supplierID, String supplierType, String settlementType,
                           String beginTime, String endTime, String pageIndex, String pageCount) {
        SupplierID = supplierID;
        SupplierType = supplierType;
        SettlementType = settlementType;
        BeginTime = beginTime;
        EndTime = endTime;
        PageIndex = pageIndex;
        PageCount = pageCount;
    }
}
