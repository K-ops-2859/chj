package com.dikai.chenghunjiclient.bean;

/**
 * Created by Lucio on 2017/9/12.
 */

public class BeanGetSupList {
    private String SupplierID;
    private String UserID;
    private String Type;

    public BeanGetSupList(String supplierID, String userID, String type) {
        SupplierID = supplierID;
        UserID = userID;
        Type = type;
    }
}
