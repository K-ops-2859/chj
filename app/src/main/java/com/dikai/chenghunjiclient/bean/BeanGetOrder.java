package com.dikai.chenghunjiclient.bean;

/**
 * Created by Lucio on 2017/9/17.
 */

public class BeanGetOrder {
    private String SupplierID;
    private String AnswerType;
    private String SettlementType;
    private String PageIndex;
    private String PageCount;

    public BeanGetOrder(String supplierID, String answerType, String settlementType, String pageIndex, String pageCount) {
        SupplierID = supplierID;
        AnswerType = answerType;
        SettlementType = settlementType;
        PageIndex = pageIndex;
        PageCount = pageCount;
    }
}
