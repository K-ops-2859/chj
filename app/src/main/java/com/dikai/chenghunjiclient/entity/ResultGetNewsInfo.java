package com.dikai.chenghunjiclient.entity;

import java.io.Serializable;

/**
 * Created by Lucio on 2017/12/12.
 */

public class ResultGetNewsInfo implements Serializable{
    private ResultCode Message;
    private String Name;
    private String DateOfBirth;
    private String PlaceOfOrigin;
    private String Constellation;
    private String Occupation;
    private String Phone;
    private String QQNumber;
    private String WechatNumber;

    public ResultCode getMessage() {
        return Message;
    }

    public String getName() {
        return Name;
    }

    public String getDateOfBirth() {
        return DateOfBirth;
    }

    public String getPlaceOfOrigin() {
        return PlaceOfOrigin;
    }

    public String getConstellation() {
        return Constellation;
    }

    public String getOccupation() {
        return Occupation;
    }

    public String getPhone() {
        return Phone;
    }

    public String getQQNumber() {
        return QQNumber;
    }

    public String getWechatNumber() {
        return WechatNumber;
    }
}
