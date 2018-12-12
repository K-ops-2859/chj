package com.dikai.chenghunjiclient.bean;

/**
 * Created by Lucio on 2018/8/9.
 */

public class BeanWXRegister {
    private String OpenId;
    private String Phone;
    private String Identity;
    private String PhoneCode;
    private String Token;
    private String Wedding;
    private String AreaId;

    public BeanWXRegister(String openId, String phone, String identity, String phoneCode, String token, String wedding, String areaId) {
        OpenId = openId;
        Phone = phone;
        Identity = identity;
        PhoneCode = phoneCode;
        Token = token;
        Wedding = wedding;
        AreaId = areaId;
    }
}
