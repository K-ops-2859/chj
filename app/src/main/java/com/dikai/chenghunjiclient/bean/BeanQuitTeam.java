package com.dikai.chenghunjiclient.bean;

/**
 * Created by Lucio on 2017/9/20.
 */

public class BeanQuitTeam {
    private String UserID;
    private String SupplierID;
    private String Start;

    public BeanQuitTeam(String userID, String supplierID, String start) {
        UserID = userID;
        SupplierID = supplierID;
        Start = start;
    }
}
