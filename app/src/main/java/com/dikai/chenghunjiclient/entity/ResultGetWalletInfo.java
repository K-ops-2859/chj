package com.dikai.chenghunjiclient.entity;

import java.util.List;

/**
 * Created by Lucio on 2018/9/28.
 */

public class ResultGetWalletInfo {
    private ResultCode Message;
    private int TotalCount;
    private String Balance;
    private int AccountNumber;
    private List<WalletInfoBean> Data;

    public ResultCode getMessage() {
        return Message;
    }

    public int getTotalCount() {
        return TotalCount;
    }

    public String getBalance() {
        return Balance;
    }

    public int getAccountNumber() {
        return AccountNumber;
    }

    public List<WalletInfoBean> getData() {
        return Data;
    }
}
