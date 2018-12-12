package com.dikai.chenghunjiclient.entity;

/**
 * Created by Lucio on 2018/11/13.
 */

public class AccountBean {
    private String userId;
    private String userName;
    private String userPhone;
    private String userLogo;
    private boolean isADD;

    public AccountBean(String userID, String userName, String userPhone, String userLogo) {
        this.userId = userID;
        this.userName = userName;
        this.userPhone = userPhone;
        this.userLogo = userLogo;
    }

    public AccountBean(boolean isADD) {
        this.isADD = isADD;
    }

    public boolean isADD() {
        return isADD;
    }

    public void setADD(boolean ADD) {
        isADD = ADD;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserLogo() {
        return userLogo;
    }

    public void setUserLogo(String userLogo) {
        this.userLogo = userLogo;
    }
}
