package com.dikai.chenghunjiclient.bean;

/**
 * Created by Lucio on 2017/9/22.
 */

public class BeanAssignCar {
    private String SupplierID;
    private String DriverID;
    private String SupplierOrderID;

    public BeanAssignCar(String supplierID, String driverID, String supplierOrderID) {
        SupplierID = supplierID;
        DriverID = driverID;
        SupplierOrderID = supplierOrderID;
    }
}
