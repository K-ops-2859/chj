package com.dikai.chenghunjiclient.entity;

import java.util.List;

/**
 * Created by Lucio on 2018/1/16.
 */

public class ResultGetCoupon {
    private ResultCode Message;
    private int TotalCount;
    private List<CouponBean> Data;

    public ResultCode getMessage() {
        return Message;
    }

    public int getTotalCount() {
        return TotalCount;
    }

    public List<CouponBean> getData() {
        return Data;
    }
}
