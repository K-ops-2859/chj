package com.dikai.chenghunjiclient.entity;

import java.io.Serializable;

/**
 * Created by Lucio on 2018/10/26.
 */

public class JiedanRenBean implements Serializable{
    private String UserId;
    private String Name;
    private String Phone;

    public JiedanRenBean() {
    }

    public JiedanRenBean(String userId, String name, String phone) {
        UserId = userId;
        Name = name;
        Phone = phone;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }
}
