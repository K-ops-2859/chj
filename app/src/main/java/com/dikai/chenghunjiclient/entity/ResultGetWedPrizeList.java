package com.dikai.chenghunjiclient.entity;

import java.util.List;

/**
 * Created by Lucio on 2018/5/2.
 */

public class ResultGetWedPrizeList {
    private ResultCode Message;
    private List<WedPrizeBean> Data;

    public ResultCode getMessage() {
        return Message;
    }

    public List<WedPrizeBean> getData() {
        return Data;
    }
}
