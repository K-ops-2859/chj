package com.dikai.chenghunjiclient.bean;

/**
 * Created by Lucio on 2017/9/18.
 */

public class BeanClearOrder {
    private String SupplierID;
    private String SupplierOrderID;

    public BeanClearOrder(String supplierID, String supplierOrderID) {
        SupplierID = supplierID;
        SupplierOrderID = supplierOrderID;
    }
}
