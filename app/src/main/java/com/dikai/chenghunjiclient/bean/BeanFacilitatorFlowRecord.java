package com.dikai.chenghunjiclient.bean;

/**
 * Created by cmk03 on 2018/10/10.
 */

public class BeanFacilitatorFlowRecord {
    private String FacilitatorId;
    private int PaymentType;
    private int Money;
    private String Phone;
    private String UserName;
    private String WeddingDate;
    private String Meno;
    private String SinglePersonUserId;

    public BeanFacilitatorFlowRecord(String facilitatorId, int paymentType, int money, String phone, String userName, String weddingDate, String meno, String singlePersonUserId) {
        FacilitatorId = facilitatorId;
        PaymentType = paymentType;
        Money = money;
        Phone = phone;
        UserName = userName;
        WeddingDate = weddingDate;
        Meno = meno;
        SinglePersonUserId = singlePersonUserId;
    }
}
