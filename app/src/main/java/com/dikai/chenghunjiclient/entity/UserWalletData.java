package com.dikai.chenghunjiclient.entity;

/**
 * Created by cmk03 on 2018/3/1.
 */

public class UserWalletData {
    private ResultCode Message;
    private String Balance;
    private int CouponCount;

    public ResultCode getMessage() {
        return Message;
    }

    public String getBalance() {
        return Balance;
    }

    public int getCouponCount() {
        return CouponCount;
    }
}
