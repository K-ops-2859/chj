package com.dikai.chenghunjiclient.bean;

/**
 * Created by Lucio on 2018/5/30.
 */

public class BeanBook {
    private String UserId;
    private String Phone;
    private String SupplierId;

    public BeanBook(String userId, String phone, String supplierId) {
        UserId = userId;
        Phone = phone;
        SupplierId = supplierId;
    }
}
