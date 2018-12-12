package com.dikai.chenghunjiclient.bean;

/**
 * Created by Lucio on 2017/9/20.
 */

public class BeanFire {
    private String RelaID;
    private String TeamType;
    private String RejectedWhy;
    private String SupplierID;

    public BeanFire(String relaID, String teamType, String rejectedWhy, String supplierID) {
        RelaID = relaID;
        TeamType = teamType;
        RejectedWhy = rejectedWhy;
        SupplierID = supplierID;
    }
}
