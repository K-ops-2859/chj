package com.dikai.chenghunjiclient.bean;

/**
 * Created by Lucio on 2017/9/22.
 */

public class BeanAssignCarList {
    private String SupplierID;
    private String CarID;
    private String PhoneOrName;
    private String DriverTime;

    public BeanAssignCarList(String supplierID, String carID, String phoneOrName, String driverTime) {
        SupplierID = supplierID;
        CarID = carID;
        PhoneOrName = phoneOrName;
        DriverTime = driverTime;
    }
}
