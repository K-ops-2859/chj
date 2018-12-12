package com.dikai.chenghunjiclient.entity;

import java.util.List;

/**
 * Created by Lucio on 2018/9/20.
 */

public class ResultGetNewOrder {
    private ResultCode Message;
    private int TotalCount;
    private List<NewOrderBean> Data;

    public ResultCode getMessage() {
        return Message;
    }

    public int getTotalCount() {
        return TotalCount;
    }

    public List<NewOrderBean> getData() {
        return Data;
    }
}
