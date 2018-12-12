package com.dikai.chenghunjiclient.bean;

/**
 * Created by Lucio on 2017/9/20.
 */

public class BeanInviteCar {
    private String UserID;
    private String SupplierID;
    private String Type;

    public BeanInviteCar(String userID, String supplierID, String type) {
        UserID = userID;
        SupplierID = supplierID;
        Type = type;
    }
}
