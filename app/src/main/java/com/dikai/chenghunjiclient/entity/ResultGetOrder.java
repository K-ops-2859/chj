package com.dikai.chenghunjiclient.entity;

import java.util.List;

/**
 * Created by Lucio on 2017/9/17.
 */

public class ResultGetOrder {
    private ResultCode Message;
    private int TotalCount;
    private List<OrderBean> Data;

    public ResultCode getMessage() {
        return Message;
    }

    public int getTotalCount() {
        return TotalCount;
    }

    public List<OrderBean> getData() {
        return Data;
    }
}
