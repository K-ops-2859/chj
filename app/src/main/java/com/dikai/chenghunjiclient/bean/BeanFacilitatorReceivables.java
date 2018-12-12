package com.dikai.chenghunjiclient.bean;

/**
 * Created by cmk03 on 2018/10/9.
 */

public class BeanFacilitatorReceivables {
    private String FacilitatorId;
    private String DistributionFacilitatorId;
    private int Money;
    private String Phone;
    private String UserName;
    private String WeddingDate;
    private String Meno;
    private String SinglePersonUserId;

    public BeanFacilitatorReceivables(String facilitatorId, String distributionFacilitatorId, int money, String phone, String userName, String weddingDate, String meno, String singlePersonUserId) {
        FacilitatorId = facilitatorId;
        DistributionFacilitatorId = distributionFacilitatorId;
        Money = money;
        Phone = phone;
        UserName = userName;
        WeddingDate = weddingDate;
        Meno = meno;
        SinglePersonUserId = singlePersonUserId;
    }
}
