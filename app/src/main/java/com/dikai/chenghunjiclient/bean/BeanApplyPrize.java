package com.dikai.chenghunjiclient.bean;

/**
 * Created by Lucio on 2018/5/2.
 */

public class BeanApplyPrize {
    private String UserId;
    private String Name;
    private String Phone;
    private String Wedding;
    private String AmountWedding;
    private String WeddingCompanyName;
    private String HotelId;

    public BeanApplyPrize(String userId, String name, String phone, String wedding, String amountWedding, String weddingCompanyName, String hotelId) {
        UserId = userId;
        Name = name;
        Phone = phone;
        Wedding = wedding;
        AmountWedding = amountWedding;
        WeddingCompanyName = weddingCompanyName;
        HotelId = hotelId;
    }
}
