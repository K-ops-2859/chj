package com.dikai.chenghunjiclient.bean;

/**
 * Created by Lucio on 2017/9/20.
 */

public class BeanGetMember {
    private String SupplierID;
    private String TrueName;
    private String ModelID;
    private String Type;
    private String ExamineStatus;

    public BeanGetMember(String supplierID, String trueName, String modelID, String type, String examineStatus) {
        SupplierID = supplierID;
        TrueName = trueName;
        ModelID = modelID;
        Type = type;
        ExamineStatus = examineStatus;
    }
}
