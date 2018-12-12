package com.dikai.chenghunjiclient.entity;

/**
 * Created by Lucio on 2018/7/6.
 */

public class ResultBuyCaigoujie {
    private ResultCode Message;
    private String TransNumber;
    private String PayAmount;

    public ResultCode getMessage() {
        return Message;
    }

    public String getTransNumber() {
        return TransNumber;
    }

    public String getPayAmount() {
        return PayAmount;
    }
}
