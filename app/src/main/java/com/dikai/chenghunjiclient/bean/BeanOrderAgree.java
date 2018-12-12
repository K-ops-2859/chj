package com.dikai.chenghunjiclient.bean;

/**
 * Created by Lucio on 2017/9/21.
 */

public class BeanOrderAgree {
    private String SupplierID;
    private String SupplierOrderID;
    private String ReviewType;

    public BeanOrderAgree(String supplierID, String supplierOrderID, String reviewType) {
        SupplierID = supplierID;
        SupplierOrderID = supplierOrderID;
        ReviewType = reviewType;
    }
}
