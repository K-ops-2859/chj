package com.dikai.chenghunjiclient.entity;

/**
 * Created by Lucio on 2018/11/2.
 */

public class SupKeYuanBean {
    private String Identity;
    private String Name;
    private String Phone;
    private String WeddingTime;
    private String Meno;
    private boolean remarkOpen = false;

    public boolean isRemarkOpen() {
        return remarkOpen;
    }

    public void setRemarkOpen(boolean remarkOpen) {
        this.remarkOpen = remarkOpen;
    }

    public String getMeno() {
        return Meno;
    }

    public String getIdentity() {
        return Identity;
    }

    public String getName() {
        return Name;
    }

    public String getPhone() {
        return Phone;
    }

    public String getWeddingTime() {
        return WeddingTime;
    }
}
