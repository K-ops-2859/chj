package com.dikai.chenghunjiclient.bean;

/**
 * Created by Lucio on 2017/9/19.
 */

public class BeanChangePhone {
    private String WornPhone;
    private String NewPhone;
    private String AuthCodeID;
    private String UserType;
    private String ObjectID;

    public BeanChangePhone(String wornPhone, String newPhone, String authCodeID,
                           String userType, String objectID) {
        WornPhone = wornPhone;
        NewPhone = newPhone;
        AuthCodeID = authCodeID;
        UserType = userType;
        ObjectID = objectID;
    }
}
