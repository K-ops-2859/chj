package com.dikai.chenghunjiclient.bean;

/**
 * Created by Lucio on 2017/9/6.
 */

public class BeanInspectCode {
    private String UserType;
    private String Phone;
    private String Type;
    private String SMSCode;

    public BeanInspectCode(String userType, String phone, String type, String SMSCode) {
        UserType = userType;
        Phone = phone;
        Type = type;
        this.SMSCode = SMSCode;
    }
}
